package shared.models.board;

import java.util.HashMap;
import java.util.Map;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.piece.Robber;

public class Board {
	
	private Robber robber;
	
	private Map <HexLocation, Hex> hexes = new HashMap <HexLocation, Hex>();
	
	public Board(Map<String, Object> map) {
		// TODO Auto-generated constructor stub
	}

	private Hex getHexAtLocation(HexLocation hexLocation) throws IndexOutOfBoundsException {
		Hex hex = hexes.get(hexLocation);
		if (hex == null)
			throw new IndexOutOfBoundsException();
		return hex;
	}
	
	

}
