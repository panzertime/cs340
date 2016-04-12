package shared.model.chat;

import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import shared.model.exceptions.BadJSONException;

public class ChatModel {
	private MessageLog chatLog;
	private MessageLog gameLog;
	public ChatModel(JSONObject chatList, JSONObject gameList) throws BadJSONException
	{
		if (chatList == null)
			chatLog = new MessageLog();
		else {
			JSONArray messageList = new JSONArray();
			messageList.addAll((Collection)chatList.get("lines"));
			chatLog = new MessageLog(messageList);
		}
		if (gameList == null)
			gameLog = new MessageLog();
		else{
			JSONArray messageList = new JSONArray();
			messageList.addAll((Collection)gameList.get("lines"));
			gameLog = new MessageLog(messageList);
		}
	}
	
	public ChatModel() {
		this.chatLog = new MessageLog();
		this.gameLog = new MessageLog();
	}

	public boolean equalsJSON(JSONObject chatList, JSONObject gameList) {

		if (chatList == null && chatLog.getSize() != 0) return false;
		if (!chatLog.equalsJSON((JSONArray)chatList.get("lines"))) return false;
		if (gameList == null && gameLog.getSize() != 0) return false;
		if (!gameLog.equalsJSON((JSONArray)gameList.get("lines"))) return false;
		
		
		return true;
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

	public void doSendChat(String message, String source) {
		this.getChatLog().add(message, source);
	}
	
	public void addGameMessage(String message, String source)
	{
		this.gameLog.add(message, source);
	}
	
}
