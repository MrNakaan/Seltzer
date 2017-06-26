package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.GetCookieCommand;
import hall.caleb.seltzer.objects.command.GetCookiesCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.selector.FillFieldCommand;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.selector.multiresult.ReadAttributeCommand;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public enum CommandType {
	Back(Command.class),
	Chain(ChainCommand.class),
	Click(SelectorCommand.class),
	Count(SelectorCommand.class),
	Delete(SelectorCommand.class),
	Exit(Command.class),
	FillField(FillFieldCommand.class),
	FormSubmit(SelectorCommand.class),
	Forward(Command.class),
	GetCookie(GetCookieCommand.class),
	GetCookieFile(Command.class),
	GetCookies(GetCookiesCommand.class),
	GetUrl(Command.class),
	GoTo(GoToCommand.class),
	ReadAttribute(ReadAttributeCommand.class),
	ReadText(MultiResultSelectorCommand.class),
	Start(Command.class),
	Wait(WaitCommand.class);
	
	private Class<? extends Command> commandClass;
	
	private CommandType(Class<? extends Command> commandClass) {
		this.commandClass = commandClass;
	}

	public Class<? extends Command> getCommandClass() {
		return commandClass;
	}
}
