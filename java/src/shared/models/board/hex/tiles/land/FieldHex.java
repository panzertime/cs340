package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;

public class FieldHex extends ProductionHex {

	public FieldHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation, productionNumber);
		hexType = HexType.WHEAT;
	}

}
