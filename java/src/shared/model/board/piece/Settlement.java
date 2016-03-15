package shared.model.board.piece;

import shared.model.Player;
import shared.model.hand.ResourceType;
import shared.model.hand.exceptions.NoRemainingResourceException;

public class Settlement extends Building{

	public Settlement(Player owner) throws IllegalArgumentException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 * @throws NoRemainingResourceException 
	 */
	@Override
	public void produce(ResourceType type) throws NoRemainingResourceException {
		owner.receiveResource(type, 1);
		owner.getGame().getBank().sendResource(type, 1);
	}

}
