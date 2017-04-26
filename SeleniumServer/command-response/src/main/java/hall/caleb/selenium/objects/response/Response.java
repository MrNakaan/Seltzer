package hall.caleb.selenium.objects.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Response {
	private UUID id = null;
	private boolean success = true;
	private String text;
	private String attribute;
	private int count;
	private List<Response> responses;
	
	public Response() {
		responses = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeleniumResponse [id=");
		builder.append(id);
		builder.append(", success=");
		builder.append(success);
		builder.append(", text=");
		builder.append(text);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append(", count=");
		builder.append(count);
		builder.append(", responses=");
		builder.append(responses);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + count;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((responses == null) ? 0 : responses.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Response other = (Response) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (count != other.count)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (responses == null) {
			if (other.responses != null)
				return false;
		} else if (!responses.equals(other.responses))
			return false;
		if (success != other.success)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public boolean isSuccess() {
		return this.success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Response> getResponses() {
		return responses;
	}

	public void setResponses(List<Response> responses) {
		this.responses = responses;
	}
}
