package tech.seltzer.tests;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MainTests1 extends Tests {
	private static final long serialVersionUID = 5495930322493176965L;

	public MainTests1(PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	protected void setTitle() {
		add(new Label("title", "Main Tests 1 | Seltzer"));
	}
}
