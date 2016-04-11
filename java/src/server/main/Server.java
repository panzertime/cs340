package server.main;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.*;

import com.sun.net.httpserver.*;

import server.*;
import server.handler.*;
import server.exception.*;
import server.persistance.IDAOFactory;
import server.data.ServerKernel;

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
		
		hserver.createContext("/", handler);
		hserver.createContext("/docs/api/data", new fileHandler.JSONAppender(""));
		hserver.createContext("/docs/api/view", new fileHandler.BasicFile(""));
		hserver.createContext("/favicon.ico", new NullHandler());
		
		logger.info("Starting HTTP Server");

		hserver.start();
	}

	private static AbstractHttpHandler handler = new RequestHandler();
	
	public static void main(String[] args) {
	//	new server(Integer.parseInt(args[0])).run();
		try {	
			File loc = new File(args[2]);
			File[] flist = loc.listFiles(new FileFilter() {
    					public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
		        	});
    			URL[] urls = new URL[flist.length];
        		for (int i = 0; i < flist.length; i++)
            			urls[i] = flist[i].toURI().toURL();
        		URLClassLoader ucl = new URLClassLoader(urls);

			int N = Integer.parseInt(args[1]);
			IDAOFactory plugin = null;
			ServiceLoader<IDAOFactory> pluginLoader = ServiceLoader.load(IDAOFactory.class, ucl);
			for (IDAOFactory df : pluginLoader) {
				String name = df.getClass().getSimpleName();
				String sname = name.toLowerCase();
				logger.log(Level.INFO, "Found plugin: " + name);
				if (sname.contains(args[0])) {
					logger.log(Level.INFO, "Using persistence plugin: " + name);
					plugin = df;
					break;
				}
			}
			if (plugin != null) {
				ServerKernel.sole().initPersistence(N, plugin);
			}
			else {
				throw new ServerAccessException("Could not match plugin name " + args[0]);
			}
			new Server(8081).run();
		}
		catch (Exception e) {
			logger.log(Level.INFO, "Fault starting server: " + e.getMessage());
		}
	}

	public static void setFake(boolean isFake) {
		handler.setFake(isFake);
	}

}

