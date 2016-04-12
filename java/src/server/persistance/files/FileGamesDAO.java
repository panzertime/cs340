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

import server.persistance.DatabaseException;
import server.persistance.IConnection;
import server.persistance.IGamesDAO;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class FileGamesDAO implements IGamesDAO {

	@Override
	public void saveGame(IConnection connection, Model model) throws DatabaseException {
		try {
			File file = new File("java/data/game/" + model.getID() + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			else { //else clear it
				PrintWriter writer = new PrintWriter(file);
				writer.print("");
				writer.close();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(model.toJSON().toJSONString());
			bw.close();
		} catch (IOException e) {
			throw new DatabaseException();
		} 


	}

	
	
	@Override
	public List<Model> getGames(IConnection connection) throws DatabaseException {
		List<Model> result = new ArrayList<Model>();
		File dir = new File("java/data/game/");

    	File[] files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );
    	JSONParser jp = new JSONParser();
    	
    	if (files!= null) for (File f: files) {
    		String jsontext = null;
    		BufferedReader in;
    		try {
    		    in = new BufferedReader(new FileReader(f));
    		    String str;
    		    while ((str = in.readLine()) != null)
    		        jsontext.concat(str);
    			in.close();
    		} catch (IOException e) {
    			throw new DatabaseException(e);
    		} 
    		try {
				result.add(new Model((JSONObject) jp.parse(jsontext)));
			} catch (BadJSONException | ParseException e) {
				throw new DatabaseException(e);
			}
    	}		
		return result;
	}

}
