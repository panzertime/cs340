package client.roll;

import java.util.Random;

import javax.swing.Timer;

import client.base.*;
import client.modelfacade.*;
import client.modelfacade.get.*;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, GetModelFacadeListener {

	private IRollResultView resultView;
	//private Timer diceTimer; 
	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		
		setResultView(resultView);

		GetModelFacade.registerListener(this);
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
		getResultView().setRollValue(n);


		
		doModelFacade.doRollDice(n);
		
		getResultView().showModal();
	}


	// override update: check that it's my turn, and that i'm rolling, then do rollDice
	@Override
	public void update() {
		if (GetModelFacade.sole().isStateRolling() && !this.getRollView().isModalShowing())
			this.getRollView().showModal();
	}
}

