package hall.caleb.seltzer.core;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommand;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		// TODO: Make these tests run headless when headless is enabled
		
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath.replace(" ", "%20") + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
	}

	@After
	public void cleanDriver() {
		session.executeCommand(new Command(CommandType.Exit, session.getId()));
	}

	@Before
	public void startSession() {
		session = new SeltzerSession();
		
		session.getDriver().navigate().to(homeUrl);
		session.getDriver().findElement(By.id("waitLink")).click();
	}
	
	@Test
	public void testAlertIsPresent() {
		WaitCommand wait = new WaitCommand(10, WaitType.AlertIsPresent, session.getId());
		
		session.getDriver().findElement(By.id("alertLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOf() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.InvisibilityOf, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.Xpath);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAll() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.InvisibilityOfAllElements, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"] | //h2[@id=\"visible2\"]", SelectorType.Xpath);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithText() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.InvisibilityOfElementWithText, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.Xpath);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOf() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.VisibilityOf, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.Xpath);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElements() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.VisibilityOfAllElements, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.Xpath);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedBy() {
		NestedVisibilityWaitCommand wait = new NestedVisibilityWaitCommand(10, WaitType.VisibilityOfNestedElementsLocatedBy, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.Xpath);
		wait.setChildSelector("//span", SelectorType.Xpath);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleContains() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TitleContains, session.getId());
		wait.setText("Change");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleIs() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TitleIs, session.getId());
		wait.setText("Title Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlContains() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.UrlContains, session.getId());
		wait.setText("changed=");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlMatches() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.UrlMatches, session.getId());
		wait.setText(".*\\?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlToBe() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.UrlToBe, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAnd() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.And, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TitleIs, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.VisibilityOf, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.Xpath);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNot() {
		LogicalNotWaitCommand wait = new LogicalNotWaitCommand(10, session.getId());
		TextMatchWaitCommand subWait = new TextMatchWaitCommand(10, WaitType.TitleIs, session.getId());
		subWait.setText("Wait Home");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testOr() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.Or, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TitleIs, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.VisibilityOf, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.Xpath);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testRefreshed() {
		RefreshedWaitCommand wait = new RefreshedWaitCommand(10, session.getId());
		TextMatchWaitCommand subWait = new TextMatchWaitCommand(10, WaitType.TitleIs, session.getId());
		subWait.setText("Title Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBe() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.NumberOfElementsToBe, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.Xpath);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThan() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.NumberOfElementsToBeLessThan, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.Xpath);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThan() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.NumberOfElementsToBeMoreThan, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.Xpath);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBe() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.NumberOfWindowsToBe, session.getId());
		wait.setBound(2);
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
}