package hall.caleb.selenium.objects.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class ChainCommand extends Command {
	protected List<Command> commands;

	public ChainCommand() {
		super();
		commands = new ArrayList<>();
		this.commandType = CommandType.Chain;
	}

	public ChainCommand(CommandType commandType) {
		super(commandType);
		commands = new ArrayList<>();
		this.commandType = CommandType.Chain;
	}

	public ChainCommand(CommandType commandType, UUID id) {
		super(commandType, id);
		commands = new ArrayList<>();
		this.commandType = CommandType.Chain;
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
		return "ChainCommand [commands=" + commands + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		return true;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}
}
