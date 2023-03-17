package fr.linkyproject.dataservice.wsservice;

import jakarta.json.JsonObject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class JsonEncoder implements Encoder.Text<JsonObject>{
	
	@Override
	public String encode(JsonObject object) throws EncodeException {
		return object.toString();
	}
	
}
