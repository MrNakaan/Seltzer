package tech.seltzer.common;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;

public class Header extends Panel {
	private static final long serialVersionUID = 5197492705200971546L;

	private Component modulesTop;
	private Component modulesLeft;
	private Component scheduleTop;
	private Component scheduleLeft;
	private Component docsTop;
	private Component docsLeft;
	private Component testsTop;
	private Component testsLeft;
	
	public Header(String id) {
		super(id);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		createComponents();
		addComponents();
	}
	
	private void createComponents() {
		modulesTop = new WebMarkupContainer("modulesTop");
		modulesLeft = new WebMarkupContainer("modulesLeft");
		scheduleTop = new WebMarkupContainer("scheduleTop");
		scheduleLeft= new WebMarkupContainer("scheduleLeft");
		docsTop = new WebMarkupContainer("docsTop");
		docsLeft = new WebMarkupContainer("docsLeft");
		testsTop = new WebMarkupContainer("testsTop");
		testsLeft= new WebMarkupContainer("testsLeft");
	}
	
	private void addComponents() {
		add(modulesTop);
		add(modulesLeft);
		add(scheduleTop);
		add(scheduleLeft);
		add(docsTop);
		add(docsLeft);
		add(testsTop);
		add(testsLeft);
	}

	public Component getModulesTop() {
		return modulesTop;
	}

	public Component getModulesLeft() {
		return modulesLeft;
	}

	public Component getScheduleTop() {
		return scheduleTop;
	}

	public Component getScheduleLeft() {
		return scheduleLeft;
	}

	public Component getDocsTop() {
		return docsTop;
	}

	public Component getDocsLeft() {
		return docsLeft;
	}

	public Component getTestsTop() {
		return testsTop;
	}

	public Component getTestsLeft() {
		return testsLeft;
	}
}
