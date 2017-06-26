package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;

public class OldVisibilityWaitCommand extends SelectorCommand {
	Integer seconds;
	
	public OldVisibilityWaitCommand() {
		super(CommandType.Wait);
	}
	
	public OldVisibilityWaitCommand(UUID id) {
		super(CommandType.Wait, id);
	}

	@Override
	public String toString() {
		return "OldVisibilityWaitCommand [seconds=" + seconds + ", selector=" + selector + ", USES_COMMAND_LIST="
				+ USES_COMMAND_LIST + ", type=" + type + ", id=" + id + "]";
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
		OldVisibilityWaitCommand other = (OldVisibilityWaitCommand) obj;
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
