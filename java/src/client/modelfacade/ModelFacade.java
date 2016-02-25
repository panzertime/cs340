package client.modelfacade;

import org.json.simple.JSONObject;

import client.data.PlayerInfo;
import shared.model.Model;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

public abstract class ModelFacade {
	
	protected ModelFacade() {
		Model.registerListener(this);
		playerInfo = new PlayerInfo();
	}

	protected static PlayerInfo playerInfo;

	protected Model gameModel;
	
	public void updateModel(Model model) {
		gameModel = model;
	}
	
	public static void setUserID(int userID) {
		playerInfo.setId(userID);
	}
	
	public void setUserIndex(int userIndex) {
		playerInfo.setPlayerIndex(userIndex);
	}
	
	public static void setUserColor(CatanColor color) {
		playerInfo.setColor(color);
	}
	
	public static void setUserName(String name) {
		playerInfo.setName(name);
	}
	
	protected PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
	
	protected Boolean hasGameModel() {
		if (gameModel == null)
			return false;
		return true;
	}
	
	/** Necessary for when the first model is passed in. Afterwards
	 * it is no longer necessary as it is always stored in Playerinfo
	 * @return
	 */
	protected void initUserIndex() {
		int userIndex = gameModel.getIndexFromPlayerID(playerInfo.getId());
		playerInfo.setPlayerIndex(userIndex);
	}
		
	synchronized public static void setModel(JSONObject jsonModel) throws BadJSONException {
		Model.shareNewModel(new Model(jsonModel));
	}

}
