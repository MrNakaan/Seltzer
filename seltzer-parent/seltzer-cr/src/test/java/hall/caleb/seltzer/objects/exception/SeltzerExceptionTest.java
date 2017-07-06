package hall.caleb.seltzer.objects.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class SeltzerExceptionTest {
	private String message = "SeltzerException message";

	@Test
	public void testGetMessage() {
		SeltzerException exception = new SeltzerException(message, new StackTraceElement[0]);

		assertEquals(message, exception.getMessage());
	}

	@Test
	public void testGetStackTrace() {
		SeltzerException exception = new SeltzerException(message, new StackTraceElement[0]);

		assertEquals(0, exception.getStackTrace().length);

		exception = new SeltzerException(message,
				new StackTraceElement[] { new StackTraceElement(this.getClass().getName(), "testGetStackTrace",
						"SeltzerExceptionTest.java", 0) });
		StackTraceElement ste = exception.getStackTrace()[0];

		assertEquals(this.getClass().getName(), ste.getClassName());
		assertEquals("testGetStackTrace", ste.getMethodName());
		assertEquals("SeltzerExceptionTest.java", ste.getFileName());
		assertEquals(0, ste.getLineNumber());
	}
}
