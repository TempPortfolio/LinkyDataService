package fr.linkyproject.dataservice.wsservice.listeners;

import java.io.IOException;
import java.util.logging.Logger;

import fr.linkyproject.dataservice.wsservice.SessionManager;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

public class PingListener implements Listener {
	private static final Logger LOGGER = Logger.getLogger("PingListener");
	
	@Override
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		LOGGER.info("Receive ping from '" + session.getId() + "'");
		
		session.getAsyncRemote().sendObject(
				Json.createObjectBuilder().add("packet", "pong").build());
	}
	
}
