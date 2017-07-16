package hall.caleb.seltzer.objects.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;

import hall.caleb.seltzer.objects.SerializableCR;

public class ResponseList<R extends Response> {
	public static final int HASH_PRIME = 31;
	
	private List<R> responses;
	private List<String> serializedResponses;
	
	public ResponseList() {
		responses = null;
		serializedResponses = null;
	}
	
	public void serialize() {
		if (serializedResponses == null) {
			serializedResponses = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		
		if (!CollectionUtils.isEmpty(responses)) { 
			for (Response subResponse : responses) {
				if (subResponse instanceof SerializableCR) {
					((SerializableCR) subResponse).serialize();
				}
				
				serializedResponses.add(gson.toJson(subResponse, subResponse.getType().getResponseClass()));
			}
			
			responses.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public void deserialize() {
		if (responses == null) {
			responses = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		Response subResponse;
		
		if (!CollectionUtils.isEmpty(serializedResponses)) {
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
	}
	
	public void addResponse(R response) {
		if (responses == null) {
			responses = new ArrayList<>();
		}
		
		responses.add(response);
	}
	
	public int getSize() {
		if (responses == null) {
			return 0;
		} else {
			return responses.size();
		}
	}

	public int getSerializedSize() {
		if (serializedResponses == null) {
			return 0;
		} else {
			return serializedResponses.size();
		}
	}
	
	@Override
	public String toString() {
		return "ResponseList [responses=" + responses + ", serializedResponses=" + serializedResponses + "]";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = HASH_PRIME * result + ((responses == null) ? 0 : responses.hashCode());
		result = HASH_PRIME * result + ((serializedResponses == null) ? 0 : serializedResponses.hashCode());
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
		if (responses == null) {
			responses = new ArrayList<>();
		}
		
		return responses;
	}

	public void setResponses(List<R> responses) {
		if (responses == null) {
			this.responses.clear();
		} else {
			this.responses = responses;
		}
	}
}