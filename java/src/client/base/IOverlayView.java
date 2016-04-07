package client.base;

import javax.swing.JPanel;

/**
 * Base interface for overlay views
 */
public interface IOverlayView extends IView
{
	
	/**
	 * Displays the modal overlay view.
	 * @param originalPanel TODO
	 */
	void showModal(JPanel originalPanel);
	
	/**
	 * Closes the modal overlay view.
	 * @param overlayPanel TODO
	 */
	void closeModal(JPanel overlayPanel);
	
	/**
	 * Indicates whether or not the overlay is currently showing.
	 * 
	 * @return True if the overlay is showing, false otherwise
	 */
	boolean isModalShowing();
}

