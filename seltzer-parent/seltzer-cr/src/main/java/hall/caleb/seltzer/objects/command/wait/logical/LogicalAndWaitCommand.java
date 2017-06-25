package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.SerializableCR;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.CommandList;

public class LogicalAndWaitCommand extends LogicalWaitCommand implements SerializableCR	 {
	public final boolean USES_COMMAND_LIST = true;
	
	private CommandList waitCommands = new CommandList(this);
	
	public LogicalAndWaitCommand(Integer seconds) {
		super(seconds, WaitType.And);
	}

	public LogicalAndWaitCommand(Integer seconds, UUID id) {
		super(seconds, WaitType.And, id);
	}

	@Override
	public void serialize() {
		waitCommands.serialize();
	}

	@Override
	public void deserialize() {
		waitCommands.deserialize();
	}
	
	public boolean addCommand(Command command) {
		return waitCommands.addCommand(command);
	}

	@Override
	public String toString() {
		return "LogicalAndWaitCommand [USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", waitCommands=" + waitCommands
				+ ", waitType=" + waitType + ", seconds=" + seconds + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (USES_COMMAND_LIST ? 1231 : 1237);
		result = prime * result + ((waitCommands == null) ? 0 : waitCommands.hashCode());
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
		LogicalAndWaitCommand other = (LogicalAndWaitCommand) obj;
		if (USES_COMMAND_LIST != other.USES_COMMAND_LIST)
			return false;
		if (waitCommands == null) {
			if (other.waitCommands != null)
				return false;
		} else if (!waitCommands.equals(other.waitCommands))
			return false;
		return true;
	}

	public CommandList getWaitCommands() {
		return waitCommands;
	}

	public void setWaitCommands(CommandList waitCommands) {
		this.waitCommands = waitCommands;
	}
}
