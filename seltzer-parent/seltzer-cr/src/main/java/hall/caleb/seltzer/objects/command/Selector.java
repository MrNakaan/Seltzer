package hall.caleb.seltzer.objects.command;

import hall.caleb.seltzer.enums.SelectorType;

public class Selector {
	protected SelectorType selectorType = SelectorType.XPATH;
	protected String selector = "";
	
	public Selector() {
		
	}
	
	public Selector(SelectorType selectorType, String selector) {
		this.selectorType = selectorType;
		this.selector = selector;
	}
	
	public void setSelector(SelectorType selectorType, String selector) {
		this.selector = selector;
		this.selectorType = selectorType;
	}

	@Override
	public String toString() {
		return "Selector [selectorType=" + selectorType + ", selector=" + selector + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
		result = prime * result + ((selectorType == null) ? 0 : selectorType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Selector other = (Selector) obj;
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
