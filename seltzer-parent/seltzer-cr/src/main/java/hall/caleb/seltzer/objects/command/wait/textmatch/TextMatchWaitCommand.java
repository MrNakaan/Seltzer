package hall.caleb.seltzer.objects.command.wait.textmatch;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public class TextMatchWaitCommand extends WaitCommand {
	private String text;
	
	public TextMatchWaitCommand(Integer seconds) {
		super(seconds);
	}

	public TextMatchWaitCommand(Integer seconds, WaitType waitType) {
		super(seconds, waitType);
	}

	public TextMatchWaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(seconds, waitType, id);
	}

	@Override
	public String toString() {
		return "TextMatchWaitCommand [text=" + text + ", waitType=" + waitType + ", seconds=" + seconds
				+ ", USES_COMMAND_LIST=" + USES_COMMAND_LIST + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		TextMatchWaitCommand other = (TextMatchWaitCommand) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
