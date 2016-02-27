package shared.model.chat;

import java.util.Map;

import org.json.simple.JSONObject;

import client.communication.LogEntry;
import client.modelfacade.get.GetModelFacade;
import shared.model.Player;
import shared.model.definitions.CatanColor;
import shared.model.exceptions.BadJSONException;

public class Message {
	private String source;
	private String message;
	public Message(JSONObject messageLine) throws BadJSONException
	{
		if (messageLine == null) throw new BadJSONException();
		String m = (String) messageLine.get("message");
		if (m == null)  throw new BadJSONException();
		this.message = m;
		String s = (String) messageLine.get("source");
		if (s == null)  throw new BadJSONException();
		this.source = s; //Static method to change this to PLAYER class
		
	}

	public boolean equalsJSON(JSONObject messageLine) {
		if (messageLine == null) return false;
		String m = (String) messageLine.get("message");
		if (m == null)  return false;
		if (!message.equals(m)) return false;
		String s = (String) messageLine.get("source");
		if (s == null)  return false;
		if (!source.equals(s)) return false;
		return true;
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
	
	public CatanColor getColor()
	{
		GetModelFacade getModelFacade = GetModelFacade.sole(); 
		for (int index: getModelFacade.getPlayerIndices())
		{
			if (getSource().equals(getModelFacade.getPlayerName(index)))
			{
				return getModelFacade.getPlayerColor(index);
			}
		}
		return null;
	}

	public LogEntry toLogEntry() {
		return new LogEntry(getColor(), message);
		
	}
	
	
}
