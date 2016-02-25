package client.main;

import client.data.PlayerInfo;
import shared.model.definitions.CatanColor;

public class ClientPlayer {
	private static ClientPlayer sole;
	private PlayerInfo clientInfo;
	private Integer curGameID;
	
	private ClientPlayer() {
		clientInfo = new PlayerInfo();
		curGameID = null;
	}
	
	public static ClientPlayer sole() {
		if(sole == null) {
			sole = new ClientPlayer();
		}
		return sole;
	}
	
	public void setUserID(int userID) {
		clientInfo.setId(userID);
	}
	
	public void setUserIndex(int userIndex) {
		clientInfo.setPlayerIndex(userIndex);
	}
	
	public void setUserColor(CatanColor color) {
		clientInfo.setColor(color);
	}
	
	public void setUserName(String name) {
		clientInfo.setName(name);
	}
	
	public int getUserID() {
		return clientInfo.getId();
	}
	
	public int getUserIndex() {
		return clientInfo.getPlayerIndex();
	}
	
	private CatanColor getUserColor(CatanColor color) {
		return clientInfo.getColor();
	}
	
	public String getUserName(String name) {
		return clientInfo.getName();
	}

	public PlayerInfo getPlayerInfo() {
		return clientInfo;
	}

	public void setGameID(Integer gameID) {
		curGameID = gameID;
	}
	
	public Integer getGameID() {
		return curGameID;
	}
}
