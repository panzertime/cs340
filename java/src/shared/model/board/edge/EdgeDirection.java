package shared.model.board.edge;

import org.json.simple.JSONObject;

import shared.model.board.vertex.VertexDirection;
import shared.model.exceptions.BadJSONException;
import shared.model.exceptions.ModelAccessException;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private String json;
	private EdgeDirection opposite;
	private EdgeDirection right;
	private EdgeDirection left;
	private VertexDirection rightVertex;
	private VertexDirection leftVertex;
	
	public static EdgeDirection fromJSON(String jsonEdgeDir) throws BadJSONException {
		switch (jsonEdgeDir) {
		case "NW" :	
			return EdgeDirection.NorthWest;
		case "N" : 
			return EdgeDirection.North;
		case "NE" :
			return EdgeDirection.NorthEast;
		case "SE" :
			return EdgeDirection.SouthEast;
		case "S" :
			return EdgeDirection.South;
		case "SW" :
			return EdgeDirection.SouthWest;
		default :
			throw new BadJSONException();
		}
	}
	
	public static String toAbbreviation(EdgeDirection dir) throws ModelAccessException
	{
		switch (dir)
		{
		case NorthWest:return "NW";
		case North:return "N";
		case NorthEast:return "NE";
		case SouthEast:return "SE";
		case South:return "S";
		case SouthWest:return "SW";
		}
		throw new ModelAccessException();
		
	}
	
	static
	{
		NorthWest.json = "NW";
		North.json = "N";
		NorthEast.json = "NE";
		SouthEast.json = "SE";
		South.json = "N";
		SouthWest.json = "NE";
		
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
		
		NorthWest.right = North;
		North.right = NorthEast;
		NorthEast.right = SouthEast;
		SouthEast.right = South;
		South.right = SouthWest;
		SouthWest.right = NorthWest;
		
		NorthWest.left = SouthWest;
		North.left = NorthWest;
		NorthEast.left = North;
		SouthEast.left = NorthEast;
		South.left = SouthEast;
		SouthWest.left = South;
		
		NorthWest.rightVertex = VertexDirection.NorthWest;
		North.rightVertex = VertexDirection.NorthEast;
		NorthEast.rightVertex = VertexDirection.East;
		SouthEast.rightVertex = VertexDirection.SouthEast;
		South.rightVertex = VertexDirection.SouthWest;
		SouthWest.rightVertex = VertexDirection.West;
		
		NorthWest.leftVertex = VertexDirection.West;
		North.leftVertex = VertexDirection.NorthWest;
		NorthEast.leftVertex = VertexDirection.NorthEast;
		SouthEast.leftVertex = VertexDirection.East;
		South.leftVertex = VertexDirection.SouthEast;
		SouthWest.leftVertex = VertexDirection.SouthWest;
	}
	
	public EdgeDirection toOpposite() {
		return opposite;
	}
	
	public EdgeDirection toRight() {
		return right;
	}
	
	public EdgeDirection toLeft() {
		return left;
	}

	public VertexDirection toRightVertex() {
		return rightVertex;
	}
	
	public VertexDirection toLeftVertex() {
		return leftVertex;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("direction", this.json);
		return json;
	}
}

