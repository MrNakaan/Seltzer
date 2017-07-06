package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class SendKeysCommand extends Command {
	private String keys;
	
	public SendKeysCommand() {
		super(CommandType.SendKeys);
	}

	public SendKeysCommand(UUID id) {
		super(CommandType.SendKeys, id);
	}

	@Override
	public String toString() {
		return "SendKeysCommand [keys=" + keys + ", USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", type=" + type + ", id="
				+ id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
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
		SendKeysCommand other = (SendKeysCommand) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		return true;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
}
