package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				"tests.poller.PollerTests",				//RT
				"tests.proxy.ProxyTests",				//RT

				"tests.model.ModelTests",				//Jacob
				"tests.model.CanSendChatTests",			//Josh
				"tests.model.CanRollNumberTests",		//Josh
				"tests.model.CanPlaceRobberTests",		//Jacob
				"tests.model.CanFinishTurnTests",		//Josh
				"tests.model.CanBuyDevCardTests",		//JR
				"tests.model.CanUseYearOfPlentyTests",	//JR
				"tests.model.CanUseRoadBuilderTests",	//Jacob
				"tests.model.CanUseSoldierTests",		//Jacob
				"tests.model.CanUseMonopolyTests",		//JR
				"tests.model.CanUseMonumentTests",		//JR
				"tests.model.CanBuildRoadTests",		//Jacob
				"tests.model.CanBuildSettlementTests",	//Jacob
				"tests.model.CanBuildCityTests",		//Jacob
				"tests.model.CanOfferTradeTests",		//JR
				"tests.model.CanAcceptTradeTests",		//JR
				"tests.model.CanMaritimeTradeTests",	//Jacob
				"tests.model.CanDiscardCardsTests"
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
