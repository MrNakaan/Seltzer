package tech.seltzer.core;

import java.util.List;

import org.openqa.selenium.WebElement;

import tech.seltzer.objects.command.Selector;

public class CachedSelection {
	private Long lastAccess = 0L;
	private List<WebElement> elements = null;
	private Selector originalSelector = null;
	
	public CachedSelection(Long lastAccess, List<WebElement> elements, Selector originalSelector) {
		super();
		this.lastAccess = lastAccess;
		this.elements = elements;
		this.originalSelector = originalSelector;
	}

	@Override
	public String toString() {
		return "CachedSelection [lastAccess=" + lastAccess + ", elements=" + elements + ", originalSelector="
				+ originalSelector + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((lastAccess == null) ? 0 : lastAccess.hashCode());
		result = prime * result + ((originalSelector == null) ? 0 : originalSelector.hashCode());
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
		CachedSelection other = (CachedSelection) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (lastAccess == null) {
			if (other.lastAccess != null)
				return false;
		} else if (!lastAccess.equals(other.lastAccess))
			return false;
		if (originalSelector == null) {
			if (other.originalSelector != null)
				return false;
		} else if (!originalSelector.equals(other.originalSelector))
			return false;
		return true;
	}

	public Long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}

	public List<WebElement> getElements() {
		return elements;
	}

	public Selector getOriginalSelector() {
		return originalSelector;
	}
}
