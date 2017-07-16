package hall.caleb.seltzer.objects.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;

import hall.caleb.seltzer.objects.SerializableCR;

public class CommandList<C extends CommandData> {
	public static final int HASH_PRIME = 31;
	
	private List<C> commands;
	private List<String> serializedCommands;
	
	public CommandList() {
		commands = null;
		serializedCommands = null;
	}
	
	public void serialize() {
		if (serializedCommands == null) {
			serializedCommands = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		
		if (!CollectionUtils.isEmpty(commands)) {
			for (CommandData subCommand : commands) {
				if (subCommand instanceof SerializableCR) {
					((SerializableCR) subCommand).serialize();
				}
				
				serializedCommands.add(gson.toJson(subCommand, subCommand.getType().getCommandClass()));
			}
			
			commands.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public void deserialize() {
		if (commands == null) {
			commands = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		CommandData subCommand;
		
		if (!CollectionUtils.isEmpty(serializedCommands)) {
			for (String serializedCommand : serializedCommands) {
				subCommand = gson.fromJson(serializedCommand, CommandData.class);
				
				subCommand = gson.fromJson(serializedCommand, subCommand.getType().getCommandClass());
				
				if (subCommand instanceof SerializableCR) {
					((SerializableCR) subCommand).deserialize();
				}
				
				commands.add((C) subCommand);
			}
			
			serializedCommands.clear();
		}
	}
	
	public void addCommand(C command) {
		if (commands == null) {
			commands = new ArrayList<>();
		}
		
		commands.add(command);
	}
	
	public int getSize() {
		if (commands == null) {
			return 0;
		} else {
			return commands.size();
		}
	}
	
	public int getSerializedSize() {
		if (serializedCommands == null) {
			return 0;
		} else {
			return serializedCommands.size();
		}
	}

	@Override
	public String toString() {
		return "CommandList [commands=" + commands + ", serializedCommands=" + serializedCommands + "]";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = HASH_PRIME * result + ((commands == null) ? 0 : commands.hashCode());
		result = HASH_PRIME * result + ((serializedCommands == null) ? 0 : serializedCommands.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommandList other = (CommandList) obj;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		if (serializedCommands == null) {
			if (other.serializedCommands != null)
				return false;
		} else if (!serializedCommands.equals(other.serializedCommands))
			return false;
		return true;
	}

	public List<C> getCommands() {
		if (commands == null) {
			commands = new ArrayList<>();
		}
		
		return commands;
	}

	public void setCommands(List<C> commands) {
		if (commands == null) {
			this.commands.clear();
		} else {
			this.commands = commands;
		}
	}
}