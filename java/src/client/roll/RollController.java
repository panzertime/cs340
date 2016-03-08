package client.roll;

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
		if(this.getRollView() != null && this.getRollView().isModalShowing()){	
		this.getRollView().closeModal();
		}
		timer.purge();
		timer.cancel();
		if(hasRolled){
			return;
		}
		hasRolled = true;
		//Rolls two die
		Random rand = new Random();
		int n = 0;
	//	do
	//	{
		n = rand.nextInt(6) + 1;
		n += rand.nextInt(6) + 1;
		getResultView().setRollValue(7);
	//	} while (n == 7);
		DoModelFacade.sole().doRollDice(7);
		getResultView().showModal();
		
	}

	boolean hasRolled = false;
	//LocalDateTime start;
	//LocalDateTime end;
	Timer timer;
	
	// override update: check that it's my turn, and that i'm rolling, then do rollDice
	@Override
	public void update() {
		if (GetModelFacade.sole().isStateRolling() && GetModelFacade.sole().isTurn(ClientPlayer.sole().getUserIndex()) && !this.getRollView().isModalShowing() && !this.getResultView().isModalShowing())
		{
			hasRolled = false;
			this.getRollView().showModal();
			//start = LocalDateTime.now();
			//end = start.plusSeconds(4);
			
			
	        timer = new Timer();
	        timer.schedule(new TimerTask() {
	           // int i = 4;
	            public void run() {
	             //   System.out.println(i--);
	               // if (i == 0)
	                //{
	                    	rollDice();
			//	i = 4;
				this.cancel();
				timer.cancel();
			 
	               // }
	            }
	        }, (long) 4000);
		

		}
		
		
	}

}


