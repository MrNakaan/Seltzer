package tech.seltzer.objects.command.wait.existence;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.wait.WaitCommandData;

public class ExistenceWaitCommandData extends WaitCommandData {
	protected Selector selector = new Selector();
	
	public ExistenceWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public ExistenceWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public ExistenceWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "ExistenceWaitCommand [selector=" + selector + ", seconds=" + seconds + ", hasCommandList="
				+ hasCommandList + ", type=" + type + ", id=" + id + "]";
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
		ExistenceWaitCommandData other = (ExistenceWaitCommandData) obj;
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
