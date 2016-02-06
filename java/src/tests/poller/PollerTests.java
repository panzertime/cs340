package tests.poller;
import static org.junit.Assert.*;

import org.junit.Test;

public class PollerTests {

	@Test
	public void test() {
		fail("Not yet implemented");
		pass(){
			ModelFacade mFacade = null;
			ServerFacade sFacade = ServerFacade.get_instance();
				// constructed with FakeProxy by default

			ServerPoller SP = new ServerPoller(sFacade, mFacade);
			
	}

}
