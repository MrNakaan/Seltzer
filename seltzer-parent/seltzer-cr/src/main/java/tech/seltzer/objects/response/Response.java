package tech.seltzer.objects.response;

import java.util.UUID;

import tech.seltzer.enums.ResponseType;
import tech.seltzer.objects.CrDataBase;

public class Response extends CrDataBase {
	protected boolean success;
	protected ResponseType type;
	protected String screenshotBefore = null;
	protected String screenshotAfter = null;
	
	public Response() {
		super();
		this.type = ResponseType.BASIC;
	}
	
	public Response(UUID id) {
		this.id = id;
		this.type = ResponseType.BASIC;
	}
	
	public Response(UUID id, boolean success) {
		this.id = id;
		this.success = success;
		this.type = ResponseType.BASIC;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", type=" + type + ", screenshotBefore=" + screenshotBefore
				+ ", screenshotAfter=" + screenshotAfter + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}

	public String getScreenshotBefore() {
		return screenshotBefore;
	}

	public void setScreenshotBefore(String screenshotBefore) {
		this.screenshotBefore = screenshotBefore;
	}

	public String getScreenshotAfter() {
		return screenshotAfter;
	}

	public void setScreenshotAfter(String screenshotAfter) {
		this.screenshotAfter = screenshotAfter;
	}
}
