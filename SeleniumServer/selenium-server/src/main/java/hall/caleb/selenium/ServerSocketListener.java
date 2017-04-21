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

import hall.caleb.selenium.enums.SeleniumCommandType;
import hall.caleb.selenium.objects.SeleniumCommand;
import hall.caleb.selenium.objects.SeleniumResponse;

public class ServerSocketListener implements Runnable {
	private static Logger logger = LogManager.getLogger(ServerSocketListener.class);
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	
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
				logger.info("Connection accepted from " + socket.getInetAddress() + ":" + socket.getPort() +".");
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

			SeleniumCommand command = new Gson().fromJson(json, SeleniumCommand.class);
			SeleniumResponse response = new SeleniumResponse();
			
			if (command.getCommandType() == SeleniumCommandType.Start) {
				response.setId(new SeleniumSession().getId());
				response.setSuccess(true);
			} else if (command.getCommandType() == SeleniumCommandType.Exit) {
				SeleniumSession.findSession(command.getId()).close();
				response.setSuccess(true);
			} else {
				response = SeleniumSession.findSession(command.getId()).executeCommand(command);
			}
			
			System.out.println("Sending response:");
			System.out.println("\t" + new Gson().toJson(response, SeleniumResponse.class));
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
		
		private void writeResponse(Socket socket, SeleniumResponse response) throws IOException {
			OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
			
			writer.write(new Gson().toJson(response, SeleniumResponse.class));
			
			writer.flush();
		}
	}
}
