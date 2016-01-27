package shared.models.board.hex.tiles.land;

import shared.models.board.vertex.Vertex;
import shared.models.hand.ResourceException;

public abstract class ProductionHex extends LandHex{
	
	protected Integer productionNumber;
	
	public ProductionHex(Integer productionNumber) throws BadProductionNumberException {
		if (productionNumber == null)
			throw new BadProductionNumberException();
		if (productionNumber < 2 || productionNumber > 12)
			throw new BadProductionNumberException();
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
