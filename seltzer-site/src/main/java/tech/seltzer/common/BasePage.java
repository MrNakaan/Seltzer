package tech.seltzer.common;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.ClassAttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	protected Header header;
	protected Footer footer;
	
	public BasePage(final PageParameters parameters) {
		super(parameters);
    }
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		header = new Header("headerPanel");
		footer = new Footer("footerPanel");
		
		add(header);
		add(footer);
		
		markActiveNavSection();
		setTitle();
	}
	
	protected abstract void markActiveNavSection();
	
	protected abstract void setTitle();
	
	protected class MarkActiveBehavior extends ClassAttributeModifier {
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
}
