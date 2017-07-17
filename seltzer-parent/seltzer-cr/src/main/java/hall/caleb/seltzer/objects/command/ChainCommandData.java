package hall.caleb.seltzer.objects.command;

import java.util.List;
import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.CrList;
import hall.caleb.seltzer.objects.SerializableCR;

public class ChainCommandData<C extends CommandData> extends CommandData implements SerializableCR {
	protected boolean usesCommandList = true;
	
	private CrList<C> commands = new CrList<>();

	public ChainCommandData() {
		super(CommandType.CHAIN);
	}

	public ChainCommandData(UUID id) {
		super(CommandType.CHAIN, id);
	}

	@Override
	public void serialize() {
		commands.serialize();
	}

	@Override
	public void deserialize() {
		commands.deserialize();
	}

	public void addCommand(C command) {
		commands.addCr(command);
	}

	@Override
	public String toString() {
		return "ChainCommand [usesCommandList=" + usesCommandList + ", commands=" + commands + ", type=" + type
				+ ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (usesCommandList ? 1231 : 1237);
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainCommandData other = (ChainCommandData) obj;
		if (usesCommandList != other.usesCommandList)
			return false;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		return true;
	}

	public CrList<C> getCommandList() {
		return commands;
	}

	public List<C> getCommands() {
		return commands.getCrs();
	}

	public void setCommandList(CrList<C> commands) {
		this.commands = commands;
	}
	
	public void setCommands(List<C> commands) {
		this.commands.setCrs(commands);
	}
}
