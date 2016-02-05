package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexLocation;
import shared.models.board.vertex.Vertex;
import shared.models.hand.exceptions.ResourceException;

public abstract class ProductionHex extends LandHex{
	
	protected Integer productionNumber;
	
	public ProductionHex(HexLocation hexLocation, Integer productionNumber) throws IllegalArgumentException {
		super(hexLocation);
		if (productionNumber == null)
			throw new IllegalArgumentException();
		if (productionNumber < 2 || productionNumber > 12)
			throw new IllegalArgumentException();
		this.productionNumber = productionNumber;
	}

	public void produce() {
		try {
			for (Vertex vert : verts)
				vert.produce(hexType.getResourceType());
		} catch (ResourceException e) {
			//TODO log "ResourceException at " print Stack trace
		}
	}
	
	/**
	 * @return the productionNumber
	 */
	public Integer getProductionNumber() {
		return productionNumber;
	}
}
