package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.GetCookieCommand;
import hall.caleb.seltzer.objects.command.GetCookiesCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.SendKeyCommand;
import hall.caleb.seltzer.objects.command.SendKeysCommand;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;
import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommand;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommand;
import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommand;

public enum CommandType {
	BACK(Command.class),
	CHAIN(ChainCommand.class),
	CLICK(SelectorCommand.class),
	COUNT(SelectorCommand.class),
	DELETE(SelectorCommand.class),
	EXIT(Command.class),
	FILL_FIELD(FillFieldCommand.class),
	FORM_SUBMIT(SelectorCommand.class),
	FORWARD(Command.class),
	GET_COOKIE(GetCookieCommand.class),
	GET_COOKIE_FILE(Command.class),
	GET_COOKIES(GetCookiesCommand.class),
	GET_URL(Command.class),
	GO_TO(GoToCommand.class),
	READ_ATTRIBUTE(ReadAttributeCommand.class),
	READ_TEXT(MultiResultSelectorCommand.class),
	SEND_KEY(SendKeyCommand.class),
	SEND_KEYS(SendKeysCommand.class),
	START(Command.class),
	WAIT(WaitCommand.class),
	ALERT_PRESENT_WAIT(WaitCommand.class),
	AND_WAIT(LogicalAndOrWaitCommand.class),
	ATTRIBUTE_CONTAINS_WAIT(TextMatchAttributeSelectorWaitCommand.class),
	ATTRIBUTE_IS_WAIT(TextMatchAttributeSelectorWaitCommand.class),
	ATTRIBUTE_IS_NOT_EMPTY_WAIT(TextMatchAttributeSelectorWaitCommand.class),
	ELEMENT_SELECTION_STATE_IS_WAIT(SelectionStateWaitCommand.class),
	ELEMENT_CLICKABLE_WAIT(ExistenceWaitCommand.class),
	SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT(ExistenceWaitCommand.class),
	ELEMENT_INVISIBLE_WAIT(InvisibilityWaitCommand.class),
	ALL_ELEMENTS_INVISBLE_WAIT(InvisibilityWaitCommand.class),
	ELEMENT_WITH_TEXT_INVISIBLE_WAIT(InvisibilityWaitCommand.class),
	JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT(JavaScriptWaitCommand.class),
	JAVASCRIPT_RETURNS_STRING_WAIT(JavaScriptWaitCommand.class),
	NOT_WAIT(LogicalNotWaitCommand.class),
	ELEMENT_COUNT_IS_WAIT(CountWaitCommand.class),
	ELEMENT_COUNT_LESS_THAN_WAIT(CountWaitCommand.class),
	ELEMENT_COUNT_GREATER_THAN_WAIT(CountWaitCommand.class),
	WINDOW_COUNT_IS_WAIT(CountWaitCommand.class),
	OR_WAIT(LogicalAndOrWaitCommand.class),
	ALL_ELEMENTS_PRESENT_WAIT(ExistenceWaitCommand.class),
	ELEMENT_PRESENT_WAIT(ExistenceWaitCommand.class),
	NESTED_ELEMENT_PRESENT_WAIT(NestedExistenceWaitCommand.class),
	NESTED_ELEMENTS_PRESENT_WAIT(NestedExistenceWaitCommand.class),
	REFRESHED_WAIT(RefreshedWaitCommand.class),
	IS_STALE_WAIT(ExistenceWaitCommand.class),
	TEXT_MATCHES_WAIT(TextMatchSelectorWaitCommand.class),
	TEXT_IS_WAIT(TextMatchSelectorWaitCommand.class),
	TEXT_PRESENT_IN_ELEMENT_WAIT(TextMatchSelectorWaitCommand.class),
	TEXT_IN_ELEMENT_VALUE_WAIT(TextMatchSelectorWaitCommand.class),
	TITLE_CONTAINS_WAIT(TextMatchWaitCommand.class),
	TITLE_IS_WAIT(TextMatchWaitCommand.class),
	URL_CONTAINS_WAIT(TextMatchWaitCommand.class),
	URL_MATCHES_WAIT(TextMatchWaitCommand.class),
	URL_IS_WAIT(TextMatchWaitCommand.class),
	ELEMENT_VISIBLE_WAIT(VisibilityWaitCommand.class),
	ALL_ELEMENTS_VISIBLE_WAIT(VisibilityWaitCommand.class),
	NESTED_ELEMENTS_VISIBLE_WAIT(NestedVisibilityWaitCommand.class);
	
	private Class<? extends Command> commandClass;
	
	private CommandType(Class<? extends Command> commandClass) {
		this.commandClass = commandClass;
	}

	public Class<? extends Command> getCommandClass() {
		return commandClass;
	}
}
