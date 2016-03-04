package client.points;

import client.base.*;
import client.main.Catan;
import client.main.ClientPlayer;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, GetModelFacadeListener {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		GetModelFacade.registerListener(this);
		//initFromModel();
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}

	private void initFromModel() {
		getPointsView().setPoints(0);
	}
	
	@Override
	public void update()
	{
		GetModelFacade getModelFacade = GetModelFacade.sole();
		getPointsView().setPoints(getModelFacade.getPoints(ClientPlayer.sole().getUserIndex()));
		if (getModelFacade.isGameOver())
		{
			getFinishedView().setWinner(getModelFacade.getWinnerName(), getModelFacade.isClientWinner());
			if (!this.getFinishedView().isModalShowing())
				{
					this.getFinishedView().showModal();
				}
		}

	}
}

