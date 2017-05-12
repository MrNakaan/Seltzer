package hall.caleb.selenium.objects.command;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.annotation.Generated;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import hall.caleb.selenium.enums.SelectorType;

@Generated(value = "org.junit-tools-1.0.5")
public class ChainCommandTest {
	private ChainCommand original;
	private ChainCommand clone;
	
	@Before
	public void createResponses() {
		UUID id = UUID.randomUUID();

		original = new ChainCommand(id);
		clone = new ChainCommand(id);
		
		Command command;
		FillFieldCommand ffCommand;
		GoToCommand gtCommand;
		MultiResultSelectorCommand mrsCommand;
		ReadAttributeCommand raCommand;
		SelectorCommand sCommand;
		ChainCommand cCommand;
		
		command = CommandFactory.newBackCommand(id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = CommandFactory.newForwardCommand(id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = CommandFactory.newGetUrlCommand(id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = CommandFactory.newBackCommand(id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = CommandFactory.newBackCommand(id);
		original.addCommand(command);
		clone.addCommand(command);
		
		ffCommand = CommandFactory.newFillFieldCommand(id, SelectorType.Xpath, "//input[@id='username']", "username");
		original.addCommand(ffCommand);
		clone.addCommand(ffCommand);
		
		gtCommand = CommandFactory.newGoToCommand(id, "www.google.com");
		original.addCommand(gtCommand);
		clone.addCommand(gtCommand);
		
		mrsCommand = CommandFactory.newReadTextCommand(id, SelectorType.TagName, "a", 0);
		original.addCommand(mrsCommand);
		clone.addCommand(mrsCommand);
		
		raCommand = CommandFactory.newReadAttributeCommand(id, SelectorType.LinkText, "Next", 5, "href");
		original.addCommand(raCommand);
		clone.addCommand(raCommand);
		
		sCommand = CommandFactory.newCountCommand(id, SelectorType.Xpath, "//div");
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		sCommand = CommandFactory.newClickCommand(id, SelectorType.Xpath, "//a[1]");
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		sCommand = CommandFactory.newCountCommand(id, SelectorType.Xpath, "//span");
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		sCommand = CommandFactory.newCountCommand(id, SelectorType.Xpath, "//p");
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		cCommand = new ChainCommand(id);
		cCommand.addCommand(raCommand);
		cCommand.addCommand(raCommand);
		cCommand.addCommand(mrsCommand);
		cCommand.addCommand(command);
		original.addCommand(cCommand);
		
		cCommand = new ChainCommand(id);
		cCommand.addCommand(raCommand);
		cCommand.addCommand(raCommand);
		cCommand.addCommand(mrsCommand);
		cCommand.addCommand(command);
		clone.addCommand(cCommand);
	}
	
	@Test
	public void testSerialize() throws Exception {
		Gson gson = new Gson();
		
		original.serialize();
		String json = gson.toJson(original, ChainCommand.class);
		original = gson.fromJson(json, ChainCommand.class);
		original.deserialize();
		
		assertEquals("Verify that original matches clone", original, clone);
	}
}