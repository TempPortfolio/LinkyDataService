package fr.linkyproject.dataservice.wsservice;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(
		value = "/",
		decoders = JsonDecoder.class,
		encoders = JsonEncoder.class
	)
public class Client {
	private static final Logger LOGGER = Logger.getLogger("Client");
	
	private SessionManager manager;
	private PacketDispatch packetDispatcher;
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		LOGGER.info("Open client '" + session.getId() + "'");
		this.manager = WSServer.get().getManager();
		this.packetDispatcher = WSServer.get().getDispatch();
		this.manager.register(session);
	}
	
	@OnMessage
	public void onMessage(JsonObject jpacket, Session session) throws IOException {
		LOGGER.info("[Client] -> [Server]");
		this.packetDispatcher.onReceive(this.manager, session, jpacket);
	}
	
	@OnClose
	public void onClose(Session session) {
		LOGGER.info("Close client '" + session.getId() + "'");
		this.manager.unregister(session);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		LOGGER.warning("An exception has occured. Client: " + session.getId() + " Error: " + throwable.getMessage());
		throwable.printStackTrace();
	}
}
