package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Command;

public abstract class WaitCommand extends Command {
	protected WaitType waitType;
	
	public WaitCommand() {
		super();
	}

	public WaitCommand(WaitType waitType) {
		super();
		this.waitType = waitType;
	}

	public WaitCommand(WaitType waitType, UUID id) {
		super();
		this.waitType = waitType;
		this.id = id;
	}

	@Override
	public String toString() {
		return "WaitCommand [waitType=" + waitType + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((waitType == null) ? 0 : waitType.hashCode());
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
		WaitCommand other = (WaitCommand) obj;
		if (waitType != other.waitType)
			return false;
		return true;
	}

	public WaitType getWaitType() {
		return waitType;
	}

	public void setWaitType(WaitType waitType) {
		this.waitType = waitType;
	}
}
