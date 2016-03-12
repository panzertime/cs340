package server.command.mock;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.command.ICommand;


public abstract class Mock implements ICommand {

	private static final String GENERIC_FILE_PATH = 
			"java/src/server/command/mock/";
	/**
	 * Creates a json object from the given file at the given path
	 * @pre The file and path are valid and contain a valid JSON Object
	 * @post a JSONObject is created from the given file
	 * @param path directory and / that follows java/src/server/command/mock/
	 * @param file *.txt
	 * @return the corresponding text json file as a JSONObject
	 */
	public JSONObject jsonFromFile(String path, String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File(path + file + ".json");
		FileInputStream fis;
		JSONObject jsonModel = null;
		try {
			fis = new FileInputStream(jsonFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Scanner scanner = new Scanner(bis);
			String x = "";
			while(scanner.hasNextLine()) {
				x += scanner.nextLine();
				x += "\n";
			}
			scanner.close();
			
			jsonModel = (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		
		return jsonModel;
	}
	
	/**
	 * Creates a JSONObject from the given file in a mock objects directory.
	 * @pre Filename is valid and contains the valid text for a json Object
	 * to be created
	 * @post A JSONobject matching the text file is created
	 * @param file name of the file at 
	 * "java/src/server/command/mock/(moveType)/" Exclude "*.txt"
	 * @return JSONObject representing that file
	 */
	public abstract JSONObject jsonFromFile(String file);
}
