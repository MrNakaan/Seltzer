package tech.seltzer.objects;

import java.util.UUID;

import tech.seltzer.enums.CrType;

/**
 * The common base shared by all responses and all commands
 */
public abstract class CrDataBase {
	protected UUID id;
	
	public abstract CrType getType();
	
	/**
	 * Get the ID of this command or response.
	 * @return the ID of this object
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Set the ID of this command or response.
	 * @param id - the new ID for this object
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		CrDataBase other = (CrDataBase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
