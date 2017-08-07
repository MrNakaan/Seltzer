package tech.seltzer.documentation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;

public class Compilation extends BasePage {
	private static final long serialVersionUID = 5138380666188445044L;

	public Compilation(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void markActiveNavSection() {
		header.getDocsTop().add(new MarkActiveBehavior());
		header.getDocsLeft().add(new MarkActiveBehavior());
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Compiling Seltzer | Seltzer"));
	}
}
