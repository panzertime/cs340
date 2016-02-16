package poller;
import static org.junit.Assert.fail;

import org.junit.Test;

import client.modelfacade.ModelFacade;
import client.serverfacade.FakeProxy;
import client.serverfacade.ServerFacade;
import client.serverfacade.ServerPoller;

public class PollerTest {

	@Test
	public void test() {
		try {
			ModelFacade mFacade = new ModelFacade();
			ServerFacade sFacade = ServerFacade.get_instance();
			FakeProxy fp = new FakeProxy();
			fp.setURL("java/test/poller/");
			sFacade.setProxy(fp);
			
			ServerPoller SP = new ServerPoller(sFacade, mFacade);
			SP.start();
			Thread.sleep(5000);
			if(!mFacade.hasModel()){
				fail("Poller failed poll test");
			}
			System.out.println("Poller passed poll test");
		}
		catch(Exception e){
			fail("Poller had an exception: " + e.toString());
		}
	}

}
