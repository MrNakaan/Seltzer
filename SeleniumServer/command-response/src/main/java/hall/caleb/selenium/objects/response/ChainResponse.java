package hall.caleb.selenium.objects.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hall.caleb.selenium.enums.ResponseType;

public class ChainResponse extends Response {
	private List<Response> responses;

	public ChainResponse() {
		super();
		responses = new ArrayList<>();
	}

	public ChainResponse(UUID id) {
		super(id, ResponseType.Chain);
		responses = new ArrayList<>();
	}
	
	public ChainResponse(UUID id, boolean success) {
		super(id, success, ResponseType.Chain);
		responses = new ArrayList<>();
	}
	
	public boolean addCommand(Response response) {
		if (this.id.equals(response.getId())) {
			responses.add(response);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "ChainResponse [responses=" + responses + ", id=" + id + ", success=" + success + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
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
		ChainResponse other = (ChainResponse) obj;
		if (responses == null) {
			if (other.responses != null)
				return false;
		} else if (!responses.equals(other.responses))
			return false;
		return true;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
}
