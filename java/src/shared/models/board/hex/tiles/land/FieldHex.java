package shared.models.board.hex.tiles.land;

import shared.models.board.hex.HexType;

public class FieldHex extends ProductionHex {

	public FieldHex(Integer productionNumber) throws BadProductionNumberException {
		super(productionNumber);
		hexType = HexType.WHEAT;
	}

}
