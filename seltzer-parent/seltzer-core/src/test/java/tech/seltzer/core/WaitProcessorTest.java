package tech.seltzer.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.wait.CountWaitCommandData;
import tech.seltzer.objects.command.wait.JavaScriptWaitCommandData;
import tech.seltzer.objects.command.wait.RefreshedWaitCommandData;
import tech.seltzer.objects.command.wait.SelectionStateWaitCommandData;
import tech.seltzer.objects.command.wait.WaitCommandData;
import tech.seltzer.objects.command.wait.existence.ExistenceWaitCommandData;
import tech.seltzer.objects.command.wait.existence.NestedExistenceWaitCommandData;
import tech.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommandData;
import tech.seltzer.objects.command.wait.logical.LogicalNotWaitCommandData;
import tech.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommandData;
import tech.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommandData;
import tech.seltzer.objects.command.wait.textmatch.TextMatchWaitCommandData;
import tech.seltzer.objects.command.wait.visibility.InvisibilityWaitCommandData;
import tech.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommandData;
import tech.seltzer.objects.command.wait.visibility.VisibilityWaitCommandData;
import tech.seltzer.objects.response.Response;

public class WaitProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	private static final int WAIT_SECONDS = 5;
	private static final int JS_DELAY = 2;
	
	private long startTime = 0;
	
	@BeforeClass
	public static void prepareClass() throws IOException {
		SeltzerServer.configureBase();

		String repoPath = System.getProperty("seltzer.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property seltzer.path not found!");
        }
        	
        homeUrl = "http://seltzer.tech/tests";
