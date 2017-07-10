package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Command;

public class WaitCommand extends Command {
	protected WaitType waitType;
	protected Integer seconds;
	
	public WaitCommand(Integer seconds) {
		super(CommandType.Wait);
		this.seconds = seconds;
	}

	public WaitCommand(Integer seconds, WaitType waitType) {
		super(CommandType.Wait);
		this.seconds = seconds;
		this.waitType = waitType;
	}

	public WaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(CommandType.Wait, id);
		this.seconds = seconds;
		this.waitType = waitType;
	}

	@Override
	public String toString() {
		return "WaitCommand [waitType=" + waitType + ", seconds=" + seconds + ", usesCommandList=" + hasCommandList
				+ ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((seconds == null) ? 0 : seconds.hashCode());
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
		if (seconds == null) {
			if (other.seconds != null)
				return false;
		} else if (!seconds.equals(other.seconds))
			return false;
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

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
}
