package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.wait.CountWaitCommand;
import hall.caleb.seltzer.objects.command.wait.JavaScriptWaitCommand;
import hall.caleb.seltzer.objects.command.wait.SelectionStateWaitCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.ExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.existence.NestedExistenceWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchAttributeSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchSelectorWaitCommand;
import hall.caleb.seltzer.objects.command.wait.textmatch.TextMatchWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.InvisibilityWaitCommand;
import hall.caleb.seltzer.objects.command.wait.visibility.VisibilityWaitCommand;

public enum WaitType {
	AlertIsPresent(WaitCommand.class),
//	And(LogicalAndOrWaitCommand.class),
	AttributeContains(TextMatchAttributeSelectorWaitCommand.class),
	AttributeToBe(TextMatchAttributeSelectorWaitCommand.class),
	AttributeToBeNotEmpty(TextMatchAttributeSelectorWaitCommand.class),
	ElementSelectionStateToBe(SelectionStateWaitCommand.class),
	ElementToBeClickable(ExistenceWaitCommand.class),
	ElementToBeSelected(ExistenceWaitCommand.class),
	FrameToBeAvailableAndSwitchToIt(ExistenceWaitCommand.class),
	InvisibilityOf(InvisibilityWaitCommand.class),
	InvisibilityOfAllElements(InvisibilityWaitCommand.class),
	InvisibilityOfElementWithText(InvisibilityWaitCommand.class),
	JavascriptThrowsNoExceptions(JavaScriptWaitCommand.class),
	JavascriptReturnsValue(JavaScriptWaitCommand.class),
//	Not(LogicalNotWaitCommand.class),
	NumberOfElementsToBe(CountWaitCommand.class),
	NumberOfElementsToBeLessThan(CountWaitCommand.class),
	NumberOfElementsToBeMoreThan(CountWaitCommand.class),
	NumberOfWindowsToBe(CountWaitCommand.class),
//	Or(LogicalAndOrWaitCommand.class),
	PresenceOfAllElementsLocatedBy(ExistenceWaitCommand.class),
	PresenceOfElementLocated(ExistenceWaitCommand.class),
	PresenceOfNestedElementLocatedBy(NestedExistenceWaitCommand.class),
	PresenceOfNestedElementsLocatedBy(NestedExistenceWaitCommand.class),
	//Refreshed(OldVisibilityWaitCommand.class),
	StalenessOf(ExistenceWaitCommand.class),
	TextMatches(TextMatchSelectorWaitCommand.class),
	TextToBe(TextMatchSelectorWaitCommand.class),
	TextToBePresentInElementLocated(TextMatchSelectorWaitCommand.class),
	TextToBePresentInElementValue(TextMatchSelectorWaitCommand.class),
	TitleContains(TextMatchWaitCommand.class),
	TitleIs(TextMatchWaitCommand.class),
	UrlContains(TextMatchWaitCommand.class),
	UrlMatches(TextMatchWaitCommand.class),
	UrlToBe(TextMatchWaitCommand.class),
	VisibilityOf(VisibilityWaitCommand.class),
	VisibilityOfAllElements(VisibilityWaitCommand.class),
	VisibilityOfNestedElementsLocatedBy(VisibilityWaitCommand.class);
	
	private Class<? extends WaitCommand> waitClass;
	
	private WaitType(Class<? extends WaitCommand> waitClass) {
		this.waitClass = waitClass;
	}

	public Class<? extends WaitCommand> getWaitClass() {
		return waitClass;
	}
}
