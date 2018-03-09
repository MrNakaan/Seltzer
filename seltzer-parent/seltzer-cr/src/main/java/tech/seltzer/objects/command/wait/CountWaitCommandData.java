package tech.seltzer.objects.command.wait;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.Selector;

public class CountWaitCommandData extends WaitCommandData {
	Integer bound;
	Selector selector = new Selector();
	
	public CountWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public CountWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public CountWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "CountWaitCommandData [bound=" + bound + ", selector=" + selector + ", seconds=" + seconds
				+ ", hasCommandList=" + hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore
				+ ", takeScreenshotAfter=" + takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bound == null) ? 0 : bound.hashCode());
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
		CountWaitCommandData other = (CountWaitCommandData) obj;
		if (!super.equals(obj))
			return false;
		if (bound == null) {
			if (other.bound != null)
				return false;
		} else if (!bound.equals(other.bound))
			return false;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		return true;
	}

	public Integer getBound() {
		return bound;
	}

	public void setBound(Integer bound) {
		this.bound = bound;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}
}
