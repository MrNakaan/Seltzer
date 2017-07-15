package hall.caleb.seltzer.objects.command;

import java.util.List;
import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.SerializableCR;

public class ChainCommand<C extends Command> extends Command implements SerializableCR {
	protected boolean usesCommandList = true;
	
	private CommandList<C> commands = new CommandList<>();

	public ChainCommand() {
		super(CommandType.CHAIN);
	}

	public ChainCommand(UUID id) {
		super(CommandType.CHAIN, id);
	}

	@Override
	public void serialize() {
		commands.serialize();
	}

	@Override
	public void deserialize() {
		commands.deserialize();
	}

	public void addCommand(C command) {
		commands.addCommand(command);
	}

	@Override
	public String toString() {
		return "ChainCommand [usesCommandList=" + usesCommandList + ", commands=" + commands + ", type=" + type
				+ ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (usesCommandList ? 1231 : 1237);
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainCommand other = (ChainCommand) obj;
		if (usesCommandList != other.usesCommandList)
			return false;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		return true;
	}

	public CommandList<C> getCommandList() {
		return commands;
	}

	public List<C> getCommands() {
		return commands.getCommands();
	}

	public void setCommandList(CommandList<C> commands) {
		this.commands = commands;
	}
	
	public void setCommands(List<C> commands) {
		this.commands.setCommands(commands);
	}
}
