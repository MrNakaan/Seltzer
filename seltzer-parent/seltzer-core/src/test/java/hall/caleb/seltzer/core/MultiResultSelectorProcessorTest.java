package hall.caleb.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import javax.annotation.Generated;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.ResponseType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.CommandData;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommandData;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.util.CommandFactory;

@Generated(value = "org.junit-tools-1.0.5")
public class MultiResultSelectorProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		// TODO: Make these tests run headless when headless is enabled
		
		SeltzerServer.configureBase();

		session = new SeltzerSession();
		
		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath.replace(" ", "%20") + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
	}

	@AfterClass
	public static void cleanDriver() {
		session.executeCommand(new CommandData(CommandType.EXIT, session.getId()));
	}

	@Before
	public void goToTestHome() {
		session.getDriver().navigate().to(homeUrl);
	}

	@Test
	public void testReadAttribute() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		String xpath = "//div[@id='read']/span";
		String attr = "data-attr1";
		ReadAttributeCommandData command = CommandFactory.newReadAttributeCommand(session.getId(), SelectorType.XPATH,
				xpath, 0, attr);
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

		MultiResultSelectorCommandData command = CommandFactory.newReadTextCommand(session.getId(), SelectorType.XPATH,
				"//div[@id='read']/span", 0);
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
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		mResponse = (MultiResultResponse) response;

		assertTrue("Is there 1 result?", mResponse.getResults().size() == 1);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));

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