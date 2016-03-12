package shared.model.board.piece;

import shared.model.Player;
import shared.model.hand.ResourceType;

public class City extends Building{

	public City(Player owner) throws IllegalArgumentException {
		super(owner);
	}

	
	/**
	 * @param type the resource to generate
	 */
	@Override
	public void produce(ResourceType type) {
		owner.receiveResource(type, 2);
	}

}
