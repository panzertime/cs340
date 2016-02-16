package shared.model.board.hex.tiles.water;

import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;

public class WaterHex extends Hex {

	public WaterHex(HexLocation hexLocation) {
		super(hexLocation);
		hexType = HexType.WATER;
	}

	/**
	 * @return Boolean the ability of the hex to hold pieces
	 */
	@Override
	public Boolean isBuildable() {
		return false;
	}
}
