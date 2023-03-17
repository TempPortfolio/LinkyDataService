package fr.linkyproject.dataservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Random;

import fr.linkyproject.dataservice.data.ServiceException;
import fr.linkyproject.dataservice.data.impl.DhtDB;
import fr.linkyproject.dataservice.data.impl.LinkyDB;
import fr.linkyproject.dataservice.entries.DHTEntry;
import fr.linkyproject.dataservice.entries.LinkyEntry;

public class SQLAccessTest {
	private static final boolean TEST_ADD = false;
	private static final boolean TEST_GET = true;
	
	public static void main(String[] args) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/", 
				"root", 
				""
			);
		
		String dbName = "test_db";
		int testSize = 100_000;
		
		LinkyDB linkydb = new LinkyDB(connection, dbName);
		DhtDB dhtdb = new DhtDB(connection, dbName);
		
		if(TEST_ADD)
			addData(linkydb, dhtdb, testSize, testSize);
		else
			System.out.println("Skip add");
		
		if(TEST_GET)
			getData(linkydb, dhtdb);
		else
			System.out.println("Skip get");
	}
	
	private static void addData(LinkyDB linkydb, DhtDB dhtdb, int linkySize, int dhtSize) throws ServiceException {
		Random rand = new Random();
		long time = 0;
		
		//LINKY
		System.out.println("Start linky add data...");
		time = System.currentTimeMillis();
		
		for(int i = 0; i < linkySize; i++)
			linkydb.register(new LinkyEntry(i*2, "11111111111", i + 1, i + 2, i + 3, i + 4, i + 5, i + 6, i + 7));
		
		time = System.currentTimeMillis() - time;
		System.out.println("End. Duration of " + linkySize + " additions: " + time / 1000 + "s");
		
		//DHT
		System.out.println("Start dht add data...");
		time = System.currentTimeMillis();
		
		for(int i = 0; i < dhtSize; i++)
			dhtdb.register(new DHTEntry(i*2, rand.nextInt(60), rand.nextInt(100)));
		
		time = System.currentTimeMillis() - time;
		System.out.println("End. Duration of " + dhtSize + " additions: " + time / 1000 + "s");
	}
	
	public static void getData(LinkyDB linkydb, DhtDB dhtdb) throws ServiceException {
		long time = 0;
		
		//LINKY
		/*System.out.println("Get linky data...");
		time = System.currentTimeMillis();
		
		List<?> linkydata = linkydb.get();
		
		time = System.currentTimeMillis() - time;
		System.out.println("End. Duration of " + linkydata.size() + " gets: " + time / 1000 + "s");
		*/
		//DHT
		System.out.println("Get dht data...");
		time = System.currentTimeMillis();
		
		List<?> dhtdata = dhtdb.get();
		
		time = System.currentTimeMillis() - time;
		System.out.println("End. Duration of " + dhtdata.size() + " gets: " + time / 1000 + "s");
		
	}
}
