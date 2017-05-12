package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class MultiResultSelectorCommand extends SelectorCommand {
	protected int maxResults;
	
	public MultiResultSelectorCommand() {
		super();
	}

	public MultiResultSelectorCommand(CommandType commandType) {
		super(commandType);
	}
	
	public MultiResultSelectorCommand(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	@Override
	public String toString() {
		return "MultiResultSelectorCommand [maxResults=" + maxResults + ", selectorType=" + selectorType + ", selector="
				+ selector + ", commandType=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxResults;
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
		MultiResultSelectorCommand other = (MultiResultSelectorCommand) obj;
		if (maxResults != other.maxResults)
			return false;
		return true;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
}
