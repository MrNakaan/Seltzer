package tech.seltzer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.google.gson.Gson;

import tech.seltzer.enums.ResponseType;
import tech.seltzer.objects.SerializableCR;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.exception.SeltzerException;
import tech.seltzer.objects.response.ExceptionResponse;
import tech.seltzer.objects.response.Response;

public class SeltzerSend {
	public static Response send(CommandData command) throws SeltzerException {
		if (command instanceof SerializableCR) {
			((SerializableCR) command).serialize();
		}
		
		String jsonOut = new Gson().toJson(command, command.getType().getCrClass());
		String jsonIn = sendAndReceive(jsonOut);
		return parseResponse(jsonIn);
	}

	private static String sendAndReceive(String json) {
		StringBuilder resultJson = new StringBuilder();

		try (Socket socket = new Socket("127.0.0.1", 39948);
				OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
			writer.write(json);
			writer.flush();

			while (!reader.ready()) {
				continue;
			}

			char[] buffer = new char[128];
			int bufferSize = 0;
			while (reader.ready()) {
				bufferSize = reader.read(buffer);
				resultJson.append(buffer, 0, bufferSize);
				Arrays.fill(buffer, '\0');
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultJson.toString();
	}

	private static Response parseResponse(String json) throws SeltzerException {
		Gson gson = new Gson();

		Response response = gson.fromJson(json, Response.class);
		response = gson.fromJson(json, response.getType().getCrClass());
		
		if (response.getType() == ResponseType.EXCEPTION) {
			ExceptionResponse e = (ExceptionResponse) response;
			throw new SeltzerException(e.getMessage(), e.getStackTrace());
		} else if (response instanceof SerializableCR) {
			((SerializableCR) response).deserialize();
		}
		
		return response;
	}
}
