package client.map.pseudo;

import shared.model.board.edge.EdgeDirection;
import shared.model.board.hex.HexLocation;
import shared.model.board.hex.HexType;
import shared.model.board.hex.tiles.water.PortType;

public class PseudoHex {
	
	public PseudoHex(HexType hexType, HexLocation hexLoc, Integer productionNum, EdgeDirection portDirection, PortType portType) {
		this.hexType = hexType;
		this.hexLoc = hexLoc;
		this.productionNum = productionNum;
		this.portDirection = portDirection;
		this.portType = portType;
	}
	
	private HexType hexType;
	private HexLocation hexLoc;
	private Integer productionNum;
	private EdgeDirection portDirection;
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
	 * @return the portDirection
	 */
	public EdgeDirection getPortDirection() {
		return portDirection;
	}
	/**
	 * @return the portType
	 */
	public PortType getPortType() {
		return portType;
	}
}
