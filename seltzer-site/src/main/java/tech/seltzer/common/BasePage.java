package tech.seltzer.common;

import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;

	protected Header header;
	protected Footer footer;
	
	public BasePage(final PageParameters parameters) {
		super(parameters);
		
		setStatelessHint(true);
    }
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		header = new Header("headerPanel");
		footer = new Footer("footerPanel");
		
		add(header);
		add(footer);
		
		add(new DebugBar("debug"));
		
		markActiveNavSection();
		setTitle();
	}
	
	@Override
	protected void onDetach() {
		header.onDetach();
		
		header = null;
		footer = null;
		
		super.onDetach();
	}
	
	protected abstract void markActiveNavSection();
	
	protected abstract void setTitle();
}
