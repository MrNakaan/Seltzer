package hall.caleb.selenium;

public class SeleniumSessionCleaner implements Runnable {
	private int sessionsCleaned = 0;
	
	@Override
	public void run() {
		int sessionsJustCleaned = 0;
		
		while (true) {
			sessionsJustCleaned = SeleniumSession.cleanSessions();
			
			if (sessionsJustCleaned > 0) {
				System.out.println("Sessions just cleaned: " + sessionsJustCleaned);
				sessionsCleaned += sessionsJustCleaned;
				sessionsJustCleaned = 0;
				System.out.println("Total sessions cleaned: " + sessionsCleaned);
			}
			
			try {
				Thread.sleep(16000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
