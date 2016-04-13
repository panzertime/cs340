package server.command.game;

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
		for (int i = 0; i < 4; i ++)
			colorsUsed.add(null);
		names.add("Samwise");
		names.add("Frodo");
		names.add("Pippin");
		names.add("Merry");
	}
	
	public String getName(int index) {
		String s = names.get(index);
		return s;
	}
	
	public void addToColorsUsed(CatanColor c, int index) {
		colorsUsed.set(index, c);
	}
	
	public CatanColor getNextColor() {
		for (CatanColor c: CatanColor.values()) {
			if (!colorsUsed.contains(c))
				return c;
		}
		return null;
	}
}
