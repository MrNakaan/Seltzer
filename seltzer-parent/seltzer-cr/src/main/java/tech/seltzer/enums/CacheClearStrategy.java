package tech.seltzer.enums;

/**
 * Cache clear strategies determine how to handle new items being added when a 
 * cache has no free slots:
 * <ul>
 *  <li>fifo: first in first out; invalidate the oldest element(s)</li>
 *  <li>lifo: last in first out; invalidate the newest element(s)</li>
 *  <li>least_recently_used: invalidate the last-used item (based on timestamps)</li>
 *  <li>none: don't free up slots; returns a failure response</li>
 * </ul>
 */
public enum CacheClearStrategy {
	FIFO("First-in-first-out"),
	LEAST_RECENTLY_USED("Least recently used"),
	LIFO("Last-in-first-out"),
	NONE("None");
	
	private String name;
	
	private CacheClearStrategy(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
