package shared.models.board.piece;

import shared.models.Player;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;

public class Settlement extends Building{

	public Settlement(Player owner) throws NullPlayerException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	@Override
	public void produce(ResourceType type) throws ResourceException {
		owner.giveResource(type, 1);
	}

}