//		homeUrl = "file:///" + repoPath + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
//		homeUrl = homeUrl.replace(" ", "%20");
	}

	@After
	public void cleanDriver() {
		session.executeCommand(new CommandData(CommandType.EXIT, session.getId()));
	}

	@Before
	public void startSession() {
		session = new SeltzerSession();
		
		session.getDriver().navigate().to(homeUrl);
		
		try {
			BaseProcessorTest.dismissModal(session.getDriver());
		} catch (InterruptedException e) {
			Assume.assumeNoException(e);
		}
		
		session.getDriver().findElement(By.linkText("Wait Tests")).click();
//		session.getDriver().findElement(By.linkText("Wait Page")).click();
		
		try {
			startTime = System.currentTimeMillis();
			Thread.sleep(1000);
			session.getDriver().findElement(By.xpath("//div[@class=\"modal-footer\"]/a")).click();
		} catch (InterruptedException e) {
			Assume.assumeNoException(e);
		}
//		startTime = System.currentTimeMillis();
	}
	
	@Test
	public void testAlertIsPresentPass() {
		Assume.assumeFalse(SeltzerSession.isHeadless());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WaitCommandData wait = new WaitCommandData(WAIT_SECONDS, CommandType.ALERT_PRESENT_WAIT, session.getId());
		
		session.getDriver().findElement(By.id("alertLink")).click();

		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_INVISBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"] | //h2[@id=\"visible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextPass() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"visible1\"]", SelectorType.XPATH);
		wait.setText("Some Visible Element");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfPass() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsPass() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByPass() {
		NestedVisibilityWaitCommandData wait = new NestedVisibilityWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"] | //h2[@id=\"invisible2\"]", SelectorType.XPATH);
		wait.setChildSelector("./span", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_CONTAINS_WAIT, session.getId());
		wait.setText("Change");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTitleIsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		wait.setText("Title Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsPass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_CONTAINS_WAIT, session.getId());
		wait.setText("changed=");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail();
		}
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesPass() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_MATCHES_WAIT, session.getId());
		wait.setText(".*\\?changed=true");
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testUrlToBePass() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_IS_WAIT, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail();
		}
		
		session.getDriver().findElement(By.id("urlLink")).click();
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAndPass() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(WAIT_SECONDS, CommandType.AND_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNotPass() {
		LogicalNotWaitCommandData wait = new LogicalNotWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Tests | Seltzer");
//		subWait.setText("Wait Home");
		
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testOrPass() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(WAIT_SECONDS, CommandType.OR_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testRefreshedPass() {
		RefreshedWaitCommandData wait = new RefreshedWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Title Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBePass() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_IS_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(2);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanPass() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_LESS_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(1);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanPass() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_GREATER_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBePass() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.WINDOW_COUNT_IS_WAIT, session.getId());
		wait.setBound(2);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail();
		}
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValuePass() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(WAIT_SECONDS, CommandType.JAVASCRIPT_RETURNS_STRING_WAIT, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsPass() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(WAIT_SECONDS, CommandType.JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT, session.getId());
		wait.setJavaScript("return \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsPass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_CONTAINS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBePass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_IS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("wait-attr");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyPass() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_IS_NOT_EMPTY_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTruePass() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[4]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalsePass() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(false);
		wait.setSelector("//div/form/select/option[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT, session.getId());
		wait.setSelector("//iframe", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testElementClickablePass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_CLICKABLE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsPass() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementPass() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h2[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"invisible3\"] | //h2[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testStalenessPass() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.IS_STALE_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"endEmpty1\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_MATCHES_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".*Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextIsPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_IS_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValuePass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_IN_ELEMENT_VALUE_WAIT, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testTextPresentPass() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_PRESENT_IN_ELEMENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds <= WAIT_SECONDS);
		assertTrue(seconds >= JS_DELAY);
		assertTrue(response.isSuccess());
	}
	
	@Test
	public void testAlertIsPresentFail() {
		Assume.assumeFalse(SeltzerSession.isHeadless());
		
		WaitCommandData wait = new WaitCommandData(WAIT_SECONDS, CommandType.ALERT_PRESENT_WAIT, session.getId());
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfAllFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_INVISBLE_WAIT, session.getId());
		wait.setSelector("//body", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testInvisibilityOfElementWithTextFail() {
		InvisibilityWaitCommandData wait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"visible\"]/h2", SelectorType.XPATH);
		wait.setText("Some Permanently Visible Element");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfFail() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfAllElementsFail() {
		VisibilityWaitCommandData wait = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testVisibilityOfNestedElementsLocatedByFail() {
		NestedVisibilityWaitCommandData wait = new NestedVisibilityWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENTS_VISIBLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		wait.setChildSelector("./h3", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleContainsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_CONTAINS_WAIT, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTitleIsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		wait.setText("Fail");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlContainsFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_CONTAINS_WAIT, session.getId());
		wait.setText("changed=");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlMatchesFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_MATCHES_WAIT, session.getId());
		wait.setText(".*\\?changed=false");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testUrlToBeFail() {
		TextMatchWaitCommandData wait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.URL_IS_WAIT, session.getId());
		wait.setText(session.getDriver().getCurrentUrl() + "?changed=true");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAndFail() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(WAIT_SECONDS, CommandType.AND_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Title Not Changed!");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//div[@id=\"hidden\"]", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNotFail() {
		LogicalNotWaitCommandData wait = new LogicalNotWaitCommandData(10, session.getId());
		InvisibilityWaitCommandData subWait = new InvisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_WITH_TEXT_INVISIBLE_WAIT, session.getId());
		subWait.setSelector("//body", SelectorType.XPATH);
		subWait.setText("Some Visible Element");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testOrFail() {
		LogicalAndOrWaitCommandData wait = new LogicalAndOrWaitCommandData(WAIT_SECONDS, CommandType.OR_WAIT, session.getId());
		TextMatchWaitCommandData subWait1 = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait1.setText("Fail");
		VisibilityWaitCommandData subWait2 = new VisibilityWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_VISIBLE_WAIT, session.getId());
		subWait2.setSelector("//head", SelectorType.XPATH);
		
		wait.addCommand(subWait1);
		wait.addCommand(subWait2);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testRefreshedFail() {
		RefreshedWaitCommandData wait = new RefreshedWaitCommandData(10, session.getId());
		TextMatchWaitCommandData subWait = new TextMatchWaitCommandData(WAIT_SECONDS, CommandType.TITLE_IS_WAIT, session.getId());
		subWait.setText("Title Not Changed!");
		wait.setWaitCommand(subWait);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testNumberOfElementsToBeFail() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_IS_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(4);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeLessThanFail() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_LESS_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"endEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(0);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfElementsToBeMoreThanFail() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_COUNT_GREATER_THAN_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]/h2", SelectorType.XPATH);
		wait.setBound(8);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testNumberOfWindowsToBeFail() {
		CountWaitCommandData wait = new CountWaitCommandData(WAIT_SECONDS, CommandType.WINDOW_COUNT_IS_WAIT, session.getId());
		wait.setBound(8);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail();
		}
		
		session.getDriver().findElement(By.id("windowLink")).click();
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testJavascriptReturnsValueFail() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(WAIT_SECONDS, CommandType.JAVASCRIPT_RETURNS_STRING_WAIT, session.getId());
		wait.setJavaScript("//Comment;");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= JS_DELAY);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testJavascriptThrowsNoExceptionsFail() {
		JavaScriptWaitCommandData wait = new JavaScriptWaitCommandData(WAIT_SECONDS, CommandType.JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT, session.getId());
		wait.setJavaScript("throw \"Wait.\";");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= JS_DELAY);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeContainsFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_CONTAINS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("gogogogogo");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testAttributeToBeFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_IS_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-wait");
		wait.setText("nowait-attr");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testAttributeToBeNotEmptyFail() {
		TextMatchAttributeSelectorWaitCommandData wait = new TextMatchAttributeSelectorWaitCommandData(WAIT_SECONDS, CommandType.ATTRIBUTE_IS_NOT_EMPTY_WAIT, session.getId());
		
		wait.setSelector("//h2[@id=\"invisible1\"]", SelectorType.XPATH);
		wait.setAttribute("data-nowait");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeTrueFail() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[2]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementSelectionStateToBeFalseFail() {
		SelectionStateWaitCommandData wait = new SelectionStateWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_SELECTION_STATE_IS_WAIT, session.getId());
		
		wait.setSelected(true);
		wait.setSelector("//div/form/select/option[2]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}

	@Test
	public void testFrameToBeAvailableSwitchFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT, session.getId());
		wait.setSelector("//iframe[2]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testElementClickableFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_CLICKABLE_WAIT, session.getId());
		wait.setSelector("//div[@id=\"hidden\"]/h2", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllNestedElementsFail() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h3", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfNestedElementFail() {
		NestedExistenceWaitCommandData wait = new NestedExistenceWaitCommandData(WAIT_SECONDS, CommandType.NESTED_ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//div[@id=\"startEmpty\"]", SelectorType.XPATH);
		wait.setChildSelector("./h3[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfElementFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ELEMENT_PRESENT_WAIT, session.getId());
		wait.setSelector("//h3[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testPresenceOfAllElementsFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.ALL_ELEMENTS_PRESENT_WAIT, session.getId());
		wait.setSelector("//h3[@id=\"invisible3\"] | //h3[@id=\"invisible4\"]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testStalenessFail() {
		ExistenceWaitCommandData wait = new ExistenceWaitCommandData(WAIT_SECONDS, CommandType.IS_STALE_WAIT, session.getId());
		wait.setSelector("//body[1]", SelectorType.XPATH);
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextMatchesFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_MATCHES_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText(".* Not Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextIsFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_IS_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Text Not Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentInValueFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_IN_ELEMENT_VALUE_WAIT, session.getId());
		wait.setSelector("//input[@id=\"changeTextInput\"]", SelectorType.XPATH);
		wait.setText("Text Not Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
	
	@Test
	public void testTextPresentFail() {
		TextMatchSelectorWaitCommandData wait = new TextMatchSelectorWaitCommandData(WAIT_SECONDS, CommandType.TEXT_PRESENT_IN_ELEMENT_WAIT, session.getId());
		wait.setSelector("//h2[@id=\"changeText\"]", SelectorType.XPATH);
		wait.setText("Not Changed!");
		
		Response response = session.executeCommand(wait);
		
		long endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		assertTrue(seconds >= WAIT_SECONDS);
		assertFalse(response.isSuccess());
	}
}