package server.command.games;

import java.util.ArrayList;

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
	ArrayList<CatanColor> colorsUsed;
	public AISelector() {
		names = new ArrayList<String>();
		colorsUsed = new ArrayList<CatanColor>();
		names.add("Frodo");
		names.add("Pippin");
		names.add("Merry");
	}
	
	public String getNextName() {
		String s = names.remove(0);
		return s;
	}
	
	public void addToColorsUsed(CatanColor c) {
		colorsUsed.add(c);
	}
	
	public CatanColor getNextColor() {
		for (CatanColor c: CatanColor.values()) {
			if (!colorsUsed.contains(c))
				return c;
		}
		return null;
	}
}
