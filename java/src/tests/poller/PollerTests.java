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
				// constructed with FakeProxy by default
	
			ServerPoller SP = new ServerPoller(sFacade, mFacade);
			SP.start();
			Thread.sleep(5000);
			assertTrue(modelFacade.getModel() != null);
		}
		catch(Exception e){
			fail("Poller had an exception: " + e.toString());
		}
	}

}
