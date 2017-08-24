package tech.seltzer.documentation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;
import tech.seltzer.common.MarkActiveBehavior;

public class ModulesQuick extends BasePage {
	private static final long serialVersionUID = 1439636669797824218L;

	public ModulesQuick(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void markActiveNavSection() {
		header.getDocsTop().add(new MarkActiveBehavior());
		header.getDocsLeft().add(new MarkActiveBehavior());
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Using Seltzer | Seltzer"));
	}
}
