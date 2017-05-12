package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class GoToCommand extends Command {
	protected String url;
	
	public GoToCommand() {
		super();
	}

	public GoToCommand(CommandType commandType) {
		super(commandType);
	}
	
	public GoToCommand(CommandType commandType, UUID id) {
		super(commandType, id);
	}

	public GoToCommand(CommandType commandType, UUID id, String url) {
		super(commandType, id);
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
