package fr.linkyproject.dataservice.wsservice.listeners;

import java.io.IOException;

import fr.linkyproject.dataservice.ServiceProvider;
import fr.linkyproject.dataservice.wsservice.JsonPacketUtil;
import fr.linkyproject.dataservice.wsservice.SessionManager;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

public class EnableDataStreamListener implements Listener {
	
	@Override
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		String serviceName = JsonPacketUtil.getServiceName(jpacket);
		
		if(serviceName.equals(ServiceProvider.DHT))
			manager.setGroup(SessionManager.DHT_GROUP, session);
		else if(serviceName.equals(ServiceProvider.LINKY))
			manager.setGroup(SessionManager.LINKY_GROUP, session);
		else
			throw new IOException("Unsupported service '" + serviceName + "'");
	}
	
}
