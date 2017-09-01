package tech.seltzer.objects.command.selector;

import java.util.UUID;

import tech.seltzer.enums.CommandType;

public final class FillFieldCommandData extends SelectorCommandData {
	protected String text;
	
	public FillFieldCommandData() {
		super(CommandType.FILL_FIELD);
	}

	public FillFieldCommandData(UUID id) {
		super(CommandType.FILL_FIELD, id);
	}

	@Override
	public String toString() {
		return "FillFieldCommandData [text=" + text + ", selector=" + selector + ", hasCommandList=" + hasCommandList
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
		FillFieldCommandData other = (FillFieldCommandData) obj;
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
