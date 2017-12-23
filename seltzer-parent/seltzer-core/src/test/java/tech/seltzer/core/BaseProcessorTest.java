package tech.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Generated;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.google.gson.Gson;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.ChainCommandData;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.GetCookieCommandData;
import tech.seltzer.objects.command.GetCookiesCommandData;
import tech.seltzer.objects.command.GoToCommandData;
import tech.seltzer.objects.command.RunJavascriptCommandData;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.response.ChainResponse;
import tech.seltzer.objects.response.MultiResultResponse;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class BaseProcessorTest {
	private static final String SQLITE_MAGIC_STRING = "SQLite format 3";
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws IOException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("seltzer.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property seltzer.path not found!");
        }
        	
        homeUrl = "http://seltzer.tech/tests";
//        homeUrl = "file:///" + repoPath + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
//        homeUrl = homeUrl.replace(" ", "%20");
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
//		session.getDriver().findElement(By.linkText("Page 1")).click();
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
//		((SelectorCommandData) subCommand).setSelector(new Selector(SelectorType.LINK_TEXT, "Page 1"));
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
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().findElement(By.linkText("Test Home")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().navigate().back();

		CommandData command = new CommandData(CommandType.FORWARD, session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertEquals("Is the URL right?", homeUrl + "/main1#!", session.getDriver().getCurrentUrl());
//		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
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
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		assertTrue("Make sure we're not at the test home.", session.getDriver().getTitle().equals("Main Tests 1 | Seltzer"));
//		assertTrue("Make sure we're not at the test home.", session.getDriver().getTitle().equals("Test Page 1"));

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
	public void testTakePageScreenshot() throws Exception {
		CommandData command = new CommandData(CommandType.SCREENSHOT_PAGE, session.getId());
		SingleResultResponse response = (SingleResultResponse) session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		assertTrue("Make sure the result that was returned is not empty.", StringUtils.isNotEmpty(response.getResult()));
	}
	
	@Test
	public void testBeforeAndAfterScreenshots() throws Exception {
		CommandData command = new CommandData(CommandType.GET_URL, session.getId());
		command.setTakeScreenshotBefore(true);
		command.setTakeScreenshotAfter(true);
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		assertTrue("Make sure the before screenshot that was returned is not empty.", StringUtils.isNotEmpty(response.getScreenshotBefore()));
		assertTrue("Make sure the after screenshot that was returned is not empty.", StringUtils.isNotEmpty(response.getScreenshotAfter()));
		assertEquals("Make sure the two screenshots are equal.", response.getScreenshotBefore(), response.getScreenshotAfter());
	}
	
	@Test
	public void testGetCookieFile() throws UnsupportedEncodingException {
		Assume.assumeFalse(SeltzerSession.isHeadless());
		
		session.getDriver().navigate().to("http://www.whatarecookies.com/cookietest.asp");
		CommandData command = new CommandData(CommandType.GET_COOKIE_FILE, session.getId());
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		String magicString = ((SingleResultResponse) response).getResult();
		magicString = new String(ArrayUtils.subarray(Base64.getDecoder().decode(magicString), 0, 16));
		assertTrue("Make sure the file starts with the right magic string", magicString.startsWith(SQLITE_MAGIC_STRING));
	}
	
	@Test
	public void testRunJavascriptNoReturn() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = 2;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertNull("Is the result null?", ((SingleResultResponse) response).getResult());
	}
	
	@Test
	public void testRunJavascriptReturnLong() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = 2 + 2; return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertEquals("Does the result parse to a Long with value 4?", Long.valueOf(4), Long.valueOf(((SingleResultResponse) response).getResult()));
	}
	
	@Test
	public void testRunJavascriptReturnDouble() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = 2 + 0.5; return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertEquals("Does the result parse to a Double with value 2.5?", Double.valueOf(2.5), Double.valueOf(((SingleResultResponse) response).getResult()));
	}
	
	@Test
	public void testRunJavascriptReturnString() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = \"foo\"; var bar = \"bar\"; var Baz = \"Baz\"; return foo + bar + Baz;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertEquals("Does the result parse to a String with value 'foobarBaz?", "foobarBaz", new Gson().fromJson(((SingleResultResponse) response).getResult(), String.class));
	}
	
	@Test
	public void testRunJavascriptReturnTrue() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = true; return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertEquals("Does the result parse to a Boolean with value true?", true, Boolean.valueOf(((SingleResultResponse) response).getResult()));
	}
	
	@Test
	public void testRunJavascriptReturnFalse() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = false; return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertEquals("Does the result parse to a Boolean with value false?", false, Boolean.valueOf(((SingleResultResponse) response).getResult()));
	}
	
	@Test
	public void testRunJavascriptReturnArray() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = [2, 4, 8, \"16\"]; return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertTrue("Does the result parse to a List?", new Gson().fromJson(((SingleResultResponse) response).getResult(), List.class) instanceof List<?>);
		List<?> results = new Gson().fromJson(((SingleResultResponse) response).getResult(), List.class);
		assertEquals("Does the result list have a size of 4?", 4, results.size());
		assertTrue("Is element 1 a Number?", results.get(0) instanceof Number);
		assertTrue("Is element 2 a Number?", results.get(1) instanceof Number);
		assertTrue("Is element 3 a Number?", results.get(2) instanceof Number);
		assertTrue("Is element 4 a String?", results.get(3) instanceof String);
		assertEquals("Is element 1 equal to 2?", 2, (long) Double.parseDouble(results.get(0).toString()));
		assertEquals("Is element 1 equal to 4?", 4, (long) Double.parseDouble(results.get(1).toString()));
		assertEquals("Is element 1 equal to 8?", 8, (long) Double.parseDouble(results.get(2).toString()));
		assertEquals("Is element 1 equal to \"16\"?", "16", results.get(3).toString());
	}
	
	// Okay, so, according to my testing, Selenium does not properly return maps...Removing this test pending a fix. Bug filed at https://github.com/SeleniumHQ/selenium/issues/5250
	//@Test
	public void testRunJavascriptReturnMap() {
		RunJavascriptCommandData command = new RunJavascriptCommandData(session.getId(), "var foo = new Map(); foo.set('one', 2); foo.set('two', 4); foo.set('three', 8); foo.set('four', '16'); return foo;");
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());
		
		assertTrue("Does the result parse to a List?", new Gson().fromJson(((SingleResultResponse) response).getResult(), Map.class) instanceof Map<?, ?>);
		Map<?, ?> results = new Gson().fromJson(((SingleResultResponse) response).getResult(), Map.class);
		assertEquals("Does the result list have a size of 4?", 4, results.size());
		assertTrue("Is 'one' in the key set?", results.keySet().contains("one"));
		assertTrue("Is 'two' in the key set?", results.keySet().contains("two"));
		assertTrue("Is 'three' in the key set?", results.keySet().contains("three"));
		assertTrue("Is 'four' in the key set?", results.keySet().contains("four"));
		assertTrue("Is element 1 a Number?", results.get("one") instanceof Number);
		assertTrue("Is element 2 a Number?", results.get("two") instanceof Number);
		assertTrue("Is element 3 a Number?", results.get("three") instanceof Number);
		assertTrue("Is element 4 a String?", results.get("four") instanceof String);
		assertEquals("Is element 1 equal to 2?", 2, (long) Double.parseDouble(results.get("one").toString()));
		assertEquals("Is element 1 equal to 4?", 4, (long) Double.parseDouble(results.get("two").toString()));
		assertEquals("Is element 1 equal to 8?", 8, (long) Double.parseDouble(results.get("three").toString()));
		assertEquals("Is element 1 equal to \"16\"?", "16", results.get("four").toString());
	}
	
	public static void dismissModal(WebDriver driver) throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class=\"modal-footer\"]/a")).click();
		Thread.sleep(1000);
	}
}