package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;

public class MountainHex extends ProductionHex {

	public MountainHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation, productionNumber);
		hexType = HexType.ORE;
	}

}
