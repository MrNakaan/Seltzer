package hall.caleb.seltzer.objects.command.selector.multiresult;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.selector.SelectorCommandData;

public class MultiResultSelectorCommandData extends SelectorCommandData {
	protected int maxResults;
	
	public MultiResultSelectorCommandData() {
		super();
	}

	public MultiResultSelectorCommandData(CommandType commandType) {
		super(commandType);
	}
	
	public MultiResultSelectorCommandData(CommandType commandType, UUID id) {
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
		MultiResultSelectorCommandData other = (MultiResultSelectorCommandData) obj;
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
