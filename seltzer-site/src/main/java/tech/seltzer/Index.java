package tech.seltzer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;

public class Index extends BasePage {
	private static final long serialVersionUID = 7146561197795644314L;

	public Index(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void markActiveNavSection() {
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Seltzer"));
	}
}
