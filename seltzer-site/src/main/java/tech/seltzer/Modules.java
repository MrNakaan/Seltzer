package tech.seltzer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;

public class Modules extends BasePage {
	private static final long serialVersionUID = -3794925557659701855L;

	public Modules(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		header.getModulesTop();
		header.getModulesLeft();
	}
	
	@Override
	protected void markActiveNavSection() {
		header.getModulesTop().add(new MarkActiveBehavior());
		header.getModulesLeft().add(new MarkActiveBehavior());
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Modules | Seltzer"));
	}
}
