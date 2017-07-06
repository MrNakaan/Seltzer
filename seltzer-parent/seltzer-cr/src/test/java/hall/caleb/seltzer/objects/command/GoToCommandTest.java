package hall.caleb.seltzer.objects.command;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.annotation.Generated;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;

@Generated(value = "org.junit-tools-1.0.5")
public class GoToCommandTest {
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
		
		command = new Command(CommandType.Back, id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = new Command(CommandType.Forward, id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = new Command(CommandType.GetUrl, id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = new Command(CommandType.Back, id);
		original.addCommand(command);
		clone.addCommand(command);
		
		command = new Command(CommandType.Back, id);
		original.addCommand(command);
		clone.addCommand(command);
		
		ffCommand = new FillFieldCommand(id);
		ffCommand.setSelector("//input[@id='username']", SelectorType.Xpath);
		ffCommand.setText("username");
		original.addCommand(ffCommand);
		clone.addCommand(ffCommand);

		gtCommand = new GoToCommand(id, "www.google.com");
		original.addCommand(gtCommand);
		clone.addCommand(gtCommand);
		
		mrsCommand = new MultiResultSelectorCommand(CommandType.ReadText, id);
		mrsCommand.setSelector("a", SelectorType.TagName);
		mrsCommand.setMaxResults(0);
		original.addCommand(mrsCommand);
		clone.addCommand(mrsCommand);

		raCommand = new ReadAttributeCommand(id);
		raCommand.setSelector("Next", SelectorType.LinkText);
		raCommand.setMaxResults(5);
		raCommand.setAttribute("href");
		original.addCommand(raCommand);
		clone.addCommand(raCommand);
		
		sCommand = new SelectorCommand(CommandType.Count, id);
		sCommand.setSelector("//div", SelectorType.Xpath);
		original.addCommand(sCommand);
		clone.addCommand(sCommand);

		sCommand = new SelectorCommand(CommandType.Click, id);
		sCommand.setSelector("//a[1]", SelectorType.Xpath);
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		sCommand = new SelectorCommand(CommandType.Count, id);
		sCommand.setSelector("//span", SelectorType.Xpath);
		original.addCommand(sCommand);
		clone.addCommand(sCommand);
		
		sCommand = new SelectorCommand(CommandType.Count, id);
		sCommand.setSelector("//p", SelectorType.Xpath);
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