package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class FillFieldCommand extends SelectorCommand {
	protected String text;
	
	public FillFieldCommand() {
		super();
	}

	public FillFieldCommand(CommandType commandType) {
		super(commandType);
	}
	
	public FillFieldCommand(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	@Override
	public String toString() {
		return "FillFieldCommand [text=" + text + ", selectorType=" + selectorType + ", selector=" + selector
				+ ", commandType=" + commandType + ", id=" + id + "]";
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
