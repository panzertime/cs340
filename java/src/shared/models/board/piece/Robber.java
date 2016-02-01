package shared.models.board.piece;

import shared.models.board.hex.HexLocation;

public class Robber {

	/**
	 * 
	 * @param l The location of the robber
	 */
	public Robber(HexLocation l)
	{
		location = l;
	}
	
	public HexLocation location;

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	
	
	
}
