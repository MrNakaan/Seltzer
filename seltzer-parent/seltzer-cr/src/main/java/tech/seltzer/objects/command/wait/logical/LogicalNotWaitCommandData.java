package tech.seltzer.objects.command.wait.logical;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.wait.WaitCommandData;

public final class LogicalNotWaitCommandData extends LogicalWaitCommandData {
	WaitCommandData waitCommand;
	
	public LogicalNotWaitCommandData(Integer seconds) {
		super(seconds, CommandType.NOT_WAIT);
	}

	public LogicalNotWaitCommandData(Integer seconds, UUID id) {
		super(seconds, CommandType.NOT_WAIT, id);
	}

	@Override
	public String toString() {
		return "LogicalNotWaitCommandData [waitCommand=" + waitCommand + ", seconds=" + seconds + ", hasCommandList="
				+ hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter="
				+ takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
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
		LogicalNotWaitCommandData other = (LogicalNotWaitCommandData) obj;
		if (waitCommand == null) {
			if (other.waitCommand != null)
				return false;
		} else if (!waitCommand.equals(other.waitCommand))
			return false;
		return true;
	}

	public WaitCommandData getWaitCommand() {
		return waitCommand;
	}

	public void setWaitCommand(WaitCommandData waitCommand) {
		this.waitCommand = waitCommand;
	}
}
