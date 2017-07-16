package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.wait.WaitCommandData;

public abstract class LogicalWaitCommandData extends WaitCommandData {
	protected LogicalWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	protected LogicalWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
}
