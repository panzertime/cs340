package shared.model.board.hex.tiles.land;

import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;

public class MountainHex extends ProductionHex {

	public MountainHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation, productionNumber);
		hexType = HexType.ORE;
	}

}
