package shared.models.chat;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;

public class Log {
	private ArrayList<Message> messageList;
	
	public Log(JSONObject[] messageLine)
	{
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
