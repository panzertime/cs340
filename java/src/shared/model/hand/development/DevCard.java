package shared.model.hand.development;

public abstract class DevCard {

	protected DevCardType type;
	private boolean enabled = false; //Enabled means it has sat in hand one turn
	
	/**
	 * 
	 * @return type of Dev Card
	 */
	public DevCardType getType() {
		return type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DevCard))
			return false;
		DevCard other = (DevCard) obj;
		if (enabled != other.enabled)
			return false;
		if (type != other.type)
			return false;
		return true;
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
