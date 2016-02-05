package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;

public class PastureHex extends ProductionHex {

	public PastureHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation, productionNumber);
		hexType = HexType.SHEEP;
	}

}
