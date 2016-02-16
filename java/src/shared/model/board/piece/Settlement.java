package shared.model.board.piece;

import shared.model.Player;
import shared.model.hand.ResourceType;

public class Settlement extends Building{

	public Settlement(Player owner) throws IllegalArgumentException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	@Override
	public void produce(ResourceType type) {
		owner.receiveResource(type, 1);
	}

}
