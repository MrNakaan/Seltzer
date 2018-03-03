package tech.seltzer.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import tech.seltzer.enums.ResponseType;
import tech.seltzer.objects.SerializableCR;
import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

/**
 * A class to handle commands including reading the raw JSON, deserializing it, sending it 
 * to be processed, serializing the response, and sending it back to the caller.
 */
public abstract class AbstractCommandHandlerThread implements Runnable {
	protected static Logger logger = LogManager.getLogger(AbstractCommandHandlerThread.class);

	protected Socket socket;

	public AbstractCommandHandlerThread(Socket socket) {
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

	protected abstract void handleJson() throws IOException;

	/**
	 * Read the raw JSON from the socket.
	 * @param socket - the socket to read from
	 * @return The raw JSON
	 * @throws IOException thrown if there is an issue reading from the socket
	 */
	protected String readJson(Socket socket) throws IOException {
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
	protected void writeResponse(Socket socket, Response response) throws IOException {
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
