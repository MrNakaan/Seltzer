package hall.caleb.seltzer.objects.command;

import java.util.UUID;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.CrDataBase;

public class CommandData extends CrDataBase {
	protected boolean hasCommandList = false;
	
	protected CommandType type = CommandType.NONE;
	
	public CommandData() {
		super();
	}

	public CommandData(CommandType commandType) {
		super();
		this.type = commandType;
	}

	public CommandData(CommandType commandType, UUID id) {
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
		CommandData other = (CommandData) obj;
		if (type != other.type)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public CommandType getType() {
		return type;
	}

	public void setType(CommandType type) {
		this.type = type;
	}

	public boolean hasCommandList() {
		return hasCommandList;
	}
}
