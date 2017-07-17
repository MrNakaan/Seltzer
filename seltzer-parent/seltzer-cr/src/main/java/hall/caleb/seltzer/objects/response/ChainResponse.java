package hall.caleb.seltzer.objects.response;

import java.util.List;
import java.util.UUID;

import hall.caleb.seltzer.enums.ResponseType;
import hall.caleb.seltzer.objects.CrList;
import hall.caleb.seltzer.objects.SerializableCR;

public class ChainResponse<R extends Response> extends Response implements SerializableCR {
	private CrList<R> responses = new CrList<>();

	public ChainResponse() {
		super();
		this.success = true;
		this.type = ResponseType.CHAIN;
	}

	public ChainResponse(UUID id) {
		super(id);
		this.success = true;
		this.type = ResponseType.CHAIN;
	}

	public ChainResponse(UUID id, boolean success) {
		super(id, success);
		this.type = ResponseType.CHAIN;
	}

	@Override
	public void serialize() {
		responses.serialize();
	}

	@Override
	public void deserialize() {
		responses.deserialize();
	}

	public boolean addResponse(R response) {
		if (this.id.equals(response.getId())) {
			responses.addCr(response);
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

	@SuppressWarnings("rawtypes")
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

	public CrList<R> getResponseList() {
		return responses;
	}

	public List<R> getResponses() {
		return responses.getCrs();
	}

	public void setResponseList(CrList<R> responses) {
		this.responses = responses;
	}
	
	public void setResponses(List<R> responses) {
		this.responses.setCrs(responses);
	}
}
