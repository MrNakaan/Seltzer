package hall.caleb.seltzer.objects.command;

import hall.caleb.seltzer.enums.SelectorType;

public class Selector {
	public static final int HASH_PRIME = 31;
	
	protected SelectorType type;
	protected String path;
	
	public Selector() {
		setSelector(SelectorType.NONE, "");
	}
	
	public Selector(SelectorType selectorType, String selector) {
		setSelector(selectorType, selector);
	}
	
	public void setSelector(SelectorType selectorType, String selector) {
		this.path = selector;
		this.type = selectorType;
	}

	@Override
	public String toString() {
		return "Selector [selectorType=" + type + ", selector=" + path + "]";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = HASH_PRIME * result + ((path == null) ? 0 : path.hashCode());
		result = HASH_PRIME * result + ((type == null) ? 0 : type.hashCode());
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
		Selector other = (Selector) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public SelectorType getType() {
		return type;
	}

	public void setType(SelectorType type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
