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
import org.openqa.selenium.WebElement;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.ResponseType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.FillFieldCommand;
import hall.caleb.seltzer.objects.command.SelectorCommand;
import hall.caleb.seltzer.objects.response.Response;
import hall.caleb.seltzer.objects.response.SingleResultResponse;
import hall.caleb.seltzer.util.CommandFactory;

@Generated(value = "org.junit-tools-1.0.5")
public class SelectorProcessorTest {
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
		session.executeCommand(new Command(CommandType.Exit, session.getId()));
	}

	@Before
	public void goToTestHome() {
		session.getDriver().navigate().to(homeUrl);
	}

	@Test
	public void testClick() throws Exception {
		SelectorCommand command = CommandFactory.newClickCommand(session.getId(), SelectorType.LinkText, "Page 1");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());
		assertTrue("Is the new page title correct?", session.getDriver().getTitle().equals("Test Page 1"));
	}

	@Test
	public void testCount() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		SelectorCommand command = CommandFactory.newCountCommand(session.getId(), SelectorType.Xpath,
				"//div[@id='count']/span");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SingleResult, response.getType());

		String text = ((SingleResultResponse) response).getResult();

		assertEquals("Is the count right?", Integer.parseInt(text), 9);
	}

	@Test
	public void testDelete() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		SelectorCommand command = CommandFactory.newDeleteCommand(session.getId(), "//div[@id='count']/span");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());

		int numSpanElements = session.getDriver().findElements(By.xpath("//div[@id='count']/span")).size();
		assertTrue("Are there 0 elements remaining?", numSpanElements == 0);
	}

	@Test
	public void testFillField() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		FillFieldCommand command = CommandFactory.newFillFieldCommand(session.getId(), SelectorType.Xpath, "//input[1]",
				"TEXT, BRO!");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());

		String inputText = session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value");
		assertEquals("Make sure the field has the right value now.", "TEXT, BRO!", inputText);
	}

	@Test
	public void testFormSubmit() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		WebElement input = session.getDriver().findElement(By.xpath("//input[1]"));
		input.sendKeys("MORE TEXT, BRO!");
		assertEquals("Make sure the initial text got sent.", "MORE TEXT, BRO!", input.getAttribute("value"));

		SelectorCommand command = CommandFactory.newFormSubmitCommand(session.getId(), SelectorType.Xpath, "//form[1]");
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());
		assertTrue("Is the field now empty?",
				session.getDriver().findElement(By.xpath("//input[1]")).getAttribute("value").isEmpty());
	}
}