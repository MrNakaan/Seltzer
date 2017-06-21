package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.WaitCommand;

public enum WaitType {
	AlertIsPresent(WaitCommand.class),
	And(WaitCommand.class),
	AttributeContains(WaitCommand.class),
	AttributeToBe(WaitCommand.class),
	AttributeToBeNotEmpty(WaitCommand.class),
	ElementStateToBe(WaitCommand.class),
	ElementToBeClickable(WaitCommand.class),
	ElementToBeSelected(WaitCommand.class),
	FrameToBeAvailableAndSwitchToIt(WaitCommand.class),
	InvisibilityOf(WaitCommand.class),
	InvisibilityOfAllElements(WaitCommand.class),
	InvisibilityOfElementLocated(WaitCommand.class),
	InvisibilityOfElementWithText(WaitCommand.class),
	JavascriptThrowsNoExceptions(WaitCommand.class),
	JavascriptReturnsValue(WaitCommand.class),
	Not(WaitCommand.class),
	NumberOfElementsToBe(WaitCommand.class),
	NumberOfElementsToBeLessThan(WaitCommand.class),
	NumberOfElementsToBeMoreThan(WaitCommand.class),
	NumberOfWindowsToBe(WaitCommand.class),
	Or(WaitCommand.class),
	PresenceOfAllElementsLocatedBy(WaitCommand.class),
	PresenceOfElementLocated(WaitCommand.class),
	PresenceOfNestedElementLocatedBy(WaitCommand.class),
	PresenceOfNestedElementsLocatedBy(WaitCommand.class),
	Refreshed(WaitCommand.class),
	StalenessOf(WaitCommand.class),
	TextMatches(WaitCommand.class),
	TextToBe(WaitCommand.class),
	TextToBePresentInElement(WaitCommand.class),
	TextToBePresentInElementLocated(WaitCommand.class),
	TextToBePresentInElementValue(WaitCommand.class),
	TitleContains(WaitCommand.class),
	TitleIs(WaitCommand.class),
	UrlContains(WaitCommand.class),
	UrlMatches(WaitCommand.class),
	UrlToBe(WaitCommand.class),
	VisibilityOf(WaitCommand.class),
	VisibilityOfAllElements(WaitCommand.class),
	VisibilityOfAllElementsLocatedBy(WaitCommand.class),
	VisibilityOfElementLocated(WaitCommand.class),
	VisibilityOfNestedElementsLocatedBy(WaitCommand.class);
	
private Class<? extends WaitCommand> commandClass;
	
	private WaitType(Class<? extends WaitCommand> commandClass) {
		this.commandClass = commandClass;
	}

	public Class<? extends WaitCommand> getCommandClass() {
		return commandClass;
	}
}
