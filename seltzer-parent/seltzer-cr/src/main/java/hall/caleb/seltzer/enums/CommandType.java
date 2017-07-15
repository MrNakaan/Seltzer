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
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

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
	WAIT(WaitCommand.class);
	
	private Class<? extends Command> commandClass;
	
	private CommandType(Class<? extends Command> commandClass) {
		this.commandClass = commandClass;
	}

	public Class<? extends Command> getCommandClass() {
		return commandClass;
	}
}
