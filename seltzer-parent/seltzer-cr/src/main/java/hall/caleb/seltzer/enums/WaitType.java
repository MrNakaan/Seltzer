package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.wait.OldVisibilityWaitCommand;

public enum WaitType {
	AlertIsPresent(OldVisibilityWaitCommand.class),
	And(OldVisibilityWaitCommand.class),//*******************************
	AttributeContains(OldVisibilityWaitCommand.class),
	AttributeToBe(OldVisibilityWaitCommand.class),
	AttributeToBeNotEmpty(OldVisibilityWaitCommand.class),
	ElementStateToBe(OldVisibilityWaitCommand.class),
	ElementToBeClickable(OldVisibilityWaitCommand.class),
	ElementToBeSelected(OldVisibilityWaitCommand.class),
	FrameToBeAvailableAndSwitchToIt(OldVisibilityWaitCommand.class),
	InvisibilityOf(OldVisibilityWaitCommand.class),
	InvisibilityOfAllElements(OldVisibilityWaitCommand.class),
	InvisibilityOfElementLocated(OldVisibilityWaitCommand.class),
	InvisibilityOfElementWithText(OldVisibilityWaitCommand.class),
	JavascriptThrowsNoExceptions(OldVisibilityWaitCommand.class),
	JavascriptReturnsValue(OldVisibilityWaitCommand.class),
	Not(OldVisibilityWaitCommand.class),//*******************************
	NumberOfElementsToBe(OldVisibilityWaitCommand.class),
	NumberOfElementsToBeLessThan(OldVisibilityWaitCommand.class),
	NumberOfElementsToBeMoreThan(OldVisibilityWaitCommand.class),
	NumberOfWindowsToBe(OldVisibilityWaitCommand.class),
	Or(OldVisibilityWaitCommand.class),//*******************************
	PresenceOfAllElementsLocatedBy(OldVisibilityWaitCommand.class),
	PresenceOfElementLocated(OldVisibilityWaitCommand.class),
	PresenceOfNestedElementLocatedBy(OldVisibilityWaitCommand.class),
	PresenceOfNestedElementsLocatedBy(OldVisibilityWaitCommand.class),
	Refreshed(OldVisibilityWaitCommand.class),
	StalenessOf(OldVisibilityWaitCommand.class),
	TextMatches(OldVisibilityWaitCommand.class),
	TextToBe(OldVisibilityWaitCommand.class),
	TextToBePresentInElement(OldVisibilityWaitCommand.class),
	TextToBePresentInElementLocated(OldVisibilityWaitCommand.class),
	TextToBePresentInElementValue(OldVisibilityWaitCommand.class),
	TitleContains(OldVisibilityWaitCommand.class),
	TitleIs(OldVisibilityWaitCommand.class),
	UrlContains(OldVisibilityWaitCommand.class),
	UrlMatches(OldVisibilityWaitCommand.class),
	UrlToBe(OldVisibilityWaitCommand.class),
	VisibilityOf(OldVisibilityWaitCommand.class),
	VisibilityOfAllElements(OldVisibilityWaitCommand.class),
	VisibilityOfAllElementsLocatedBy(OldVisibilityWaitCommand.class),
	VisibilityOfElementLocated(OldVisibilityWaitCommand.class),
	VisibilityOfNestedElementsLocatedBy(OldVisibilityWaitCommand.class);
	
private Class<? extends OldVisibilityWaitCommand> commandClass;
	
	private WaitType(Class<? extends OldVisibilityWaitCommand> commandClass) {
		this.commandClass = commandClass;
	}

	public Class<? extends OldVisibilityWaitCommand> getCommandClass() {
		return commandClass;
	}
}
