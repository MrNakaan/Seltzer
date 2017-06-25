package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.wait.WaitCommand;

public abstract class LogicalWaitCommand extends WaitCommand {
	protected LogicalWaitCommand(Integer seconds, WaitType waitType) {
		super(seconds, waitType);
	}

	protected LogicalWaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(seconds, waitType, id);
	}
}
