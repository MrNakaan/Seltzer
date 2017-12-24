package tech.seltzer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.lang.Bytes;

import tech.seltzer.documentation.Apple;
import tech.seltzer.documentation.Blackberry;
import tech.seltzer.documentation.Compilation;
import tech.seltzer.documentation.Documentation;
import tech.seltzer.documentation.ModulesQuick;
import tech.seltzer.documentation.ServerQuick;
import tech.seltzer.tests.MainTests1;
import tech.seltzer.tests.Tests;
import tech.seltzer.tests.WaitTests;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see tech.seltzer.Start#main(String[])
 */
public class SeltzerSite extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return Index.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		
		getStoreSettings().setMaxSizePerSession(Bytes.kilobytes(64));
		getStoreSettings().setInmemoryCacheSize(0);
		
		mountPages();
	}

	private void mountPages() {
		mountDocs();
		mountPage("/modules", Modules.class);
		mountPage("/schedule", Schedule.class);
		mountTests();
	}

	private void mountDocs() {
		mountPage("/docs", Documentation.class);
		mountPage("/docs/compilation", Compilation.class);
		mountPage("/docs/quick/server", ServerQuick.class);
		mountPage("/docs/quick/modules", ModulesQuick.class);
		mountPage("/docs/apple", Apple.class);
		mountPage("/docs/blackberry", Blackberry.class);
	}

	private void mountTests() {
		mountPage("/tests", Tests.class);
		mountPage("/tests/main1", MainTests1.class);
		mountPage("/tests/wait", WaitTests.class);
	}
}
