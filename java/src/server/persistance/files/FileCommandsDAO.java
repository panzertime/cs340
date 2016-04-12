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

import server.command.moves.MovesCommand;
import server.persistance.DatabaseException;
import server.persistance.ICommandsDAO;
import server.persistance.IConnection;
import shared.model.Model;
import shared.model.exceptions.BadJSONException;

public class FileCommandsDAO implements ICommandsDAO {

	@Override
	public void saveCommmand(IConnection connection, Integer gameID, MovesCommand movesCommand)
			throws DatabaseException {
		try {
			File file = new File("java/data/command/" + gameID + "version" + movesCommand.getGameVersion() + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(movesCommand.getArguments().toJSONString());
			bw.close();
		} catch (IOException e) {
			throw new DatabaseException();
		} 


	}

	@Override
	public List<JSONObject> getCommands(IConnection connection, Integer gameID) throws DatabaseException {
		List<JSONObject> result = new ArrayList<JSONObject>();
		File dir = new File("java/data/command/");

    	File[] files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt") && filename.startsWith("" + gameID); }
    	} );
    	JSONParser jp = new JSONParser();
    	
    	for (File f: files) {
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
				result.add((JSONObject) jp.parse(jsontext.toString()));
			} catch (ParseException e) {
				throw new DatabaseException(e);
			}
    	}
		
		
		return result;

	}

	@Override
	public void deleteCommands(IConnection connection, Integer gameID) throws DatabaseException {
		List<JSONObject> result = new ArrayList<JSONObject>();
		File dir = new File("java/data/command/");

    	File[] files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt") && filename.startsWith("" + gameID); }
    	} );
    	JSONParser jp = new JSONParser();
    	
    	if (files!= null) for (File f: files) {
    		f.delete();
    	}

	}

}
