package fr.linkyproject.dataservice.config;

public interface Configuration {
	
	public default String getString(String key) {
		return this.getString(key, null);
	}
	
	public String getString(String key, String defaultValue);
	
	public default Integer getInt(String key) {
		return this.getInt(key, null);
	}
	
	public Integer getInt(String key, Integer defaultValue);
	
	public default Boolean getBoolean(String key) {
		return this.getBoolean(key, null);
	}
	
	public Boolean getBoolean(String key, Boolean defaultValue);
}
