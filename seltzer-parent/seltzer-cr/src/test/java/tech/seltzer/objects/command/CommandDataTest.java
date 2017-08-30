package tech.seltzer.objects.command;

import org.junit.Test;

import org.junit.Assert;
import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.command.wait.WaitCommandData;
import tech.seltzer.objects.command.wait.existence.ExistenceWaitCommandData;
import tech.seltzer.objects.command.wait.textmatch.TextMatchWaitCommandData;

public class CommandDataTest {
	@Test
	public void testCommandTypeCheckingHappyPath() {
		new CommandData(CommandType.NONE);
		new CommandData(CommandType.BACK);
		new CommandData(CommandType.FORWARD);

		new SelectorCommandData(CommandType.CLICK);
		new SelectorCommandData(CommandType.FORM_SUBMIT);
		new SelectorCommandData(CommandType.COUNT);

		new MultiResultSelectorCommandData(CommandType.READ_TEXT);

		new WaitCommandData(0, CommandType.WAIT);
		new WaitCommandData(0, CommandType.ALERT_PRESENT_WAIT);

		new ExistenceWaitCommandData(0, CommandType.ELEMENT_PRESENT_WAIT);
		new ExistenceWaitCommandData(0, CommandType.ELEMENT_CLICKABLE_WAIT);
		new ExistenceWaitCommandData(0, CommandType.IS_STALE_WAIT);
		
		new TextMatchWaitCommandData(0, CommandType.URL_IS_WAIT);
		new TextMatchWaitCommandData(0, CommandType.TITLE_IS_WAIT);
	}
	
	@Test
	public void testCommandTypeCheckingLessSpecific() {
		boolean exceptionThrown = false;
		
		try {
			new SelectorCommandData(CommandType.BACK);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
			Assert.assertNotNull(e);
		}
		Assert.assertTrue(exceptionThrown);
		exceptionThrown = false;
		
		try {
			new MultiResultSelectorCommandData(CommandType.BACK);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
			Assert.assertNotNull(e);
		}
		Assert.assertTrue(exceptionThrown);
		exceptionThrown = false;
		
		try {
			new MultiResultSelectorCommandData(CommandType.CLICK);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
			Assert.assertNotNull(e);
		}
		Assert.assertTrue(exceptionThrown);
	}
	
	@Test
	public void testCommandTypeCheckingMoreSpecific() {
		boolean exceptionThrown = false;
		
		try {
			new CommandData(CommandType.CLICK);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
			Assert.assertNotNull(e);
		}
		Assert.assertTrue(exceptionThrown);
		exceptionThrown = false;
		
		try {
			new SelectorCommandData(CommandType.READ_TEXT);
		} catch (IllegalArgumentException e) {
			exceptionThrown = true;
			Assert.assertNotNull(e);
		}
		Assert.assertTrue(exceptionThrown);
	}
}
