package tech.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.ChainCommandData;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.GetCookieCommandData;
import tech.seltzer.objects.command.GetCookiesCommandData;
import tech.seltzer.objects.command.GoToCommandData;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class BaseProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("seltzer.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property seltzer.path not found!");
        }
        	
        homeUrl = "http://seltzer.tech/tests/";
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
		
		try {
			BaseProcessorTest.dismissModal(session.getDriver());
		} catch (InterruptedException e) {
			Assume.assumeNoException(e);
		}
	}

	@Test
	public void testBack() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		
		CommandData command = new CommandData(CommandType.BACK, session.getId());
		
		// Multiple back commands due to how Materialize works
		Response response = session.executeCommand(command);
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		
		response = session.executeCommand(command);
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		
		response = session.executeCommand(command);
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
		((SelectorCommandData) subCommand).setSelector(new Selector(SelectorType.LINK_TEXT, "Main Tests 1"));
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
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().findElement(By.linkText("Test Home")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().navigate().back();

		CommandData command = new CommandData(CommandType.FORWARD, session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl + "main1#!", session.getDriver().getCurrentUrl());
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
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		assertTrue("Make sure we're not at the test home.", session.getDriver().getTitle().equals("Main Tests 1 | Seltzer"));

		GoToCommandData command = new GoToCommandData(session.getId());
		command.setUrl(homeUrl);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}
	
	@Test
	public void testGetCookie() throws UnsupportedEncodingException {
		session.getDriver().navigate().to("http://www.whatarecookies.com/cookietest.asp");
		GetCookieCommandData command = new GetCookieCommandData(session.getId());
		command.setCookieName("dta");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		SingleResultResponse sResponse = (SingleResultResponse) response;
		sResponse.setResult(URLDecoder.decode(sResponse.getResult(), "UTF-8"));
		
		Pattern pattern = Pattern.compile("^vcount=.*?,prev=.*$");
		Matcher matcher = pattern.matcher(sResponse.getResult());
		
		assertTrue("Does the cookie contain the right value?", matcher.matches());
	}
	
	@Test
	public void testGetCookies() throws UnsupportedEncodingException {
		session.getDriver().navigate().to("http://www.whatarecookies.com/cookietest.asp");
		GetCookiesCommandData command = new GetCookiesCommandData(session.getId());
		command.addCookie("dta");
		command.addCookie("dta");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MULTI_RESULT, response.getType());
		
		String value = "";
		for (String c : ((MultiResultResponse) response).getResults()) {
			value += URLDecoder.decode(c, "UTF-8");
		}
		
		Pattern pattern = Pattern.compile("^(vcount=.*?,prev=.*?){2}$");
		Matcher matcher = pattern.matcher(value);
		
		assertTrue("Does the cookie contain the right value?", matcher.matches());
	}
	
	@Test
	public void testGetCookieFile() {
		// I'm sorry, I haven't determined the best way to test this yet.
	}
	
	public static void dismissModal(WebDriver driver) throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class=\"modal-footer\"]/a")).click();
		Thread.sleep(1000);
	}
}