package shared.models.board.edge;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	private EdgeDirection right;
	private EdgeDirection left;
	
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

