package tech.seltzer.server.util;

import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The listener for incoming connections to the server.
 */
public abstract class AbstractServerSocketListener implements Runnable {
	private static Logger logger = LogManager.getLogger(AbstractServerSocketListener.class);

	protected static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 3000, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());

	protected int port;
	protected int connections;
	protected boolean sslEnabled;
	protected boolean shutdown = false;
	
	/**
	 * Build a new listener.
	 * @param port - the port the listener should bind to
	 * @param backlog - the connection backlog count
	 */
	public AbstractServerSocketListener(int port, int backlog) {
		logger.info(Messages.getString("ServerSocketListener.creating"));
		String message = Messages.getString("ServerSocketListener.port");
		message = MessageFormat.format(message, port, backlog);
		logger.info(message);

		this.port = port;
		this.connections = backlog;
	}
	
	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}
}
