package model.execute.trade.accept;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import shared.model.Model;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ViolatedPreconditionException;

public class DoAcceptTradeTest {

	public JSONObject getJSONFrom(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/execute/trade/accept/" + file);
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
	public void testDoAcceptTrade1() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer.json"));
			model.doAcceptTrade(true, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with acceptance and valid index");
		}
		if (model.equalsJSON(getJSONFrom("offerAccepted.json"))) {
			System.out.println("passed testDoAcceptTrade with acceptance and valid index");
		} else {
			fail("failed testDoAcceptTrade with acceptance and valid index");
		}
	}
	
	@Test
	public void testDoAcceptTrade2() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer.json"));
			model.doAcceptTrade(false, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with no acceptance and valid index");
		}
		if (model.equalsJSON(getJSONFrom("offerDeclined.json"))) {
			System.out.println("passed testDoAcceptTrade with no acceptance and valid index");
		} else {
			fail("failed testDoAcceptTrade with no acceptance and valid index");
		}
	}
	
	@Test
	public void testDoAcceptTrade3() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer-notPlaying.json"));
			model.doAcceptTrade(true, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with acceptance and valid index while model isn't playing");
		}
		if (model.equalsJSON(getJSONFrom("offerAccepted-notPlaying.json"))) {
			System.out.println("passed testDoAcceptTrade with acceptance and valid index while model isn't playing");
		} else {
			fail("failed testDoAcceptTrade with acceptance and valid index while model isn't playing");
		}
	}
	
	@Test
	public void testDoAcceptTrade4() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer-notPlaying.json"));
			model.doAcceptTrade(false, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with no acceptance and valid index while model isn't playing");
		}
		if (model.equalsJSON(getJSONFrom("offerDeclined-notPlaying.json"))) {
			System.out.println("passed testDoAcceptTrade with no acceptance and valid index while model isn't playing");
		} else {
			fail("failed testDoAcceptTrade with no acceptance and valid index while model isn't playing");
		}
	}
	
	@Test
	public void testDoAcceptTrade5() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer-notYourTurn.json"));
			model.doAcceptTrade(true, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with acceptance and valid index while not your turn");
		}
		if (model.equalsJSON(getJSONFrom("offerAccepted-notYourTurn.json"))) {
			System.out.println("passed testDoAcceptTrade with acceptance and valid index while not your turn");
		} else {
			fail("failed testDoAcceptTrade with acceptance and valid index while not your turn");
		}
	}
	
	@Test
	public void testDoAcceptTrade6() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("offer-notYourTurn.json"));
			model.doAcceptTrade(false, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			fail("failed testDoAcceptTrade with no acceptance and valid index while not your turn");
		}
		if (model.equalsJSON(getJSONFrom("offerDeclined-notYourTurn.json"))) {
			System.out.println("passed testDoAcceptTrade with no acceptance and valid index while not your turn");
		} else {
			fail("failed testDoAcceptTrade with no acceptance and valid index while not your turn");
		}
	}
	
	@Test
	public void testDoAcceptTrade7() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("noOffer.json"));
			model.doAcceptTrade(true, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoAcceptTrade with acceptance and valid index");
		}
		if (!model.equalsJSON(getJSONFrom("noOffer.json"))) {
			fail("failed testDoAcceptTrade with acceptance and valid index");
		}
	}
	
	@Test
	public void testDoAcceptTrade8() {
		Model model = null;
		try {
			model = new Model(getJSONFrom("noOffer.json"));
			model.doAcceptTrade(false, 0);
		} catch (BadJSONException e) {
			fail("Failed doAcceptTrade while initilizing model");
		} catch (ViolatedPreconditionException e) {
			System.out.println("passed testDoAcceptTrade with no acceptance and valid index");
		}
		if (!model.equalsJSON(getJSONFrom("noOffer.json"))) {
			fail("failed testDoAcceptTrade with no acceptance and valid index");
		}
	}
}
