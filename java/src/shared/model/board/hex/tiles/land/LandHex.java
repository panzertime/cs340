package shared.model.board.hex.tiles.land;

import org.json.simple.JSONObject;

import shared.model.board.hex.Hex;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;
import shared.model.board.piece.Robber;
import shared.model.exceptions.BadJSONException;

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
	
	@Override
	public boolean equals(JSONObject jsonHex) {
		if (!super.equals(jsonHex))
			return false;
    	Object resource = jsonHex.get("resource");
    	if (resource == null)
    		return true;
    	try {
			if (hexType != HexType.fromJSON((String)resource))
				return false;
		} catch (BadJSONException e) {
			// this will never happen, because the json will always be good during testing
		}
    	return true;
    }
}
