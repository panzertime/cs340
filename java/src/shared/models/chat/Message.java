package shared.models.chat;

import java.util.Map;

import shared.models.Player;

public class Message {
	private String source;
	private String message;
	public Message(Map<String, Object> messageLine)
	{
		this.message = (String) messageLine.get("message");
		this.source = (String) messageLine.get("source"); //Static method to change this to PLAYER class
		
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
