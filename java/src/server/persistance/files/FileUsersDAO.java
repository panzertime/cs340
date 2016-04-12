package server.persistance.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.data.User;
import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IUsersDAO;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class FileUsersDAO implements IUsersDAO {

	@Override
	public void saveUser(IConnection connection, User user) throws DatabaseException {
		try {
			File file = new File("java/data/user/" + user.getID() + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(user.toJSON().toJSONString());
			bw.close();
		} catch (IOException e) {
			throw new DatabaseException();
		} 
		

	}

	@Override
	public List<User> getUsers(IConnection connection) throws DatabaseException {
		List<User> result = new ArrayList<User>();
		File dir = new File("java/data/user/");

    	File[] files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );
    	JSONParser jp = new JSONParser();
    	
    	if (files!= null) for (File f: files) {
    		StringBuilder jsontext = new StringBuilder();
    		BufferedReader in;
    		try {
    		    in = new BufferedReader(new FileReader(f));
    		    String str;
    		    while ((str = in.readLine()) != null)
    		        jsontext.append(str);
    			in.close();
    		} catch (IOException e) {
    			throw new DatabaseException(e);
    		} 
    		try {
				result.add(new User((JSONObject) jp.parse(jsontext.toString())));
			} catch (BadJSONException | ParseException e) {
				throw new DatabaseException(e);
			}
    	}
		
		
		return result;

	}

}
