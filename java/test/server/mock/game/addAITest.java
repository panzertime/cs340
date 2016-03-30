package server.mock.game;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Test;

public class addAITest {
	@Before
	private void startServer() {
		
	}
	
	
	@Test
	private void submitRequest() {

		try {
			URLConnection connectionSeed = new URL("localhost:8081/game/addAI").openConnection();
			HttpURLConnection connection = (HttpURLConnection) connectionSeed;
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Content-Length", "0");

			OutputStream requestBody = 
				new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
			requestBody.write(0);
			requestBody.flush();
			requestBody.close();
			
			if (connection.getResponseCode() != 200) 
				throw new Exception();

			
			connection.getContent();
			connection.getContentType();

			
			DataInputStream responseBody = 
				new DataInputStream(new BufferedInputStream(connection.getInputStream()));

			StringBuilder JSONBuilder = new StringBuilder();
			InputStreamReader JSONReader = new InputStreamReader(responseBody);
			int letter = JSONReader.read();
			letter = JSONReader.read();
			while(letter >= 0){
				JSONBuilder.append((char) letter);
				letter = JSONReader.read();
			}
			JSONReader.close();
			System.out.println(matchBrackets(JSONBuilder.toString()));

		}

		catch(Exception e){
			fail("addAI Mock Test");
		}
	}


	private String matchBrackets(String matchable){
		char bracket = matchable.charAt(matchable.length() - 1);
		String closer;
		if(bracket == ']'){
			// System.out.println("Matching a [");		
			closer = "[";
		}
		else {
			// System.out.println("Matching a {");
			closer = "{";
		}
		return closer + matchable;
	}
}
