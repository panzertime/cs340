package client.roll;

import java.util.Random;

import javax.swing.*;
import java.awt.event.*;

import client.base.*;
import client.main.ClientPlayer;
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
		//Rolls two die
		Random rand = new Random();
		int  n = rand.nextInt(6) + 1;
		n += rand.nextInt(6) + 1;
		getResultView().setRollValue(n);

		System.out.println("ROLLING DICE: " + n);
		
		DoModelFacade.sole().doRollDice(n);
		this.getRollView().closeModal();

		getResultView().showModal();

	}


	// override update: check that it's my turn, and that i'm rolling, then do rollDice
	@Override
	public void update() {
		if (GetModelFacade.sole().isStateRolling() && GetModelFacade.sole().isTurn(ClientPlayer.sole().getUserIndex()) && !this.getRollView().isModalShowing() && !this.getResultView().isModalShowing())
			this.getRollView().showModal();
		
	}
}

