package tech.seltzer.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigManager {
	private static String configFileName = "config.properties";
	private static Properties config = new Properties();
	
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

	private static Properties readInternalConfig() {
		Properties internalConfig = new Properties();
		
		ResourceBundle configBundle = ResourceBundle.getBundle("config");
		for (String key : configBundle.keySet()) {
			internalConfig.put(key, configBundle.getString(key));
		}
		
		return internalConfig;
	}

	private static void writeExternalConfig() throws IOException {
		Properties internalConfig = readInternalConfig();
		
		File externalConfigFile = new File("./" + configFileName);
		try (FileOutputStream fis = new FileOutputStream(externalConfigFile)) {
			internalConfig.store(fis, "");
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static String getConfigValue(String key) {
		return config.getProperty(key);
	}
}
