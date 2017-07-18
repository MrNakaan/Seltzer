package hall.caleb.seltzer.core;

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

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.ResponseType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.ChainCommandData;
import hall.caleb.seltzer.objects.command.CommandData;
import hall.caleb.seltzer.objects.command.GetCookieCommandData;
import hall.caleb.seltzer.objects.command.GetCookiesCommandData;
import hall.caleb.seltzer.objects.command.GoToCommandData;
import hall.caleb.seltzer.objects.command.Selector;
import hall.caleb.seltzer.objects.command.selector.SelectorCommandData;
import hall.caleb.seltzer.objects.response.ChainResponse;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class BaseProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
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
	public void testBack() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		CommandData command = new CommandData(CommandType.BACK, session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testChain() throws Exception {
		ChainCommandData<CommandData> command = new ChainCommandData<>(session.getId());
		CommandData subCommand;
		
		subCommand = new SelectorCommandData(CommandType.CLICK, session.getId());
		((SelectorCommandData) subCommand).setSelector(new Selector(SelectorType.LINK_TEXT, "Page 1"));
		command.addCommand(subCommand);
		
		command.addCommand(new CommandData(CommandType.BACK, session.getId()));
		command.addCommand(new CommandData(CommandType.FORWARD, session.getId()));
		
		subCommand = new SelectorCommandData(CommandType.COUNT, session.getId());
		((SelectorCommandData) subCommand).setSelector(new Selector(SelectorType.XPATH, "//div[@id='count']/span"));
		command.addCommand(subCommand);
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

		CommandData command = new CommandData(CommandType.FORWARD, session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testGetUrl() throws Exception {
		CommandData command = new CommandData(CommandType.GET_URL, session.getId());
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

		GoToCommandData command = new GoToCommandData(session.getId());
		command.setUrl(homeUrl);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}
	
	@Test
	public void testGetCookie() {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		GetCookieCommandData command = new GetCookieCommandData(session.getId());
		command.setCookieName("cookie1");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		assertEquals("Is the cookie value right?", "Games ", ((SingleResultResponse) response).getResult());
	}
	
	@Test
	public void testGetCookies() {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		GetCookiesCommandData command = new GetCookiesCommandData(session.getId());
		command.addCookie("cookie1");
		command.addCookie("cookie2");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		String name = "";
		for (String c : ((MultiResultResponse) response).getResults()) {
			name += c;
		}
		assertEquals("Is the completed name right?", "Games Done Quick", name);
	}
	
	@Test
	public void testGetCookieFile() {
		
	}
}