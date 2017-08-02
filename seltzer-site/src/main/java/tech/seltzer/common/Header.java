package tech.seltzer.common;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import tech.seltzer.Index;
import tech.seltzer.Modules;
import tech.seltzer.Schedule;
import tech.seltzer.documentation.Documentation;
import tech.seltzer.tests.Tests;

public class Header extends Panel {
	private static final long serialVersionUID = 5197492705200971546L;

	public Header(String id) {
		super(id);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new navLink("homeTop", Index.class));
		add(new navLink("homeSide", Index.class));
		add(new navLink("modulesTop", Modules.class));
		add(new navLink("modulesSide", Modules.class));
		add(new navLink("scheduleTop", Schedule.class));
		add(new navLink("scheduleSide", Schedule.class));
		add(new navLink("documentationTop", Documentation.class));
		add(new navLink("documentationSide", Documentation.class));
		add(new navLink("testsTop", Tests.class));
		add(new navLink("testsSide", Tests.class));
	}
	
	private static class navLink extends Link<WebPage> {
		private static final long serialVersionUID = 4280585915062494994L;
		
		private Class<? extends WebPage> page;
		
		public navLink(String id, Class<? extends WebPage> page) {
			super(id);
			this.page = page;
		}

		@Override
		public void onClick() {
			setResponsePage(page);
		}
	}
}
