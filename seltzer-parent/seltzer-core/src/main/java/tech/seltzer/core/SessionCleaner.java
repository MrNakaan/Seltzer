package tech.seltzer.core;

import java.text.MessageFormat;

public class SessionCleaner implements Runnable {
	private int sessionsCleaned = 0;
	
	@Override
	public void run() {
		int sessionsJustCleaned = 0;
		
		while (true) {
			sessionsJustCleaned = SeltzerSession.cleanSessions();
			
			if (sessionsJustCleaned > 0) {
				String message = Messages.getString("SessionCleaner.justCleaned");
				System.out.println(MessageFormat.format(message, sessionsJustCleaned));
				sessionsCleaned += sessionsJustCleaned;
				sessionsJustCleaned = 0;
				message = Messages.getString("SessionCleaner.totalCleaned");
				System.out.println(MessageFormat.format(message, sessionsCleaned));
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
