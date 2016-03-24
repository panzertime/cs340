package server.main;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.*;
import server.handler.*;

public class Server {

	private int port;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("catan server"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	
	private HttpServer hserver;
	
	/**
	 * create a new server object
	 * @param listenPort the port number to listen on
	 */
	private Server(int listenPort) {
		port = listenPort;
	}
	
	private void run() {
		
		
		logger.info("Initializing HTTP Server");
		
		try {
			hserver = HttpServer.create(new InetSocketAddress(port),
					MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		hserver.setExecutor(null); // use the default executor
		
		HttpHandler hhandler = (HttpHandler) handler;
		hserver.createContext("/", handler);
		server.createContext("/docs/api/data", new fileHandler.JSONAppender(""));
		server.createContext("/docs/api/view", new fileHandler.BasicFile(""))
		
		logger.info("Starting HTTP Server");

		hserver.start();
	}

	private static AbstractHttpHandler handler = new RequestHandler();
	
	public static void main(String[] args) {
	//	new server(Integer.parseInt(args[0])).run();
		new Server(8081).run();
	}

	public static void setFake(boolean isFake) {
		handler.setFake(isFake);
	}

}

