package hall.caleb.seltzer.objects.command.wait.visibility;

import java.util.UUID;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Selector;
import hall.caleb.seltzer.objects.command.wait.WaitCommandData;

public class InvisibilityWaitCommandData extends WaitCommandData {
	protected Selector selector = new Selector();
	protected String text;
	
	public InvisibilityWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public InvisibilityWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public InvisibilityWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "InvisibilityWaitCommand [selector=" + selector + ", text=" + text + ", seconds=" + seconds
				+ ", hasCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
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
		InvisibilityWaitCommandData other = (InvisibilityWaitCommandData) obj;
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
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
