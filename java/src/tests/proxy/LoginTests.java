package tests.proxy;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;

import client.servercommunicator.*;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.VertexLocation;
import shared.models.definitions.CatanColor;
import shared.models.hand.ResourceType;

import org.json.simple.JSONObject;

public class LoginTests {
	
	private ServerFacade serverFacade;
	private int gameID;
	private void setID(int id){
		gameID = id;
	}
	
	@Before
	public void setup(){
		serverFacade = ServerFacade.get_instance();
		ServerProxy sp = new ServerProxy();
		sp.setURL("http://localhost:8081");
		serverFacade.setProxy(sp);
	}

	private int getSeed(){
		return (int) (Math.random()*10000000); 
	}


	//Good tests
	@Test
	public void testlogin200() {
		String username = "Sam";
		String password = "sam";
		try {
			if(serverFacade.login(username, password) > -1){
				System.out.println("Passed login test.");
			}
			
		} catch (ServerException e) {
			System.out.println("Failed login Proxy test: " + e.toString());
			e.printStackTrace();
			fail("Login failure.");
		}
		
	}

	@Test
	public void testregister200() {
		String username = "Newguy" + getSeed();
		System.out.println("Generated random test username: " + username);
		String password = "Newguypass";
		try {
			if(serverFacade.register(username, password) > -1){
				System.out.println("Passed register test.");
			}
		} catch (ServerException e) {
			System.out.println("Failed register Proxy test");
			e.printStackTrace();
			fail("Registration failure.  Note: this test may only work once per server startup.");

		}
		
	}


}
