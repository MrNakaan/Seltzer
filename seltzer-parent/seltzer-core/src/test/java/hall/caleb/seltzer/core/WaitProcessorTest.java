package hall.caleb.seltzer.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import javax.annotation.Generated;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.enums.SelectorType;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.command.selector.SelectorCommand;
import hall.caleb.seltzer.objects.command.wait.OldVisibilityWaitCommand;
import hall.caleb.seltzer.objects.response.MultiResultResponse;
import hall.caleb.seltzer.util.CommandFactory;

@Generated(value = "org.junit-tools-1.0.5")
public class WaitProcessorTest {
	private static SeltzerSession session;
	private static String homeUrl;

	@BeforeClass
	public static void prepareClass() throws FileNotFoundException {
		SeltzerServer.configureBase();

		session = new SeltzerSession();
		
		String repoPath = System.getProperty("repo.path");
        if (repoPath == null) {
            throw new IllegalArgumentException("Property repo.path not found!");
        }
        	
		homeUrl = "file:///" + repoPath.replace(" ", "%20") + "/seltzer-parent/seltzer-core/src/test/resources/testHome.htm";
	}

	@AfterClass
	public static void cleanDriver() {
		session.executeCommand(new Command(CommandType.Exit, session.getId()));
	}

	@Before
	public void goToTestHome() {
		session.getDriver().navigate().to(homeUrl);
	}

	@Test
	public void testWait() {
		SelectorCommand readCommand = CommandFactory.newReadAttributeCommand(session.getId(), SelectorType.Xpath, "//h2[1]", 1, "style");
		MultiResultResponse response = (MultiResultResponse) session.executeCommand(readCommand);
		assertTrue("Is there 1 result?", response.getResults().size() == 1);
		assertEquals("Is the <h2> hidden?", "display: none;", response.getResults().get(0));
		
		OldVisibilityWaitCommand waitCommand = new OldVisibilityWaitCommand(session.getId());
		waitCommand.setSelector("//h2[1]", SelectorType.Xpath);
		waitCommand.setSeconds(15);
		session.executeCommand(waitCommand);

		readCommand = CommandFactory.newReadTextCommand(session.getId(), SelectorType.Xpath, "//h2[1]", 1);
		response = (MultiResultResponse) session.executeCommand(readCommand);
		
		assertTrue("Is there 1 result?", response.getResults().size() == 1);
		assertEquals("Is the text of the result right?", "Some Hidden Element", response.getResults().get(0));
	}
}