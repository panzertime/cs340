package shared.models.board;

import java.util.HashMap;
import java.util.Map;

import shared.models.Player;
import shared.models.board.edge.BadEdgeDirectionException;
import shared.models.board.edge.Edge;
import shared.models.board.edge.EdgeLocation;
import shared.models.board.edge.EdgeNotLinkedException;
import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.piece.City;
import shared.models.board.piece.PositionTakenException;
import shared.models.board.piece.Road;
import shared.models.board.piece.Robber;
import shared.models.board.piece.Settlement;
import shared.models.board.vertex.BadVertexDirectionException;
import shared.models.board.vertex.Vertex;
import shared.models.board.vertex.VertexLocation;
import shared.models.board.vertex.VertexNotLinkedException;
import shared.models.exceptions.BuildException;

public class Board {

	private Robber robber;

	private Map<HexLocation, Hex> hexes = new HashMap<HexLocation, Hex>();

	public Board(Map<String, Object> map) {
		// TODO Auto-generated constructor stub
	}

	private Hex getHexAt(HexLocation hexLocation) throws IndexOutOfBoundsException {
		Hex hex = hexes.get(hexLocation);
		if (hex == null)
			throw new IndexOutOfBoundsException();
		return hex;
	}

	private Vertex getVertexAt(VertexLocation vertexLoc) throws IndexOutOfBoundsException {
		Hex hex = getHexAt(vertexLoc.getHexLoc());
		Vertex vertex = null;
		try {
			vertex = hex.getVertex(vertexLoc.getDir());
		} catch (BadVertexDirectionException e) {
			// TODO Log stuff because the map got screwed
			e.printStackTrace();
		}
		return vertex;
	}

	private Edge getEdgeAt(EdgeLocation edgeLoc) throws IndexOutOfBoundsException {
		Hex hex = getHexAt(edgeLoc.getHexLoc());
		Edge edge = null;
		try {
			edge = hex.getEdge(edgeLoc.getDir());
		} catch (BadEdgeDirectionException e) {
			// TODO Log stuff because the map got screwed
			e.printStackTrace();
		}
		return edge;
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
		try {
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
		} catch (VertexNotLinkedException e) {
			// TODO Log exception
		}
		return false;
	}

	/**
	 * @param vertexLoc A vertexLocation to be checked
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
	
	
}
