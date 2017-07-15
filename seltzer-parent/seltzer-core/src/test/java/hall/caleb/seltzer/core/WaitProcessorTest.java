package hall.caleb.seltzer.core;

import static org.junit.Assert.assertFalse;
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
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommand;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommand;
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
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath.replace(" ", "%20") + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
	}

	@After
	public void cleanDriver() {
		session.executeCommand(new Command(CommandType.EXIT, session.getId()));
	}

	@Before
	public void startSession() {
		session = new SeltzerSession();
		
		session.getDriver().navigate().to(homeUrl);
		session.getDriver().findElement(By.id("waitLink")).click();
	}
	
	@Test
	public void testAlertIsPresentPass() {
		WaitCommand wait = new WaitCommand(10, WaitType.ALERT_PRESENT, session.getId());
		
		session.getDriver().findElement(By.id("alertLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfPass() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ELEMENT_INVISIBLE, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllPass() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ALL_ELEMENTS_INVISBLE, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"] | //h2[@id=\"visible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextPass() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ELEMENT_WITH_TEXT_INVISIBLE, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfPass() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsPass() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.ALL_ELEMENTS_VISIBLE, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByPass() {
		NestedVisibilityWaitCommand wait = new NestedVisibilityWaitCommand(10, WaitType.NESTED_ELEMENTS_VISIBLE, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		wait.setChildSelector("./span", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsPass() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TITLE_CONTAINS, session.getId());
		wait.setText("Change");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleIsPass() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		wait.setText("Title Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsPass() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_CONTAINS, session.getId());
		wait.setText("changed=");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesPass() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_MATCHES, session.getId());
		wait.setText(".*\\?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlToBePass() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_IS, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAndPass() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.AND, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNotPass() {
		LogicalNotWaitCommand wait = new LogicalNotWaitCommand(10, session.getId());
		TextMatchWaitCommand subWait = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait.setText("Wait Home");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testOrPass() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.OR, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testRefreshedPass() {
		RefreshedWaitCommand wait = new RefreshedWaitCommand(10, session.getId());
		TextMatchWaitCommand subWait = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait.setText("Title Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBePass() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_IS, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanPass() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_LESS_THAN, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanPass() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_GREATER_THAN, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBePass() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.WINDOW_COUNT_IS, session.getId());
		wait.setBound(2);
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValuePass() {
		JavaScriptWaitCommand wait = new JavaScriptWaitCommand(10, WaitType.JAVASCRIPT_RETURNS_STRING, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsPass() {
		JavaScriptWaitCommand wait = new JavaScriptWaitCommand(10, WaitType.JAVASCRIPT_THROWS_NO_EXCEPTIONS, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsPass() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_CONTAINS, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBePass() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_IS, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait-attr");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyPass() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_IS_NOT_EMPTY, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTruePass() {
		SelectionStateWaitCommand wait = new SelectionStateWaitCommand(10, WaitType.ELEMENT_SELECTION_STATE_IS, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[4]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalsePass() {
		SelectionStateWaitCommand wait = new SelectionStateWaitCommand(10, WaitType.ELEMENT_SELECTION_STATE_IS, session.getId());
		
		wait.setSelected(false);
		wait.setSelector("//div/form/select/option[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchPass() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.SWITCH_TO_FRAME_WHEN_AVAILABLE, session.getId());
		wait.setSelector("//iframe", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementClickablePass() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ELEMENT_CLICKABLE, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsPass() {
		NestedExistenceWaitCommand wait = new NestedExistenceWaitCommand(10, WaitType.NESTED_ELEMENTS_PRESENT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementPass() {
		NestedExistenceWaitCommand wait = new NestedExistenceWaitCommand(10, WaitType.NESTED_ELEMENT_PRESENT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementPass() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ELEMENT_PRESENT, session.getId());
		wait.setSelector("//h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsPass() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ALL_ELEMENTS_PRESENT, session.getId());
		wait.setSelector("//h2[@id=\"invisible3\"] | //h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testStalenessPass() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.IS_STALE, session.getId());
		wait.setSelector("//h2[@id=\"endEmpty1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesPass() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_MATCHES, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".*Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextIsPass() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_IS, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValuePass() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_IN_ELEMENT_VALUE, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentPass() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_PRESENT_IN_ELEMENT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAlertIsPresentFail() {
		WaitCommand wait = new WaitCommand(10, WaitType.ALERT_PRESENT, session.getId());
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfFail() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ELEMENT_INVISIBLE, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllFail() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ALL_ELEMENTS_INVISBLE, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextFail() {
		InvisibilityWaitCommand wait = new InvisibilityWaitCommand(10, WaitType.ELEMENT_WITH_TEXT_INVISIBLE, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfFail() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsFail() {
		VisibilityWaitCommand wait = new VisibilityWaitCommand(10, WaitType.ALL_ELEMENTS_VISIBLE, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByFail() {
		NestedVisibilityWaitCommand wait = new NestedVisibilityWaitCommand(10, WaitType.NESTED_ELEMENTS_VISIBLE, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsFail() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TITLE_CONTAINS, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleIsFail() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsFail() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_CONTAINS, session.getId());
		wait.setText("changed=");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesFail() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_MATCHES, session.getId());
		wait.setText(".*\\?changed=true");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlToBeFail() {
		TextMatchWaitCommand wait = new TextMatchWaitCommand(10, WaitType.URL_IS, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAndFail() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.AND, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		subWait2.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNotFail() {
		LogicalNotWaitCommand wait = new LogicalNotWaitCommand(10, session.getId());
		InvisibilityWaitCommand subWait = new InvisibilityWaitCommand(10, WaitType.ELEMENT_WITH_TEXT_INVISIBLE, session.getId());
		subWait.setSelector("//body", SelectorType.XPATH);
		subWait.setText("Some Visible Element");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testOrFail() {
		LogicalAndOrWaitCommand wait = new LogicalAndOrWaitCommand(10, WaitType.OR, session.getId());
		TextMatchWaitCommand subWait1 = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait1.setText("Fail");
		VisibilityWaitCommand subWait2 = new VisibilityWaitCommand(10, WaitType.ELEMENT_VISIBLE, session.getId());
		subWait2.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testRefreshedFail() {
		RefreshedWaitCommand wait = new RefreshedWaitCommand(10, session.getId());
		TextMatchWaitCommand subWait = new TextMatchWaitCommand(10, WaitType.TITLE_IS, session.getId());
		subWait.setText("Title Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBeFail() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_IS, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(2);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanFail() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_LESS_THAN, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanFail() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.ELEMENT_COUNT_GREATER_THAN, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBeFail() {
		CountWaitCommand wait = new CountWaitCommand(10, WaitType.WINDOW_COUNT_IS, session.getId());
		wait.setBound(2);
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValueFail() {
		JavaScriptWaitCommand wait = new JavaScriptWaitCommand(10, WaitType.JAVASCRIPT_RETURNS_STRING, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsFail() {
		JavaScriptWaitCommand wait = new JavaScriptWaitCommand(10, WaitType.JAVASCRIPT_THROWS_NO_EXCEPTIONS, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsFail() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_CONTAINS, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBeFail() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_IS, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait-attr");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyFail() {
		TextMatchAttributeSelectorWaitCommand wait = new TextMatchAttributeSelectorWaitCommand(10, WaitType.ATTRIBUTE_IS_NOT_EMPTY, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTrueFail() {
		SelectionStateWaitCommand wait = new SelectionStateWaitCommand(10, WaitType.ELEMENT_SELECTION_STATE_IS, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[4]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalseFail() {
		SelectionStateWaitCommand wait = new SelectionStateWaitCommand(10, WaitType.ELEMENT_SELECTION_STATE_IS, session.getId());
		
		wait.setSelected(false);
		wait.setSelector("//div/form/select/option[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchFail() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.SWITCH_TO_FRAME_WHEN_AVAILABLE, session.getId());
		wait.setSelector("//iframe", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementClickableFail() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ELEMENT_CLICKABLE, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsFail() {
		NestedExistenceWaitCommand wait = new NestedExistenceWaitCommand(10, WaitType.NESTED_ELEMENTS_PRESENT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementFail() {
		NestedExistenceWaitCommand wait = new NestedExistenceWaitCommand(10, WaitType.NESTED_ELEMENT_PRESENT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementFail() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ELEMENT_PRESENT, session.getId());
		wait.setSelector("//h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsFail() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.ALL_ELEMENTS_PRESENT, session.getId());
		wait.setSelector("//h2[@id=\"invisible3\"] | //h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testStalenessFail() {
		ExistenceWaitCommand wait = new ExistenceWaitCommand(10, WaitType.IS_STALE, session.getId());
		wait.setSelector("//h2[@id=\"endEmpty1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesFail() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_MATCHES, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".*Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextIsFail() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_IS, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValueFail() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_IN_ELEMENT_VALUE, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentFail() {
		TextMatchSelectorWaitCommand wait = new TextMatchSelectorWaitCommand(10, WaitType.TEXT_PRESENT_IN_ELEMENT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
}