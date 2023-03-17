package fr.linkyproject.dataservice.data.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.linkyproject.dataservice.data.SQLService;
import fr.linkyproject.dataservice.data.ServiceException;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.utils.MapBuilder;

public class DhtDB extends SQLService<DHTEntry> {
	
	public DhtDB(Connection connection, String databaseName) throws SQLException, IOException {
		super(connection, databaseName);
		
		this.execute("/sql/dht_setup.sql");
	}
	
	@Override
	public void register(DHTEntry entry) throws ServiceException {
		this.execute("/sql/dht_insert.sql", new MapBuilder<String, Object>()
				.put("time", entry.getTime())
				.put("temperature", entry.getTemperature())
				.put("humidity", entry.getHumidity())
				.build()
			);
	}
	
	@Override
	public List<DHTEntry> get(Object... args) throws ServiceException {
		List<DHTEntry> entries = new ArrayList<>();
		
		ResultSet result = this.executeQuery("/sql/dht_get.sql");
		
		try {
			while(result.next()) {
				long time = result.getLong(1);
				double temperature = result.getDouble(2);
				double humidity = result.getDouble(3);
				
				entries.add(new DHTEntry(time, temperature, humidity));
			}
		}
		catch (SQLException e) {
			throw new ServiceException(e);
		}
		
		return entries;
	}
}
