package tests;

import org.junit.Test;

import client.servercommunicator.ServerException;
import client.servercommunicator.ServerFacade;
import client.servercommunicator.ServerProxy;

public class test {
	private static ServerFacade serverFacade;

	public static void setup(){
		serverFacade = ServerFacade.get_instance();
		ServerProxy sp = new ServerProxy();
		sp.setURL("http://localhost:8081");
		serverFacade.setProxy(sp);
	}
	
	//Good tests
	public static void main(String[] args) {
		 setup();
		String username = "Sam";
		String password = "sam";
		try {
			serverFacade.login(username, password);
		} catch (ServerException e) {
			System.out.println("Failed login Proxy test: " + e.toString());
			e.printStackTrace();
		}
		
	}
}
