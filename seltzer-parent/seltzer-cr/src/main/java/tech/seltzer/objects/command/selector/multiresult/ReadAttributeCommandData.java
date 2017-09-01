package tech.seltzer.objects.command.selector.multiresult;

import java.util.UUID;

import tech.seltzer.enums.CommandType;

public final class ReadAttributeCommandData extends MultiResultSelectorCommandData {
	protected String attribute;
	
	public ReadAttributeCommandData() {
		super(CommandType.READ_ATTRIBUTE);
	}

	public ReadAttributeCommandData(UUID id) {
		super(CommandType.READ_ATTRIBUTE, id);
	}

	@Override
	public String toString() {
		return "ReadAttributeCommandData [attribute=" + attribute + ", maxResults=" + maxResults + ", selector="
				+ selector + ", hasCommandList=" + hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore
				+ ", takeScreenshotAfter=" + takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
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
		ReadAttributeCommandData other = (ReadAttributeCommandData) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		return true;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
}
