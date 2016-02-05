package shared.models.chat;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;

import shared.models.exceptions.BadJSONException;

public class MessageLog {
	private ArrayList<Message> messageList;
	
	public MessageLog()
	{
		messageList = new ArrayList<Message>();
	}
	public MessageLog(JSONObject[] messageLine) throws BadJSONException
	{
		messageList = new ArrayList<Message>();
		for (int i = 0; i < messageLine.length; i++)
		{
			messageList.add(new Message((JSONObject)messageLine[i]));
		}
	}
	
	public ArrayList<Message> getMessageList() {
		return messageList;
	}
	
	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}
	
	
}
