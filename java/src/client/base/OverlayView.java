package client.base;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Base class for overlay views
 */
@SuppressWarnings("serial")
public class OverlayView extends PanelView implements IOverlayView
{
	/**
	 * The frame window for the overlays
	 */
	private static JFrame window;
	
	/**
	 * Default glass pane component (displayed when no overlays are visible)
	 */
	private static Component defaultGlassPane;
	
	/**
	 * Stack of overlays that are currently displayed
	 */
	private static Deque<OverlayInfo> overlayStack;
	
	public static void setWindow(JFrame window)
	{
		OverlayView.window = window;
		defaultGlassPane = window.getGlassPane();
		overlayStack = new ArrayDeque<OverlayInfo>();
	}
	
	public OverlayView()
	{
		super();
	}
	
	/**
	 * Displays the overlay. The overlay is displayed on top of any other
	 * overlays that are already visible.
	 */
	public void showModal(JPanel originalPanel)
	{
		// Open the new overlay
		JPanel overlayPanel = new JPanel();
		overlayPanel.setLayout(new BorderLayout());
		overlayPanel.setOpaque(false);
		
		// Discard all mouse and keyboard events
		MouseAdapter mouseAdapter = new MouseAdapter() {};
		overlayPanel.addMouseListener(mouseAdapter);
		overlayPanel.addMouseMotionListener(mouseAdapter);
		overlayPanel.addMouseWheelListener(mouseAdapter);
		
		KeyAdapter keyAdapter = new KeyAdapter() {};
		overlayPanel.addKeyListener(keyAdapter);
		
		Dimension winSize = window.getContentPane().getSize();
		Dimension prefSize = this.getPreferredSize();
		
		int widthDiff = (int)(winSize.getWidth() - prefSize.getWidth());
		int heightDiff = (int)(winSize.getHeight() - prefSize.getHeight());
		
		overlayPanel.add(this, BorderLayout.CENTER);
		if(widthDiff / 2 > 0)
		{
			overlayPanel.add(Box.createRigidArea(new Dimension(widthDiff / 2, 0)),
							 BorderLayout.WEST);
			overlayPanel.add(Box.createRigidArea(new Dimension(widthDiff / 2, 0)),
							 BorderLayout.EAST);
		}
		if(heightDiff / 2 > 0)
		{
			overlayPanel.add(Box.createRigidArea(new Dimension(0,
															   heightDiff / 2)),
							 BorderLayout.NORTH);
			overlayPanel.add(Box.createRigidArea(new Dimension(0,
															   heightDiff / 2)),
							 BorderLayout.SOUTH);
		}
		
		if(overlayStack.size() > 0)
		{
			
			// Hide the currently-visible overlay
			overlayStack.peek().getOverlayPanel().setVisible(false);
		}
		
		window.setGlassPane(overlayPanel);
		overlayPanel.setVisible(true);
		overlayStack.push(new OverlayInfo(this, overlayPanel, originalPanel));
		System.out.println("Stack(" + overlayStack.size() +") gained: " + originalPanel.getClass().toGenericString());
	}
	
	/**
	 * Hides the top-most overlay
	 */
	public void closeModal(JPanel originalPanel)
	{
		
		//assert overlayStack.size() > 0;
		assert window.getGlassPane() == overlayStack.peek().getOverlayPanel();
		
		if(overlayStack.size() > 0)
		{
			Iterator<OverlayInfo> stackIt = overlayStack.iterator();
			System.out.println("Searching stack(" + overlayStack.size() +") for: " + originalPanel.getClass().toGenericString());
			while (stackIt.hasNext()) {
				OverlayInfo stackPanelInfo = stackIt.next();
				System.out.println(stackPanelInfo.getOriginalPanel().getClass().toGenericString());
				if (stackPanelInfo.getOriginalPanel() == originalPanel) {
					System.out.println("Found the Panel To Close!");
					overlayStack.remove(stackPanelInfo);
					stackPanelInfo.getOriginalPanel().setVisible(false);
					if(overlayStack.size() > 0)
					{
						window.setGlassPane(overlayStack.peek().getOverlayPanel());
						overlayStack.peek().getOverlayPanel().setVisible(true);
					}
					else
					{
						window.setGlassPane(defaultGlassPane);
						window.getGlassPane().setVisible(false);
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Is the overlay currently showing?
	 * 
	 * @return True if overlay is showing, false otherwise
	 */
	@Override
	public boolean isModalShowing()
	{
		
		for (OverlayInfo info : overlayStack)
		{
			
			if(info.getOverlayView() == this)
				return true;
		}
		
		return false;
	}
	
	private static class OverlayInfo
	{
		private OverlayView overlayView;
		private JPanel overlayPanel;
		private JPanel originalPanel;
		
		public OverlayInfo(OverlayView overlayView, JPanel overlayPanel, JPanel originalPanel)
		{
			setOverlayView(overlayView);
			setOverlayPanel(overlayPanel);
			setOriginalPanel(originalPanel);
		}
		
		public OverlayView getOverlayView()
		{
			
			return overlayView;
		}
		
		public void setOverlayView(OverlayView overlayView)
		{
			
			this.overlayView = overlayView;
		}
		
		public JPanel getOverlayPanel()
		{
			
			return overlayPanel;
		}
		
		public void setOverlayPanel(JPanel overlayPanel)
		{
			
			this.overlayPanel = overlayPanel;
		}
		public JPanel getOriginalPanel() {
			return originalPanel;
		}

		public void setOriginalPanel(JPanel originalPanel) {
			this.originalPanel = originalPanel;
		}
	}
	
}

