package shared.models.hand.development;

public abstract class DevCard {

	protected DevCardType type;
	private boolean enabled;
	
	/**
	 * 
	 * @return type of Dev Card
	 */
	public DevCardType getType() {
		return type;
	}
	
	/**
	 * @return if card can be used
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled - if card has been held one turn
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
}
