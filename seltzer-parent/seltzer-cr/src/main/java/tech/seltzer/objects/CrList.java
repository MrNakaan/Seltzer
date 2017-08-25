package tech.seltzer.objects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;

import tech.seltzer.objects.command.CommandData;

public class CrList<C extends CrDataBase> {
	public static final int HASH_PRIME = 31;
	
	private List<C> crs;
	private List<String> serializedCrs;
	
	public CrList() {
		crs = null;
		serializedCrs = null;
	}
	
	public void serialize() {
		if (serializedCrs == null) {
			serializedCrs = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		
		if (!CollectionUtils.isEmpty(crs)) {
			for (CrDataBase subCr : crs) {
				if (subCr instanceof SerializableCR) {
					((SerializableCR) subCr).serialize();
				}
				
				serializedCrs.add(gson.toJson(subCr, subCr.getType().getCrClass()));
			}
			
			crs.clear();
		}
	}

	@SuppressWarnings("unchecked")
	public void deserialize() {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		Gson gson = new Gson();
		CommandData subCommand;
		
		if (!CollectionUtils.isEmpty(serializedCrs)) {
			for (String serializedCommand : serializedCrs) {
				subCommand = gson.fromJson(serializedCommand, CommandData.class);
				
				subCommand = gson.fromJson(serializedCommand, subCommand.getType().getCrClass());
				
				if (subCommand instanceof SerializableCR) {
					((SerializableCR) subCommand).deserialize();
				}
				
				crs.add((C) subCommand);
			}
			
			serializedCrs.clear();
		}
	}
	
	public void addCr(C cr) {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		crs.add(cr);
	}
	
	public int getSize() {
		if (crs == null) {
			return 0;
		} else {
			return crs.size();
		}
	}
	
	public int getSerializedSize() {
		if (serializedCrs == null) {
			return 0;
		} else {
			return serializedCrs.size();
		}
	}

	@Override
	public String toString() {
		return "CommandList [commands=" + crs + ", serializedCommands=" + serializedCrs + "]";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = HASH_PRIME * result + ((crs == null) ? 0 : crs.hashCode());
		result = HASH_PRIME * result + ((serializedCrs == null) ? 0 : serializedCrs.hashCode());
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
		CrList other = (CrList) obj;
		if (crs == null) {
			if (other.crs != null)
				return false;
		} else if (!crs.equals(other.crs))
			return false;
		if (serializedCrs == null) {
			if (other.serializedCrs != null)
				return false;
		} else if (!serializedCrs.equals(other.serializedCrs))
			return false;
		return true;
	}

	public List<C> getCrs() {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		return crs;
	}

	public void setCrs(List<C> crs) {
		if (crs == null) {
			this.crs.clear();
		} else {
			this.crs = crs;
		}
	}
}