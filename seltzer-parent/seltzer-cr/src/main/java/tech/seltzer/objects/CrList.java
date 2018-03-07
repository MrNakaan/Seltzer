package tech.seltzer.objects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.gson.Gson;

import tech.seltzer.objects.command.CommandData;

/**
 * A list for holding multiple commands or responses. 
 *
 * @param <C> - The parent class of whatever this list will be holding. 
 * This is intended to be <code>CommandData</code> or <code>Response</code> 
 * but it does not hurt to be more specific.
 */
public class CrList<C extends CrDataBase> {
	public static final int HASH_PRIME = 31;
	
	private List<C> crs;
	private List<String> serializedCrs;
	
	public CrList() {
		crs = null;
		serializedCrs = null;
	}
	
	/**
	 * Serialize all items in this list.
	 */
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

	/**
	 * Deserialize all items in this list.
	 */
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
	
	/**
	 * Get the size of this list, ignoring all serialized items.
	 * @return the total number of deserialized items in this list
	 */
	public int getSize() {
		if (crs == null) {
			return 0;
		} else {
			return crs.size();
		}
	}
	
	/**
	 * Get the size of this list, ignoring all deserialized items.
	 * @return the total number of serialized items in this list
	 */
	public int getSerializedSize() {
		if (serializedCrs == null) {
			return 0;
		} else {
			return serializedCrs.size();
		}
	}

	/**
	 * A method to add a CrDataBase object meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param cr - the object to add
	 */
	public void addCr(C cr) {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		crs.add(cr);
	}
	
	/**
	 * A method to add CrDataBase objects meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param crs - the objects to add
	 */
	public void addCr(List<C> crs) {
		addCrs(crs);
	}
	
	/**
	 * A method to add CrDataBase objects meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param crs - the objects to add
	 */
	public void addCrs(List<C> crs) {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		crs.addAll(crs);
	}
	
	/**
	 * A method to remove a CrDataBase object meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param cr - the object to remove
	 */
	public void removeCr(C cr) {
		if (crs == null) {
			crs = new ArrayList<>();
		} else {
			crs.remove(cr);
		}
	}
	
	/**
	 * A method to remove CrDataBase objects meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param crs - the objects to remove
	 */
	public void removeCr(List<C> crs) {
		removeCrs(crs);
	}
	
	/**
	 * A method to remove CrDataBase objects meant to be called from  
	 * within the classes using CrList from a method with a more 
	 * descriptive name.
	 * @param crs - the objects to remove
	 */
	public void removeCrs(List<C> crs) {
		if (crs == null) {
			crs = new ArrayList<>();
		} else {
			crs.removeAll(crs);
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

	/**
	 * Get the list of items that this wraps. Does not include serialized items.
	 * @return The raw list of deserialized items.
	 */
	public List<C> getCrs() {
		if (crs == null) {
			crs = new ArrayList<>();
		}
		
		return crs;
	}

	/**
	 * Set the list of items that this wraps. This will remove any currently serialized 
	 * items in the list
	 * @param crs - the new list of items 
	 */
	public void setCrs(List<C> crs) {
		if (crs == null) {
			this.crs.clear();
		} else {
			this.crs = crs;
		}
		
		if (serializedCrs != null) {
			this.serializedCrs.clear();
		}
	}
}