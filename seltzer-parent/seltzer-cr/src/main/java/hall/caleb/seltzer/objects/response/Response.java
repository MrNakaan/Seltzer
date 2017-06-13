package hall.caleb.seltzer.objects.response;

import java.util.UUID;

import hall.caleb.seltzer.enums.ResponseType;

public class Response {
	protected UUID id;
	protected boolean success;
	protected ResponseType type;
	
	public Response() {
		super();
		this.type = ResponseType.Basic;
	}
	
	public Response(UUID id) {
		this.id = id;
		this.type = ResponseType.Basic;
	}
	
	public Response(UUID id, boolean success) {
		this.id = id;
		this.success = success;
		this.type = ResponseType.Basic;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", success=" + success + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (success != other.success)
			return false;
		if (type != other.type)
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
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}
}
