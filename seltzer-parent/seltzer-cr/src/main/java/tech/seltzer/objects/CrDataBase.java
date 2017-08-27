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
}
