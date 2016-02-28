package client.roll;

import java.util.Random;

import client.base.*;
import client.modelfacade.DoModelFacade;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		DoModelFacade doModelFacade = DoModelFacade.sole();
		//Rolls two die
		Random rand = new Random();
		int  n = rand.nextInt(6) + 1;
		n += rand.nextInt(6) + 1;
		this.getResultView().setRollValue(n);

		doModelFacade.doRollDice(n);
		
		getResultView().showModal();
	}

}

