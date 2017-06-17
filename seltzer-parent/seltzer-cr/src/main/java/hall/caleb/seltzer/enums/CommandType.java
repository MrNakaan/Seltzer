package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.command.ChainCommand;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.FillFieldCommand;
import hall.caleb.seltzer.objects.command.GoToCommand;
import hall.caleb.seltzer.objects.command.MultiResultSelectorCommand;
import hall.caleb.seltzer.objects.command.ReadAttributeCommand;
import hall.caleb.seltzer.objects.command.SelectorCommand;
import hall.caleb.seltzer.objects.command.WaitCommand;

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
