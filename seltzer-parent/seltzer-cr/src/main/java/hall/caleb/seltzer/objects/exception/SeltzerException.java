package hall.caleb.seltzer.objects.exception;

public class SeltzerException extends Exception {
	private static final long serialVersionUID = -5723798363729577812L;

	public SeltzerException(String message, StackTraceElement[] stackTrace) {
		super(message);
		this.setStackTrace(stackTrace);
	}
}
