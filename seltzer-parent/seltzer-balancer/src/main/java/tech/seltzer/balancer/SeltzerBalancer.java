package tech.seltzer.balancer;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SeltzerBalancer {
	private static Logger logger = LogManager.getLogger(SeltzerBalancer.class);
	private static Thread listenerThread;
	
	public static void main(String[] args) {
		logger.info(Messages.getString("SeltzerBalancer.starting")); 
		
		try {
			configureBase();
		} catch (IOException e) {
			logger.fatal(e);
			logger.fatal(Messages.getString("SeltzerBalancer.configException")); 
			return;
		}
		
		String portString = ConfigManager.getConfigValue("connection.port");
		int port = 39848;
		if (!StringUtils.isEmpty(portString)) {
			try {
				port = Integer.parseInt(portString);
				if (port <= 1024 || port > 65535) {
					throw new IllegalArgumentException();
				}
			} catch (NumberFormatException e) {
				logger.error("Config value '" + portString + "' is not a valid port number.");
			} catch (IllegalArgumentException e) {
				if (port < 0) {
					logger.error("Config value '" + port + "' is too low to be a valid port number.");
				} else if (port <= 1024) {
					logger.error("Config value '" + port + "' is too low, Seltzer will not run on well-known ports.");
				} else {
					logger.error("Config value '" + port + "' is too high to be a valid port number.");
				}
			}
		}
		listener = new ServerSocketListener(port, 1);
		listenerThread = new Thread(listener);
		listenerThread.start();
		logger.info(Messages.getString("SeltzerServer.listenerStarted"));
		
		try {
			listenerThread.join();
			cleanerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Configure Seltzer Balancer. Initially called during startup but must be called before any unit tests
	 * @throws IOException thrown if the internal configuration files cannot be found.
	 */
	public static void configureBase() throws IOException {
		logger.info(Messages.getString("SeltzerBalancer.configuring")); 
		
		ConfigManager.loadConfiguration();
		
		String repoPath = System.getProperty("seltzer.path");
		if (StringUtils.isEmpty(repoPath)) {
			repoPath = ConfigManager.getConfigValue("seltzer.path");
			if (StringUtils.isEmpty(repoPath)) {
				logger.warn(Messages.getString("SeltzerBalancer.pathNotFound"));
				repoPath = "~/";
			}
		}
		
		logger.info(Messages.getString("SeltzerServer.configured"));
	}
}
