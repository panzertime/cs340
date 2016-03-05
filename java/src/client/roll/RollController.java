package client.roll;

import java.time.LocalDateTime;
import java.util.Random;

import java.util.Timer;
import java.util.TimerTask;

import client.base.Controller;
import client.main.ClientPlayer;
import client.modelfacade.DoModelFacade;
import client.modelfacade.get.GetModelFacade;
import client.modelfacade.get.GetModelFacadeListener;


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
		timer.cancel();
		this.getRollView().closeModal();
		//Rolls two die
		Random rand = new Random();
		int n = 0;
	//	do
	//	{
		n = rand.nextInt(6) + 1;
		n += rand.nextInt(6) + 1;
		getResultView().setRollValue(n);
	//	} while (n == 7);
		
		DoModelFacade.sole().doRollDice(n);
		getResultView().showModal();
		
	}

	Timer timer;
	
	// override update: check that it's my turn, and that i'm rolling, then do rollDice
	@Override
	public void update() {
		if (GetModelFacade.sole().isStateRolling() && GetModelFacade.sole().isTurn(ClientPlayer.sole().getUserIndex()) && !this.getRollView().isModalShowing() && !this.getResultView().isModalShowing())
		{
			this.getRollView().showModal();
			timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            int i = 4;
	            public void run() {
	                i--;
	                if (i< 0)
	                {
	                    rollDice();
	                }
	            }
	        }, 0, 1000);
		

		}
		
		
	}

}

