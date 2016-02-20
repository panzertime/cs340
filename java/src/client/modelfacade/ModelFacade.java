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

	protected PlayerInfo playerInfo;

	protected Model gameModel;
	
	public void updateModel(Model model) {
		gameModel = model;
	}
	
	public void setUserID(int userID) {
		this.playerInfo.setId(userID); 
	}
	
	public void setUserIndex(int userIndex) {
		this.playerInfo.setPlayerIndex(userIndex);
	}
	
	public void setUserColor(CatanColor color) {
		this.playerInfo.setColor(color);
	}
	
	public void setUserName(String name) {
		this.playerInfo.setName(name);
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
