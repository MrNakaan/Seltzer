package hall.caleb.seltzer.objects.command.wait.textmatch;

import java.util.UUID;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Selector;

public class TextMatchAttributeSelectorWaitCommandData extends TextMatchWaitCommandData {
	protected Selector selector = new Selector();
	String attribute = "";
	
	public TextMatchAttributeSelectorWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public TextMatchAttributeSelectorWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public TextMatchAttributeSelectorWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "TextMatchAttributeSelectorWaitCommand [selector=" + selector + ", attribute=" + attribute + ", seconds="
				+ seconds + ", hasCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
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
		TextMatchAttributeSelectorWaitCommandData other = (TextMatchAttributeSelectorWaitCommandData) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
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

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
