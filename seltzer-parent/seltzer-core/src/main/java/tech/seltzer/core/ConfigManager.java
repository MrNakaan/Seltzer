package tech.seltzer.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Handles the internal and external configuration files.
 */
public class ConfigManager {
	private static String configFileName = "config.properties";
	private static Properties config = new Properties();
	
	/**
	 * Load the internal and external configuration files for Seltzer and merge them. 
	 * If a value is present in the external configuration file, then that version takes precedence.
	 * @throws IOException thrown if the external configuration file cannot be read or written
	 */
	public static void loadConfiguration() throws IOException {
		File externalConfigFile = new File("./" + configFileName);
		if (!externalConfigFile.exists()) {
			writeExternalConfig();
		}
		
		Properties internalConfig = readInternalConfig();
		Properties externalConfig = readExternalConfig();
		
		for (Object key : externalConfig.keySet()) {
			config.put(key, externalConfig.get(key));
		}
		
		for (Object key : internalConfig.keySet()) {
			config.putIfAbsent(key, internalConfig.get(key));
		}
	}

	/**
	 * Read in the external configuration file
	 * @return the external configuration as a Properties object
	 */
	private static Properties readExternalConfig() throws IOException {
		Properties externalConfig = new Properties();
		
		File externalConfigFile = new File("./" + configFileName);
		try (FileInputStream fis = new FileInputStream(externalConfigFile)) {
			externalConfig.load(fis);
		} catch (IOException e) {
			throw e;
		}
		
		return externalConfig;
	}

	/**
	 * Read in the internal configuration file
	 * @return the internal configuration as a Properties object
	 */
	private static Properties readInternalConfig() {
		Properties internalConfig = new Properties();
		
		ResourceBundle configBundle = ResourceBundle.getBundle("config");
		for (String key : configBundle.keySet()) {
			internalConfig.put(key, configBundle.getString(key));
		}
		
		return internalConfig;
	}

	/**
	 * Write a new external configuration that is a copy of the default, internal configuration.
	 * @throws IOException thrown if the file cannot be written to
	 */
	private static void writeExternalConfig() throws IOException {
		Properties internalConfig = readInternalConfig();
		
		File externalConfigFile = new File("./" + configFileName);
		try (FileOutputStream fis = new FileOutputStream(externalConfigFile)) {
			internalConfig.store(fis, "");
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Get a config value.
	 * @param key - the key of the config value needed. 
	 * @return a config value
	 */
	public static String getConfigValue(String key) {
		return config.getProperty(key);
	}
}
