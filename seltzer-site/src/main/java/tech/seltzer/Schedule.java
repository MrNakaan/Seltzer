package tech.seltzer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import tech.seltzer.common.BasePage;
import tech.seltzer.common.MarkActiveBehavior;

public class Schedule extends BasePage {
	private static final long serialVersionUID = 1832193049544720638L;

	public Schedule(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void markActiveNavSection() {
		header.getScheduleTop().add(new MarkActiveBehavior());
		header.getScheduleLeft().add(new MarkActiveBehavior());
	}

	@Override
	protected void setTitle() {
		add(new Label("title", "Schedule | Seltzer"));
	}
}
