package tech.seltzer.objects.command;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.wait.WaitCommandData;

public class RunJavascriptCommandData extends CommandData {
	protected String javascript;
	protected WaitCommandData waitBefore = null;
	protected WaitCommandData waitAfter = null;
	
	public RunJavascriptCommandData() {
		super(CommandType.RUN_JAVASCRIPT);
	}

	public RunJavascriptCommandData(UUID id) {
		super(CommandType.RUN_JAVASCRIPT, id);
	}

	public RunJavascriptCommandData(UUID id, String javascript) {
		super(CommandType.RUN_JAVASCRIPT, id);
		this.javascript = javascript;
	}

	@Override
	public String toString() {
		return "RunJavascriptCommandData [javascript=" + javascript + ", waitBefore=" + waitBefore + ", waitAfter="
				+ waitAfter + ", hasCommandList=" + hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore
				+ ", takeScreenshotAfter=" + takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((javascript == null) ? 0 : javascript.hashCode());
		result = prime * result + ((waitAfter == null) ? 0 : waitAfter.hashCode());
		result = prime * result + ((waitBefore == null) ? 0 : waitBefore.hashCode());
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
		RunJavascriptCommandData other = (RunJavascriptCommandData) obj;
		if (javascript == null) {
			if (other.javascript != null)
				return false;
		} else if (!javascript.equals(other.javascript))
			return false;
		if (waitAfter == null) {
			if (other.waitAfter != null)
				return false;
		} else if (!waitAfter.equals(other.waitAfter))
			return false;
		if (waitBefore == null) {
			if (other.waitBefore != null)
				return false;
		} else if (!waitBefore.equals(other.waitBefore))
			return false;
		return true;
	}

	public String getJavascript() {
		return javascript;
	}

	public void setJavascript(String javascript) {
		this.javascript = javascript;
	}

	public WaitCommandData getWaitBefore() {
		return waitBefore;
	}

	public void setWaitBefore(WaitCommandData waitBefore) {
		this.waitBefore = waitBefore;
	}

	public WaitCommandData getWaitAfter() {
		return waitAfter;
	}

	public void setWaitAfter(WaitCommandData waitAfter) {
		this.waitAfter = waitAfter;
	}
}
