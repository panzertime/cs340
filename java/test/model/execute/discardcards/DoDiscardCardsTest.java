package model.execute.discardcards;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import shared.model.Model;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ViolatedPreconditionException;
import shared.model.hand.ResourceType;

public class DoDiscardCardsTest {
	
	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/discardcards/" + file);
		FileInputStream fis;
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
			return (JSONObject) parser.parse(x);
			
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testDoDiscardCards1() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 4);
		resourceList.put(ResourceType.WHEAT, 0);
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeDiscard.json"));
			model.doDiscardCards(resourceList, 0);
		} catch (BadJSONException e) {
			fail("Failed doDiscardCards while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("Failed doDiscardCards with availible cards");
		}
		if(model.equalsJSON(getJSONFrom("afterDiscard.json"))) {
			System.out.println("passed testDoDiscardCards with availible cards");
		} else {
			fail("Failed testDoDiscardCards with availible cards");
		}
	}
	
	@Test
	public void testDoDiscardCards2() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 4);
		resourceList.put(ResourceType.WHEAT, 0);
		Model model = null;
		try {
			model = new Model(getJSONFrom("notDiscarding.json"));
			model.doDiscardCards(resourceList, 0);
		} catch (BadJSONException e) {
			fail("Failed doDiscardCards while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoDiscardCards while not discarding");
		}
		if(!model.equalsJSON(getJSONFrom("notDiscarding.json"))) {
			fail("Failed testDoDiscardCards while not discarding");
		}
	}
	
	@Test
	public void testDoDiscardCards3() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 1);
		resourceList.put(ResourceType.ORE, 1);
		resourceList.put(ResourceType.SHEEP, 1);
		resourceList.put(ResourceType.BRICK, 0);
		resourceList.put(ResourceType.WHEAT, 1);
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeDiscard.json"));
			model.doDiscardCards(resourceList, 0);
		} catch (BadJSONException e) {
			fail("Failed doDiscardCards while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoDiscardCards with unavailible cards");
		}
		if(!model.equalsJSON(getJSONFrom("beforeDiscard.json"))) {
			fail("Failed testDoDiscardCards with unavailible cards");
		}
	}
	
	@Test
	public void testDoDiscardCards4() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 3);
		resourceList.put(ResourceType.WHEAT, 0);
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeDiscard.json"));
			model.doDiscardCards(resourceList, 0);
		} catch (BadJSONException e) {
			fail("Failed doDiscardCards while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoDiscardCards with incorrect number of cards");
		}
		if(!model.equalsJSON(getJSONFrom("beforeDiscard.json"))) {
			fail("Failed testDoDiscardCards with incorrect number of cards");
		}
	}
	
	@Test
	public void testDoDiscardCards5() {
		Map<ResourceType, Integer> resourceList = new HashMap<ResourceType, Integer>();
		resourceList.put(ResourceType.WOOD, 0);
		resourceList.put(ResourceType.ORE, 0);
		resourceList.put(ResourceType.SHEEP, 0);
		resourceList.put(ResourceType.BRICK, 4);
		resourceList.put(ResourceType.WHEAT, 0);
		Model model = null;
		try {
			model = new Model(getJSONFrom("beforeDiscard.json"));
			model.doDiscardCards(resourceList, 99);
		} catch (BadJSONException e) {
			fail("Failed doDiscardCards while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoDiscardCards with availible cards and bad index");
		}
		if(!model.equalsJSON(getJSONFrom("beforeDiscard.json"))) {
			fail("Failed testDoDiscardCards with availible cards and bad index");
		}
	}
}
