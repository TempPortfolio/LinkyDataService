package fr.linkyproject.dataservice.wsservice;

import org.junit.jupiter.api.Test;

import fr.linkyproject.dataservice.TestSession;

public class SessionGroupTest {
	@Test
	public void cleanTest() {
		TestSession s1 = new TestSession();
		TestSession s2 = new TestSession();
		
		SessionGroup gr = new SessionGroup();
		
		gr.add(s1);
		gr.add(s2);
		
		gr.clean();
		
		s1 = null;
		s2 = null;
		
		System.gc();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { e.printStackTrace(); }
		
		gr.clean();
	}
}
