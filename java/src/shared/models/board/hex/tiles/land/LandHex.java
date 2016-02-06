package shared.models.board.hex.tiles.land;

import shared.models.board.hex.Hex;
import shared.models.board.hex.HexLocation;
import shared.models.board.hex.HexType;
import shared.models.board.piece.Robber;

public class LandHex extends Hex {
	
	private Robber robber;
	
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

	public Boolean hasRobber() {
		if (robber == null) 
			return false;
		return true;
	}

	/**
	 * @param robber the robber to set
	 */
	public void placeRobber(Robber robber) {
		this.robber = robber;
	}

}
