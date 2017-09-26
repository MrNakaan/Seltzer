package tech.seltzer.objects.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tech.seltzer.enums.ResponseType;

public class MultiResultResponse extends Response {
	private List<String> results;
	
	public MultiResultResponse() {
		super();
		this.type = ResponseType.MULTI_RESULT;
	}

	public MultiResultResponse(UUID id) {
		super(id);
		results = new ArrayList<>();
		this.type = ResponseType.MULTI_RESULT;
	}
	
	public MultiResultResponse(UUID id, boolean success) {
		super(id, success);
		results = new ArrayList<>();
		this.type = ResponseType.MULTI_RESULT;
	}

	@Override
	public String toString() {
		return "MultiResultResponse [results=" + results + ", success=" + success + ", type=" + type
				+ ", screenshotBefore=" + screenshotBefore + ", screenshotAfter=" + screenshotAfter + ", id=" + id
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((results == null) ? 0 : results.hashCode());
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
		MultiResultResponse other = (MultiResultResponse) obj;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}
}
