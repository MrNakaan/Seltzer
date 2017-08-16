package tech.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Generated;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.enums.SeltzerKeys;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.FillFieldCommandData;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.selector.SendKeyCommandData;
import tech.seltzer.objects.command.selector.SendKeysCommandData;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class SelectorProcessorTest {
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
	public void testClick() throws Exception {
		SelectorCommandData command = new SelectorCommandData(CommandType.CLICK, session.getId());
		command.setSelector(new Selector(SelectorType.LINK_TEXT, "Main Tests 1"));
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertTrue("Is the new page title correct?", session.getDriver().getTitle().equals("Main Tests 1 | Seltzer"));
	}

	@Test
	public void testCount() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		
		SelectorCommandData command = new SelectorCommandData(CommandType.COUNT, session.getId());
		command.setSelector("//div[@id='count']/span", SelectorType.XPATH);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SINGLE_RESULT, response.getType());

		String text = ((SingleResultResponse) response).getResult();

		assertEquals("Is the count right?", Integer.parseInt(text), 9);
	}

	@Test
	public void testDelete() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		
		SelectorCommandData command = new SelectorCommandData(CommandType.DELETE, session.getId());
		command.setSelector("//div[@id='count']/span", SelectorType.XPATH);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());

		int numSpanElements = session.getDriver().findElements(By.xpath("//div[@id='count']/span")).size();
		assertTrue("Are there 0 elements remaining?", numSpanElements == 0);
	}

	@Test
	public void testFillField() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		
		FillFieldCommandData command = new FillFieldCommandData(session.getId());
		command.setSelector("//input[1]", SelectorType.XPATH);
		command.setText("TEXT, BRO!");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());

		String inputText = session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value");
		assertEquals("Make sure the field has the right value now.", "TEXT, BRO!", inputText);
	}

	@Test
	public void testFormSubmit() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		WebElement input = session.getDriver().findElement(By.xpath("//input[1]"));
		input.sendKeys("MORE TEXT, BRO!");
		assertEquals("Make sure the initial text got sent.", "MORE TEXT, BRO!", input.getAttribute("value"));

		SelectorCommandData command = new SelectorCommandData(CommandType.FORM_SUBMIT, session.getId());
		command.setSelector("//form[1]", SelectorType.XPATH);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());
		assertTrue("Is the field now empty?",
				session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value").isEmpty());
	}
	
	@Test
	public void testSendKey() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().findElement(By.xpath("//input[1]")).click();
		session.getDriver().findElement(By.xpath("//input[1]")).sendKeys("DUDE, KEYS!");
		
		SendKeyCommandData command = new SendKeyCommandData(session.getId());
		command.setSelector(new Selector(SelectorType.XPATH, "//input[1]"));
		command.setKey(SeltzerKeys.END);
		Response response = session.executeCommand(command);
		command.setKey(SeltzerKeys.ARROW_LEFT);
		response = session.executeCommand(command);
		for (int i = 0; i < 6; i++) {
			command.setKey(SeltzerKeys.BACK_SPACE);
			response = session.executeCommand(command);
		}

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());

		String inputText = session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value");
		assertEquals("Make sure the field has the right value now.", "DUDE!", inputText);
	}
	
	@Test
	public void testSendKeys() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());
		session.getDriver().findElement(By.xpath("//input[1]")).click();

		SendKeysCommandData command = new SendKeysCommandData(session.getId());
		command.setSelector(new Selector(SelectorType.XPATH, "//input[1]"));
		command.setKeys("DUDE, KEYS!");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.BASIC, response.getType());

		String inputText = session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value");
		assertEquals("Make sure the field has the right value now.", "DUDE, KEYS!", inputText);
	}
}