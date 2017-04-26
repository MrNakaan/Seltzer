package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class ReadAttributeCommand extends MultiResultSelectorCommand {
	protected String attribute;
	
	public ReadAttributeCommand() {
		super();
	}

	public ReadAttributeCommand(CommandType commandType) {
		super(commandType);
	}
	
	public ReadAttributeCommand(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	@Override
	public String toString() {
		return "ReadAttributeCommand [attribute=" + attribute + ", maxResults=" + maxResults + ", selectorType="
				+ selectorType + ", selector=" + selector + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
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
		ReadAttributeCommand other = (ReadAttributeCommand) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		return true;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
