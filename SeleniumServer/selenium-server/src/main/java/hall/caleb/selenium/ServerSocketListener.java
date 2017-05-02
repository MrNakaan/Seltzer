package hall.caleb.selenium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import hall.caleb.selenium.enums.CommandType;
import hall.caleb.selenium.objects.command.ChainCommand;
import hall.caleb.selenium.objects.command.Command;
import hall.caleb.selenium.objects.command.FillFieldCommand;
import hall.caleb.selenium.objects.command.GoToCommand;
import hall.caleb.selenium.objects.command.MultiResultSelectorCommand;
import hall.caleb.selenium.objects.command.ReadAttributeCommand;
import hall.caleb.selenium.objects.command.SelectorCommand;
import hall.caleb.selenium.objects.response.ChainResponse;
import hall.caleb.selenium.objects.response.MultiResultResponse;
import hall.caleb.selenium.objects.response.Response;
import hall.caleb.selenium.objects.response.SingleResultResponse;

public class ServerSocketListener implements Runnable {
	private static Logger logger = LogManager.getLogger(ServerSocketListener.class);

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 3000, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	private int port;
	private int connections;

	public ServerSocketListener(int port, int backlog) {
		logger.info("Creating new ServerSocketListener.");
		logger.info("Using port " + port + " with a backlog of " + backlog + ".");

		this.port = port;
		this.connections = backlog;
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(port, connections)) {
			while (true) {
				Socket socket = serverSocket.accept();
				logger.info("Connection accepted from " + socket.getInetAddress() + ":" + socket.getPort() + ".");
				logger.info("Adding task to thread pool...");
				executor.execute(new CommandHandlerThread(socket));
				logger.info("Task added to the thread pool!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

			System.out.println("Command received:");
			System.out.println("\t" + json);

			Command baseCommand = new Gson().fromJson(json, Command.class);
			Class<? extends Command> commandClass;
			
			switch (baseCommand.getCommandType()) {
				case Start:
				case Exit:
				case Forward:
				case Back:
				case GetUrl:
					commandClass = Command.class;
					break;
				case GoTo:
					commandClass = GoToCommand.class;
					break;
				case Click:
				case Count:
				case FormSubmit:
				case Delete:
					commandClass = SelectorCommand.class;
					break;
				case FillField:
					commandClass = FillFieldCommand.class;
					break;
				case ReadText:
					commandClass = MultiResultSelectorCommand.class;
					break;
				case ReadAttribute:
					commandClass = ReadAttributeCommand.class;
					break;
				case Chain:
					commandClass = ChainCommand.class;
					break;
				default:
					commandClass = Command.class;
			}
			
			Command command = new Gson().fromJson(json, commandClass);
			
			Response response = new Response();

			if (command.getCommandType() == CommandType.Start) {
				response.setId(new SeleniumSession().getId());
				response.setSuccess(true);
			} else if (command.getCommandType() == CommandType.Exit) {
				SeleniumSession.findSession(command.getId()).close();
				response.setSuccess(true);
			} else {
				response = SeleniumSession.findSession(command.getId()).executeCommand(command);
			}

			System.out.println("Sending response:");
			System.out.println("\t" + new Gson().toJson(response, Response.class));
			writeResponse(socket, response);

			socket.close();
		}

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

		private void writeResponse(Socket socket, Response response) throws IOException {
			OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

			Class<? extends Response> responseClass;
			switch (response.getType()) {
				case Chain:
					responseClass = ChainResponse.class;
					break;
				case SingleResult:
					responseClass = SingleResultResponse.class;
					break;
				case MultiResult:
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
