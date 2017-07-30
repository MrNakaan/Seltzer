package tech.seltzer.objects.command.wait.logical;

import java.util.List;
import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.CrList;
import tech.seltzer.objects.SerializableCR;
import tech.seltzer.objects.command.wait.WaitCommandData;

public final class LogicalAndOrWaitCommandData extends LogicalWaitCommandData implements SerializableCR {
	private boolean usesCommandList = false;
	
	private CrList<WaitCommandData> waitCommands = new CrList<>();
	
	public LogicalAndOrWaitCommandData(Integer seconds, CommandType waitType) {
		super(seconds, waitType);
		if (waitType != CommandType.AND_WAIT && waitType != CommandType.OR_WAIT) {
			throw new IllegalArgumentException("Supplied CommandType is '" + waitType.name() + "'; must be 'And' or 'Or'.");
		}
	}

	public LogicalAndOrWaitCommandData(Integer seconds, CommandType waitType, UUID id) {
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
	
	public void addCommand(WaitCommandData command) {
		waitCommands.addCr(command);
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
		LogicalAndOrWaitCommandData other = (LogicalAndOrWaitCommandData) obj;
		if (usesCommandList != other.usesCommandList)
			return false;
		if (waitCommands == null) {
			if (other.waitCommands != null)
				return false;
		} else if (!waitCommands.equals(other.waitCommands))
			return false;
		return true;
	}

	public CrList<WaitCommandData> getCommandList() {
		return waitCommands;
	}

	public List<WaitCommandData> getCommands() {
		return waitCommands.getCrs();
	}

	public void setCommandList(CrList<WaitCommandData> commands) {
		this.waitCommands = commands;
	}
	
	public void setCommands(List<WaitCommandData> commands) {
		this.waitCommands.setCrs(commands);
	}
}
