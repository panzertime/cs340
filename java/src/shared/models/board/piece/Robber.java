package shared.models.board.piece;

import org.json.simple.JSONObject;

import shared.models.board.hex.Hex;

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
	 * @param l The location of the robber
	 */
	public Robber(Hex hex)
	{
		this.hex = hex;
	}
    public boolean equals(JSONObject jsonHex) {
    	JSONObject jsonHexLoc = (JSONObject) jsonHex.get("Location");
    	if (hex.getHexLocation().getX() != (Long) jsonHexLoc.get("x"))
    		return false;
    	if (hex.getHexLocation().getY() != (Long) jsonHexLoc.get("y"))
    		return false;
    	return true;
    }
}
