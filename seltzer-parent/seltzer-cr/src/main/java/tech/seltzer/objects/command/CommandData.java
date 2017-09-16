package tech.seltzer.objects.command;

import java.util.UUID;

import tech.seltzer.enums.CommandType;
import tech.seltzer.objects.CrDataBase;

public class CommandData extends CrDataBase {
	protected boolean hasCommandList = false;
	protected boolean takeScreenshotBefore = false;
	protected boolean takeScreenshotAfter = false;
	protected CommandType commandType = CommandType.NONE;
	
	public CommandData() {
		super();
	}

	public CommandData(CommandType commandType) {
		super();
		
		if (this.getClass().equals(commandType.getCrClass())) {
			this.commandType = commandType;
		} else {
			throw new IllegalArgumentException("Passed command type '" + commandType.toString() + "' does not match this command.");
		}
	}

	public CommandData(CommandType commandType, UUID id) {
		super();
		
		if (this.getClass().equals(commandType.getCrClass())) {
			this.commandType = commandType;
		} else {
			throw new IllegalArgumentException("Passed command type '" + commandType.toString() + "' does not match this command.");
		}
		
		this.id = id;
	}

	@Override
	public String toString() {
		return "CommandData [hasCommandList=" + hasCommandList + ", takeScreenshotBefore=" + takeScreenshotBefore
				+ ", takeScreenshotAfter=" + takeScreenshotAfter + ", commandType=" + commandType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		result = prime * result + (hasCommandList ? 1231 : 1237);
		result = prime * result + (takeScreenshotAfter ? 1231 : 1237);
		result = prime * result + (takeScreenshotBefore ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandData other = (CommandData) obj;
		if (commandType != other.commandType)
			return false;
		if (hasCommandList != other.hasCommandList)
			return false;
		if (takeScreenshotAfter != other.takeScreenshotAfter)
			return false;
		if (takeScreenshotBefore != other.takeScreenshotBefore)
			return false;
		return true;
	}

	@Override
	public CommandType getType() {
		return commandType;
	}

	public void setType(CommandType commandType) {
		if (this.getClass().equals(commandType.getCrClass())) {
			this.commandType = commandType;
		} else {
			throw new IllegalArgumentException("Passed command type '" + commandType.toString() + "' does not match this command.");
		}
		this.commandType = commandType;
	}

	public boolean takeScreenshotBefore() {
		return takeScreenshotBefore;
	}

	public void setTakeScreenshotBefore(boolean takeScreenshotBefore) {
		this.takeScreenshotBefore = takeScreenshotBefore;
	}

	public boolean isTakeScreenshotAfter() {
		return takeScreenshotAfter;
	}

	public void setTakeScreenshotAfter(boolean takeScreenshotAfter) {
		this.takeScreenshotAfter = takeScreenshotAfter;
	}

	public boolean hasCommandList() {
		return hasCommandList;
	}
}
