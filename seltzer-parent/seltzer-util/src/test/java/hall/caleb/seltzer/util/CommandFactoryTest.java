package hall.caleb.seltzer.util;

import java.util.UUID;

import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;
import hall.caleb.seltzer.util.CommandFactory;

@Generated(value = "org.junit-tools-1.0.5")
public class CommandFactoryTest {
	@Test
	public void testNewStartCommand() throws Exception {
		Command result;

		result = CommandFactory.newStartCommand();
		
		Assert.assertEquals("Make sure the command type is Start.", result.getType(), CommandType.Start);
		Assert.assertNull("Make sure the ID is null.", result.getId());
	}

	@Test
	public void testNewExitCommand() throws Exception {
		UUID id = UUID.randomUUID();
		Command result;

		result = CommandFactory.newExitCommand(id);
		
		Assert.assertEquals("Make sure the command type is Exit.", result.getType(), CommandType.Exit);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
	}

	@Test
	public void testNewBackCommand() throws Exception {
		UUID id = UUID.randomUUID();
		Command result;

		result = CommandFactory.newBackCommand(id);
		
		Assert.assertEquals("Make sure the command type is Back.", result.getType(), CommandType.Back);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
	}

	@Test
	public void testNewForwardCommand() throws Exception {
		UUID id = UUID.randomUUID();
		Command result;

		result = CommandFactory.newForwardCommand(id);
		
		Assert.assertEquals("Make sure the command type is Forward.", result.getType(), CommandType.Forward);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
	}

	@Test
	public void testNewGetUrlCommand() throws Exception {
		UUID id = UUID.randomUUID();
		Command result;

		result = CommandFactory.newGetUrlCommand(id);
		
		Assert.assertEquals("Make sure the command type is GetUrl.", result.getType(), CommandType.GetUrl);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
	}

	@Test
	public void testNewGoToCommand() throws Exception {
		UUID id = UUID.randomUUID();
		String url = "http://www.example.org/";
		GoToCommand result;

		result = CommandFactory.newGoToCommand(id, url);
		
		Assert.assertEquals("Make sure the command type is GoTo.", result.getType(), CommandType.GoTo);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the URL matches.", result.getUrl(), url);
	}

	@Test
	public void testNewClickCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.ClassName;
		String selector = "link";
		SelectorCommand result;

		result = CommandFactory.newClickCommand(id, type, selector);
		
		Assert.assertEquals("Make sure the command type is Click.", result.getType(), CommandType.Click);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is ClassName.", result.getSelector().getSelectorType(), type);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
	}

	@Test
	public void testNewCountCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.CssSelector;
		String selector = ".className";
		SelectorCommand result;

		result = CommandFactory.newCountCommand(id, type, selector);
		
		Assert.assertEquals("Make sure the command type is Count.", result.getType(), CommandType.Count);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is CssSelector.", result.getSelector().getSelectorType(), type);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
	}

	@Test
	public void testNewFormSubmitCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.Id;
		String selector = "loginForm";
		SelectorCommand result;

		result = CommandFactory.newFormSubmitCommand(id, type, selector);
		
		Assert.assertEquals("Make sure the command type is FormSubmit.", result.getType(), CommandType.FormSubmit);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is Id.", result.getSelector().getSelectorType(), type);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
	}

	@Test
	public void testNewDeleteCommand() throws Exception {
		UUID id = UUID.randomUUID();
		String selector = "//body";
		SelectorCommand result;

		result = CommandFactory.newDeleteCommand(id, selector);
		
		Assert.assertEquals("Make sure the command type is Delete.", result.getType(), CommandType.Delete);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is Xpath.", result.getSelector().getSelectorType(), SelectorType.Xpath);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
	}

	@Test
	public void testNewFillFieldCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.Name;
		String selector = "username";
		String text = "SOME INPUT";
		FillFieldCommand result;

		result = CommandFactory.newFillFieldCommand(id, type, selector, text);
		
		Assert.assertEquals("Make sure the command type is FillField.", result.getType(), CommandType.FillField);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is Name.", result.getSelector().getSelectorType(), SelectorType.Name);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
		Assert.assertEquals("Make sure the text matches.", result.getText(), text);
	}

	@Test
	public void testNewReadTextCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.LinkText;
		String selector = "Link!";
		int maxResults = 6;
		MultiResultSelectorCommand result;

		result = CommandFactory.newReadTextCommand(id, type, selector, maxResults);
		
		Assert.assertEquals("Make sure the command type is ReadText.", result.getType(), CommandType.ReadText);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is LinkText.", result.getSelector().getSelectorType(), SelectorType.LinkText);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
		Assert.assertEquals("Make sure max results matches.", result.getMaxResults(), maxResults);
	}

	@Test
	public void testNewReadAttributeCommand() throws Exception {
		UUID id = UUID.randomUUID();
		SelectorType type = SelectorType.TagName;
		String selector = "span";
		int maxResults = 1;
		String attribute = "style";
		ReadAttributeCommand result;

		result = CommandFactory.newReadAttributeCommand(id, type, selector, maxResults, attribute);
		
		Assert.assertEquals("Make sure the command type is ReadAttribute.", result.getType(), CommandType.ReadAttribute);
		Assert.assertEquals("Make sure the ID matches.", result.getId(), id);
		Assert.assertEquals("Make sure the selector type is TagName.", result.getSelector().getSelectorType(), SelectorType.TagName);
		Assert.assertEquals("Make sure the selector matches.", result.getSelector(), selector);
		Assert.assertEquals("Make sure max results matches.", result.getMaxResults(), maxResults);
		Assert.assertEquals("Make sure the attribute matches.", result.getAttribute(), attribute);
	}
}