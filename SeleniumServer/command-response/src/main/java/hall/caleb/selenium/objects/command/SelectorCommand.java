package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;
import hall.caleb.selenium.enums.SelectorType;

public class SelectorCommand extends Command {
	protected SelectorType selectorType;
	protected String selector;
	
	public SelectorCommand() {
		super();
	}

	public SelectorCommand(CommandType commandType) {
		super(commandType);
	}
	
	public SelectorCommand(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	public void setSelector(String selector, SelectorType selectorType) {
		this.selector = selector;
		this.selectorType = selectorType;
	}
	
	@Override
	public String toString() {
		return "SelectorSeleniumCommand [selectorType=" + selectorType + ", selector=" + selector + ", commandType="
				+ commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
		result = prime * result + ((selectorType == null) ? 0 : selectorType.hashCode());
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
		SelectorCommand other = (SelectorCommand) obj;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		if (selectorType != other.selectorType)
			return false;
		return true;
	}

	public SelectorType getSelectorType() {
		return selectorType;
	}

	public void setSelectorType(SelectorType selectorType) {
		this.selectorType = selectorType;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}
}
