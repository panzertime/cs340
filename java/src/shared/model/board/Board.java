package shared.model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import client.map.pseudo.PseudoHex;
import shared.model.Model;
import shared.model.Player;
import shared.model.board.edge.Edge;
import shared.model.board.edge.EdgeDirection;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;
import shared.model.board.hex.tiles.land.ClayHex;
import shared.model.board.hex.tiles.land.FieldHex;
import shared.model.board.hex.tiles.land.ForestHex;
import shared.model.board.hex.tiles.land.LandHex;
import shared.model.board.hex.tiles.land.MountainHex;
import shared.model.board.hex.tiles.land.PastureHex;
import shared.model.board.hex.tiles.land.ProductionHex;
import shared.model.board.hex.tiles.water.PortHex;
import shared.model.board.hex.tiles.water.PortType;
import shared.model.board.hex.tiles.water.WaterHex;
import shared.model.board.piece.Building;
import shared.model.board.piece.City;
import shared.model.board.piece.Road;
import shared.model.board.piece.Robber;
import shared.model.board.piece.Settlement;
import shared.model.board.vertex.Vertex;
import shared.model.board.vertex.VertexDirection;
import shared.model.board.vertex.VertexLocation;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ModelAccessException;

public class Board {

	private Model game;
	private Robber robber;
	private static Map<HexLocation, Hex> hexes;
	private int radius;

