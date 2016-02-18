package poller;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import client.modelfacade.CanModelFacade;
import client.modelfacade.testing.TestingModelFacade;
import client.serverfacade.FakeProxy;
import client.serverfacade.ServerFacade;
import client.serverfacade.ServerPoller;

public class PollerTest {
	
	@Before
	public void initFacades() {
		CanModelFacade.sole().setUserID(0);
		TestingModelFacade.sole().setUserID(0);
	}

	@Test
	public void test() {
		try {
			ServerFacade sFacade = ServerFacade.get_instance();
			FakeProxy fp = new FakeProxy();
			fp.setURL("java/test/poller/");
			sFacade.setProxy(fp);
			
			ServerPoller SP = new ServerPoller(sFacade);
			SP.start();
			Thread.sleep(5000);
			if(!TestingModelFacade.sole().hasModel()){
				fail("Poller failed poll test");
			}
			System.out.println("Poller passed poll test");
		}
		catch(Exception e){
			fail("Poller had an exception: " + e.toString());
		}
	}

}
