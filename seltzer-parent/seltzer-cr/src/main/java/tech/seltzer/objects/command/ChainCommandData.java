package tech.seltzer.objects.command;

import java.util.List;
import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.CrList;
import tech.seltzer.objects.SerializableCR;

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

	public boolean addCommand(C command) {
		if (this.id.equals(command.getId())) {
			commands.addCr(command);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addCommand(List<C> commands) {
		return addCommands(commands);
	}
	
	public boolean addCommands(List<C> commands) {
		for (C command : commands) {
			if (!this.id.equals(command.getId())) {
				return false;
			}
		}

		this.commands.addCrs(commands);
		return true;
	}
	
	public boolean removeCommand(int index) {
		return commands.getCrs().remove(index) != null;
	}
	
	public boolean removeCommand(C command) {
		if (this.id.equals(command.getId())) {
			commands.removeCr(command);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeCommand(List<C> commands) {
		return removeCommands(commands);
	}
	
	public boolean removeCommands(List<C> commands) {
		for (C command : commands) {
			if (!this.id.equals(command.getId())) {
				return false;
			}
		}

		this.commands.removeCrs(commands);
		return true;
	}

	@Override
	public String toString() {
		return "ChainCommandData [usesCommandList=" + usesCommandList + ", commands=" + commands + ", hasCommandList="
				+ hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore + ", takeScreenshotAfter="
				+ takeScreenshotAfter + ", commandType=" + commandType + ", id=" + id + "]";
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
		if (!super.equals(obj))
			return false;
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
