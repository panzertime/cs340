package client.communication;

import java.util.*;
import java.util.List;

import client.base.*;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;
import shared.model.definitions.CatanColor;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, GetModelFacadeListener {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		//initFromModel();
		GetModelFacade.registerListener(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	private void initFromModel() {
		
		//<temp>
		
		List<LogEntry> entries = new ArrayList<LogEntry>();
		//entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		//entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		//entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		//entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		//entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		//entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));
		//entries.add(new LogEntry(CatanColor.BROWN, "This is a brown message"));
		//entries.add(new LogEntry(CatanColor.ORANGE, "This is an orange message ss x y z w.  This is an orange message.  This is an orange message.  This is an orange message."));

		entries = GetModelFacade.sole().getGameHistory();
		
		getView().setEntries(entries);
	
		//</temp>
	}

	@Override
	public void update() {
		List<LogEntry> entries = GetModelFacade.sole().getGameHistory();
		getView().setEntries(entries);		
	}
	
}

