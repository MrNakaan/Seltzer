package hall.caleb.seltzer.objects.command.wait.logical;

import java.util.UUID;

import hall.caleb.seltzer.enums.WaitType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.CommandList;
import hall.caleb.seltzer.objects.command.SerializableCommand;

public class LogicalAndOrWaitCommand extends LogicalWaitCommand implements SerializableCommand	 {
	private boolean usesCommandList = false;
	
	private CommandList waitCommands = new CommandList();
	
	public LogicalAndOrWaitCommand(Integer seconds, WaitType waitType) {
		super(seconds, waitType);
		if (waitType != WaitType.AND && waitType != WaitType.OR) {
			throw new IllegalArgumentException("Supplied WaitType is '" + waitType.name() + "'; must be 'And' or 'Or'.");
		}
	}

	public LogicalAndOrWaitCommand(Integer seconds, WaitType waitType, UUID id) {
		super(seconds, waitType, id);
		
		if (waitType != WaitType.AND && waitType != WaitType.OR) {
			throw new IllegalArgumentException("Supplied WaitType is '" + waitType.name() + "'; must be 'And' or 'Or'.");
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
		return "LogicalAndWaitCommand [usesCommandList=" + usesCommandList + ", waitCommands=" + waitCommands
				+ ", waitType=" + waitType + ", seconds=" + seconds + ", type=" + type + ", id=" + id + "]";
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
