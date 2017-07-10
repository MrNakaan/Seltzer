package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class ChainCommand extends Command implements SerializableCommand {
	protected boolean usesCommandList = false;
	
	private CommandList commands = new CommandList();

	public ChainCommand() {
		super(CommandType.Chain);
	}

	public ChainCommand(UUID id) {
		super(CommandType.Chain, id);
	}

	@Override
	public void serialize() {
		commands.serialize();
	}

	@Override
	public void deserialize() {
		commands.deserialize();
	}

	public void addCommand(Command command) {
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

	@Override
	public CommandList getCommands() {
		return commands;
	}

	@Override
	public void setCommands(CommandList commands) {
		this.commands = commands;
	}
}
