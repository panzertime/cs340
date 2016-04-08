package server.data;

import shared.model.Model;

public class AI extends User {

	int index;
	Model game;
	
	public AI() {
		
	}
	public AI(Model game) {
		this.game = game;
	}
	
	public void setIndex() {}
	
	public int getIndex() { return index; }
	
	//added as Model listener
	//notified when Model initiated or version updated
	public void play() {
		if (game.isStateDiscarding()) {
			
			
		}
		else if (game.isTurn(index)) {
			if (game.isStateRolling()){
				
			}
			else if (game.isStateRobbing()) {
				
			}
			else if (game.isStatePlaying()) {
				
			}
		}
		
		
	}
}
