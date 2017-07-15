package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class SendKeysCommand extends Command {
	private String keys;
	private Selector selector = new Selector();
	
	public SendKeysCommand() {
		super(CommandType.SEND_KEYS);
	}

	public SendKeysCommand(UUID id) {
		super(CommandType.SEND_KEYS, id);
	}

	@Override
	public String toString() {
		return "SendKeysCommand [keys=" + keys + ", selector=" + selector + ", usesCommandList=" + hasCommandList
				+ ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
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
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		return true;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}
}
