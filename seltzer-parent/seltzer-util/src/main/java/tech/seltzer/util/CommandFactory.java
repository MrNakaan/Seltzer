package tech.seltzer.util;

import java.util.List;
import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.enums.SelectorType;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.GetCookieCommandData;
import tech.seltzer.objects.command.GetCookiesCommandData;
import tech.seltzer.objects.command.GoToCommandData;
import tech.seltzer.objects.command.selector.FillFieldCommandData;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.ReadAttributeCommandData;

public class CommandFactory {
	@Deprecated
	public static CommandData newStartCommand() {
		return new CommandData(CommandType.START);
	}
	
	@Deprecated
	public static CommandData newExitCommand(UUID id) {
		return new CommandData(CommandType.EXIT, id);
	}
	@Deprecated
	public static CommandData newBackCommand(UUID id) {
		return new CommandData(CommandType.BACK, id);
	}
	
	@Deprecated
	public static CommandData newForwardCommand(UUID id) {
		return new CommandData(CommandType.FORWARD, id);
	}
	
	@Deprecated
	public static CommandData newGetUrlCommand(UUID id) {
		return new CommandData(CommandType.GET_URL, id);
	}
	
	@Deprecated
	public static GoToCommandData newGoToCommand(UUID id, String url) {
		return new GoToCommandData(id, url);
	}
	
	@Deprecated
	public static SelectorCommandData newClickCommand(UUID id, SelectorType type, String selector) {
		SelectorCommandData command = new SelectorCommandData(CommandType.CLICK, id);
		command.setSelector(selector, type);
		return command;
	}
	
	@Deprecated
	public static SelectorCommandData newCountCommand(UUID id, SelectorType type, String selector) {
		SelectorCommandData command = new SelectorCommandData(CommandType.COUNT, id);
		command.setSelector(selector, type);
		return command;
	}
	
	@Deprecated
	public static SelectorCommandData newFormSubmitCommand(UUID id, SelectorType type, String selector) {
		SelectorCommandData command = new SelectorCommandData(CommandType.FORM_SUBMIT, id);
		command.setSelector(selector, type);
		return command;
	}

	@Deprecated
	public static SelectorCommandData newDeleteCommand(UUID id, String selector) {
		SelectorCommandData command = new SelectorCommandData(CommandType.DELETE, id);
		command.setSelector(selector, SelectorType.XPATH);
		return command;
	}

	@Deprecated
	public static FillFieldCommandData newFillFieldCommand(UUID id, SelectorType type, String selector, String text) {
		FillFieldCommandData command = new FillFieldCommandData(id);
		command.setSelector(selector, type);
		command.setText(text);
		return command;
	}
	
	@Deprecated
	public static CommandData newGetCookieFileCommand(UUID id) {
		CommandData command = new CommandData(CommandType.GET_COOKIE_FILE, id);
		return command;
	}
	
	@Deprecated
	public static GetCookieCommandData newGetCookieCommand(UUID id, String cookieName) {
		GetCookieCommandData command = new GetCookieCommandData(id);
		command.setCookieName(cookieName);
		return command;
	}
	
	@Deprecated
	public static GetCookiesCommandData newGetCookiesCommand(UUID id, List<String> cookieNames) {
		GetCookiesCommandData command = new GetCookiesCommandData(id);
		command.setCookieNames(cookieNames);
		return command;
	}
	
	@Deprecated
	public static MultiResultSelectorCommandData newReadTextCommand(UUID id, SelectorType type, String selector, int maxResults) {
		MultiResultSelectorCommandData command = new MultiResultSelectorCommandData(CommandType.READ_TEXT, id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		return command;
	}

	@Deprecated
	public static ReadAttributeCommandData newReadAttributeCommand(UUID id, SelectorType type, String selector, int maxResults, String attribute) {
		ReadAttributeCommandData command = new ReadAttributeCommandData(id);
		command.setSelector(selector, type);
		command.setMaxResults(maxResults);
		command.setAttribute(attribute);
		return command;
	}
}
