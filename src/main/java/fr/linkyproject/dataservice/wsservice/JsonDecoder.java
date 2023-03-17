package fr.linkyproject.dataservice.wsservice;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

public class JsonDecoder implements Decoder.Text<JsonObject> {
	
	@Override
	public JsonObject decode(String data) throws DecodeException {
		JsonReader reader = Json.createReader(new StringReader(data));
		return reader.readObject();
	}
	
	@Override
	public boolean willDecode(String data) {
		return data != null;
	}
	
}
