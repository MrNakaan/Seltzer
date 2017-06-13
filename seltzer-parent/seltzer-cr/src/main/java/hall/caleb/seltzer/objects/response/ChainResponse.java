package hall.caleb.seltzer.objects.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import hall.caleb.seltzer.enums.ResponseType;

public class ChainResponse extends Response {
	private List<Response> responses;
	private List<String> serializedResponses;

	public ChainResponse() {
		super();
		responses = new ArrayList<>();
		serializedResponses = new ArrayList<>();
		this.success = true;
		this.type = ResponseType.Chain;
	}

	public ChainResponse(UUID id) {
		super(id);
		responses = new ArrayList<>();
		serializedResponses = new ArrayList<>();
		this.success = true;
		this.type = ResponseType.Chain;
	}

	public ChainResponse(UUID id, boolean success) {
		super(id, success);
		responses = new ArrayList<>();
		serializedResponses = new ArrayList<>();
		this.type = ResponseType.Chain;
	}

	public void serialize() {
		Gson gson = new Gson();
		
		for (Response subResponse : responses) {
			switch(subResponse.getType()) {
				case Chain:
					((ChainResponse) subResponse).serialize();
					serializedResponses.add(gson.toJson(subResponse, ChainResponse.class));
					break;
				case SingleResult:
					serializedResponses.add(gson.toJson(subResponse, SingleResultResponse.class));
					break;
				case MultiResult:
					serializedResponses.add(gson.toJson(subResponse, MultiResultResponse.class));
					break;
				default:
					serializedResponses.add(gson.toJson(subResponse, Response.class));
					break;
			}
		}
		
		responses = new ArrayList<>();
	}

	public void deserialize() {
		Gson gson = new Gson();
		Response subResponse;
		
		for (String serializedResponse : serializedResponses) {
			subResponse = gson.fromJson(serializedResponse, Response.class);
			switch(subResponse.getType()) {
				case Chain:
					subResponse = gson.fromJson(serializedResponse, ChainResponse.class);
					((ChainResponse) subResponse).deserialize();
					break;
				case SingleResult:
					subResponse = gson.fromJson(serializedResponse, SingleResultResponse.class);
					break;
				case MultiResult:
					subResponse = gson.fromJson(serializedResponse, MultiResultResponse.class);
					break;
				default:
					subResponse = gson.fromJson(serializedResponse, Response.class);
					break;
			}
			
			responses.add(subResponse);
		}
		
		serializedResponses = new ArrayList<>();
	}

	public boolean addResponse(Response response) {
		if (this.id.equals(response.getId())) {
			responses.add(response);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "ChainResponse [responses=" + responses + ", serializedResponses=" + serializedResponses + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + ((serializedResponses == null) ? 0 : serializedResponses.hashCode());
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
		if (serializedResponses == null) {
			if (other.serializedResponses != null)
				return false;
		} else if (!serializedResponses.equals(other.serializedResponses))
			return false;
		return true;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}

	public List<String> getSerializedResponses() {
		return serializedResponses;
	}

	public void setSerializedResponses(List<String> serializedResponses) {
		this.serializedResponses = serializedResponses;
	}
}
