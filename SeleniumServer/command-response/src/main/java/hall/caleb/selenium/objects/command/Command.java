package hall.caleb.selenium.objects.command;

import java.util.UUID;

import hall.caleb.selenium.enums.CommandType;

public class Command {
	protected CommandType commandType;
	protected UUID id;
	
	public Command() {
		
	}

	public Command(CommandType commandType) {
		super();
		this.commandType = commandType;
	}

	public Command(CommandType commandType, UUID id) {
		super();
		this.commandType = commandType;
		this.id = id;
	}

	@Override
	public String toString() {
		return "SeleniumCommand [commandType=" + commandType + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Command other = (Command) obj;
		if (commandType != other.commandType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
