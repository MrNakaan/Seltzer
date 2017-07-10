package hall.caleb.seltzer.objects.command.selector.multiresult;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;

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
		return "MultiResultSelectorCommand [maxResults=" + maxResults + ", selector=" + selector
				+ ", usesCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
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
