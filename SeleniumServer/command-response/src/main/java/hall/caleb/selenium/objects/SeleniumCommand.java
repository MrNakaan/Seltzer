package hall.caleb.selenium.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hall.caleb.selenium.enums.SeleniumCommandType;
import hall.caleb.selenium.enums.SeleniumSelectorType;

// Maybe add some sort of modifier or subclass to handle/return groups of items
public class SeleniumCommand {
	private UUID id;
	private SeleniumCommandType commandType;
	private SeleniumSelectorType selectorType = SeleniumSelectorType.Xpath;
	private String selector;
	private String attribute;
	private String url;
	private String text;
	private List<SeleniumCommand> commands;

	public SeleniumCommand(SeleniumCommandType commandType) {
		this.commandType = commandType;
		this.commands = new ArrayList<>();
	}

	public SeleniumCommand(UUID id, SeleniumCommandType commandType) {
		this.id = id;
		this.commandType = commandType;
		this.commands = new ArrayList<>();
	}

	/**
	 * A convenience method for simultaneously setting the selector and selector
	 * type.
	 * 
	 * @param selector
	 * @param selectorType
	 */
	public void setSelector(String selector, SeleniumSelectorType selectorType) {
		this.selector = selector;
		this.selectorType = selectorType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SeleniumCommand [id=");
		builder.append(id);
		builder.append(", commandType=");
		builder.append(commandType);
		builder.append(", selectorType=");
		builder.append(selectorType);
		builder.append(", selector=");
		builder.append(selector);
		builder.append(", attribute=");
		builder.append(attribute);
		builder.append(", url=");
		builder.append(url);
		builder.append(", text=");
		builder.append(text);
		builder.append(", commands=");
		builder.append(commands);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attribute == null) ? 0 : attribute.hashCode());
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		result = prime * result + ((commands == null) ? 0 : commands.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((selector == null) ? 0 : selector.hashCode());
		result = prime * result + ((selectorType == null) ? 0 : selectorType.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		SeleniumCommand other = (SeleniumCommand) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		if (commandType != other.commandType)
			return false;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (selector == null) {
			if (other.selector != null)
				return false;
		} else if (!selector.equals(other.selector))
			return false;
		if (selectorType != other.selectorType)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public SeleniumSelectorType getSelectorType() {
		return selectorType;
	}

	public void setSelectorType(SeleniumSelectorType selectorType) {
		this.selectorType = selectorType;
	}

	public SeleniumCommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(SeleniumCommandType commandType) {
		this.commandType = commandType;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public String getAttribute() {
		return attribute;
	}

	/**
	 * Sets the attribute that will be read. Multiple attributes can be
	 * specified by separating them with slashes. For example, "id/class" will
	 * return the value of the "id" attribute if it exists and the "class"
	 * attribute otherwise.
	 * 
	 * @param attribute
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<SeleniumCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<SeleniumCommand> commands) {
		this.commands = commands;
	}
}
