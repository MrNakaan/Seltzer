package tech.seltzer.tests;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;
import tech.seltzer.common.MarkActiveBehavior;

public class Tests extends BasePage {
	private static final long serialVersionUID = -6230825932807094208L;

	public Tests(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void markActiveNavSection() {
		header.getTestsTop().add(new MarkActiveBehavior());
		header.getTestsLeft().add(new MarkActiveBehavior());
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Tests | Seltzer"));
	}
}
