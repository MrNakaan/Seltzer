	package hall.caleb.seltzer.core;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import hall.caleb.seltzer.core.processor.BaseProcessor;
import hall.caleb.seltzer.objects.command.Command;
import hall.caleb.seltzer.objects.response.Response;

public class SeltzerSession implements Closeable {
	private static List<SeltzerSession> sessions = new CopyOnWriteArrayList<>();
	
	public static final long SESSION_NEVER_USED_TIMEOUT = 600000; // 10 minutes
	public static final long SESSION_INACTIVE_TIMEOUT = 3600000; // 1 hour
	
	private UUID id = null;
	private WebDriver driver = null;
	private long startedTime = 0;
	private long lastUsed = 0;
	private Path dataDir;
	
	public SeltzerSession() {
		start();
	}
	
	public static SeltzerSession findSession(String id) {
		return findSession(UUID.fromString(id));
	}
	
	public static SeltzerSession findSession(UUID id) {
		synchronized(sessions) {
			for (SeltzerSession session : sessions) {
				if (session.getId().equals(id)) {
					return session;
				}
			}
		}
		
		return null;
	}
	
	public static int cleanSessions() {
		int sessionsCleaned = 0;
		
		for (SeltzerSession session : sessions) {
			try {
				if (session.getLastUsed() == 0) {
					if (System.currentTimeMillis() - session.getStartedTime() > SESSION_NEVER_USED_TIMEOUT) {
						session.close();
						sessionsCleaned++;
					}
				} else {
					if (System.currentTimeMillis() - session.getLastUsed() > SESSION_INACTIVE_TIMEOUT) {
						session.close();
						sessionsCleaned++;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sessionsCleaned;
	}
	
	public void start() {
		UUID id;
		while (this.id == null) {
			id = UUID.randomUUID();
			if (findSession(id) == null) {
				this.id = id;
			}
		}
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		dataDir = Paths.get(System.getProperty("repo.path"), "ChromeProfile", this.id.toString());
		options.addArguments("user-data-dir=" + dataDir);

		driver = new ChromeDriver(options);
		
		startedTime = System.currentTimeMillis();
		sessions.add(this);
	}

	@Override
	public void close() throws IOException {
		synchronized(sessions) {
			sessions.remove(this);
		}
		
		id = null;
		driver.quit();
		driver = null;

		FileUtils.deleteDirectory(dataDir.toFile());
	}
	
	public Response executeCommand(Command command) {
		this.lastUsed = System.currentTimeMillis();
		
		return BaseProcessor.processCommand(driver, command);
	}

	@Override
	public String toString() {
		return "SeltzerSession [id=" + id + ", driver=" + driver + ", startedTime=" + startedTime + ", lastUsed="
				+ lastUsed + ", dataDir=" + dataDir + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDir == null) ? 0 : dataDir.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (lastUsed ^ (lastUsed >>> 32));
		result = prime * result + (int) (startedTime ^ (startedTime >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeltzerSession other = (SeltzerSession) obj;
		if (dataDir == null) {
			if (other.dataDir != null)
				return false;
		} else if (!dataDir.equals(other.dataDir))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUsed != other.lastUsed)
			return false;
		if (startedTime != other.startedTime)
			return false;
		return true;
	}

	public UUID getId() {
		return id;
	}

	public long getStartedTime() {
		return startedTime;
	}

	public long getLastUsed() {
		return lastUsed;
	}

	public Path getDataDir() {
		return dataDir;
	}
}
