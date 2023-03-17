package fr.linkyproject.dataservice.wsservice;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Logger;

import fr.linkyproject.dataservice.wsservice.listeners.AskDataListener;
import fr.linkyproject.dataservice.wsservice.listeners.DataRegistrationListener;
import fr.linkyproject.dataservice.wsservice.listeners.EnableDataStreamListener;
import fr.linkyproject.dataservice.wsservice.listeners.Listener;
import fr.linkyproject.dataservice.wsservice.listeners.PingListener;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

/**
 * Le PakcetDispatch permet d'envoyer un packet
 * au {@link Listener} qui lui correspond.
 * 
 * Si un {@link Listener} correspond au packet,
 * il est alors instancié et utilisé pour interpréter
 * le packet.
 * 
 * Les listeners sont appelés sur le thread de la méthode onMessage
 * du {@link Client}.
 */
public class PacketDispatch {
	private static final Logger LOGGER = Logger.getLogger("PacketDispatch");
	private static final String PACKET_NAME = "packet";
	private Map<String, Supplier<Listener>> listeners = Collections.synchronizedMap(new HashMap<>());
	
	public PacketDispatch() {
		this.initListeners();
	}
	
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		if(!jpacket.containsKey(PACKET_NAME)) {
			LOGGER.info("Unknow json message from '" + session.getId() + "'");
			return;
		}
		
		String packetName = jpacket.getString(PACKET_NAME);
		
		if(!this.listeners.containsKey(packetName))
			throw new IOException("Unknow packet name '" + packetName + "'");
		
		LOGGER.info("Dispatch '" + packetName + "' packet");
		this.listeners.get(packetName).get().onReceive(manager, session, jpacket);
	}
	
	private void initListeners() {
		this.listeners.put("ask_data", AskDataListener::new);
		this.listeners.put("data_registration", DataRegistrationListener::new);
		this.listeners.put("enable_data_stream", EnableDataStreamListener::new);
		this.listeners.put("ping", PingListener::new);
	}
	
	public void addListener(String packetName, Supplier<Listener> listener) {
		this.listeners.put(packetName, listener);
	}
	
	public void removeListener(String packetName) {
		this.listeners.remove(packetName);
	}
}
