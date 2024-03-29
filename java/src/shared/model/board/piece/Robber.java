package shared.model.board.piece;

import org.json.simple.JSONObject;

import shared.model.board.hex.Hex;

public class Robber {
	
	public Hex hex;

	/**
	 * @return the hex
	 */
	public Hex getHex() {
		return hex;
	}

	/**
	 * @param hex the hex to set
	 */
	public void setHex(Hex hex) {
		this.hex = hex;
	}

	/**
	 * @param hex The location of the robber
	 */
	public Robber(Hex hex)
	{
		this.hex = hex;
	}

	
	public Robber()
        {
        	hex = null;
    	}

    public boolean equals(JSONObject jsonHexLoc) {
    	if (hex.getHexLocation().getX() != (Long) jsonHexLoc.get("x"))
    		return false;
    	if (hex.getHexLocation().getY() != (Long) jsonHexLoc.get("y"))
    		return false;
    	return true;
    }
}
