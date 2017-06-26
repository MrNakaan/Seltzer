package hall.caleb.seltzer.objects.command;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CommandList {
	private List<Command> commands;
	private List<String> serializedCommands;
	public final Command owner;
	
	public CommandList(Command owner) {
		this.owner = owner;
		commands = new ArrayList<>();
		serializedCommands = new ArrayList<>();
	}
	
	public void serialize() {
		Gson gson = new Gson();
		
		for (Command subCommand : commands) {
			if (subCommand instanceof SerializableCommand) {
				((SerializableCommand) subCommand).serialize();
			}
			
			serializedCommands.add(gson.toJson(subCommand, subCommand.getType().getCommandClass()));
		}
		
		commands = new ArrayList<>();
	}

	public void deserialize() {
		Gson gson = new Gson();
		Command subCommand;
		
		for (String serializedCommand : serializedCommands) {
			subCommand = gson.fromJson(serializedCommand, Command.class);
			subCommand = gson.fromJson(serializedCommand, subCommand.getType().getCommandClass());
			
			if (subCommand instanceof SerializableCommand) {
				((SerializableCommand) subCommand).deserialize();
			}
			
			commands.add(subCommand);
		}
		
		serializedCommands = new ArrayList<>();
	}
	
	public boolean addCommand(Command command) {
		if (owner.getId().equals(command.getId())) {
			commands.add(command);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "CommandList [commands=" + commands + ", serializedCommands=" + serializedCommands + ", owner=" + owner
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((serializedCommands == null) ? 0 : serializedCommands.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandList other = (CommandList) obj;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (serializedCommands == null) {
			if (other.serializedCommands != null)
				return false;
		} else if (!serializedCommands.equals(other.serializedCommands))
			return false;
		return true;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public List<String> getSerializedCommands() {
		return serializedCommands;
	}

	public void setSerializedCommands(List<String> serializedCommands) {
		this.serializedCommands = serializedCommands;
	}
}