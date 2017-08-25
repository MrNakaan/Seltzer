package tech.seltzer.common;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.ClassAttributeModifier;

public class MarkActiveBehavior extends ClassAttributeModifier {
	private static final long serialVersionUID = 5319837675470506142L;

	public MarkActiveBehavior() {
		super();
		
	}
	
	@Override
	protected Set<String> update(Set<String> oldClasses) {
		Set<String> newClasses = new HashSet<>();
		
		newClasses.addAll(oldClasses);
		newClasses.add("active");
		
		return newClasses;
	}
}