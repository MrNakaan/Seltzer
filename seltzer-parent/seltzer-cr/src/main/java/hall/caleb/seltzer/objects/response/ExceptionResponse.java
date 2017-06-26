package hall.caleb.seltzer.objects.response;

import java.util.Arrays;
import java.util.UUID;

import hall.caleb.seltzer.enums.ResponseType;

public class ExceptionResponse extends Response {
	private String message;
	private StackTraceElement[] stackTrace;

	public ExceptionResponse() {
		super();
		this.type = ResponseType.SingleResult;
	}

	public ExceptionResponse(UUID id) {
		super(id);
		this.type = ResponseType.SingleResult;
	}
	
	public ExceptionResponse(UUID id, boolean success) {
		super(id, success);
		this.type = ResponseType.SingleResult;
	}

	@Override
	public String toString() {
		return "ExceptionResponse [message=" + message + ", stackTrace=" + Arrays.toString(stackTrace) + ", id=" + id
				+ ", success=" + success + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + Arrays.hashCode(stackTrace);
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
		ExceptionResponse other = (ExceptionResponse) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (!Arrays.equals(stackTrace, other.stackTrace))
			return false;
		return true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}
}
