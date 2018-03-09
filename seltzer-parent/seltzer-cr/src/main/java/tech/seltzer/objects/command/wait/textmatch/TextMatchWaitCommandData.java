package tech.seltzer.objects.command.wait.textmatch;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.wait.WaitCommandData;

public class TextMatchWaitCommandData extends WaitCommandData {
	private String text;
	
	public TextMatchWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public TextMatchWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public TextMatchWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}

	@Override
	public String toString() {
		return "TextMatchWaitCommandData [text=" + text + ", seconds=" + seconds + ", hasCommandList=" + hasCommandList
				+ ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter=" + takeScreenshotAfter
				+ ", commandType=" + commandType + ", id=" + id + "]";
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
		TextMatchWaitCommandData other = (TextMatchWaitCommandData) obj;
		if (!super.equals(obj))
			return false;
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
