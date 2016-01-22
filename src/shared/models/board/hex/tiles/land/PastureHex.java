package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexType;

public class PastureHex extends ProductionHex {

	public PastureHex(Integer productionNumber) throws BadProductionNumberException {
		super(productionNumber);
		hexType = HexType.SHEEP;
	}

}
