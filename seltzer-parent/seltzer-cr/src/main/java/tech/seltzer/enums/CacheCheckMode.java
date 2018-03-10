package tech.seltzer.enums;

/**
 * The cache check mode determines when (if ever) Seltzer will check the 
 * validity of a cache item.
 * <ul>
 *  <li>smart: check only cache elements that are referenced by the current 
 *  command or that could very likely be invalidated</li>
 *  <li>full: check all cache elements on every command</li>
 *  <li>none: never check cache elements; will very likely result in more 
 *  exception responses</li>
 * </ul>
 */
public enum CacheCheckMode {
	FULL("Full"),
	NONE("None"),
	SMART("Smart");
	
	private String name;
	
	private CacheCheckMode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
