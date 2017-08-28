package tech.seltzer.objects.command.selector;

import java.util.UUID;

import tech.seltzer.enums.CommandType;

public class SendKeysCommandData extends SelectorCommandData {
	private String keys;
	
	public SendKeysCommandData() {
		super(CommandType.SEND_KEYS);
	}

	public SendKeysCommandData(UUID id) {
		super(CommandType.SEND_KEYS, id);
	}

	@Override
	public String toString() {
		return "SendKeysCommandData [keys=" + keys + ", selector=" + selector + ", hasCommandList=" + hasCommandList
				+ ", type=" + commandType + ", id=" + id + "]";
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
		SendKeysCommandData other = (SendKeysCommandData) obj;
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
