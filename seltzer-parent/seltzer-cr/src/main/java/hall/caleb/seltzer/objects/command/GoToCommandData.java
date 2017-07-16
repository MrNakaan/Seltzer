package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public final class GoToCommandData extends CommandData {
	protected String url;
	
	public GoToCommandData() {
		super(CommandType.GO_TO);
	}

	public GoToCommandData(UUID id) {
		super(CommandType.GO_TO, id);
	}

	public GoToCommandData(UUID id, String url) {
		super(CommandType.GO_TO, id);
		this.url = url;
	}

	@Override
	public String toString() {
		return "GoToCommand [url=" + url + ", commandType=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		GoToCommandData other = (GoToCommandData) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
