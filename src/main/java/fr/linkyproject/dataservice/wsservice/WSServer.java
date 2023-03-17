package fr.linkyproject.dataservice.wsservice;

import org.glassfish.tyrus.server.Server;

import jakarta.websocket.DeploymentException;

/**
 * Classe de lancement pour le serveur
 * websocket.
 */
public class WSServer {
	private Server server = null;
	
	private SessionManager manager = new SessionManager();
	private PacketDispatch dispatcher = new PacketDispatch();
	
	private boolean wasStarted = false;
	
	private WSServer() {
		
	}
	
	public void start(String host, int port) throws DeploymentException {
		if(this.wasStarted) return;
		this.wasStarted = true;
		
		this.server = new Server(host, port, "/", null, Client.class);
		this.server.start();
	}
	
	public void stop() {
		if(!wasStarted || this.server == null)
			return;
		
		this.server.stop();
	}
	
	public SessionManager getManager() {
		return this.manager;
	}
	
	public PacketDispatch getDispatch() {
		return this.dispatcher;
	}
	
	private static final WSServer INSTANCE = new WSServer();
	public static WSServer get() {
		return INSTANCE;
	}
}
