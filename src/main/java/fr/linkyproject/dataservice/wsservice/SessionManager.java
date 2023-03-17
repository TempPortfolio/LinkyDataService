package fr.linkyproject.dataservice.wsservice;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.json.JsonObject;
import jakarta.websocket.Session;

/**
 * Garde en mémoire les sessions actives du serveur
 * web socket, et permet l'utilisation de groupes pour
 * envoyer des packets groupés.
 */
public class SessionManager {
	public static final String DHT_GROUP = "dht_listeners";
	public static final String LINKY_GROUP = "linky_listeners";
	
	private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
	private final Map<String, SessionGroup> groups = Collections.synchronizedMap(new HashMap<>());
	
	public SessionManager() {
		this.addGroup(DHT_GROUP);
		this.addGroup(LINKY_GROUP);
	}
	
	public void register(Session session) {
		this.sessions.add(session);
	}
	
	public void unregister(Session session) {
		this.sessions.remove(session);
	}
	
	public void addGroup(String name) {
		this.groups.put(name, new SessionGroup());
	}
	
	private SessionGroup getGroup(String name) {
		return this.groups.get(name);
	}
	
	public void setGroup(String name, Session session) {
		this.getGroup(name).add(session);
	}
	
	public void send(String group, JsonObject jpacket) {
		this.getGroup(group).send(jpacket);
	}
	
	public Set<Session> getSessions() {
		return Collections.unmodifiableSet(this.sessions);
	}
}
