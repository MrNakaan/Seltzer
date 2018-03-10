package tech.seltzer.core;

import org.openqa.selenium.WebElement;

public class CachedWebElement {
	private Long lastAccess = 0L;
	private WebElement element = null;

	public CachedWebElement(Long lastAccess, WebElement element) {
		super();
		this.lastAccess = lastAccess;
		this.element = element;
	}

	@Override
	public String toString() {
		return "CachedWebElement [lastAccess=" + lastAccess + ", element=" + element + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((lastAccess == null) ? 0 : lastAccess.hashCode());
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
		CachedWebElement other = (CachedWebElement) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (lastAccess == null) {
			if (other.lastAccess != null)
				return false;
		} else if (!lastAccess.equals(other.lastAccess))
			return false;
		return true;
	}

	public Long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}

	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}
}
