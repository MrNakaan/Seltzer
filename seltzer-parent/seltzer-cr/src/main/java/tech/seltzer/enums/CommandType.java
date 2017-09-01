package tech.seltzer.enums;

import tech.seltzer.objects.command.ChainCommandData;
import tech.seltzer.objects.command.CommandData;
import tech.seltzer.objects.command.GetCookieCommandData;
import tech.seltzer.objects.command.GetCookiesCommandData;
import tech.seltzer.objects.command.GoToCommandData;
import tech.seltzer.objects.command.selector.FillFieldCommandData;
import tech.seltzer.objects.command.selector.SelectorCommandData;
import tech.seltzer.objects.command.selector.SendKeyCommandData;
import tech.seltzer.objects.command.selector.SendKeysCommandData;
import tech.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommandData;
import tech.seltzer.objects.command.selector.multiresult.ReadAttributeCommandData;
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

/**
 * A list of command data types that Seltzer can handle.
 */
public enum CommandType implements CrType {
	NONE(CommandData.class),
	BACK(CommandData.class),
	CHAIN(ChainCommandData.class),
	CLICK(SelectorCommandData.class),
	COUNT(SelectorCommandData.class),
	DELETE(SelectorCommandData.class),
	EXIT(CommandData.class),
	FILL_FIELD(FillFieldCommandData.class),
	FORM_SUBMIT(SelectorCommandData.class),
	FORWARD(CommandData.class),
	GET_COOKIE(GetCookieCommandData.class),
	GET_COOKIE_FILE(CommandData.class),
	GET_COOKIES(GetCookiesCommandData.class),
	GET_URL(CommandData.class),
	GO_TO(GoToCommandData.class),
	READ_ATTRIBUTE(ReadAttributeCommandData.class),
	READ_TEXT(MultiResultSelectorCommandData.class),
	SEND_KEY(SendKeyCommandData.class),
	SEND_KEYS(SendKeysCommandData.class),
	START(CommandData.class),
	TAKE_SCREENSHOT(CommandData.class),
	WAIT(WaitCommandData.class),
	ALERT_PRESENT_WAIT(WaitCommandData.class),
	AND_WAIT(LogicalAndOrWaitCommandData.class),
	ATTRIBUTE_CONTAINS_WAIT(TextMatchAttributeSelectorWaitCommandData.class),
	ATTRIBUTE_IS_WAIT(TextMatchAttributeSelectorWaitCommandData.class),
	ATTRIBUTE_IS_NOT_EMPTY_WAIT(TextMatchAttributeSelectorWaitCommandData.class),
	ELEMENT_SELECTION_STATE_IS_WAIT(SelectionStateWaitCommandData.class),
	ELEMENT_CLICKABLE_WAIT(ExistenceWaitCommandData.class),
	SWITCH_TO_FRAME_WHEN_AVAILABLE_WAIT(ExistenceWaitCommandData.class),
	ELEMENT_INVISIBLE_WAIT(InvisibilityWaitCommandData.class),
	ALL_ELEMENTS_INVISBLE_WAIT(InvisibilityWaitCommandData.class),
	ELEMENT_WITH_TEXT_INVISIBLE_WAIT(InvisibilityWaitCommandData.class),
	JAVASCRIPT_THROWS_NO_EXCEPTIONS_WAIT(JavaScriptWaitCommandData.class),
	JAVASCRIPT_RETURNS_STRING_WAIT(JavaScriptWaitCommandData.class),
	NOT_WAIT(LogicalNotWaitCommandData.class),
	ELEMENT_COUNT_IS_WAIT(CountWaitCommandData.class),
	ELEMENT_COUNT_LESS_THAN_WAIT(CountWaitCommandData.class),
	ELEMENT_COUNT_GREATER_THAN_WAIT(CountWaitCommandData.class),
	WINDOW_COUNT_IS_WAIT(CountWaitCommandData.class),
	OR_WAIT(LogicalAndOrWaitCommandData.class),
	ALL_ELEMENTS_PRESENT_WAIT(ExistenceWaitCommandData.class),
	ELEMENT_PRESENT_WAIT(ExistenceWaitCommandData.class),
	NESTED_ELEMENT_PRESENT_WAIT(NestedExistenceWaitCommandData.class),
	NESTED_ELEMENTS_PRESENT_WAIT(NestedExistenceWaitCommandData.class),
	REFRESHED_WAIT(RefreshedWaitCommandData.class),
	IS_STALE_WAIT(ExistenceWaitCommandData.class),
	TEXT_MATCHES_WAIT(TextMatchSelectorWaitCommandData.class),
	TEXT_IS_WAIT(TextMatchSelectorWaitCommandData.class),
	TEXT_PRESENT_IN_ELEMENT_WAIT(TextMatchSelectorWaitCommandData.class),
	TEXT_IN_ELEMENT_VALUE_WAIT(TextMatchSelectorWaitCommandData.class),
	TITLE_CONTAINS_WAIT(TextMatchWaitCommandData.class),
	TITLE_IS_WAIT(TextMatchWaitCommandData.class),
	URL_CONTAINS_WAIT(TextMatchWaitCommandData.class),
	URL_MATCHES_WAIT(TextMatchWaitCommandData.class),
	URL_IS_WAIT(TextMatchWaitCommandData.class),
	ELEMENT_VISIBLE_WAIT(VisibilityWaitCommandData.class),
	ALL_ELEMENTS_VISIBLE_WAIT(VisibilityWaitCommandData.class),
	NESTED_ELEMENTS_VISIBLE_WAIT(NestedVisibilityWaitCommandData.class);
	
	private Class<? extends CommandData> commandClass;
	
	private CommandType(Class<? extends CommandData> commandClass) {
		this.commandClass = commandClass;
	}

	@Override
	public Class<? extends CommandData> getCrClass() {
		return commandClass;
	}
}
