package tech.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import tech.seltzer.enums.CacheClearStrategy;
import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.ResponseType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.Selector;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.response.Response;
import tech.seltzer.objects.response.SingleResultResponse;

public class SeltzerSessionTest {
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
	}
	
	@Before
	public void startSession() throws Exception {
		session = new SeltzerSession();
		session.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		session.getDriver().navigate().to(homeUrl);
		BaseProcessorTest.dismissModal(session.getDriver());
	}
	
	@After
	public void cleanDriver() {
		session.executeCommand(new CommandData(CommandType.EXIT, session.getId()));
	}

	@Test
	public void testFindSession() {
		SeltzerSession decoySession = new SeltzerSession();
		assertEquals("Does the session returned with a string search match?", session, SeltzerSession.findSession(session.getId().toString()));
		assertNotEquals("Does the session returned with a string search match the decoy?", decoySession, SeltzerSession.findSession(session.getId().toString()));
		assertEquals("Does the session returned match?", session, SeltzerSession.findSession(session.getId()));
		assertNotEquals("Does the session returned match the decoy?", decoySession, SeltzerSession.findSession(session.getId()));
	}
	
	@Test
	public void testCacheSingleElementWithClearStrategyNone() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());

		int iterations = 5;
		int index = -1;
		String xpath = "//div[@id='count']/span";
		WebDriver driver = session.getDriver();
		
		SeltzerSession.setSelectionCacheSize(iterations);
		SeltzerSession.setWebElementCacheClearStrategy(CacheClearStrategy.NONE);
		
		// Load up the cache
		for (int i = 0; i < iterations; i++) {
			index = session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath)));
			
			assertEquals("Make sure the returned index equals i.", i, index);
			assertEquals("Make sure there's only 1 element in this cached selection.", 1, session.getCachedSelection(i).size());
			assertEquals("Make sure only the first matching element was grabbed.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(i).get(0).getText());
			assertNull("Make sure that asking for the next index returns null.", session.getCachedSelection(i + 1));
		}
		
		assertEquals("Make sure the element is rejected.", -1, session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath + "[4]"))));

		for (int i = 0; i < iterations; i++) {
			assertNotNull("Make sure element " + (i + 1) + " isn't null.", session.getCachedSelection(iterations - 1));
			assertEquals("Make sure element " + (i + 1) + " is still the first element.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(iterations - 1).get(0).getText());
		}
	}
	
	@Test
	public void testCacheSingleElementWithClearStrategyFifo() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());

		int iterations = 5;
		int index = -1;
		String xpath = "//div[@id='count']/span";
		WebDriver driver = session.getDriver();
		
		SeltzerSession.setSelectionCacheSize(iterations);
		SeltzerSession.setWebElementCacheClearStrategy(CacheClearStrategy.FIFO);

		fail("Code below not ready yet.");
		// Load up the cache
		for (int i = 0; i < iterations; i++) {
			index = session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath)));
			
			assertEquals("Make sure the returned index equals i.", i, index);
			assertEquals("Make sure there's only 1 element in this cached selection.", 1, session.getCachedSelection(i).size());
			assertEquals("Make sure only the first matching element was grabbed.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(i).get(0).getText());
			assertNull("Make sure that asking for the next index returns null.", session.getCachedSelection(i + 1));
		}
		
		assertEquals("Make sure the element is rejected.", -1, session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath + "[4]"))));

		for (int i = 0; i < iterations; i++) {
			assertNotNull("Make sure element " + (i + 1) + " isn't null.", session.getCachedSelection(iterations - 1));
			assertEquals("Make sure element " + (i + 1) + " is still the first element.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(iterations - 1).get(0).getText());
		}
	}
	
	@Test
	public void testCacheSingleElementWithClearStrategyLifo() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());

		int iterations = 5;
		int index = -1;
		String xpath = "//div[@id='count']/span";
		WebDriver driver = session.getDriver();
		
		SeltzerSession.setSelectionCacheSize(iterations);
		SeltzerSession.setWebElementCacheClearStrategy(CacheClearStrategy.LIFO);
		
		fail("Code below not ready yet.");
		// Load up the cache
		for (int i = 0; i < iterations; i++) {
			index = session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath)));
			
			assertEquals("Make sure the returned index equals i.", i, index);
			assertEquals("Make sure there's only 1 element in this cached selection.", 1, session.getCachedSelection(i).size());
			assertEquals("Make sure only the first matching element was grabbed.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(i).get(0).getText());
			assertNull("Make sure that asking for the next index returns null.", session.getCachedSelection(i + 1));
		}
		
		assertEquals("Make sure the element is rejected.", -1, session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath + "[4]"))));

		for (int i = 0; i < iterations; i++) {
			assertNotNull("Make sure element " + (i + 1) + " isn't null.", session.getCachedSelection(iterations - 1));
			assertEquals("Make sure element " + (i + 1) + " is still the first element.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(iterations - 1).get(0).getText());
		}
	}
	
	@Test
	public void testCacheSingleElementWithClearStrategyLeastRecentlyUsed() throws Exception {
		session.getDriver().findElement(By.linkText("Main Tests 1")).click();
//		session.getDriver().findElement(By.linkText("Page 1")).click();
		BaseProcessorTest.dismissModal(session.getDriver());

		int iterations = 5;
		int index = -1;
		String xpath = "//div[@id='count']/span";
		WebDriver driver = session.getDriver();
		
		SeltzerSession.setSelectionCacheSize(iterations);
		SeltzerSession.setWebElementCacheClearStrategy(CacheClearStrategy.LEAST_RECENTLY_USED);
		
		fail("Code below not ready yet.");
		// Load up the cache
		for (int i = 0; i < iterations; i++) {
			index = session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath)));
			
			assertEquals("Make sure the returned index equals i.", i, index);
			assertEquals("Make sure there's only 1 element in this cached selection.", 1, session.getCachedSelection(i).size());
			assertEquals("Make sure only the first matching element was grabbed.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(i).get(0).getText());
			assertNull("Make sure that asking for the next index returns null.", session.getCachedSelection(i + 1));
		}
		
		assertEquals("Make sure the element is rejected.", -1, session.cacheWebElement(new Selector(SelectorType.XPATH, xpath), driver.findElement(By.xpath(xpath + "[4]"))));

		for (int i = 0; i < iterations; i++) {
			assertNotNull("Make sure element " + (i + 1) + " isn't null.", session.getCachedSelection(iterations - 1));
			assertEquals("Make sure element " + (i + 1) + " is still the first element.", driver.findElement(By.xpath(xpath + "[1]")).getText(), session.getCachedSelection(iterations - 1).get(0).getText());
		}
	}
}
