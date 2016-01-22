package shared.models.board.edge;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexNotLinkedException;

public class Edge {
	
	private Hex hex0;
	private Hex hex1;

	/**
	 * @Pre hex is either hex0 or hex1;
	 * @param hex one of two possible hexes attached to this
	 * @throws HexNotLinkedException 
	 * @return hex the Other hex attached to this
	 */
	public Hex getOtherHex(Hex hex) throws HexNotLinkedException {
		if (hex == hex0) {
			return hex1;
		} else if (hex == hex1) {
			return hex0;
		}
		throw new HexNotLinkedException();
	}

}
