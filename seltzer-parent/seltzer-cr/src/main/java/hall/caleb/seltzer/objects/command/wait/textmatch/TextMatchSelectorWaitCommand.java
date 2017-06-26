package hall.caleb.seltzer.objects.command.wait.textmatch;

import java.util.UUID;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Selector;

public class TextMatchSelectorWaitCommand extends TextMatchWaitCommand {
	protected Selector selector;
	
	public TextMatchSelectorWaitCommand(Integer seconds) {
		super(seconds);
	}

	public TextMatchSelectorWaitCommand(Integer seconds, WaitType waitType) {
		super(seconds, waitType);
	}

	public TextMatchSelectorWaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "TextMatchSelectorWaitCommand [selector=" + selector + ", waitType=" + waitType + ", seconds=" + seconds
				+ ", USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", type=" + type + ", id=" + id + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		TextMatchSelectorWaitCommand other = (TextMatchSelectorWaitCommand) obj;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		return true;
	}
	
	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}
}
