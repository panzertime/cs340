package shared.models.board;

import java.util.HashMap;
import java.util.Map;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;

public class Board {
	
	private Map <HexLocation, Hex> hexes = new HashMap <HexLocation, Hex>();
	
	private Hex getHexAtLocation(HexLocation hexLocation) throws IndexOutOfBoundsException {
		Hex hex = hexes.get(hexLocation);
		if (hex == null)
			throw new IndexOutOfBoundsException();
		return hex;
	}
	
	
	

}
