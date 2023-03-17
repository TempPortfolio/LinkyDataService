package fr.linkyproject.dataservice.wsservice;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.json.JsonObject;
import jakarta.websocket.Session;

/**
 * Simple conteneur session.
 */
public class SessionGroup {
	private Set<WeakReference<Session>> sessions = new HashSet<>();
	
	public SessionGroup() {
		
	}
	
	public void add(Session session) {
		this.clean();
		this.sessions.add(new WeakReference<Session>(session));
	}
	
	public void send(JsonObject jpacket) {
		this.clean();
		this.sessions.forEach(ws -> ws.get().getAsyncRemote().sendObject(jpacket));
	}
	
	public void clean() {
		List<WeakReference<Session>> toRemove = new ArrayList<>();
		for(WeakReference<Session> wr : this.sessions)
			if(wr.get() == null)
				toRemove.add(wr);
		this.sessions.removeAll(toRemove);
	}
}
