package tech.seltzer.objects.response;

import java.util.UUID;

import tech.seltzer.enums.ResponseType;

public class SingleResultResponse extends Response {
	private String result;

	public SingleResultResponse() {
		super();
		this.type = ResponseType.SINGLE_RESULT;
	}

	public SingleResultResponse(UUID id) {
		super(id);
		this.type = ResponseType.SINGLE_RESULT;
	}
	
	public SingleResultResponse(UUID id, boolean success) {
		super(id, success);
		this.type = ResponseType.SINGLE_RESULT;
	}

	@Override
	public String toString() {
		return "SingleResultResponse [result=" + result + ", id=" + id + ", success=" + success + ", type=" + type
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
		SingleResultResponse other = (SingleResultResponse) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
