package hall.caleb.seltzer.util;

import java.util.List;
import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.GetCookieCommand;
import hall.caleb.seltzer.objects.command.GetCookiesCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;
import hall.caleb.seltzer.objects.command.wait.OldVisibilityWaitCommand;
public class CommandFactory {
	public static Command newStartCommand() {
		return new Command(CommandType.Start);
	}
	
	public static Command newExitCommand(UUID id) {
		return new Command(CommandType.Exit, id);
	}
	
	public static Command newBackCommand(UUID id) {
		return new Command(CommandType.Back, id);
	}
	
	public static Command newForwardCommand(UUID id) {
		return new Command(CommandType.Forward, id);
	}
	
	public static Command newGetUrlCommand(UUID id) {
		return new Command(CommandType.GetUrl, id);
	}
	
	public static GoToCommand newGoToCommand(UUID id, String url) {
		return new GoToCommand(id, url);
	}
	
	public static SelectorCommand newClickCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.Click, id);
		command.setSelector(selector, type);
		return command;
	}
	
	public static SelectorCommand newCountCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.Count, id);
		command.setSelector(selector, type);
		return command;
	}
	
	public static SelectorCommand newFormSubmitCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.FormSubmit, id);
		command.setSelector(selector, type);
		return command;
	}

	public static SelectorCommand newDeleteCommand(UUID id, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.Delete, id);
		command.setSelector(selector, SelectorType.Xpath);
		return command;
	}

	public static FillFieldCommand newFillFieldCommand(UUID id, SelectorType type, String selector, String text) {
		FillFieldCommand command = new FillFieldCommand(id);
		command.setSelector(selector, type);
		command.setText(text);
		return command;
	}
	
	public static Command newGetCookieFileCommand(UUID id) {
		Command command = new Command(CommandType.GetCookieFile, id);
		return command;
	}
	
	public static GetCookieCommand newGetCookieCommand(UUID id, String cookieName) {
		GetCookieCommand command = new GetCookieCommand(id);
		command.setCookieName(cookieName);
		return command;
	}
	
	public static GetCookiesCommand newGetCookiesCommand(UUID id, List<String> cookieNames) {
		GetCookiesCommand command = new GetCookiesCommand(id);
		command.setCookieNames(cookieNames);
		return command;
	}
	
	public static MultiResultSelectorCommand newReadTextCommand(UUID id, SelectorType type, String selector, int maxResults) {
		MultiResultSelectorCommand command = new MultiResultSelectorCommand(CommandType.ReadText, id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		return command;
	}

	public static ReadAttributeCommand newReadAttributeCommand(UUID id, SelectorType type, String selector, int maxResults, String attribute) {
		ReadAttributeCommand command = new ReadAttributeCommand(id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		command.setAttribute(attribute);
		return command;
	}
	
	public static OldVisibilityWaitCommand newWaitCommand(UUID id, SelectorType type, String selector, int seconds) {
		OldVisibilityWaitCommand command = new OldVisibilityWaitCommand(id);
		command.setSelector(selector, type);
		command.setSeconds(seconds);
		return command;
	}
}
