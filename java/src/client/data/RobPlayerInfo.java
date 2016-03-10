package client.data;

import shared.model.definitions.CatanColor;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * <li>NumCards: Number of development cards the player has (>= 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{
	
	private Integer numCards;
	
	public RobPlayerInfo(Integer id, Integer playerIndex, String name, CatanColor color, Integer numCards) {
		super(id, playerIndex, name, color);
		this.numCards = numCards;
	}
	
	public RobPlayerInfo() {
		playerIndex = -1;
	}

	public int getNumCards(){
		return numCards;
	}
	
}

