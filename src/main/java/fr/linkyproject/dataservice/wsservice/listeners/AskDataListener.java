package fr.linkyproject.dataservice.wsservice.listeners;

import java.io.IOException;
import java.util.List;

import fr.linkyproject.dataservice.ServiceProvider;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.entries.LinkyEntry;
import fr.linkyproject.dataservice.wsservice.JsonPacketUtil;
import fr.linkyproject.dataservice.wsservice.SessionManager;
import jakarta.json.JsonObject;
import jakarta.websocket.Session;

public class AskDataListener implements Listener {

	@Override
	public void onReceive(SessionManager manager, Session session, JsonObject jpacket) throws IOException {
		String serviceName = JsonPacketUtil.getServiceName(jpacket);
		
		JsonObject response = JsonPacketUtil.createPacket("data_response", serviceName);
		
		if(serviceName.equals(ServiceProvider.DHT)) {
			List<DHTEntry> entries = ServiceProvider.get().getDhtService().get();
			response = JsonPacketUtil.setDhtEntry(response, entries);
		}
		else if(serviceName.equals(ServiceProvider.LINKY)) {
			List<LinkyEntry> entries = ServiceProvider.get().getLinkyService().get();
			response = JsonPacketUtil.setLinkyEntry(response, entries);
		}
		else
			throw new IOException("Unsupported service '" + serviceName + "'");
		
		session.getAsyncRemote().sendObject(response);
	}

}
