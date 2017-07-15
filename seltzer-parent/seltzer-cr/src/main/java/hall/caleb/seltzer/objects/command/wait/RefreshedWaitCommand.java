package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public final class RefreshedWaitCommand extends WaitCommand {
WaitCommand waitCommand;
	
	public RefreshedWaitCommand(Integer seconds) {
		super(seconds, CommandType.REFRESHED_WAIT);
	}

	public RefreshedWaitCommand(Integer seconds, UUID id) {
		super(seconds, CommandType.REFRESHED_WAIT, id);
	}

	@Override
	public String toString() {
		return "RefreshedWaitCommand [waitCommand=" + waitCommand + ", seconds=" + seconds + ", hasCommandList="
				+ hasCommandList + ", type=" + type + ", id=" + id + "]";
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
		RefreshedWaitCommand other = (RefreshedWaitCommand) obj;
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
