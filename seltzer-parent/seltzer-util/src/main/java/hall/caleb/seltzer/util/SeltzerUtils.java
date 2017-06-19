package hall.caleb.seltzer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.google.gson.Gson;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.ResponseType;
import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.Response;

public class SeltzerUtils {
	public static Response send(Command command) {
		if (command.getType() == CommandType.Chain) {
			((ChainCommand) command).serialize();
		}
		
		String jsonOut = new Gson().toJson(command, command.getType().getCommandClass());
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

	private static Response parseResponse(String json) {
		Gson gson = new Gson();

		Response response = gson.fromJson(json, Response.class);
		response = gson.fromJson(json, response.getType().getResponseClass());
		
		if (response.getType() == ResponseType.Chain) {
			((ChainResponse) response).deserialize();
		}
		
		return response;
	}
}
