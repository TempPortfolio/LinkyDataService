package fr.linkyproject.dataservice.wsservice.listeners;

import java.io.IOException;

import fr.linkyproject.dataservice.wsservice.SessionManager;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

/**
 * Permets d'écouter et interpréter des packets. 
 */
public interface Listener {
	
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException;
	
}
