package tech.seltzer.enums;

import tech.seltzer.objects.CrDataBase;

public interface CrType {
	public Class<? extends CrDataBase> getCrClass();
}
