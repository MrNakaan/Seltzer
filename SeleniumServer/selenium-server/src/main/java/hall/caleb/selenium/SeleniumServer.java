package hall.caleb.selenium;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeleniumServer {
	private static Logger logger = LogManager.getLogger(SeleniumServer.class);
	
	private static ServerSocketListener listener;
	private static SeleniumSessionCleaner cleaner;
	private static Thread listenerThread;
	private static Thread cleanerThread;
	
	public static void main(String[] args) {
		logger.info("Starting Selenium server...");
		
		try {
			configureBase();
		} catch (FileNotFoundException e) {
			logger.fatal(e);
			logger.fatal("Exception occurred during configuration, server exiting!");
			return;
		}
		
		listener = new ServerSocketListener(39948, 1);
		listenerThread = new Thread(listener);
		listenerThread.start();
		logger.info("Connection listener started.");
		
		// Removed pending testing of the cleaning system
//		cleaner = new SeleniumSessionCleaner();
//		cleanerThread = new Thread(cleaner);
//		cleanerThread.start();
//		logger.info("Session cleaner started.");
		
		logger.info("Selenium server startup complete.");
		
		try {
			listenerThread.join();
			cleanerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void configureBase() throws FileNotFoundException {
		logger.info("Configuring server..");
		
		logger.debug("Configuring web driver location.");
		logger.debug("Web driver should exist at \"web_drivers/chromedriver.exe\".");
		
		if (new File("web_drivers/chromedriver.exe").exists()) {
			System.setProperty("webdriver.chrome.driver", "web_drivers/chromedriver.exe");
		} else {
			throw new FileNotFoundException("Web driver not found!");
		}
		
		logger.info("Server configured.");
	}
}