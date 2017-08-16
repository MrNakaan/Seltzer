package tech.seltzer.objects.command.wait.existence;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.Selector;

public class NestedExistenceWaitCommandData extends ExistenceWaitCommandData {
	protected Selector childSelector = new Selector();
	
	public NestedExistenceWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public NestedExistenceWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public NestedExistenceWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	@Override
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}
	
	public void setChildSelector(String selector, SelectorType selectorType) {
		this.childSelector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "NestedExistenceWaitCommand [childSelector=" + childSelector + ", selector=" + selector + ", seconds="
				+ seconds + ", hasCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childSelector == null) ? 0 : childSelector.hashCode());
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
		NestedExistenceWaitCommandData other = (NestedExistenceWaitCommandData) obj;
		if (childSelector == null) {
			if (other.childSelector != null)
				return false;
		} else if (!childSelector.equals(other.childSelector))
			return false;
		return true;
	}

	public Selector getChildSelector() {
		return childSelector;
	}

	public void setChildSelector(Selector childSelector) {
		this.childSelector = childSelector;
	}
}