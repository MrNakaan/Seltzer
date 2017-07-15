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

public class CommandFactory {
	@Deprecated
	public static Command newStartCommand() {
		return new Command(CommandType.START);
	}
	
	@Deprecated
	public static Command newExitCommand(UUID id) {
		return new Command(CommandType.EXIT, id);
	}
	@Deprecated
	public static Command newBackCommand(UUID id) {
		return new Command(CommandType.BACK, id);
	}
	
	@Deprecated
	public static Command newForwardCommand(UUID id) {
		return new Command(CommandType.FORWARD, id);
	}
	
	@Deprecated
	public static Command newGetUrlCommand(UUID id) {
		return new Command(CommandType.GET_URL, id);
	}
	
	@Deprecated
	public static GoToCommand newGoToCommand(UUID id, String url) {
		return new GoToCommand(id, url);
	}
	
	@Deprecated
	public static SelectorCommand newClickCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.CLICK, id);
		command.setSelector(selector, type);
		return command;
	}
	
	@Deprecated
	public static SelectorCommand newCountCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.COUNT, id);
		command.setSelector(selector, type);
		return command;
	}
	
	@Deprecated
	public static SelectorCommand newFormSubmitCommand(UUID id, SelectorType type, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.FORM_SUBMIT, id);
		command.setSelector(selector, type);
		return command;
	}

	@Deprecated
	public static SelectorCommand newDeleteCommand(UUID id, String selector) {
		SelectorCommand command = new SelectorCommand(CommandType.DELETE, id);
		command.setSelector(selector, SelectorType.XPATH);
		return command;
	}

	@Deprecated
	public static FillFieldCommand newFillFieldCommand(UUID id, SelectorType type, String selector, String text) {
		FillFieldCommand command = new FillFieldCommand(id);
		command.setSelector(selector, type);
		command.setText(text);
		return command;
	}
	
	@Deprecated
	public static Command newGetCookieFileCommand(UUID id) {
		Command command = new Command(CommandType.GET_COOKIE_FILE, id);
		return command;
	}
	
	@Deprecated
	public static GetCookieCommand newGetCookieCommand(UUID id, String cookieName) {
		GetCookieCommand command = new GetCookieCommand(id);
		command.setCookieName(cookieName);
		return command;
	}
	
	@Deprecated
	public static GetCookiesCommand newGetCookiesCommand(UUID id, List<String> cookieNames) {
		GetCookiesCommand command = new GetCookiesCommand(id);
		command.setCookieNames(cookieNames);
		return command;
	}
	
	@Deprecated
	public static MultiResultSelectorCommand newReadTextCommand(UUID id, SelectorType type, String selector, int maxResults) {
		MultiResultSelectorCommand command = new MultiResultSelectorCommand(CommandType.READ_TEXT, id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		return command;
	}

	@Deprecated
	public static ReadAttributeCommand newReadAttributeCommand(UUID id, SelectorType type, String selector, int maxResults, String attribute) {
		ReadAttributeCommand command = new ReadAttributeCommand(id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		command.setAttribute(attribute);
		return command;
	}
}
