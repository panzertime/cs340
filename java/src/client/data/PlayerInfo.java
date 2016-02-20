package client.data;

import org.json.simple.JSONObject;

import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{
	
	private int id;
	private int playerIndex;
	private String name;
	private CatanColor color;
	
	public PlayerInfo()
	{
		createNullPlayer();
	}
	
	private void createNullPlayer() {
		setId(-1);
		setPlayerIndex(-1);
		setName("");
		setColor(CatanColor.WHITE);
	}

	public PlayerInfo(JSONObject jsonPlayer) throws BadJSONException {
		String color = (String) jsonPlayer.get("color");
		String name = (String) jsonPlayer.get("name");
		Long id = (Long) jsonPlayer.get("id");
		
		try {
			this.color = CatanColor.valueOf(color.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new BadJSONException("Bad Catan Color inputed");
		} catch(NullPointerException e) {		
			if(name == null && id == null) {
				createNullPlayer();
				return;
			} else {
				throw new BadJSONException("No Catan Color Inputed");
			}
		}
		
		if(name == null) {
			throw new BadJSONException("No Player Name Inputed");
		} else if (id == null) {
			throw new BadJSONException("No Player ID Inputed");
		}
		
		this.id = id.intValue();
		this.name = name;
	}

	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		if(color != null && name != null && id != -1) {
			result.put("color", color.toString().toLowerCase());
			result.put("name", name);
			result.put("id", id);
		} else if (color == null && name == null && id == -1) {
			//not init - fine, just don't add anything to the arry
			//this line is neccesary so that errors can be caught
			//in bad Setups
		} else {
			throw new NullPointerException("Exactly one or two values in a "
					+ "player object were null.");
		}
		return result;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getPlayerIndex()
	{
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public CatanColor getColor()
	{
		return color;
	}
	
	public void setColor(CatanColor color)
	{
		this.color = color;
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}

	/**
	 * This function assumes that
	 * id and index = -1 and color is white only for non existing players
	 * @return Whether or not a player exists
	 */
	public boolean exists() {
		return (!this.name.isEmpty() && this.id != -1 && this.playerIndex != -1
				&& this.color != CatanColor.WHITE);
	}
}

