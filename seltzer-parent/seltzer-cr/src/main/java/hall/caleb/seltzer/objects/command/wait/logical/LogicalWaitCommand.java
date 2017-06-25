package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public abstract class LogicalWaitCommand extends WaitCommand {
	public LogicalWaitCommand(WaitType waitType) {
		super(waitType);
	}

	public LogicalWaitCommand(WaitType waitType, UUID id) {
		super(waitType, id);
	}
}
