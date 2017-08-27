package tech.seltzer.enums;

import tech.seltzer.objects.CrDataBase;

/**
 * An interface implemented by both <code>CommandType</code> and 
 * <code>ResponseType</code> primarily to facilitate serialization and 
 * deserialization. In the future, this interface will be used to 
 * provide more robust checking for command/repsonse types.
 */
public interface CrType {
	/**
	 * Get the class that provides this type of command/response 
	 * 
	 * @return The <code>Response</code>/<code>CommandData</code> class that 
	 * provides this response/command
	 */
	public Class<? extends CrDataBase> getCrClass();
}
