package hall.caleb.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import javax.annotation.Generated;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import hall.caleb.selenium.enums.ResponseType;
import hall.caleb.selenium.enums.SelectorType;
import hall.caleb.selenium.objects.command.ChainCommand;
import hall.caleb.selenium.objects.command.Command;
import hall.caleb.selenium.objects.command.CommandFactory;
import hall.caleb.selenium.objects.command.FillFieldCommand;
import hall.caleb.selenium.objects.command.MultiResultSelectorCommand;
import hall.caleb.selenium.objects.command.ReadAttributeCommand;
import hall.caleb.selenium.objects.command.SelectorCommand;
import hall.caleb.selenium.objects.response.ChainResponse;
import hall.caleb.selenium.objects.response.MultiResultResponse;
import hall.caleb.selenium.objects.response.Response;
import hall.caleb.selenium.objects.response.SingleResultResponse;

@Generated(value = "org.junit-tools-1.0.5")
public class CommandProcessorTest {
	private static SeleniumSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeleniumServer.configureBase();

		session = new SeleniumSession();
		
		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath + "/SeleniumServer/selenium-server/src/test/resources/testHome.htm";
	}

	@AfterClass
	public static void cleanDriver() {
		session.getDriver().quit();
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
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testChain() throws Exception {
		ChainCommand command = new ChainCommand(session.getId());
		command.addCommand(CommandFactory.newClickCommand(session.getId(), SelectorType.LinkText, "Page 1"));
		command.addCommand(CommandFactory.newBackCommand(session.getId()));
		command.addCommand(CommandFactory.newForwardCommand(session.getId()));
		command.addCommand(CommandFactory.newCountCommand(session.getId(), SelectorType.Xpath, "//div[@id='count']/span"));
		Response response = session.executeCommand(command);
		
		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Chain, response.getType());
		assertTrue("Is the response a ChainResponse?", response instanceof ChainResponse);
		
		ChainResponse cResponse = (ChainResponse) response;
		
		assertTrue("Are there 4 responses?", cResponse.getResponses().size() == 4);
		for (Response r : cResponse.getResponses()) {
			assertTrue("Was the command a success?", r.isSuccess());
			assertEquals("Make sure IDs match.", session.getId(), r.getId());
		}
		
		String text = ((SingleResultResponse) cResponse.getResponses().get(3)).getResult();
		assertEquals("Is the count right?", Integer.parseInt(text), 9);
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

	@Test
	public void testForward() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();
		session.getDriver().findElement(By.linkText("Test Home")).click();
		session.getDriver().navigate().back();

		Command command = CommandFactory.newForwardCommand(session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testGetUrl() throws Exception {
		Command command = CommandFactory.newGetUrlCommand(session.getId());
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.SingleResult, response.getType());
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
		assertEquals("Is this the right response type?", ResponseType.Basic, response.getType());
		assertEquals("Is the URL right?", homeUrl, session.getDriver().getCurrentUrl());
	}

	@Test
	public void testReadAttribute() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		String xpath = "//div[@id='read']/span";
		String attr = "data-attr1";
		ReadAttributeCommand command = CommandFactory.newReadAttributeCommand(session.getId(), SelectorType.Xpath,
				xpath, 0, attr);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());

		MultiResultResponse mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr1.1' in the results?", mResponse.getResults().contains("attr1.1"));
		assertTrue("Is 'attr1.2' in the results?", mResponse.getResults().contains("attr1.2"));

		attr = "data-attr2";
		command.setAttribute(attr);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr2.1' in the results?", mResponse.getResults().contains("attr2.1"));
		assertTrue("Is 'attr2.2' in the results?", mResponse.getResults().contains("attr2.2"));
		
		attr = "class/data-attr2";
		command.setAttribute(attr);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 2 results?", mResponse.getResults().size() == 2);
		assertTrue("Is 'attr2.1' in the results?", mResponse.getResults().contains("attr2.1"));
		assertTrue("Is 'attr2.2' in the results?", mResponse.getResults().contains("attr2.2"));
	}

	@Test
	public void testReadText() throws Exception {
		session.getDriver().findElement(By.linkText("Page 1")).click();

		MultiResultSelectorCommand command = CommandFactory.newReadTextCommand(session.getId(), SelectorType.Xpath,
				"//div[@id='read']/span", 0);
		Response response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());
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
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		mResponse = (MultiResultResponse) response;

		assertTrue("Is there 1 result?", mResponse.getResults().size() == 1);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));

		command.setMaxResults(3);
		response = session.executeCommand(command);

		assertTrue("Was the command a success?", response.isSuccess());
		assertEquals("Make sure IDs match.", session.getId(), response.getId());
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());
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
		assertEquals("Is this the right response type?", ResponseType.MultiResult, response.getType());
		assertTrue("Is the response a MultiResultResponse?", response instanceof MultiResultResponse);

		mResponse = (MultiResultResponse) response;

		assertTrue("Are there 3 results?", mResponse.getResults().size() == 3);
		assertTrue("Is 'Span 1' in the results?", mResponse.getResults().contains("Span 1"));
		assertTrue("Is 'Span 2' in the results?", mResponse.getResults().contains("Span 2"));
		assertTrue("Is 'Span 3' in the results?", mResponse.getResults().contains("Span 3"));
	}
}