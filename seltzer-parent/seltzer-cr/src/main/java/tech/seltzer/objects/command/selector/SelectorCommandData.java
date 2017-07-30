package tech.seltzer.objects.command.selector;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.Selector;

public class SelectorCommandData extends CommandData {
	protected Selector selector = new Selector();
	
	public SelectorCommandData() {
		super();
	}

	public SelectorCommandData(CommandType commandType) {
		super(commandType);
	}
	
	public SelectorCommandData(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}
	
	@Override
	public String toString() {
		return "SelectorCommand [selector=" + selector + ", usesCommandList=" + hasCommandList + ", type=" + type
				+ ", id=" + id + "]";
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
		SelectorCommandData other = (SelectorCommandData) obj;
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
