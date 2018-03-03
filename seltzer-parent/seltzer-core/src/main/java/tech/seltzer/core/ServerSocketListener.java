package tech.seltzer.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.seltzer.server.util.AbstractServerSocketListener;

/**
 * The listener for incoming connections to the server.
 */
public class ServerSocketListener extends AbstractServerSocketListener implements Runnable {
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
		super(port, backlog);
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
				executor.execute(new CoreCommandHandlerThread(socket));
				logger.info(Messages.getString("ServerSocketListener.added"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