	/**
	 * @param jsonMap new version of the map as passed by the model
	 * @param game reference to the model
	 * @throws BadJSONException any time jsonMap is missing elements
	 */
	public Board(JSONObject jsonMap, Model game) throws BadJSONException {
		this.game = game;

		hexes = new HashMap<HexLocation, Hex>();
		JSONArray jsonHexes = (JSONArray) jsonMap.get("hexes");
		if (jsonHexes == null)
			throw new BadJSONException();
		if (jsonHexes.size() != 19)
			throw new BadJSONException();

		JSONArray jsonPorts = (JSONArray) jsonMap.get("ports");
		if (jsonPorts == null)
			throw new BadJSONException();
		if (jsonPorts.size() != 9)
			throw new BadJSONException();

		JSONArray jsonRoads = (JSONArray) jsonMap.get("roads");
		if (jsonRoads == null)
			throw new BadJSONException();

		JSONArray jsonSettlements = (JSONArray) jsonMap.get("settlements");
		if (jsonSettlements == null)
			throw new BadJSONException();

		JSONArray jsonCities = (JSONArray) jsonMap.get("cities");
		if (jsonCities == null)
			throw new BadJSONException();

		JSONObject jsonRobber = (JSONObject) jsonMap.get("robber");
		if (jsonRobber == null)
			throw new BadJSONException();

		// add land hexes to the map
		for (Object hexObject : jsonHexes) {
			JSONObject jsonHex = (JSONObject) hexObject;

			JSONObject jsonHexLoc = (JSONObject) jsonHex.get("location");
			if (jsonHexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonHexLoc);

			Long numberLong = (Long) jsonHex.get("number");
			Integer number = null;
			if (numberLong != null)
				number = numberLong.intValue();
			String resource = (String) jsonHex.get("resource");

			if (number != null && resource != null) {
				switch (resource) {
				case "wood":
					hexes.put(hexLoc, new ForestHex(hexLoc, number));
					break;
				case "brick":
					hexes.put(hexLoc, new ClayHex(hexLoc, number));
					break;
				case "sheep":
					hexes.put(hexLoc, new PastureHex(hexLoc, number));
					break;
				case "wheat":
					hexes.put(hexLoc, new FieldHex(hexLoc, number));
					break;
				case "ore":
					hexes.put(hexLoc, new MountainHex(hexLoc, number));
					break;
				default:
					throw new BadJSONException();
				}
			} else if (number == null && resource == null)
				this.hexes.put(hexLoc, new LandHex(hexLoc));
			else
				throw new BadJSONException();
		}
		
		//add ports and water hexes to the map
		for (Object portObject : jsonPorts) {
			JSONObject jsonPort = (JSONObject) portObject;

			JSONObject jsonHexLoc = (JSONObject) jsonPort.get("location");
			if (jsonHexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonHexLoc);

			String direction = (String) jsonPort.get("direction");
			EdgeDirection edgeDir = EdgeDirection.fromJSON(direction);

			String resource = (String) jsonPort.get("resource");

			if (resource != null) {
				resource = resource.toLowerCase();
				switch (resource) {
				case "wood":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.WOOD, edgeDir));
					break;
				case "brick":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.BRICK, edgeDir));
					break;
				case "sheep":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.SHEEP, edgeDir));
					break;
				case "wheat":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.WHEAT, edgeDir));
					break;
				case "ore":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.ORE, edgeDir));
					break;
				default:
					throw new BadJSONException();
				}
			} else {
				this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.THREE, edgeDir));
			}

			EdgeDirection sideHexDir = edgeDir.toRight();
			while (this.hexes.get(hexLoc.getNeighborLoc(sideHexDir)) != null)
				sideHexDir = sideHexDir.toRight();
			HexLocation sideHexLoc = hexLoc.getNeighborLoc(sideHexDir);

			this.hexes.put(sideHexLoc, new WaterHex(sideHexLoc));
		}

		// Recursively link the hexes with edges and vertices
		Hex centerHex = this.hexes.get(new HexLocation(0, 0));

		connectHexEdge(centerHex);
		connectHexVertex(centerHex);

		// place roads
		for (Object roadObject : jsonRoads) {
			JSONObject jsonRoad = (JSONObject) roadObject;

			Long ownerIndexLong = (Long) jsonRoad.get("owner");
			if (ownerIndexLong == null)
				throw new BadJSONException();
			Integer ownerID = ownerIndexLong.intValue();

			Player player = game.getPlayerFromIndex(ownerID);
			if (player == null)
				throw new BadJSONException();
			
			Road road = player.getFreeRoad();
			if (road == null)
				throw new BadJSONException();

			JSONObject jsonEdgeLoc = (JSONObject) jsonRoad.get("location");
			if (jsonEdgeLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonEdgeLoc);

			String direction = (String) jsonEdgeLoc.get("direction");
			EdgeDirection edgeDir = EdgeDirection.fromJSON(direction);

			EdgeLocation edgeLoc = new EdgeLocation(hexLoc, edgeDir);

			buildRoad(road, edgeLoc);
		}

		// place cities
		for (Object cityObject : jsonCities) {
			JSONObject jsonCity = (JSONObject) cityObject;

			Long ownerIndexLong = (Long) jsonCity.get("owner");
			if (ownerIndexLong == null)
				throw new BadJSONException();
			Integer ownerIndex = ownerIndexLong.intValue();

			Player player = game.getPlayerFromIndex(ownerIndex);
			if (player == null)
				throw new BadJSONException();
			
			City city = player.getFreeCity();
			if (city == null)
				throw new BadJSONException();

			JSONObject jsonVertexLoc = (JSONObject) jsonCity.get("location");
			if (jsonVertexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonVertexLoc);

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = VertexDirection.fromJSON(direction);

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			buildCity(city, vertexLoc);
		}

		// place settlements
		for (Object settlementObject : jsonSettlements) {
			JSONObject jsonSettlement = (JSONObject) settlementObject;

			Long ownerIndexLong = (Long) jsonSettlement.get("owner");
			if (ownerIndexLong == null)
				throw new BadJSONException();
			Integer ownerID = ownerIndexLong.intValue();

			Player player = game.getPlayerFromIndex(ownerID);
			if (player == null)
				throw new BadJSONException();
			
			Settlement settlement = player.getFreeSettlement();
			if (settlement == null)
				throw new BadJSONException();

			JSONObject jsonVertexLoc = (JSONObject) jsonSettlement.get("location");
			if (jsonVertexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonVertexLoc);

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = VertexDirection.fromJSON(direction);

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			buildSettlement(settlement, vertexLoc);
		}
		Long radius = (Long) jsonMap.get("radius");
		if (radius != null)
			this.radius = radius.intValue();
		robber = new Robber(getHexAt(new HexLocation(jsonRobber)));
	}

	public JSONObject toJSON() {

		Map<String, Object> jsonMap = new HashMap<String, Object>();
		JSONArray hexes = new JSONArray();
		JSONArray ports = new JSONArray();
		JSONArray roads = new JSONArray();
		JSONArray settlements = new JSONArray();
		JSONArray cities = new JSONArray();
		for (Hex hex: this.hexes.values())
		{
			if (hex instanceof LandHex)
				hexes.add(hex.toJSON(true));
		}
		for (Hex hex: this.hexes.values())
		{
			if (hex instanceof PortHex)
				ports.add(hex.toJSON(false));
		}
		for (Player p: game.getPlayers())
		{
			for (Road r: p.getRoads())
			{
				if (r.isPlaced())
				{
				Map<String, Object> jsonRoad = new HashMap<String, Object>();
				Map<String, Object> roadLoc = new HashMap<String, Object>();
				roadLoc.put("x", r.getEdge().getEdgeLocation().getHexLoc().getX());
				roadLoc.put("y", r.getEdge().getEdgeLocation().getHexLoc().getY());
				try {
					roadLoc.put("direction", EdgeDirection.toAbbreviation(r.getEdge().getEdgeLocation().getDir()));
				} catch (ModelAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonRoad.put("owner", p.getPlayerIndex());
				jsonRoad.put("location", (JSONObject) roadLoc);
				roads.add((JSONObject) jsonRoad);
				}
			}
			for (City c: p.getCities())
			{
				if (c.isPlaced())
				{
				Map<String, Object> jsonCity = new HashMap<String, Object>();
				Map<String, Object> cityLoc = new HashMap<String, Object>();
				cityLoc.put("x", c.getVertex().getVertexLocation().getHexLoc().getX());
				cityLoc.put("y", c.getVertex().getVertexLocation().getHexLoc().getY());
				try {
					cityLoc.put("direction", VertexDirection.toAbbreviation(c.getVertex().getVertexLocation().getDir()));
				} catch (ModelAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonCity.put("owner", p.getPlayerIndex());
				jsonCity.put("location", (JSONObject) cityLoc);
				roads.add((JSONObject) jsonCity);
				}
			}
			for (Settlement s: p.getSettlements())
			{
				if (s.isPlaced())
				{
				Map<String, Object> jsonSettlement = new HashMap<String, Object>();
				Map<String, Object> settlementLoc = new HashMap<String, Object>();
				settlementLoc.put("x", s.getVertex().getVertexLocation().getHexLoc().getX());
				settlementLoc.put("y", s.getVertex().getVertexLocation().getHexLoc().getY());
				try {
					settlementLoc.put("direction", VertexDirection.toAbbreviation(s.getVertex().getVertexLocation().getDir()));
				} catch (ModelAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonSettlement.put("owner", p.getPlayerIndex());
				jsonSettlement.put("location", (JSONObject) settlementLoc);
				roads.add((JSONObject) jsonSettlement);
				}
			}
		}

		jsonMap.put("hexes", hexes);
		jsonMap.put("ports", ports);
		jsonMap.put("roads", roads);
		jsonMap.put("settlements", settlements);
		jsonMap.put("cities", cities);
		jsonMap.put("radius", this.radius);
		Map<String, Object> robberLoc = new HashMap<String, Object>();
		robberLoc.put("x", this.getRobberLocation().getX());
		robberLoc.put("y", this.getRobberLocation().getY());
		jsonMap.put("robber", (JSONObject) robberLoc);
		
		return (JSONObject) jsonMap;
	}
	
	public static Hex getHexAt(HexLocation hexLocation) {
		return hexes.get(hexLocation);
	}

	public static Vertex getVertexAt(VertexLocation vertexLoc) {
		VertexDirection vertDir = vertexLoc.getDir();
		HexLocation hexLoc = vertexLoc.getHexLoc();
		Hex hex = getHexAt(hexLoc);
		Vertex vertex = null;
		if (hex != null)
			vertex = hex.getVertex(vertDir);
		if (hex == null) {
			hex = getHexAt(hexLoc.getNeighborLoc(vertDir.toRightEdge()));
			if (hex != null)
				vertex = hex.getVertex(vertDir.toRightEdge().toOpposite().toRightVertex());
		}
		if (hex == null) {
			hex = getHexAt(hexLoc.getNeighborLoc(vertDir.toLeftEdge()));
			if (hex != null)
				vertex = hex.getVertex(vertDir.toLeftEdge().toOpposite().toLeftVertex());
		}
		return vertex;
	}

	public Edge getEdgeAt(EdgeLocation edgeLoc) {
		EdgeDirection edgeDir = edgeLoc.getDir();
		HexLocation hexLoc = edgeLoc.getHexLoc();
		Hex hex = getHexAt(hexLoc);
		Edge edge = null;
		if (hex != null)
			edge = hex.getEdge(edgeDir);
		if (edge == null) {
			hex = getHexAt(hexLoc.getNeighborLoc(edgeDir));
			if (hex != null)
				edge = hex.getEdge(edgeDir.toOpposite());
		}
		return edge;
	}

	/**
	 * 
	 * @param e
	 *            - An edge to be checked
	 * @return - True if a Road can be built
	 */
	public Boolean canBuildRoad(Player player, EdgeLocation edgeLocation) {
		Edge edge = getEdgeAt(edgeLocation);
		if (edge.hasRoad())
			return false;
		if (!edge.isBuildable())
			return false;

		for (Vertex vertex : edge.getAllVertices()) {
			if (vertex.getBuilding() != null && vertex.getBuilding().getOwner().equals(player))
				return true;
			if (vertex.getBuilding() == null) {

				if (vertex.getLeftEdge(edge) != null && 
						vertex.getLeftEdge(edge).getRoad() != null &&
						vertex.getLeftEdge(edge).getRoad().getOwner().equals(player))
					return true;
				if (vertex.getRightEdge(edge) != null && 
						vertex.getRightEdge(edge).getRoad() != null && 
						vertex.getRightEdge(edge).getRoad().getOwner().equals(player))
					return true;
			}

		}
		return false;
	}

	public boolean canBuildSetupRoad(Player player, EdgeLocation edgeLocation) {
		Edge edge = getEdgeAt(edgeLocation);
		if (edge.hasRoad())
			return false;
		if (!edge.isBuildable())
			return false;
		for (Vertex vertex : edge.getAllVertices()) {
			if (vertex.getBuilding() != null && vertex.getBuilding().getOwner().equals(player)) {
				if (!vertex.getLeftEdge(edge).hasRoad() && !vertex.getRightEdge(edge).hasRoad()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean canBuildRoadTwo(Player player, EdgeLocation one, EdgeLocation edgeLocation) {
		one = getEdgeAt(one).getEdgeLocation();
		Edge edge = getEdgeAt(edgeLocation);
		if (edge.hasRoad())
			return false;
		if (!edge.isBuildable())
			return false;

		for (Vertex vertex : edge.getAllVertices()) {
			if (vertex.getBuilding() != null && vertex.getBuilding().getOwner().equals(player))
				return true;
			if (vertex.getBuilding() == null) {

				if (vertex.getLeftEdge(edge) != null && 
						vertex.getLeftEdge(edge).getRoad() != null &&
						vertex.getLeftEdge(edge).getRoad().getOwner().equals(player))
				{
					return true;
				}
				else
				{
					EdgeLocation el = vertex.getLeftEdge(edge).getEdgeLocation();
					if (el.equals(one))
						return true;
				}
				if (vertex.getRightEdge(edge) != null && 
						vertex.getRightEdge(edge).getRoad() != null && 
						vertex.getRightEdge(edge).getRoad().getOwner().equals(player))
				{
					return true;
				}
				else
				{
					EdgeLocation el = vertex.getRightEdge(edge).getEdgeLocation();
					if (el.equals(one))
						return true;
				}
			}

		}
		return false;
	}

	/**
	 * @param vertexLoc
	 *            a vertexLocation to be checked
	 * @return True if a settlement can be build
	 */
	public Boolean canBuildSettlement(Player player, VertexLocation vertexLoc) throws IndexOutOfBoundsException {
		Vertex vertex = getVertexAt(vertexLoc);
		if (vertex.hasBuilding())
			return false;
		if (!vertex.isBuildable())
			return false;

		Edge[] edges = vertex.getAllEdges();
		for (Edge edge : edges) {
			if (edge.getOtherVertex(vertex).hasBuilding())
				return false;
		}
		for (Edge edge : edges) {
			Road road = edge.getRoad();
			if (road != null && road.getOwner().equals(player))
				return true;
		}
		return false;
	}

	public Boolean canBuildSetupSettlement(Player player, VertexLocation vertexLoc) throws IndexOutOfBoundsException {
		Vertex vertex = getVertexAt(vertexLoc);
		if (vertex.hasBuilding())
			return false;
		if (!vertex.isBuildable())
			return false;
		Edge[] edges = vertex.getAllEdges();
		for (Edge edge : edges) {
			if (edge.getOtherVertex(vertex).hasBuilding())
			return false;
		}
		return true;
	}

	/**
	 * @param vertexLoc
	 *            A vertexLocation to be checked
	 * @return - True if a city can be built
	 */
	public Boolean canBuildCity(Player player, VertexLocation vertexLoc) throws IndexOutOfBoundsException {
		Vertex vertex = getVertexAt(vertexLoc);
		if (!vertex.hasBuilding())
			return false;
		if (!vertex.getBuilding().getOwner().equals(player))
			return false;
		if (!(vertex.getBuilding() instanceof Settlement))
			return false;
		return true;
	}

	public Boolean canPlaceRobber(HexLocation hexLoc) {
		Hex target = hexes.get(hexLoc);
		if (target == null)
			return false;
		if (!target.isBuildable())
			return false;
		if (target.getHexLocation().equals(robber.getHex().getHexLocation()))
			return false;
		return true;
	}

	public void placeRobber(HexLocation hexLoc) {
		LandHex target = (LandHex) hexes.get(hexLoc);
		target.placeRobber(robber);
		robber.setHex(target);
	}

	public void buildSettlement(Settlement settlement, VertexLocation vertexLoc) {
		Vertex vertex = getVertexAt(vertexLoc);
		settlement.setVertex(vertex);
		vertex.setBuilding(settlement);
	}

	public void buildCity(City city, VertexLocation vertexLoc) {
		Vertex vertex = getVertexAt(vertexLoc);
		city.setVertex(vertex);
		vertex.setBuilding(city);
	}

	public void buildRoad(Road road, EdgeLocation edgeLoc) {
		Edge edge = getEdgeAt(edgeLoc);
		road.setEdge(edge);
		edge.setRoad(road);
	}

	private void connectHexEdge(Hex hex) {
		if (hex.getEdge(EdgeDirection.North) == null)
			connectEdge(hex, EdgeDirection.North);
		if (hex.getEdge(EdgeDirection.NorthWest) == null)
			connectEdge(hex, EdgeDirection.NorthWest);
		if (hex.getEdge(EdgeDirection.SouthWest) == null)
			connectEdge(hex, EdgeDirection.SouthWest);
		if (hex.getEdge(EdgeDirection.South) == null)
			connectEdge(hex, EdgeDirection.South);
		if (hex.getEdge(EdgeDirection.SouthEast) == null)
			connectEdge(hex, EdgeDirection.SouthEast);
		if (hex.getEdge(EdgeDirection.NorthEast) == null)
			connectEdge(hex, EdgeDirection.NorthEast);
	}

	private void connectHexVertex(Hex hex) {
		if (hex.getVertex(VertexDirection.NorthWest) == null)
			connectVertex(hex, VertexDirection.NorthWest);
		if (hex.getVertex(VertexDirection.West) == null)
			connectVertex(hex, VertexDirection.West);
		if (hex.getVertex(VertexDirection.SouthWest) == null)
			connectVertex(hex, VertexDirection.SouthWest);
		if (hex.getVertex(VertexDirection.SouthEast) == null)
			connectVertex(hex, VertexDirection.SouthEast);
		if (hex.getVertex(VertexDirection.East) == null)
			connectVertex(hex, VertexDirection.East);
		if (hex.getVertex(VertexDirection.NorthEast) == null)
			connectVertex(hex, VertexDirection.NorthEast);
	}

	private void connectEdge(Hex hex, EdgeDirection edgeDir) {
		Hex adjacent = getHexAt(hex.getHexLocation().getNeighborLoc(edgeDir));
		EdgeLocation edgeLoc = new EdgeLocation(hex.getHexLocation(), edgeDir);

		Edge edge = new Edge(edgeLoc, hex, adjacent);

		hex.setEdge(edgeDir, edge);

		if (adjacent != null) {
			adjacent.setEdge(edgeDir.toOpposite(), edge);
			connectHexEdge(adjacent);
		}
	}

	private void connectVertex(Hex hex, VertexDirection vertexDir) {
		Edge edgeLeft = hex.getEdge(vertexDir.toLeftEdge());
		Edge edgeRight = hex.getEdge(vertexDir.toRightEdge());
		Edge edgeFar = null;

		Hex hexLeft = edgeLeft.getOtherHex(hex);
		Hex hexRight = edgeRight.getOtherHex(hex);

		if (hexLeft != null)
			edgeFar = hexLeft.getEdge(vertexDir.toLeftEdge().toOpposite().toLeft());
		if (hexRight != null)
			edgeFar = hexRight.getEdge(vertexDir.toRightEdge().toOpposite().toRight());

		VertexLocation vertexLoc = new VertexLocation(hex.getHexLocation(), vertexDir);

		Vertex vertex = new Vertex(vertexLoc, hex, edgeLeft, hexLeft, edgeFar, hexRight, edgeRight);

		hex.setVertex(vertexDir, vertex);
		edgeLeft.setRightVertex(hex, vertex);
		edgeRight.setLeftVertex(hex, vertex);
		if (hexLeft != null) {
			hexLeft.setVertex(vertexDir.toRight().toRight(), vertex);
			edgeFar.setRightVertex(hexLeft, vertex);
		}
		if (hexRight != null) {
			hexRight.setVertex(vertexDir.toLeft().toLeft(), vertex);
			edgeFar.setLeftVertex(hexRight, vertex);
		}

		if (hexLeft != null)
			connectHexVertex(hexLeft);
		if (hexRight != null)
			connectHexVertex(hexRight);
	}

	/**
	 * @param jsonMap
	 * @param game
	 * @return Boolean if jsonMap matches the boards state.
	 */
	public Boolean equalsJSON(JSONObject jsonMap, Model game) {

		JSONArray jsonHexes = (JSONArray) jsonMap.get("hexes");

		JSONArray jsonPorts = (JSONArray) jsonMap.get("ports");

		JSONArray jsonRoads = (JSONArray) jsonMap.get("roads");

		JSONArray jsonSettlements = (JSONArray) jsonMap.get("settlements");

		JSONArray jsonCities = (JSONArray) jsonMap.get("cities");

		JSONObject jsonRobber = (JSONObject) jsonMap.get("robber");

		for (Object hexObject : jsonHexes) {
			JSONObject jsonHex = (JSONObject) hexObject;

			JSONObject jsonHexLoc = (JSONObject) jsonHex.get("location");
			HexLocation hexLoc = new HexLocation(((Long) jsonHexLoc.get("x")).intValue(),
					((Long) jsonHexLoc.get("y")).intValue());
			if (!getHexAt(hexLoc).equals(jsonHex))
				return false;
		}

		for (Object portObject : jsonPorts) {
			JSONObject jsonPort = (JSONObject) portObject;

			JSONObject jsonHexLoc = (JSONObject) jsonPort.get("location");
			HexLocation hexLoc = new HexLocation(((Long) jsonHexLoc.get("x")).intValue(),
					((Long) jsonHexLoc.get("y")).intValue());

			if (!getHexAt(hexLoc).equals(jsonPort))
				return false;
		}

		for (Object roadObject : jsonRoads) {
			JSONObject jsonRoad = (JSONObject) roadObject;

			JSONObject jsonEdgeLoc = (JSONObject) jsonRoad.get("location");
			HexLocation hexLoc = null;
			try {
				hexLoc = new HexLocation(jsonEdgeLoc);
			} catch (BadJSONException e2) {
				// this will never happen, because the json will always be good
				// during testing
			}

			String direction = (String) jsonEdgeLoc.get("direction");
			EdgeDirection edgeDir = null;
			try {
				edgeDir = EdgeDirection.fromJSON(direction);
			} catch (BadJSONException e1) {
				// this will never happen, because the json will always be good
				// during testing
			}

			EdgeLocation edgeLoc = new EdgeLocation(hexLoc, edgeDir);

			Road road = getEdgeAt(edgeLoc).getRoad();

			if (!road.equals(jsonRoad))
				return false;
		}

		for (Object cityObject : jsonCities) {
			JSONObject jsonCity = (JSONObject) cityObject;

			JSONObject jsonVertexLoc = (JSONObject) jsonCity.get("location");
			HexLocation hexLoc = null;
			try {
				hexLoc = new HexLocation(jsonVertexLoc);
			} catch (BadJSONException e2) {
				// this will never happen, because the json will always be good
				// during testing
			}

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = null;
			try {
				vertexDir = VertexDirection.fromJSON(direction);
			} catch (BadJSONException e1) {
				// this will never happen, because the json will always be good
				// during testing
			}

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			Building building = getVertexAt(vertexLoc).getBuilding();

			if (!building.equals(jsonCity))
				return false;
		}

		for (Object settlementObject : jsonSettlements) {
			JSONObject jsonSettlement = (JSONObject) settlementObject;

			JSONObject jsonVertexLoc = (JSONObject) jsonSettlement.get("location");
			HexLocation hexLoc = null;
			try {
				hexLoc = new HexLocation(jsonVertexLoc);
			} catch (BadJSONException e2) {
				// this will never happen, because the json will always be good
				// during testing
			}

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = null;
			try {
				vertexDir = VertexDirection.fromJSON(direction);
			} catch (BadJSONException e1) {
				// this will never happen, because the json will always be good
				// during testing
			}

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			Building building = getVertexAt(vertexLoc).getBuilding();

			if (!building.equals(jsonSettlement))
				return false;
		}

		robber.equals(jsonRobber);
		return true;
	}
	
	public List<PseudoHex> getPseudoHexes() {
		List<PseudoHex> phexes = new ArrayList<PseudoHex>();
		for (Hex hex : new ArrayList<Hex>(hexes.values())) {
			HexType hexType = hex.getHexType();
			HexLocation hexLoc = hex.getHexLocation().copy();
			Integer productionNum = null;
			EdgeDirection portDirection = null;
			PortType portType = null;
			if (hex instanceof ProductionHex)
				productionNum = ((ProductionHex) hex).getProductionNumber();
			if (hex instanceof PortHex) {
				portDirection = ((PortHex) hex).getPortDirection();
				portType = ((PortHex) hex).getPortType();
			}
			phexes.add(new PseudoHex(hexType, hexLoc, productionNum, portDirection, portType));
		}
		return phexes;
	}
	
	public HexLocation getRobberLocation() {
		return robber.getHex().getHexLocation().copy();
	}

	public boolean couldBeRobbedFrom(HexLocation robberLoc, Integer targetPlayerIndex) {
		for(Vertex vertex : hexes.get(robberLoc).getVerts()) {
			if (vertex.hasBuilding()) {
				if (vertex.getBuilding().getOwner().getPlayerIndex() == targetPlayerIndex)
					return true;
			}
		}
		return false;
	}

	
}
