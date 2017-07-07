package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public class LogicalNotWaitCommand extends LogicalWaitCommand {
	WaitCommand waitCommand;
	
	public LogicalNotWaitCommand(Integer seconds) {
		super(seconds, WaitType.Not);
	}

	public LogicalNotWaitCommand(Integer seconds, UUID id) {
		super(seconds, WaitType.Not, id);
	}

	@Override
	public String toString() {
		return "LogicalNotWaitCommand [waitCommand=" + waitCommand + ", waitType=" + waitType + ", seconds=" + seconds
				+ ", USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((waitCommand == null) ? 0 : waitCommand.hashCode());
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
		LogicalNotWaitCommand other = (LogicalNotWaitCommand) obj;
		if (waitCommand == null) {
			if (other.waitCommand != null)
				return false;
		} else if (!waitCommand.equals(other.waitCommand))
			return false;
		return true;
	}

	public WaitCommand getWaitCommand() {
		return waitCommand;
	}

	public void setWaitCommand(WaitCommand waitCommand) {
		this.waitCommand = waitCommand;
	}
}
