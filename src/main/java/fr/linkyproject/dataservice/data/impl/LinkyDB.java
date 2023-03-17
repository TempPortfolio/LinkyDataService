package fr.linkyproject.dataservice.data.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.linkyproject.dataservice.data.SQLService;
import fr.linkyproject.dataservice.data.ServiceException;
import fr.linkyproject.dataservice.entries.LinkyEntry;
import fr.linkyproject.dataservice.utils.MapBuilder;

public class LinkyDB extends SQLService<LinkyEntry> {
	
	public LinkyDB(Connection connection, String databaseName) throws SQLException, IOException {
		super(connection, databaseName);
		
		this.execute("/sql/linky_setup.sql");
	}
	
	@Override
	public void register(LinkyEntry entry) throws ServiceException {
		this.execute("/sql/linky_insert.sql", new MapBuilder<String, Object>()
				.put("time", entry.getTime())
				.put("id", entry.getId())
				.put("subscribe_instensity", entry.getSubscribeIntensity())
				.put("base_hour_index", entry.getBaseHourIndex())
				.put("full_hour_index", entry.getFullHourIndex())
				.put("offpeak_hour_index", entry.getOffpeakHourIndex())
				.put("max_instensity", entry.getMaxIntensity())
				.put("instant_intensity", entry.getInstantIntensity())
				.put("current_tariff_option", entry.getCurrentTariffOption())
				.build()
			);
	}
	
	@Override
	public List<LinkyEntry> get(Object... args) throws ServiceException {
		List<LinkyEntry> entries = new ArrayList<>();
		
		ResultSet result = this.executeQuery("/sql/linky_get.sql");
		
		try {
			while(result.next()) {
				entries.add(new LinkyEntry(
						result.getLong(1), 
						result.getString(2), 
						result.getInt(3), 
						result.getInt(4), 
						result.getInt(5), 
						result.getInt(6), 
						result.getInt(7), 
						result.getInt(8), 
						result.getInt(9)
					)
				);
			}
		}
		catch (SQLException e) {
			throw new ServiceException(e);
		}
		
		return entries;
	}
	
}
