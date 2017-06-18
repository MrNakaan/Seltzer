package hall.caleb.seltzer.core;

public class SessionCleaner implements Runnable {
	private int sessionsCleaned = 0;
	
	@Override
	public void run() {
		int sessionsJustCleaned = 0;
		
		while (true) {
			sessionsJustCleaned = SeltzerSession.cleanSessions();
			
			if (sessionsJustCleaned > 0) {
				System.out.println("Sessions just cleaned: " + sessionsJustCleaned);
				sessionsCleaned += sessionsJustCleaned;
				sessionsJustCleaned = 0;
				System.out.println("Total sessions cleaned: " + sessionsCleaned);
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
