package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.Keys;

public class SendKeyCommand extends Command {
	private Keys key;

	public SendKeyCommand() {
		super(CommandType.SendKey);
	}

	@Override
	public String toString() {
		return "SendKeyCommand [key=" + key + ", USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", type=" + type + ", id="
				+ id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		SendKeyCommand other = (SendKeyCommand) obj;
		if (key != other.key)
			return false;
		return true;
	}

	public Keys getKey() {
		return key;
	}

	public void setKey(Keys key) {
		this.key = key;
	}

	public SendKeyCommand(UUID id) {
		super(CommandType.SendKey, id);
	}
	
	
}
