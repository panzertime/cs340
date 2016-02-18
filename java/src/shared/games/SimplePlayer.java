package shared.games;

import org.json.simple.JSONObject;

import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

public class SimplePlayer {

	private CatanColor color;
	private String name;
	private Integer id;
	
	public SimplePlayer(JSONObject jsonPlayer) throws BadJSONException {
		String color = (String) jsonPlayer.get("color");
		String name = (String) jsonPlayer.get("name");
		Long id = (Long) jsonPlayer.get("id");
		
		try {
			this.color = CatanColor.valueOf(color.toUpperCase());
		} catch(IllegalArgumentException e) {
			throw new BadJSONException("Bad Catan Color inputed");
		} catch(NullPointerException e) {		
			if(name == null && id == null) {
				//uninitialized
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
		if(color != null && name != null && id != null) {
			result.put("color", color.toString().toLowerCase());
			result.put("name", name);
			result.put("id", id);
		} else if (color == null && name == null && id == null) {
			//not init - fine, just don't add anything to the arry
			//this line is neccesary so that errors can be caught
			//in bad Setups
		} else {
			throw new NullPointerException("Exactly one or two values in a "
					+ "player object were null.");
		}
		return result;
	}
}
