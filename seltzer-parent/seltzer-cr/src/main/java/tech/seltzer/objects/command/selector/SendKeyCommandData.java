package tech.seltzer.objects.command.selector;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SeltzerKeys;

public class SendKeyCommandData extends SelectorCommandData {
	private SeltzerKeys key;
	
	public SendKeyCommandData() {
		super(CommandType.SEND_KEY);
	}
	
	public SendKeyCommandData(UUID sessionId) {
		super(CommandType.SEND_KEY, sessionId);
	}

	@Override
	public String toString() {
		return "SendKeyCommandData [key=" + key + ", selector=" + selector + ", hasCommandList=" + hasCommandList
				+ ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter=" + takeScreenshotAfter
				+ ", commandType=" + commandType + ", id=" + id + "]";
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
		SendKeyCommandData other = (SendKeyCommandData) obj;
		if (!super.equals(obj))
			return false;
		if (key != other.key)
			return false;
		return true;
	}

	public SeltzerKeys getKey() {
		return key;
	}

	public void setKey(SeltzerKeys key) {
		this.key = key;
	}
}
