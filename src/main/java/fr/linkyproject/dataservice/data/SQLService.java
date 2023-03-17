package fr.linkyproject.dataservice.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class SQLService<T> implements Service<T> {
	protected final Connection connection;
	protected final String databaseName;
	
	public SQLService(Connection connection, String databaseName) {
		this.connection = connection;
		this.databaseName = databaseName;
	}
	
	public boolean execute(String path) throws ServiceException {
		return this.execute(path, new HashMap<>());
	}
	
	public boolean execute(String path, Map<String, Object> replacement) throws ServiceException {
		try {
			String[] requests = this.getRequest(path, replacement);
			
			for(int i = 0; i < requests.length - 1; i++)
				this.connection.prepareStatement(requests[i]).execute();
			
			return this.connection.prepareStatement(requests[requests.length - 1]).execute();
		}
		catch(SQLException | IOException e) {
			throw new ServiceException(e);
		}
	}
	
	public ResultSet executeQuery(String path) throws ServiceException {
		return this.executeQuery(path, new HashMap<>());
	}
	
	public ResultSet executeQuery(String path, Map<String, Object> replacement) throws ServiceException {
		try {
			String[] requests = this.getRequest(path, replacement);
			
			for(int i = 0; i < requests.length - 1; i++)
				this.connection.prepareStatement(requests[i]).execute();
			
			return this.connection.prepareStatement(requests[requests.length - 1]).executeQuery();
		}
		catch(SQLException | IOException e) {
			throw new ServiceException(e);
		}
	}
	
	private String[] getRequest(String path, Map<String, Object> replacement) throws IOException {
		if(!this.databaseName.isEmpty())
			replacement.put("database", this.databaseName);
		
		InputStream in = this.getClass().getResourceAsStream(path);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		
		StringBuffer builder = new StringBuffer();
		String str;
		
		while((str = reader.readLine()) != null)
			builder.append(str);
		
		String request = builder.toString();
		
		for(Map.Entry<String, Object> entry : replacement.entrySet())
			request = request.replaceAll(Pattern.quote("${" + entry.getKey() + "}"), entry.getValue().toString());
			
		return request.split(";");
	}
}
