package tests.poller;
import static org.junit.Assert.*;

import org.junit.Test;


import client.servercommunicator.*;
import shared.models.ModelFacade;

public class PollerTests {

	@Test
	public void test() {
		try {
			ModelFacade mFacade = new ModelFacade();
			ServerFacade sFacade = ServerFacade.get_instance();
			FakeProxy fp = new FakeProxy();
			fp.setURL("java/src/tests/poller/");
			sFacade.setProxy(fp);
			
			ServerPoller SP = new ServerPoller(sFacade, mFacade);
			SP.start();
			Thread.sleep(5000);
			if(mFacade.getGameModel() == null){
				fail("Poller failed poll test");
			}
			System.out.println("Poller passed poll test");
		}
		catch(Exception e){
			fail("Poller had an exception: " + e.toString());
		}
	}

}
