package fr.linkyproject.dataservice.data.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import fr.linkyproject.dataservice.data.Service;
import fr.linkyproject.dataservice.data.ServiceException;
import fr.linkyproject.dataservice.entries.DHTEntry;

public class TestDhtDB implements Service<DHTEntry> {
	private static Logger LOGGER = Logger.getLogger("TestDhtDB");
	private List<DHTEntry> entries = new ArrayList<>();
	
	public TestDhtDB(int nbEntries, int sampling) {
		long time = System.currentTimeMillis();
		Random rand = new Random();
		
		for(int i = 0; i < nbEntries; i++) {
			this.entries.add(
					new DHTEntry(
							time + i * 60_000 * sampling, 
							rand.nextDouble(53d), 
							rand.nextDouble(88d)
						)
				);
		}
	}
	
	@Override
	public void register(DHTEntry entry) throws ServiceException {
		LOGGER.info("Register new entry temp=" + entry.getTemperature() + " hum=" + entry.getHumidity() + " time=" + entry.getTime());
		this.entries.add(entry);
	}
	
	@Override
	public List<DHTEntry> get(Object... args) throws ServiceException {
		List<DHTEntry> clone = new ArrayList<>();
		clone.addAll(this.entries);
		return clone;
	}
	
}
