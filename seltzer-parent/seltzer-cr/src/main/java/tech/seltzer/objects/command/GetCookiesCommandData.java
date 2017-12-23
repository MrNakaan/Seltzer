package tech.seltzer.objects.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import tech.seltzer.enums.CommandType;

public final class GetCookiesCommandData extends CommandData {
	private List<String> cookieNames;
	
	public GetCookiesCommandData(UUID seleniumId) {
		super(CommandType.GET_COOKIES, seleniumId);
		cookieNames = new ArrayList<>();
	}

	public void addCookie(String cookieName) {
		cookieNames.add(cookieName);
	}
	
	@Override
	public String toString() {
		return "GetCookiesCommandData [cookieNames=" + cookieNames + ", hasCommandList=" + hasCommandList
				+ ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter=" + takeScreenshotAfter
				+ ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cookieNames == null) ? 0 : cookieNames.hashCode());
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
		GetCookiesCommandData other = (GetCookiesCommandData) obj;
		if (cookieNames == null) {
			if (other.cookieNames != null)
				return false;
		} else if (!cookieNames.equals(other.cookieNames))
			return false;
		return true;
	}

	public List<String> getCookieNames() {
		return cookieNames;
	}

	public void setCookieNames(List<String> cookieNames) {
		this.cookieNames = cookieNames;
	}
}
