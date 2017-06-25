package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public final class GetCookieCommand extends Command {
	private String cookieName;
	
	public GetCookieCommand(UUID seleniumId) {
		super(CommandType.GetCookie, seleniumId);
	}

	@Override
	public String toString() {
		return "GetCookieCommand [cookieName=" + cookieName + ", type=" + type + ", id=" + id + "]";
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
		GetCookieCommand other = (GetCookieCommand) obj;
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
