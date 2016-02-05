package shared.models.board.hex.tiles.water;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;

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
