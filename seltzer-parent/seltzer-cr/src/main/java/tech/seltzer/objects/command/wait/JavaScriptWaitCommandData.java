package tech.seltzer.objects.command.wait;

import java.util.UUID;

import tech.seltzer.enums.CommandType;

public class JavaScriptWaitCommandData extends WaitCommandData {
	private String javaScript;
	
	public JavaScriptWaitCommandData(Integer seconds) {
		super(seconds);
	}

	public JavaScriptWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public JavaScriptWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}

	@Override
	public String toString() {
		return "JavaScriptWaitCommandData [javaScript=" + javaScript + ", seconds=" + seconds + ", hasCommandList="
				+ hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter="
				+ takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((javaScript == null) ? 0 : javaScript.hashCode());
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
		JavaScriptWaitCommandData other = (JavaScriptWaitCommandData) obj;
		if (!super.equals(obj))
			return false;
		if (javaScript == null) {
			if (other.javaScript != null)
				return false;
		} else if (!javaScript.equals(other.javaScript))
			return false;
		return true;
	}

	public String getJavaScript() {
		return javaScript;
	}

	public void setJavaScript(String javaScript) {
		this.javaScript = javaScript;
	}
}
