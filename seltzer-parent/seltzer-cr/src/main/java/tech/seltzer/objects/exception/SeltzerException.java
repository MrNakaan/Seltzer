package tech.seltzer.objects.exception;

/**
 * An exception for use within Seltzer and the programs that use it.
 */
public class SeltzerException extends Exception {
	private static final long serialVersionUID = -5723798363729577812L;

	public SeltzerException(String message, StackTraceElement[] stackTrace) {
		super(message);
		this.setStackTrace(stackTrace);
	}
}
