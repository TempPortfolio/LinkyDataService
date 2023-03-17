package fr.linkyproject.dataservice.wsservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import fr.linkyproject.dataservice.TestListener;
import fr.linkyproject.dataservice.TestSession;
import jakarta.json.Json;
import jakarta.websocket.Session;

public class PacketDispatchTest {
	
	@Test
	public void onReceiveTest() {
		SessionManager manager = new SessionManager();
		Session session = new TestSession();
		TestListener listener = new TestListener();
		
		final String ID = UUID.randomUUID().toString();
		PacketDispatch disp = new PacketDispatch();
		
		disp.addListener(ID, () -> listener);
		
		try {
			disp.onReceive(
					manager, 
					session, 
					Json.createObjectBuilder().add("packet", ID).build()
				);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false, "exception happened: " + e.getMessage());
		}
		
		assertTrue(
				session == listener.getLastSession(), 
				"Session equality error"
			);
	}
	
}
