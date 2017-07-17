package hall.caleb.seltzer.enums;

import hall.caleb.seltzer.objects.CrDataBase;

public interface CrType {
	public Class<? extends CrDataBase> getCrClass();
}
