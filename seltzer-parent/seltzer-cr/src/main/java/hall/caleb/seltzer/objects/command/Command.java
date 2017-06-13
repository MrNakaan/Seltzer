package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;

public class Command {
	protected CommandType type;
	protected UUID id;
	
	public Command() {
		super();
	}

	public Command(CommandType commandType) {
		super();
		this.type = commandType;
	}

	public Command(CommandType commandType, UUID id) {
		super();
		this.type = commandType;
		this.id = id;
	}

	@Override
	public String toString() {
		return "SeleniumCommand [commandType=" + type + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type != other.type)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
