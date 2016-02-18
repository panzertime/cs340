package shared.model.board.edge;

import shared.model.exceptions.BadJSONException;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	private EdgeDirection right;
	private EdgeDirection left;
	
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
	
	static
	{
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
}

