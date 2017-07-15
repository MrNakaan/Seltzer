package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Selector;

public class CountWaitCommand extends WaitCommand {
	Integer bound;
	Selector selector = new Selector();
	
	public CountWaitCommand(Integer seconds) {
		super(seconds);
	}

	public CountWaitCommand(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public CountWaitCommand(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "CountWaitCommand [bound=" + bound + ", selector=" + selector + ", seconds=" + seconds
				+ ", hasCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
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
		CountWaitCommand other = (CountWaitCommand) obj;
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
