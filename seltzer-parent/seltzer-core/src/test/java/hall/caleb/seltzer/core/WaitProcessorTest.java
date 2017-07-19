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
import hall.caleb.seltzer.objects.command.CommandData;
import hall.caleb.seltzer.objects.command.wait.CountWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.WaitCommandData;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommandData;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommandData;
import hall.caleb.seltzer.objects.response.Response;

public class WaitProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("seltzer.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property seltzer.path not found!");
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
		
		session.getDriver().navigate().to(homeUrl);
		session.getDriver().findElement(By.id("waitLink")).click();
	}
	
	@Test
	public void testAlertIsPresentPass() {
		WaitCommandData wait = new WaitCommandData(5, CommandType.ALERT_PRESENT_WAIT, session.getId());
		
		session.getDriver().findElement(By.id("alertLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ELEMENT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ALL_ELEMENTS_INVISBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"] | //h2[@id=\"visible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfPass() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsPass() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(5, CommandType.ALL_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByPass() {
		NestedVisibilityWaitCommandData wait = new NestedVisibilityWaitCommandData(5, CommandType.NESTED_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		wait.setChildSelector("./span", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.TITLE_CONTAINS_WAIT, session.getId());
		wait.setText("Change");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleIsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		wait.setText("Title Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_CONTAINS_WAIT, session.getId());
		wait.setText("changed=");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_MATCHES_WAIT, session.getId());
		wait.setText(".*\\?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlToBePass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_IS_WAIT, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAndPass() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(5, CommandType.AND_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNotPass() {
		LogicalNotWaitCommandData wait = new LogicalNotWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Wait Home");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testOrPass() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(5, CommandType.OR_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testRefreshedPass() {
		RefreshedWaitCommandData wait = new RefreshedWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Title Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBePass() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_IS_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(2);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanPass() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_LESS_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanPass() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_GREATER_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBePass() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.WINDOW_COUNT_IS_WAIT, session.getId());
		wait.setBound(2);
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValuePass() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(5, CommandType.JAVASCRIPT_RETURNS_STRING_WAIT, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsPass() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(5, CommandType.JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsPass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_CONTAINS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBePass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_IS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait-attr");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyPass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_IS_NOT_EMPTY_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTruePass() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(5, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[4]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalsePass() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(5, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(false);
		wait.setSelector("//div/form/select/option[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT, session.getId());
		wait.setSelector("//iframe", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementClickablePass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ELEMENT_CLICKABLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsPass() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(5, CommandType.NESTED_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementPass() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(5, CommandType.NESTED_ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ALL_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible3\"] | //h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testStalenessPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.IS_STALE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"endEmpty1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_MATCHES_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".*Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextIsPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_IS_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValuePass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_IN_ELEMENT_VALUE_WAIT, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_PRESENT_IN_ELEMENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Changed!");
		
		Response response = session.executeCommand(wait);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAlertIsPresentFail() {
		WaitCommandData wait = new WaitCommandData(5, CommandType.ALERT_PRESENT_WAIT, session.getId());
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ELEMENT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ALL_ELEMENTS_INVISBLE_WAIT, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(5, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfFail() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsFail() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(5, CommandType.ALL_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByFail() {
		NestedVisibilityWaitCommandData wait = new NestedVisibilityWaitCommandData(5, CommandType.NESTED_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		wait.setChildSelector("./h3", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.TITLE_CONTAINS_WAIT, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleIsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_CONTAINS_WAIT, session.getId());
		wait.setText("changed=");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_MATCHES_WAIT, session.getId());
		wait.setText(".*\\?changed=false");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlToBeFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(5, CommandType.URL_IS_WAIT, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAndFail() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(5, CommandType.AND_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNotFail() {
		LogicalNotWaitCommandData wait = new LogicalNotWaitCommandData(10, session.getId());
		InvisibilityWaitCommandData subWait = new InvisibilityWaitCommandData(5, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		subWait.setSelector("//body", SelectorType.XPATH);
		subWait.setText("Some Visible Element");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testOrFail() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(5, CommandType.OR_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Fail");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(5, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testRefreshedFail() {
		RefreshedWaitCommandData wait = new RefreshedWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(5, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Title Not Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBeFail() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_IS_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(4);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanFail() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_LESS_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(4);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanFail() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.ELEMENT_COUNT_GREATER_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(8);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBeFail() {
		CountWaitCommandData wait = new CountWaitCommandData(5, CommandType.WINDOW_COUNT_IS_WAIT, session.getId());
		wait.setBound(2);
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValueFail() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(5, CommandType.JAVASCRIPT_RETURNS_STRING_WAIT, session.getId());
		wait.setJavaScript("//Comment;");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsFail() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(5, CommandType.JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_CONTAINS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("gogogogogo");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBeFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_IS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("nowait-attr");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(5, CommandType.ATTRIBUTE_IS_NOT_EMPTY_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-nowait");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTrueFail() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(5, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[4]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalseFail() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(5, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[2]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT, session.getId());
		wait.setSelector("//iframe[2]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementClickableFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ELEMENT_CLICKABLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsFail() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(5, CommandType.NESTED_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h3", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementFail() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(5, CommandType.NESTED_ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.ALL_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible3\"] | //h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testStalenessFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(5, CommandType.IS_STALE_WAIT, session.getId());
		wait.setSelector("//body[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_MATCHES_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".*Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextIsFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_IS_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Not Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValueFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_IN_ELEMENT_VALUE_WAIT, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Not Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(5, CommandType.TEXT_PRESENT_IN_ELEMENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Not Changed!");
		
		Response response = session.executeCommand(wait);
		assertFalse(response.isSuccess());
	}
}