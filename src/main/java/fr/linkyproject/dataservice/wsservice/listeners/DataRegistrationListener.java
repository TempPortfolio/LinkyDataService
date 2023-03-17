package fr.linkyproject.dataservice.wsservice.listeners;

import java.io.IOException;

import fr.linkyproject.dataservice.ServiceProvider;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.entries.LinkyEntry;
import fr.linkyproject.dataservice.wsservice.JsonPacketUtil;
import fr.linkyproject.dataservice.wsservice.SessionManager;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

public class DataRegistrationListener implements Listener {
	
	@Override
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		String serviceName = JsonPacketUtil.getServiceName(jpacket);
		
		JsonObject response = JsonPacketUtil.createPacket("new_data_registration", serviceName);
		
		if(serviceName.equals(ServiceProvider.DHT)) {
			DHTEntry dht = JsonPacketUtil.getDhtEntry(jpacket);
			
			ServiceProvider.get().getDhtService().register(dht);
			
			response = JsonPacketUtil.setDhtEntry(response, dht);
			manager.send(SessionManager.DHT_GROUP, response);
		}
		else if(serviceName.equals(ServiceProvider.LINKY)) {
			LinkyEntry linky = JsonPacketUtil.getLinkyEntry(jpacket);
			
			ServiceProvider.get().getLinkyService().register(linky);
			
			response = JsonPacketUtil.setLinkyEntry(response, linky);
			manager.send(SessionManager.LINKY_GROUP, response);
		}
		else
			throw new IOException("Unsupported service '" + serviceName + "'");
	}
}
