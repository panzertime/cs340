package shared.models.board.piece;

import shared.models.Player;
import shared.models.hand.ResourceType;
import shared.models.hand.exceptions.ResourceException;

public class City extends Building{

	public City(Player owner) throws IllegalArgumentException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 * @throws ResourceException 
	 */
	@Override
	public void produce(ResourceType type) throws ResourceException {
		owner.receiveResource(type, 2);
	}

}
