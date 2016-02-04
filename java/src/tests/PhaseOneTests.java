package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				"tests.PollerTests",
				"tests.ModelTests",
				"tests.ProxyTests"
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
