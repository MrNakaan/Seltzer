package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Command;

public class WaitCommand extends Command {
	protected Integer seconds;
	
	public WaitCommand(Integer seconds) {
		super(CommandType.WAIT);
		this.seconds = seconds;
	}

	public WaitCommand(Integer seconds, CommandType commandType) {
		super(CommandType.WAIT);
		this.seconds = seconds;
	}

	public WaitCommand(Integer seconds, CommandType commandType, UUID id) {
		super(CommandType.WAIT, id);
		this.seconds = seconds;
	}

	@Override
	public String toString() {
		return "WaitCommand [seconds=" + seconds + ", hasCommandList=" + hasCommandList + ", type=" + type + ", id="
				+ id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((seconds == null) ? 0 : seconds.hashCode());
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
		return true;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
}
