package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Selector;

public class SelectionStateWaitCommand extends WaitCommand {
	Boolean selected;
	Selector selector = new Selector();
	
	public SelectionStateWaitCommand(Integer seconds) {
		super(seconds);
	}

	public SelectionStateWaitCommand(Integer seconds, WaitType waitType) {
		super(seconds, waitType);
	}

	public SelectionStateWaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(seconds, waitType, id);
	}
	
	public void setSelector(String selector, SelectorType selectorType) {
		this.selector.setSelector(selectorType, selector);
	}

	@Override
	public String toString() {
		return "SelectionStateWaitCommand [selected=" + selected + ", selector=" + selector + ", waitType=" + waitType
				+ ", seconds=" + seconds + ", usesCommandList=" + hasCommandList + ", type=" + type + ", id=" + id
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((selected == null) ? 0 : selected.hashCode());
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
		SelectionStateWaitCommand other = (SelectionStateWaitCommand) obj;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		return true;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public Selector getSelector() {
		return selector;
	}

	public void setSelector(Selector selector) {
		this.selector = selector;
	}
}
