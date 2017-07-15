package hall.caleb.seltzer.objects.command.wait.visibility;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.Selector;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public class VisibilityWaitCommand extends WaitCommand {
protected Selector selector = new Selector();
	
	public VisibilityWaitCommand(Integer seconds) {
		super(seconds);
	}

	public VisibilityWaitCommand(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public VisibilityWaitCommand(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "VisibilityWaitCommand [selector=" + selector + ", seconds=" + seconds + ", hasCommandList="
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
		VisibilityWaitCommand other = (VisibilityWaitCommand) obj;
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
