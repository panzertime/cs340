package server.command.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shared.model.Model;
import shared.model.definitions.CatanColor;

public class AISelector {
	static AISelector self;
	public static void initiate() {
		self = new AISelector();
	}
	public static AISelector sole(){
		return self;
	}
	
	ArrayList<String> names;
	HashMap<Integer, ArrayList<CatanColor>> gameColors;
	public AISelector() {
		names = new ArrayList<String>();
		gameColors = new HashMap<Integer, ArrayList<CatanColor>>();
		names.add("Samwise");
		names.add("Frodo");
		names.add("Pippin");
		names.add("Merry");
	}
	
	public void addGameColors(Model game) {
		ArrayList<CatanColor> colors = new ArrayList<CatanColor>();
			for (int i = 0; i < 4; i++)
			{	colors.add(null);
			}
			for (int i: game.getPlayerIndices())
			{
				colors.set(i, game.getPlayerColor(i));
			}
			gameColors.put(game.getID(), colors);
	}
		
	
	public String getName(int index) {
		String s = names.get(index);
		return s;
	}
	
	public void addToColorsUsed(int gameID, CatanColor c, int index) {
		gameColors.get(gameID).set(index, c);
	}
	
	public CatanColor getNextColor(int gameID) {
		for (CatanColor c: CatanColor.values()) {
			if (!gameColors.get(gameID).contains(c))
				return c;
		}
		return null;
	}
}
