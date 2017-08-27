package tech.seltzer.objects;

/**
 * This interface is used by commands or responses that make use 
 * of lists of subcommands/subresponses in order to properly serialize
 * and deserialize those children.
 */
public interface SerializableCR {
	/**
	 * Serialize child commands/responses within the object
	 */
	void serialize();
	
	/**
	 * Deserialize child commands/responses within the object
	 */
	void deserialize();
}
