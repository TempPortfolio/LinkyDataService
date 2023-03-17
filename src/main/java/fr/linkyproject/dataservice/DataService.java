package fr.linkyproject.dataservice;

import static fr.linkyproject.dataservice.utils.Errors.error;
import static fr.linkyproject.dataservice.utils.Errors.trycatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import fr.linkyproject.dataservice.commands.CommandManager;
import fr.linkyproject.dataservice.commands.Result;
import fr.linkyproject.dataservice.config.Configuration;
import fr.linkyproject.dataservice.config.JsonConfiguration;
import fr.linkyproject.dataservice.data.impl.DhtDB;
import fr.linkyproject.dataservice.data.impl.LinkyDB;
import fr.linkyproject.dataservice.data.impl.TestDhtDB;
import fr.linkyproject.dataservice.wsservice.WSServer;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Session;

public class DataService {
	private static final Logger LOGGER = Logger.getLogger("Main");
	private final CommandManager commands = new CommandManager();
	private final Configuration config;
	
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "\u001B[36m[%1$tF %1$tT] [%4$-7s] %5$s %n");
		
		Configuration config = null;
		DataService dservice;
		
		LOGGER.info("Load configuration");
		try {
			File fconfig = getFileConfig();
			config = JsonConfiguration.load(new FileInputStream(fconfig));
		}
		catch (IOException e) {
			error(e, "Can't load configuration !");
		}
		
		dservice = new DataService(config);
		
		if(config.getString("data_service.type", "test").equals("test"))
			dservice.initTestServices();
		else if(config.getString("data_service.type").equals("sql"))
			trycatch(() -> dservice.initServices(), "Can't initialize services !");
		else
			error("Unknow data service type '" + config.getString("data_service.type") + "'");
		
		trycatch(() -> dservice.initWSServer(), "Can't start web socket server !");
		
		dservice.initCommands();
		dservice.startConsoleListener();
	}
	
	private static File getFileConfig() throws IOException {
		File file = new File("./config.json");
		
		if(!file.exists())
			Files.copy(DataService.class.getResourceAsStream("/config.json"), file.toPath());
		
		return file;
	}
	
	public DataService(Configuration config) {
		this.config = config;
	}
	
	private void initServices() throws SQLException, ClassNotFoundException, IOException {
		LOGGER.info("Init SQL services");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection connection = DriverManager.getConnection(
				this.config.getString("mysql.url", "jdbc:mysql://localhost:3306/"), 
				this.config.getString("mysql.user", "root"), 
				this.config.getString("mysql.password", ""));
		
		String databaseName = this.config.getString("database_name", "linky_project");
		
		LOGGER.info("  -> Used database: " + databaseName);
		
		ServiceProvider.get().initServices(
				new DhtDB(connection, databaseName),
				new LinkyDB(connection, databaseName)
			);
	}
	
	private void initTestServices() {
		ServiceProvider.get().initServices(
				new TestDhtDB(60000, 5),
				null
			);
	}
	
	private void initWSServer() throws DeploymentException {
		LOGGER.info("Start server");
		WSServer.get().start(
				this.config.getString("ws_server.host", "localhost"), 
				this.config.getInt("ws_server.port", 8098)
			);
	}
	
	private void initCommands() {
		LOGGER.info("Init commands");
		commands.register("stop", args -> {
			LOGGER.info("Stop server");
			return Result.EXIT;
		});
		commands.register("list", args -> {
			Set<Session> sessions = WSServer.get().getManager().getSessions();
			String message = "Sessions (" + sessions.size() + "): ";

			for(Session session : sessions)
				message += ", " + session.getId();
			
			message = message.replaceFirst(",", "");
			LOGGER.info(message);
			
			return Result.OK;
		});
	}
	
	private void startConsoleListener() {
		do {
			@SuppressWarnings("resource") //STD stream
			Scanner sc = new Scanner(System.in);
			String[] line = sc.nextLine().split(" ");
			
			String cmdName = line[0];
			String cmdArgs[] = new String[0];
			
			if(line.length > 1) {
				cmdArgs = new String[line.length - 1];
				for(int i = 0; i < cmdArgs.length; i++)
					cmdArgs[i] = line[i + 1];
			}
			
			Result result = this.commands.call(cmdName, cmdArgs);
			
			if(result  == Result.UNKNOW)
				LOGGER.info("Unknow command !");
			else if(result  == Result.EXIT)
				break;
		}
		while(true);
		
		WSServer.get().stop();
	}
}
