package shared.models.board.piece;

import shared.models.Player;
import shared.models.hand.ResourceException;
import shared.models.hand.ResourceType;

public class City extends Building{

	public City(Player owner) throws NullPlayerException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	@Override
	public void produce(ResourceType type) throws ResourceException {
		owner.giveResource(type, 2);
	}

}
