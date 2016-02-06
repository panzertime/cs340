package shared.models.board;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import shared.models.GameModel;
import shared.models.Player;
import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.tiles.land.ClayHex;
import shared.models.board.hex.tiles.land.FieldHex;
import shared.models.board.hex.tiles.land.ForestHex;
import shared.models.board.hex.tiles.land.LandHex;
import shared.models.board.hex.tiles.land.MountainHex;
import shared.models.board.hex.tiles.land.PastureHex;
import shared.models.board.hex.tiles.water.PortHex;
import shared.models.board.hex.tiles.water.PortType;
import shared.models.board.hex.tiles.water.WaterHex;
import shared.models.board.piece.City;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Robber;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexDirection;
import shared.models.board.vertex.VertexLocation;
import shared.models.exceptions.BadJSONException;
import shared.models.exceptions.BadPlayerIndexException;

public class Board {

	private GameModel game;
	private Robber robber;

	private Map<HexLocation, Hex> hexes;

	public Board(JSONObject jsonMap, GameModel game) throws BadJSONException {
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

		for (Object hexObject : jsonHexes) {
			JSONObject jsonHex = (JSONObject) hexObject;

			JSONObject jsonHexLoc = (JSONObject) jsonHex.get("location");
			if (jsonHexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonHexLoc);

			Integer number = (Integer) jsonHex.get("number");
			String resource = (String) jsonHex.get("resource");

			if (number != null && resource != null) {
				switch (resource) {
				case "Wood":
					hexes.put(hexLoc, new ForestHex(hexLoc, number));
					break;
				case "Brick":
					hexes.put(hexLoc, new ClayHex(hexLoc, number));
					break;
				case "Sheep":
					hexes.put(hexLoc, new PastureHex(hexLoc, number));
					break;
				case "Wheat":
					hexes.put(hexLoc, new FieldHex(hexLoc, number));
					break;
				case "Ore":
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
				switch (resource) {
				case "Wood":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.WOOD, edgeDir));
					break;
				case "Brick":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.BRICK, edgeDir));
					break;
				case "Sheep":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.SHEEP, edgeDir));
					break;
				case "Wheat":
					this.hexes.put(hexLoc, new PortHex(hexLoc, PortType.WHEAT, edgeDir));
					break;
				case "Ore":
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

		// FINALY! the recursive call to connect the board
		Hex centerHex = this.hexes.get(new HexLocation(0, 0));

		connectHexEdge(centerHex);
		connectHexVertex(centerHex);

		for (Object roadObject : jsonRoads) {
			JSONObject jsonRoad = (JSONObject) roadObject;

			Integer ownerIndex = (Integer) jsonRoad.get("owner");
			Road road = null;
			try {
				road = game.whichPlayer(ownerIndex).getFreeRoad();
			} catch (BadPlayerIndexException e) {
				throw new BadJSONException();
			}

			JSONObject jsonEdgeLoc = (JSONObject) jsonRoad.get("location");
			if (jsonEdgeLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonEdgeLoc);

			String direction = (String) jsonEdgeLoc.get("direction");
			EdgeDirection edgeDir = EdgeDirection.fromJSON(direction);

			EdgeLocation edgeLoc = new EdgeLocation(hexLoc, edgeDir);

			try {
				buildRoad(road, edgeLoc);
			} catch (PositionTakenException e) {
				throw new BadJSONException();
			}
		}

		for (Object cityObject : jsonCities) {
			JSONObject jsonCity = (JSONObject) cityObject;

			Integer ownerIndex = (Integer) jsonCity.get("owner");
			City city = null;
			try {
				city = game.whichPlayer(ownerIndex).getFreeCity();
			} catch (BadPlayerIndexException e) {
				throw new BadJSONException();
			}

			JSONObject jsonVertexLoc = (JSONObject) jsonCity.get("location");
			if (jsonVertexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonVertexLoc);

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = VertexDirection.fromJSON(direction);

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			try {
				buildCity(city, vertexLoc);
			} catch (PositionTakenException e) {
				throw new BadJSONException();
			}
		}

		for (Object settlementObject : jsonSettlements) {
			JSONObject jsonSettlement = (JSONObject) settlementObject;

			Integer ownerIndex = (Integer) jsonSettlement.get("owner");
			Settlement settlement = null;
			try {
				settlement = game.whichPlayer(ownerIndex).getFreeSettlement();
			} catch (BadPlayerIndexException e) {
				throw new BadJSONException();
			}

			JSONObject jsonVertexLoc = (JSONObject) jsonSettlement.get("location");
			if (jsonVertexLoc == null)
				throw new BadJSONException();
			HexLocation hexLoc = new HexLocation(jsonVertexLoc);

			String direction = (String) jsonVertexLoc.get("direction");
			VertexDirection vertexDir = VertexDirection.fromJSON(direction);

			VertexLocation vertexLoc = new VertexLocation(hexLoc, vertexDir);

			try {
				buildSettlement(settlement, vertexLoc);
			} catch (PositionTakenException e) {
				throw new BadJSONException();
			}
		}

		HexLocation robberLoc = new HexLocation(jsonRobber);

	}

	public Hex getHexAt(HexLocation hexLocation) {
		return hexes.get(hexLocation);
	}

	public Vertex getVertexAt(VertexLocation vertexLoc) {
		Hex hex = getHexAt(vertexLoc.getHexLoc());
		Vertex vertex = null;
		vertex = hex.getVertex(vertexLoc.getDir());
		return vertex;
	}

	public Edge getEdgeAt(EdgeLocation edgeLoc) {
		Hex hex = getHexAt(edgeLoc.getHexLoc());
		Edge edge = null;
		edge = hex.getEdge(edgeLoc.getDir());
		return edge;
	}

	public Boolean canPlaceRobber(HexLocation hexLoc) {
		Hex target = hexes.get(hexLoc);
		if (target == null)
			return false;
		if (!target.isBuildable())
			return false;
		if (target.getHexlocation().equals(robber.getHex().getHexlocation()))
			return false;
		return true;
	}

	/**
	 * @param vertexLoc a vertexLocation to be checked
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
			if (edge.getOtherVertex(vertex).getBuilding() != null)
				return false;
		}
		for (Edge edge : edges) {
			if (edge.getRoad().getOwner().equals(player))
				return true;
		}
		return false;
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
			if (vertex.getBuilding().getOwner().equals(this))
				return true;
			if (vertex.getBuilding() == null) {

				if (vertex.getLeftEdge(edge).getRoad().getOwner().equals(player))
					return true;
				if (vertex.getRightEdge(edge).getRoad().getOwner().equals(player))
					return true;
			}
		}
		return false;
	}

	public void placeRobber(HexLocation hexLoc) {
		LandHex target = (LandHex) hexes.get(hexLoc);
		target.placeRobber(robber);
		robber.setHex(target);
	}

	public void buildSettlement(Settlement settlement, VertexLocation vertexLoc) throws PositionTakenException {
		Vertex vertex = getVertexAt(vertexLoc);
		settlement.setVertex(vertex);
		vertex.setBuilding(settlement);
	}

	public void buildCity(City city, VertexLocation vertexLoc) throws PositionTakenException {
		Vertex vertex = getVertexAt(vertexLoc);
		city.setVertex(vertex);
		vertex.setBuilding(city);
	}

	public void buildRoad(Road road, EdgeLocation edgeLoc) throws PositionTakenException {
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
		Hex adjacent = getHexAt(hex.getHexlocation().getNeighborLoc(edgeDir));
		EdgeLocation edgeLoc = new EdgeLocation(hex.getHexlocation(), edgeDir);

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

		VertexLocation vertexLoc = new VertexLocation(hex.getHexlocation(), vertexDir);

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
}
