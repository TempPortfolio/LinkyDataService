package fr.linkyproject.dataservice.data;

import java.io.IOException;

@SuppressWarnings("serial")
public class ServiceException extends IOException {
	public ServiceException(String msg) {
		super(msg);
	}
	
	public ServiceException(Throwable throwable) {
		super(throwable);
	}
}
