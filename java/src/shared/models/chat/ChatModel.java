package shared.models.chat;

import java.util.Map;

import org.json.simple.JSONObject;

import shared.models.exceptions.BadJSONException;

public class ChatModel {
	private MessageLog chatLog;
	private MessageLog gameLog;
	public ChatModel(JSONObject chatList, JSONObject gameList) throws BadJSONException
	{
		if (chatList == null)
			chatLog = new MessageLog();
		else	
		chatLog = new MessageLog((JSONObject[])chatList.get("lines"));
		if (gameList == null)
			gameLog = new MessageLog();
		else
		gameLog = new MessageLog((JSONObject[])gameList.get("lines"));
	}
	public MessageLog getChatLog() {
		return chatLog;
	}
	public void setChatLog(MessageLog chatLog) {
		this.chatLog = chatLog;
	}
	public MessageLog getGameLog() {
		return gameLog;
	}
	public void setGameLog(MessageLog gameLog) {
		this.gameLog = gameLog;
	}
	
	
}
