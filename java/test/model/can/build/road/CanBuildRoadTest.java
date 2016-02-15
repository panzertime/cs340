package model.can.build.road;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import shared.models.ModelFacade;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.HexLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.ModelAccessException;

public class CanBuildRoadTest {
	
	ModelFacade modelFacade;

	public void initModel(String file) {
		JSONParser parser = new JSONParser();
		File jsonFile = new File("java/bin/test/model/can/build/road/" + file);
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
			
			Map jsonModel = (Map) parser.parse(x);
			
			modelFacade = new ModelFacade((JSONObject) jsonModel, 0);
		} catch (FileNotFoundException | ParseException | BadJSONException e) {
			e.printStackTrace();
		}
	}

	//no game in facade
	@Test
	public void testCanBuildRoad1() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.SouthWest);
		ModelFacade mf = new ModelFacade();
		try {
			mf.canBuildRoad(edgeLoc);
			fail("failed testCanBuildRoad test with uninit model");
		} catch (NullPointerException e) {
			System.out.println("passed testCanBuildRoad test with uninit model");
		}
	}
	
	//it is not your turn
	@Test
	public void testCanBuildRoad2() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.SouthWest);
		this.initModel("noTurn.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when not your turn");
			} else {
				fail("failed testCanBuildRoad test when not your turn");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when not your turn - model not created");
		}
	}
	
	//client status is not playing
	@Test
	public void testCanBuildRoad3() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.SouthWest);
		this.initModel("noPlaying.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when not in playing mode");
			} else {
				fail("failed testCanBuildRoad test when not in playing mode");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when not in playing mode - model not created");
		}
	}
	
	//road location is not open
	@Test
	public void testCanBuildRoad4() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0),
				EdgeDirection.South);
		this.initModel("goodBuildRoad.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when road loc not open");
			} else {
				fail("failed testCanBuildRoad test when road loc not open");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when road loc not open - model not created");
		}
	}
	
	//road loaction is not connected to another
	//road owned by the player - normal map init
	@Test
	public void testCanBuildRoad5() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,0),
				EdgeDirection.SouthEast);
		this.initModel("goodBuildRoad.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when road loc not connected to player");
			} else {
				fail("failed testCanBuildRoad test when road loc not connected to player");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when road loc not connected to player - model not created");
		}
	}
	
	//road location is on water - normal init
	@Test
	public void testCanBuildRoad6() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,3),
				EdgeDirection.SouthEast);
		this.initModel("goodBuildRoad.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when road loc on water");
			} else {
				fail("failed testCanBuildRoad test when road loc on water");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when road loc on water - model not created");
		}
	}
	
	//user doesn't have the resources
	@Test
	public void testCanBuildRoad7() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.SouthWest);
		this.initModel("noRes.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when user doesn't have res.");
			} else {
				fail("failed testCanBuildRoad test when user doesn't have res.");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when user doesn't have res. - model not created");
		}
	}
	
	//setup round - placed right next to road
	@Test
	public void testCanBuildRoad8() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(1,0),
				EdgeDirection.South);
		this.initModel("goodBuildRoadSetup.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when user sets up right next to road");
			} else {
				fail("failed testCanBuildRoad test when user sets up right next to road");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when user sets up right next to road - model not created");
		}
	}
	
	//setup round - not placed near settlement
	@Test
	public void testCanBuildRoad8_5() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(1,1),
				EdgeDirection.South);
		this.initModel("goodBuildRoadSetup.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when user doesn't set up next to settlement");
			} else {
				fail("failed testCanBuildRoad test when user doesn't set up next to settlement");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when user doesn't set up next to settlement - model not created");
		}
	}
	
	//setup mode - bad near first settlement
	@Test
	public void testCanBuildRoad11() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(2,0),
				EdgeDirection.South);
		this.initModel("goodBuildRoadSetup.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == false) {
				System.out.println("passed testCanBuildRoad test when places 2nd road near 1st settlement");
			} else {
				fail("failed testCanBuildRoad test when places 2nd road near 1st settlement");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when places 2nd road near 1st settlement - model not created");
		}
	}
	
	//works normal
	@Test
	public void testCanBuildRoad9() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.SouthWest);
		this.initModel("goodBuildRoad.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == true) {
				System.out.println("passed testCanBuildRoad test when valid play");
			} else {
				fail("failed testCanBuildRoad test when valid play");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when valid play - model not created");
		}
	}
	
	//works setup mode
	@Test
	public void testCanBuildRoad10() {
		EdgeLocation edgeLoc = new EdgeLocation(new HexLocation(0,1),
				EdgeDirection.South);
		this.initModel("goodBuildRoadSetup.txt");
		try {
			if(modelFacade.canBuildRoad(edgeLoc) == true) {
				System.out.println("passed testCanBuildRoad test when valid setup");
			} else {
				fail("failed testCanBuildRoad test when valid setup");
			}
		} catch (NullPointerException e) {
			fail("failed testCanBuildRoad test when valid setup - model not created");
		}
	}
}
