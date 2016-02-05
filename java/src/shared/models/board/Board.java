package shared.models.board;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.models.Player;
import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeDirection;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.edge.EdgeNotLinkedException;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.piece.City;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Robber;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexDirection;
import shared.models.board.vertex.VertexLocation;

public class Board {

	private Robber robber;

	private Map<HexLocation, Hex> hexes;

	public Board(JSONObject map) {
		hexes = new HashMap<HexLocation, Hex>();
		
	}

	private Hex getHexAt(HexLocation hexLocation) {
		return hexes.get(hexLocation);
	}

	private Vertex getVertexAt(VertexLocation vertexLoc) {
		Hex hex = getHexAt(vertexLoc.getHexLoc());
		Vertex vertex = null;
		vertex = hex.getVertex(vertexLoc.getDir());
		return vertex;
	}

	private Edge getEdgeAt(EdgeLocation edgeLoc) {
		Hex hex = getHexAt(edgeLoc.getHexLoc());
		Edge edge = null;
		edge = hex.getEdge(edgeLoc.getDir());
		return edge;
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
		try {
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
		} catch (EdgeNotLinkedException e) {
			// TODO Log Exception
		}
		return false;
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

	public void buildSettlement(Road road, EdgeLocation edgeLoc) throws PositionTakenException {
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
		EdgeLocation edgeLoc = new EdgeLocation(hex.getHexlocation(), edgeDir).getNormalizedLocation();

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

		VertexLocation vertexLoc = new VertexLocation(hex.getHexlocation(), vertexDir).getNormalizedLocation();

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
