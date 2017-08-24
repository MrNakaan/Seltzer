package tech.seltzer.documentation;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;
import tech.seltzer.common.MarkActiveBehavior;

public class ServerQuick extends BasePage {
	private static final long serialVersionUID = 6322463595916567949L;

	public ServerQuick(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void markActiveNavSection() {
		header.getDocsTop().add(new MarkActiveBehavior());
		header.getDocsLeft().add(new MarkActiveBehavior());
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Running Seltzer | Seltzer"));
	}
}
