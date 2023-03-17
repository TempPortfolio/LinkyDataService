package fr.linkyproject.dataservice.config;

import java.io.IOException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class JsonConfiguration implements Configuration {
	private final JsonObject data;
	
	public JsonConfiguration(JsonObject data) {
		this.data = data;
	}
	
	@Override
	public String getString(String key, String defaultValue) {
		return this.data.containsKey(key) ? this.data.getString(key) : defaultValue;
	}
	
	@Override
	public Integer getInt(String key, Integer defaultValue) {
		return this.data.containsKey(key) ? this.data.getInt(key) : defaultValue;
	}
	
	@Override
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return this.data.containsKey(key) ? this.data.getBoolean(key) : defaultValue;
	}
	
	public static JsonConfiguration load(InputStream in) throws IOException {
		return new JsonConfiguration(Json.createReader(in).readObject());
	}
}
