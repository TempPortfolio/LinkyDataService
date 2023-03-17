package fr.linkyproject.dataservice.data;

import java.util.List;

/**
 * Pour un accès thread-safe au service.
 * 
 * @param <T>
 */
public class SynchronizedService<T> implements Service<T> {
	private final Service<T> service;
	private Object lock;
	
	public SynchronizedService(Service<T> service) {
		this.lock = this;
		this.service = service;
	}
	
	@Override
	public void register(T object) throws ServiceException {
		synchronized(this.lock) {
			this.service.register(object);
		}
	}
	
	@Override
	public synchronized List<T> get(Object... args) throws ServiceException {
		synchronized(this.lock) {
			return this.service.get(args);
		}
	}

}
