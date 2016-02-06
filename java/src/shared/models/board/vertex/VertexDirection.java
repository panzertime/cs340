package shared.models.board.vertex;

import shared.models.board.edge.EdgeDirection;
import shared.models.exceptions.BadJSONException;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;
	
	private VertexDirection opposite;
	private VertexDirection right;
	private VertexDirection left;
	private EdgeDirection rightEdgeDirection;
	private EdgeDirection leftEdgeDirection;
	
	public static VertexDirection fromJSON(String fromJSON) throws BadJSONException {
		switch (fromJSON) {
		case "NW" :	
			return VertexDirection.NorthWest;
		case "NE" :
			return VertexDirection.NorthEast;
		case "E" : 
			return VertexDirection.East;
		case "SE" :
			return VertexDirection.SouthEast;
		case "SW" :
			return VertexDirection.SouthWest;
		case "W" :
			return VertexDirection.West;
		default :
			throw new BadJSONException();
		}
	}
	
	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
		
		West.right = NorthWest;
		NorthWest.right = NorthEast;
		NorthEast.right = East;
		East.right = SouthEast;
		SouthEast.right = SouthWest;
		SouthWest.right = West;
		
		West.left = SouthWest;
		NorthWest.left = West;
		NorthEast.left = NorthWest;
		East.left = NorthEast;
		SouthEast.left = East;
		SouthWest.left = SouthEast;
		
		West.rightEdgeDirection = EdgeDirection.NorthWest;
		NorthWest.rightEdgeDirection = EdgeDirection.North;
		NorthEast.rightEdgeDirection = EdgeDirection.NorthEast;
		East.rightEdgeDirection = EdgeDirection.SouthEast;
		SouthEast.rightEdgeDirection = EdgeDirection.South;
		SouthWest.rightEdgeDirection = EdgeDirection.SouthWest;
		
		West.leftEdgeDirection = EdgeDirection.SouthWest;
		NorthWest.leftEdgeDirection = EdgeDirection.NorthWest;
		NorthEast.leftEdgeDirection = EdgeDirection.North;
		East.leftEdgeDirection = EdgeDirection.NorthEast;
		SouthEast.leftEdgeDirection = EdgeDirection.SouthEast;
		SouthWest.leftEdgeDirection = EdgeDirection.South;
	}
	
	public VertexDirection toOpposite() {
		return opposite;
	}
	
	public EdgeDirection toLeftEdge() {
		return leftEdgeDirection;
	}
	
	public EdgeDirection toRightEdge() {
		return rightEdgeDirection;
	}
	
	public VertexDirection toLeft() {
		return left;
	}
	
	public VertexDirection toRight() {
		return right;
	}
}

