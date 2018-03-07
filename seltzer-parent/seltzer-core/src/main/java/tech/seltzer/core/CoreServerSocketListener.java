package tech.seltzer.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.MessageFormat;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tech.seltzer.server.util.AbstractServerSocketListener;

/**
 * The listener for incoming connections to the server.
 */
public class CoreServerSocketListener extends AbstractServerSocketListener implements Runnable {
	private static Logger logger = LogManager.getLogger(CoreServerSocketListener.class);

	/**
	 * Build a new listener.
	 * @param port - the port the listener should bind to
	 * @param backlog - the connection backlog count
	 */
	public CoreServerSocketListener(int port, int backlog) {
		super(port, backlog);
	}

	@Override
	public void run() {
		sslEnabled = Boolean.valueOf(ConfigManager.getConfigValue("ssl.enabled"));
		
		try (ServerSocket serverSocket = getServerSocket()) {
			while (!shutdown) {
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

	private ServerSocket getServerSocket() throws IOException {
		if (sslEnabled) {
			SSLContext sslContext = createSslContext();
			SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
			return sslServerSocketFactory.createServerSocket(port, connections);
		} else {
			return new ServerSocket(port, connections);
		}
	}
	
	private SSLContext createSslContext() {
		try {
			// Load the key store
			KeyStore keyStore = KeyStore.getInstance("JKS");
			String keyStoreFileName = ConfigManager.getConfigValue("ssl.keystore.file");
			char[] keyStorePassword = null;
			if (Boolean.valueOf(ConfigManager.getConfigValue("ssl.keystore.password.ask"))) {
				// TODO SEL-42
			} else {
				keyStorePassword = ConfigManager.getConfigValue("ssl.keystore.password").toCharArray();
			}
			keyStore.load(new FileInputStream(keyStoreFileName), keyStorePassword);
			
			// Create the key manager
			String kmAlgorithm = ConfigManager.getConfigValue("ssl.keystore.keymanager.algorithm");
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(kmAlgorithm);
			keyManagerFactory.init(keyStore, keyStorePassword);
			KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
			
			// Create the trust manager
			String tmAlgorithm = ConfigManager.getConfigValue("ssl.keystore.trustmanager.algorithm");
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmAlgorithm);
			trustManagerFactory.init(keyStore);
			TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
			
			// Create the SSL sontext
			String sslProtocol = ConfigManager.getConfigValue("ssl.protocol");
			SSLContext sslContext = SSLContext.getInstance(sslProtocol);
			sslContext.init(keyManagers, trustManagers, new SecureRandom());
			
			return sslContext;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}
}
