package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class GoToCommand extends Command {
	protected String url;
	
	public GoToCommand() {
		super(CommandType.GoTo);
	}

	public GoToCommand(UUID id) {
		super(CommandType.GoTo, id);
	}

	public GoToCommand(UUID id, String url) {
		super(CommandType.GoTo, id);
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
		GoToCommand other = (GoToCommand) obj;
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
