package hall.caleb.seltzer.objects.command.selector;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public final class FillFieldCommand extends SelectorCommand {
	protected String text;
	
	public FillFieldCommand() {
		super(CommandType.FillField);
	}

	public FillFieldCommand(UUID id) {
		super(CommandType.FillField, id);
	}

	@Override
	public String toString() {
		return "FillFieldCommand [text=" + text + ", selectorType=" + selectorType + ", selector=" + selector
				+ ", commandType=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		FillFieldCommand other = (FillFieldCommand) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
