package shared.models.board.hex.tiles.land;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;

public class LandHex extends Hex {
	
	public LandHex(HexLocation hexLocation) {
		super(hexLocation);
		hexType = HexType.DESERT;
	}
	
	/**
	 * @return Boolean the ability of the hex to hold pieces
	 */
	@Override
	public Boolean isBuildable() {
		return true;
	}

}
