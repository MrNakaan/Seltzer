package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.CommandList;
import hall.caleb.seltzer.objects.command.SerializableCommand;

public final class LogicalAndOrWaitCommand extends LogicalWaitCommand implements SerializableCommand	 {
	private boolean usesCommandList = false;
	
	private CommandList waitCommands = new CommandList();
	
	public LogicalAndOrWaitCommand(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
		if (waitType != CommandType.AND_WAIT && waitType != CommandType.OR_WAIT) {
			throw new IllegalArgumentException("Supplied CommandType is '" + waitType.name() + "'; must be 'And' or 'Or'.");
		}
	}

	public LogicalAndOrWaitCommand(Integer seconds, CommandType waitType, UUID id) {
		super(seconds, waitType, id);
		
		if (waitType != CommandType.AND_WAIT && waitType != CommandType.OR_WAIT) {
			throw new IllegalArgumentException("Supplied CommandType is '" + waitType.name() + "'; must be 'And' or 'Or'.");
		}
	}

	@Override
	public void serialize() {
		waitCommands.serialize();
	}

	@Override
	public void deserialize() {
		waitCommands.deserialize();
	}
	
	public void addCommand(Command command) {
		waitCommands.addCommand(command);
	}

	@Override
	public String toString() {
		return "LogicalAndOrWaitCommand [usesCommandList=" + usesCommandList + ", waitCommands=" + waitCommands
				+ ", seconds=" + seconds + ", hasCommandList=" + hasCommandList + ", type=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (usesCommandList ? 1231 : 1237);
		result = prime * result + ((waitCommands == null) ? 0 : waitCommands.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogicalAndOrWaitCommand other = (LogicalAndOrWaitCommand) obj;
		if (usesCommandList != other.usesCommandList)
			return false;
		if (waitCommands == null) {
			if (other.waitCommands != null)
				return false;
		} else if (!waitCommands.equals(other.waitCommands))
			return false;
		return true;
	}

	@Override
	public CommandList getCommands() {
		return waitCommands;
	}

	@Override
	public void setCommands(CommandList waitCommands) {
		this.waitCommands = waitCommands;
	}
}
