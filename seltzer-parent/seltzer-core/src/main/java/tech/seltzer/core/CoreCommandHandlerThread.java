package tech.seltzer.core;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import tech.seltzer.core.processor.BaseProcessor;
import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.objects.SerializableCR;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.response.Response;
import tech.seltzer.server.util.AbstractCommandHandlerThread;

public class CoreCommandHandlerThread extends AbstractCommandHandlerThread {

	public CoreCommandHandlerThread(Socket socket) {
		super(socket);
	}

	@Override
	protected void handleJson() throws IOException {
		String json = readJson(socket);

		System.out.println(Messages.getString("ServerSocketListener.received"));
		System.out.println("\t" + json);

		CommandData command;

		command = new Gson().fromJson(json, CommandData.class);
		command = new Gson().fromJson(json, command.getType().getCrClass());

		if (command.getType() == CommandType.CHAIN) {
			((SerializableCR) command).deserialize();
		}

		Response response = new Response();

		if (command.getType() == CommandType.START) {
			response = BaseProcessor.processCommand(null, command);
		} else if (command.getType() == CommandType.EXIT) {
			SeltzerSession.findSession(command.getId()).close();
			response.setSuccess(true);
		} else {
			response = SeltzerSession.findSession(command.getId()).executeCommand(command);
		}

		System.out.println(Messages.getString("ServerSocketListener.response"));
		if (response.getType() == ResponseType.CHAIN) {
			((SerializableCR) response).serialize();
		}
		System.out.println("\t" + new Gson().toJson(response, response.getType().getCrClass()));

		writeResponse(socket, response);

		socket.close();
	}
}
