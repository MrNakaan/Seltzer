package tech.seltzer.objects.command;

import java.util.UUID;

import tech.seltzer.enums.CommandType;

public final class GetCookieCommandData extends CommandData {
	private String cookieName;
	
	public GetCookieCommandData(UUID seleniumId) {
		super(CommandType.GET_COOKIE, seleniumId);
	}

	@Override
	public String toString() {
		return "GetCookieCommandData [cookieName=" + cookieName + ", hasCommandList=" + hasCommandList
				+ ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter=" + takeScreenshotAfter
				+ ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cookieName == null) ? 0 : cookieName.hashCode());
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
		GetCookieCommandData other = (GetCookieCommandData) obj;
		if (cookieName == null) {
			if (other.cookieName != null)
				return false;
		} else if (!cookieName.equals(other.cookieName))
			return false;
		return true;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
}
