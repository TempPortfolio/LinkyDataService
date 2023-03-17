package fr.linkyproject.dataservice.wsservice;

import java.io.IOException;
import java.util.Collection;

import fr.linkyproject.dataservice.ServiceProvider;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.entries.LinkyEntry;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

/**
 * Utilitaire pour l'écriture et la lecture de packets json.
 */
public final class JsonPacketUtil {
	
	public static JsonObject createPacket(String packetName, String serviceName) {
		return Json.createObjectBuilder()
				.add("packet", packetName)
				.add("service", serviceName)
				.build();
	}
	
	public static DHTEntry getDhtEntry(JsonObject jpacket) throws IOException {
		if(!jpacket.containsKey("data"))
			throw new IOException("Can't find dht data");
		
		JsonObject jdht = jpacket.getJsonObject("data");
		//long time = jdht.getJsonNumber("time").longValue();
		long time = System.currentTimeMillis();
		double temperature = jdht.getJsonNumber("temperature").doubleValue();
		double humidity = jdht.getJsonNumber("humidity").doubleValue();
		
		return new DHTEntry(time, temperature, humidity);
	}
	
	public static JsonObject setDhtEntry(JsonObject jpacket, DHTEntry dht) {
		JsonObject jdht = Json.createObjectBuilder()
			.add("time", Long.toString(dht.getTime()))
			.add("temperature", dht.getTemperature())
			.add("humidity", dht.getHumidity())
			.build();
		
		return Json.createObjectBuilder(jpacket)
			.add("data", jdht)
			.build();
	}
	
	public static JsonObject setDhtEntry(JsonObject jpacket, Collection<DHTEntry> entries) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for(DHTEntry entry : entries) {
			builder.add(
				Json.createObjectBuilder()
					.add("time", Long.toString(entry.getTime()))
					.add("temperature", entry.getTemperature())
					.add("humidity", entry.getHumidity())
					.build()
			);
		}
		
		return Json.createObjectBuilder(jpacket)
				.add("data", builder.build())
				.build();
	}
	
	public static LinkyEntry getLinkyEntry(JsonObject jpacket) throws IOException {
		if(!jpacket.containsKey("data"))
			throw new IOException("Can't find linky data");
		
		JsonObject jlinky = jpacket.getJsonObject("data");
		
		return new LinkyEntry(
				System.currentTimeMillis(), 
				jlinky.getString("id"), 
				jlinky.getInt("subscribe_intensity"), 
				jlinky.getInt("base_hour_index"), 
				jlinky.getInt("full_hour_index"), 
				jlinky.getInt("offpeak_hour_index"), 
				jlinky.getInt("max_instensity"), 
				jlinky.getInt("instant_intensity"),
				jlinky.getInt("current_tarif_option")
			);
	}
	
	public static JsonObject setLinkyEntry(JsonObject jpacket, LinkyEntry linky) {
		JsonObject jdata = Json.createObjectBuilder().build();
		jdata = setLinkyEntryIn(jdata, linky);
		return Json.createObjectBuilder(jpacket)
				.add("data", jdata)
				.build();
	}
	
	public static JsonObject setLinkyEntry(JsonObject jpacket, Collection<LinkyEntry> entries) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		
		for(LinkyEntry entry : entries) {
			JsonObject jdata = Json.createObjectBuilder().build();
			jdata = setLinkyEntryIn(jdata, entry);
			builder.add(jdata);
		}
		
		return Json.createObjectBuilder(jpacket)
				.add("data", jpacket)
				.build();
	}
	
	private static JsonObject setLinkyEntryIn(JsonObject jpacket, LinkyEntry linky) {
		return Json.createObjectBuilder(jpacket)
				.add("time", Long.toString(linky.getTime()))
				.add("id", Json.createValue(linky.getId()))
				.add("subscribe_intensity", Json.createValue(linky.getSubscribeIntensity()))
				.add("base_hour_index", Json.createValue(linky.getBaseHourIndex()))
				.add("full_hour_index", Json.createValue(linky.getFullHourIndex()))
				.add("offpeak_hour_index", Json.createValue(linky.getOffpeakHourIndex()))
				.add("max_instensity", Json.createValue(linky.getMaxIntensity()))
				.add("instant_intensity", Json.createValue(linky.getInstantIntensity()))
				.add("current_tarif_option", Json.createValue(linky.getCurrentTariffOption()))
				.build();
	}
	
	public static String getPacketName(JsonObject jpacket) throws IOException {
		if(!jpacket.containsKey("packet"))
			throw new IOException("Can't find packet name");
		
		return jpacket.getString("packet");
	}
	
	public static String getServiceName(JsonObject jpacket) throws IOException {
		if(!jpacket.containsKey("service"))
			throw new IOException("Can't find service name");
		
		String serviceName = jpacket.getString("service");
		
		if(!serviceName.equals(ServiceProvider.DHT) && !serviceName.equals(ServiceProvider.LINKY))
			throw new IOException("Unknow service '" + serviceName + "'");
		
		return serviceName;
	}
	
	private JsonPacketUtil() { }
}
