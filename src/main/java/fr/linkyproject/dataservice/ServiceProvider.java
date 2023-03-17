package fr.linkyproject.dataservice;

import fr.linkyproject.dataservice.data.Service;
import fr.linkyproject.dataservice.data.SynchronizedService;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.entries.LinkyEntry;

public final class ServiceProvider {
	public static final String DHT = "dhtService", LINKY = "linkyService";
	
	private Service<DHTEntry> dhtService;
	private Service<LinkyEntry> linkyService;
	
	public Service<DHTEntry> getDhtService() {
		return this.dhtService;
	}
	
	public Service<LinkyEntry> getLinkyService() {
		return this.linkyService;
	}
	
	public void initServices(Service<DHTEntry> dhtService, Service<LinkyEntry> linkyService) {
		this.dhtService = new SynchronizedService<>(dhtService);
		this.linkyService = new SynchronizedService<>(linkyService);
	}
	
	private static ServiceProvider INSTANCE = new ServiceProvider();
	public static ServiceProvider get() {
		return ServiceProvider.INSTANCE;
	}
}
