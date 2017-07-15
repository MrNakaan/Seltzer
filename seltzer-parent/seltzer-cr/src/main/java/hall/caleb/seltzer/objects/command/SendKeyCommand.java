package hall.caleb.seltzer.objects.command;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SeltzerKeys;

public class SendKeyCommand extends Command {
	private SeltzerKeys key;
	private Selector selector = new Selector();
	
	public SendKeyCommand() {
		super(CommandType.SEND_KEY);
	}

	public SeltzerKeys getKey() {
		return key;
	}

	public void setKey(SeltzerKeys key) {
		this.key = key;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		SendKeyCommand other = (SendKeyCommand) obj;
		if (key != other.key)
			return false;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SendKeyCommand [key=" + key + ", selector=" + selector + ", usesCommandList=" + hasCommandList
				+ ", type=" + type + ", id=" + id + "]";
	}
}
