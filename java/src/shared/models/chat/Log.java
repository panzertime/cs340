package shared.models.chat;

import java.util.ArrayList;
import java.util.Map;

public class Log {
	private ArrayList<Message> messageList;
	public Log(Map<String, Object>[] messageLine)
	{
		for (int i = 0; i < messageLine.length; i++)
		{
			messageList.add(new Message((Map<String, Object>)messageLine[i]));
		}
	}
	public ArrayList<Message> getMessageList() {
		return messageList;
	}
	public void setMessageList(ArrayList<Message> messageList) {
		this.messageList = messageList;
	}
	
	
}
