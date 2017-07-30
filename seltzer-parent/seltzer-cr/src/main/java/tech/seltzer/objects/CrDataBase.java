package tech.seltzer.objects;

import java.util.UUID;

import tech.seltzer.enums.CrType;

public abstract class CrDataBase {
	protected UUID id;
	
	public abstract CrType getType();
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
