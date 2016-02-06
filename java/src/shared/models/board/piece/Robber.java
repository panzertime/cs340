package shared.models.board.piece;

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
	
	
	
}
