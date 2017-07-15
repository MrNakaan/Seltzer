package hall.caleb.seltzer.objects.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import hall.caleb.seltzer.objects.SerializableCR;

public class ResponseList<R extends Response> {
	private List<R> responses;
	private List<String> serializedResponses;
	
	public ResponseList() {
		responses = new ArrayList<>();
		serializedResponses = new ArrayList<>();
	}
	
	public void serialize() {
		Gson gson = new Gson();
		
		for (Response subResponse : responses) {
			if (subResponse instanceof SerializableCR) {
				((SerializableCR) subResponse).serialize();
			}
			
			serializedResponses.add(gson.toJson(subResponse, subResponse.getType().getResponseClass()));
		}
		
		responses.clear();
	}

	@SuppressWarnings("unchecked")
	public void deserialize() {
		Gson gson = new Gson();
		Response subResponse;
		
		for (String serializedResponse : serializedResponses) {
			subResponse = gson.fromJson(serializedResponse, Response.class);
			
			subResponse = gson.fromJson(serializedResponse, subResponse.getType().getResponseClass());
			
			if (subResponse instanceof SerializableCR) {
				((SerializableCR) subResponse).deserialize();
			}
			
			responses.add((R) subResponse);
		}
		
		serializedResponses.clear();
	}
	
	public void addResponse(R response) {
		responses.add(response);
	}
	
	public int getSize() {
		return responses.size();
	}

	public int getSerializedSize() {
		return serializedResponses.size();
	}
	
	@Override
	public String toString() {
		return "ResponseList [responses=" + responses + ", serializedResponses=" + serializedResponses + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + ((serializedResponses == null) ? 0 : serializedResponses.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseList other = (ResponseList) obj;
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

	public List<R> getResponses() {
		return responses;
	}

	public void setResponses(List<R> responses) {
		this.responses = responses;
	}
}