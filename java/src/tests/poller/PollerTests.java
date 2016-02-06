package tests.poller;
import static org.junit.Assert.*;

import org.junit.Test;


import client.servercommunicator.*;
import shared.models.ModelFacade;

public class PollerTests {

	@Test
	public void test() {
		try {
			ModelFacade mFacade = null;
			ServerFacade sFacade = ServerFacade.get_instance();
				// constructed with FakeProxy by default
	
			ServerPoller SP = new ServerPoller(sFacade, mFacade);
			System.out.println("running poller...");
			SP.run();
			System.out.println("Going to sleep while poller runs...");
			Thread.sleep(5000);
			assertTrue(mFacade != null);
		}
		catch(Exception e){
			fail("Poller had an exception: " + e.toString());
		}
	}

}
