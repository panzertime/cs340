package client.map.pseudo;

import shared.model.board.edge.EdgeDirection;
import shared.model.board.edge.EdgeLocation;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;
import shared.model.board.hex.tiles.water.PortType;

public class PseudoHex {
	
	public PseudoHex(HexType hexType, HexLocation hexLoc, Integer productionNum, EdgeDirection portLocation, PortType portType) {
		this.hexType = hexType;
		this.hexLoc = hexLoc;
		this.productionNum = productionNum;
		this.portLocation = new EdgeLocation(hexLoc, portLocation);
		this.portType = portType;
	}
	
	private HexType hexType;
	private HexLocation hexLoc;
	private Integer productionNum;
	private EdgeLocation portLocation;
	private PortType portType;

	/**
	 * @return the hexType
	 */
	public HexType getHexType() {
		return hexType;
	}
	/**
	 * @return the hexLoc
	 */
	public HexLocation getHexLoc() {
		return hexLoc;
	}
	/**
	 * @return the productionNum
	 */
	public Integer getProductionNum() {
		return productionNum;
	}
	/**
	 * @return the portLocation
	 */
	public EdgeLocation getPortLocation() {
		return portLocation;
	}
	/**
	 * @return the portType
	 */
	public PortType getPortType() {
		return portType;
	}
}
