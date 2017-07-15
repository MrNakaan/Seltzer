package hall.caleb.seltzer.objects.command.wait;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class JavaScriptWaitCommand extends WaitCommand {
	private String javaScript;
	
	public JavaScriptWaitCommand(Integer seconds) {
		super(seconds);
	}

	public JavaScriptWaitCommand(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	public JavaScriptWaitCommand(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}

	@Override
	public String toString() {
		return "JavaScriptWaitCommand [javaScript=" + javaScript + ", seconds=" + seconds + ", hasCommandList="
				+ hasCommandList + ", type=" + type + ", id=" + id + "]";
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
		JavaScriptWaitCommand other = (JavaScriptWaitCommand) obj;
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
