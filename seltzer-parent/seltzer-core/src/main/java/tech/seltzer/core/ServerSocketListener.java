package tech.seltzer.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import tech.seltzer.core.processor.BaseProcessor;
import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.objects.SerializableCR;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * The listener for incoming connections to the server.
 */
public class ServerSocketListener implements Runnable {
	private static Logger logger = LogManager.getLogger(ServerSocketListener.class);

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 3000, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	private int port;
	private int connections;

	/**
	 * Build a new listener.
	 * @param port - the port the listener should bind to
	 * @param backlog - the connection backlog count
	 */
	public ServerSocketListener(int port, int backlog) {
		logger.info(Messages.getString("ServerSocketListener.creating"));
		String message = Messages.getString("ServerSocketListener.port");
		message = MessageFormat.format(message, port, backlog);
		logger.info(message);

		this.port = port;
		this.connections = backlog;
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port, connections)) {
			while (true) {
				Socket socket = serverSocket.accept();
				String message = Messages.getString("ServerSocketListener.accepted");
				message = MessageFormat.format(message, socket.getInetAddress(), socket.getPort());
				logger.info(message);
				logger.info(Messages.getString("ServerSocketListener.adding"));
				executor.execute(new CommandHandlerThread(socket));
				logger.info(Messages.getString("ServerSocketListener.added"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A class to handle commands including reading the raw JSON, deserializing it, sending it 
	 * to be processed, serializing the response, and sending it back to the caller.
	 */
	private static class CommandHandlerThread implements Runnable {
		private static Logger logger = LogManager.getLogger(CommandHandlerThread.class);

		private Socket socket;

		public CommandHandlerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				handleJson();
			} catch (IOException e) {
				logger.error(e);
			}
		}

		private void handleJson() throws IOException {
			String json = readJson(socket);

			System.out.println(Messages.getString("ServerSocketListener.received"));
			System.out.println("\t" + json);

			CommandData command;

			command = new Gson().fromJson(json, CommandData.class);
			command = new Gson().fromJson(json, command.getType().getCrClass());

			if (command.getType() == CommandType.CHAIN) {
				((SerializableCR) command).deserialize();
			}

			Response response = new Response();

			if (command.getType() == CommandType.START) {
				response = BaseProcessor.processCommand(null, command);
			} else if (command.getType() == CommandType.EXIT) {
				SeltzerSession.findSession(command.getId()).close();
				response.setSuccess(true);
			} else {
				response = SeltzerSession.findSession(command.getId()).executeCommand(command);
			}

			System.out.println(Messages.getString("ServerSocketListener.response"));
			if (response.getType() == ResponseType.CHAIN) {
				((SerializableCR) response).serialize();
			}
			System.out.println("\t" + new Gson().toJson(response, response.getType().getCrClass()));

			writeResponse(socket, response);

			socket.close();
		}

		/**
		 * Read the raw JSON from the socket.
		 * @param socket - the socket to read from
		 * @return The raw JSON
		 * @throws IOException thrown if there is an issue reading from the socket
		 */
		private String readJson(Socket socket) throws IOException {
			StringBuilder builder = new StringBuilder();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			while (!reader.ready()) {
				continue;
			}

			char[] buffer = new char[128];
			int bufferSize = 0;
			while (reader.ready()) {
				bufferSize = reader.read(buffer);
				builder.append(buffer, 0, bufferSize);
				Arrays.fill(buffer, '\0');
			}

			return builder.toString();
		}

		/**
		 * Send the response object back to the caller.
		 * @param socket - the socket to write to
		 * @param response - the response object to write
		 * @throws IOException thrown if there is an issue writing to the socket
		 */
		private void writeResponse(Socket socket, Response response) throws IOException {
			OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

			if (response.getType() == ResponseType.CHAIN) {
				((SerializableCR) response).serialize();
			}

			Class<? extends Response> responseClass;
			switch (response.getType()) {
			case CHAIN:
				responseClass = ChainResponse.class;
				break;
			case SINGLE_RESULT:
				responseClass = SingleResultResponse.class;
				break;
			case MULTI_RESULT:
				responseClass = MultiResultResponse.class;
				break;
			default:
				responseClass = Response.class;
				break;
			}

			writer.write(new Gson().toJson(response, responseClass));

			writer.flush();
		}
	}
}
