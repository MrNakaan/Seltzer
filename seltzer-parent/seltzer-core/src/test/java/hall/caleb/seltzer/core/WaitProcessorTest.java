package hall.caleb.seltzer.core;

import java.io.FileNotFoundException;

import javax.annotation.Generated;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import hall.caleb.seltzer.enums.CommandType;
import hall.caleb.seltzer.objects.command.Command;

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
}