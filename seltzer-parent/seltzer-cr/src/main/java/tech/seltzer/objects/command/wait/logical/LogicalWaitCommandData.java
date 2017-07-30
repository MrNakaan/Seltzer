package tech.seltzer.objects.command.wait.logical;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.command.wait.WaitCommandData;

public abstract class LogicalWaitCommandData extends WaitCommandData {
	protected LogicalWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
	}

	protected LogicalWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
	}
}
