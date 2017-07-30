package tech.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import tech.seltzer.core.SeltzerServer;
import tech.seltzer.core.SeltzerSession;
import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.ReadAttributeCommandData;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class MultiResultSelectorProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("seltzer.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property seltzer.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath.replace(" ", "%20") + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
	}

	@After
	public void cleanDriver() {
		session.executeCommand(new CommandData(CommandType.EXIT, session.getId()));
	}

	@Before
	public void startSession() {
		session = new SeltzerSession();
		session.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		session.getDriver().navigate().to(homeUrl);
	}

	@Test
	public void testReadAttribute() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		String xpath = "//div[@id='read']/span";
		String attr = "data-attr1";
		ReadAttributeCommandData command = new ReadAttributeCommandData(session.getId());
		command.setSelector(xpath, SelectorType.XPATH);
		command.setMaxResults(0);
		command.setAttribute(attr);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());

		MultiResultResponse mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr1.1' in the results?", mResponse.getResults().contains("attr1.1"));
		assertTrue("Is 'attr1.2' in the results?", mResponse.getResults().contains("attr1.2"));

		attr = "data-attr2";
		command.setAttribute(attr);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr2.1' in the results?", mResponse.getResults().contains("attr2.1"));
		assertTrue("Is 'attr2.2' in the results?", mResponse.getResults().contains("attr2.2"));
		
		attr = "class/data-attr2";
		command.setAttribute(attr);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr2.1' in the results?", mResponse.getResults().contains("attr2.1"));
		assertTrue("Is 'attr2.2' in the results?", mResponse.getResults().contains("attr2.2"));
	}

	@Test
	public void testReadText() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		MultiResultSelectorCommandData command = new MultiResultSelectorCommandData(CommandType.READ_TEXT, session.getId());
		command.setSelector("//div[@id='read']/span", SelectorType.XPATH);
		command.setMaxResults(0);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		MultiResultResponse mResponse = (MultiResultResponse) response;

		assertTrue("Are there 3 results?", mResponse.getResults().size() == 3);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));
		assertTrue("Is 'Span 2' in the results?", mResponse.getResults().contains("Span 2"));
		assertTrue("Is 'Span 3' in the results?", mResponse.getResults().contains("Span 3"));

		command.setMaxResults(1);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		assertTrue("Is the response a SingleResultResponse?", response instanceof SingleResultResponse);

		SingleResultResponse sResponse = (SingleResultResponse) response;

		assertTrue("Is 'Span 1' in the results?", sResponse.getResult().contains("Span 1"));

		command.setMaxResults(3);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 3 results?", mResponse.getResults().size() == 3);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));
		assertTrue("Is 'Span 2' in the results?", mResponse.getResults().contains("Span 2"));
		assertTrue("Is 'Span 3' in the results?", mResponse.getResults().contains("Span 3"));

		command.setMaxResults(32);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 3 results?", mResponse.getResults().size() == 3);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));
		assertTrue("Is 'Span 2' in the results?", mResponse.getResults().contains("Span 2"));
		assertTrue("Is 'Span 3' in the results?", mResponse.getResults().contains("Span 3"));
	}
}