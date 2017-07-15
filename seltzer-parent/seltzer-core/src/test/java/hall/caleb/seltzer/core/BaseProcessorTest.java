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
import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;
import hall.caleb.seltzer.util.CommandFactory;

@Generated(value = "org.junit-tools-1.0.5")
public class BaseProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
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
		session.executeCommand(new Command(CommandType.EXIT, session.getId()));
	}

	@Before
	public void goToTestHome() {
		session.getDriver().navigate().to(homeUrl);
	}

	@Test
	public void testBack() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		Command command = CommandFactory.newBackCommand(session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testChain() throws Exception {
		ChainCommand<Command> command = new ChainCommand<>(session.getId());
		command.addCommand(CommandFactory.newClickCommand(session.getId(), SelectorType.LINK_TEXT, "Page 1"));
		command.addCommand(CommandFactory.newBackCommand(session.getId()));
		command.addCommand(CommandFactory.newForwardCommand(session.getId()));
		command.addCommand(CommandFactory.newCountCommand(session.getId(), SelectorType.XPATH, "//div[@id='count']/span"));
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.CHAIN, response.getType());
		assertTrue("Is the response a ChainResponse?", response instanceof ChainResponse);
		
		ChainResponse<?> cResponse = (ChainResponse<?>) response;
		
		assertTrue("Are there 4 responses?", cResponse.getResponses().size() == 4);
		for (Response r : cResponse.getResponses()) {
			assertTrue("Was the command a success?", r.isSuccess());
			assertEquals("Make sure IDs match.", session.getId(), r.getId());
		}
		
		String text = ((SingleResultResponse) cResponse.getResponses().get(3)).getResult();
		assertEquals("Is the count right?", Integer.parseInt(text), 9);
	}

	@Test
	public void testForward() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		session.getDriver().findElement(By.linkText("Test Home")).click();
		session.getDriver().navigate().back();

		Command command = CommandFactory.newForwardCommand(session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testGetUrl() throws Exception {
		Command command = CommandFactory.newGetUrlCommand(session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		assertTrue("Is the response a SingleResultResponse?", response instanceof SingleResultResponse);

		String text = ((SingleResultResponse) response).getResult();

		assertEquals("Is the URL right?", text, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testGoTo() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		assertTrue("Make sure we're not at the test home.", session.getDriver().getTitle().equals("Test Page 1"));

		Command command = CommandFactory.newGoToCommand(session.getId(), homeUrl);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}
}