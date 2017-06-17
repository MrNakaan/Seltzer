package hall.caleb.seltzer.objects.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import hall.caleb.seltzer.enums.CommandType;

public class ChainCommand extends Command {
	protected List<Command> commands;
	private List<String> serializedCommands;

	public ChainCommand() {
		super(CommandType.Chain);
		commands = new ArrayList<>();
		serializedCommands = new ArrayList<>();
	}

	public ChainCommand(UUID id) {
		super(CommandType.Chain, id);
		commands = new ArrayList<>();
		serializedCommands = new ArrayList<>();
	}

	public void serialize() {
		Gson gson = new Gson();
		
		for (Command subCommand : commands) {
			if (subCommand.getType() == CommandType.Chain) {
				((ChainCommand) subCommand).serialize();
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
			
			if (subCommand.getType() == CommandType.Chain) {
				((ChainCommand) subCommand).deserialize();
			}
			
			commands.add(subCommand);
		}
		
		serializedCommands = new ArrayList<>();
	}

	public boolean addCommand(Command command) {
		if (this.id.equals(command.getId())) {
			commands.add(command);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "ChainCommand [commands=" + commands + ", serializedCommands=" + serializedCommands + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		result = prime * result + ((serializedCommands == null) ? 0 : serializedCommands.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainCommand other = (ChainCommand) obj;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
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
