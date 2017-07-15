//package hall.caleb.seltzer.enums;
//
//import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.RefreshedWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.WaitCommand;
//import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.logical.LogicalAndOrWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.logical.LogicalNotWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.visibility.NestedVisibilityWaitCommand;
//import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommand;
//
//public enum WaitType {
//	ALERT_PRESENT(WaitCommand.class),
//	AND(LogicalAndOrWaitCommand.class),
//	ATTRIBUTE_CONTAINS(TextMatchAttributeSelectorWaitCommand.class),
//	ATTRIBUTE_IS(TextMatchAttributeSelectorWaitCommand.class),
//	ATTRIBUTE_IS_NOT_EMPTY(TextMatchAttributeSelectorWaitCommand.class),
//	ELEMENT_SELECTION_STATE_IS(SelectionStateWaitCommand.class),
//	ELEMENT_CLICKABLE(ExistenceWaitCommand.class),
//	SWITCH_TO_FRAME_WHEN_AVAILABLE(ExistenceWaitCommand.class),
//	ELEMENT_INVISIBLE(InvisibilityWaitCommand.class),
//	ALL_ELEMENTS_INVISBLE(InvisibilityWaitCommand.class),
//	ELEMENT_WITH_TEXT_INVISIBLE(InvisibilityWaitCommand.class),
//	JAVASCRIPT_THROWS_NO_EXCEPTIONS(JavaScriptWaitCommand.class),
//	JAVASCRIPT_RETURNS_STRING(JavaScriptWaitCommand.class),
//	NOT(LogicalNotWaitCommand.class),
//	ELEMENT_COUNT_IS(CountWaitCommand.class),
//	ELEMENT_COUNT_LESS_THAN(CountWaitCommand.class),
//	ELEMENT_COUNT_GREATER_THAN(CountWaitCommand.class),
//	WINDOW_COUNT_IS(CountWaitCommand.class),
//	OR(LogicalAndOrWaitCommand.class),
//	ALL_ELEMENTS_PRESENT(ExistenceWaitCommand.class),
//	ELEMENT_PRESENT(ExistenceWaitCommand.class),
//	NESTED_ELEMENT_PRESENT(NestedExistenceWaitCommand.class),
//	NESTED_ELEMENTS_PRESENT(NestedExistenceWaitCommand.class),
//	REFRESHED(RefreshedWaitCommand.class),
//	IS_STALE(ExistenceWaitCommand.class),
//	TEXT_MATCHES(TextMatchSelectorWaitCommand.class),
//	TEXT_IS(TextMatchSelectorWaitCommand.class),
//	TEXT_PRESENT_IN_ELEMENT(TextMatchSelectorWaitCommand.class),
//	TEXT_IN_ELEMENT_VALUE(TextMatchSelectorWaitCommand.class),
//	TITLE_CONTAINS(TextMatchWaitCommand.class),
//	TITLE_IS(TextMatchWaitCommand.class),
//	URL_CONTAINS(TextMatchWaitCommand.class),
//	URL_MATCHES(TextMatchWaitCommand.class),
//	URL_IS(TextMatchWaitCommand.class),
//	ELEMENT_VISIBLE(VisibilityWaitCommand.class),
//	ALL_ELEMENTS_VISIBLE(VisibilityWaitCommand.class),
//	NESTED_ELEMENTS_VISIBLE(NestedVisibilityWaitCommand.class);
//	
//	private Class<? extends WaitCommand> waitClass;
//	
//	private WaitType(Class<? extends WaitCommand> waitClass) {
//		this.waitClass = waitClass;
//	}
//
//	public Class<? extends WaitCommand> getWaitClass() {
//		return waitClass;
//	}
//}
