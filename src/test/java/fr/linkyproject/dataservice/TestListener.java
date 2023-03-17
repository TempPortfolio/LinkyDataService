package fr.linkyproject.dataservice;

import java.io.IOException;

import fr.linkyproject.dataservice.wsservice.SessionManager;
import fr.linkyproject.dataservice.wsservice.listeners.Listener;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;
import lombok.Getter;

@Getter
public class TestListener implements Listener {
	private SessionManager lastManager;
	private Session lastSession;
	private JsonObject lastJpacket;
	
	@Override
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		this.lastManager = manager;
		this.lastSession = session;
		this.lastJpacket = jpacket;
	}
	
}
