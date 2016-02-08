package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				"tests.poller.PollerTests",				//RT
				"tests.proxy.ProxyTests",				//RT

				"tests.model.ModelTests",				//Jacob
				"tests.model.CanSendChatTests",
				"tests.model.CanRollNumberTests",
				"tests.model.CanPlaceRobberTests",		//Jacob
				"tests.model.CanFinishTurnTests",
				"tests.model.CanBuyDevCardTests",
				"tests.model.CanUseYearOfPlentyTests",
				"tests.model.CanUseRoadBuilderTests",	//Jacob
				"tests.model.CanUseSoldierTests",		//Jacob
				"tests.model.CanUseMonopolyTests",
				"tests.model.CanUseMonumentTests",
				"tests.model.CanBuildRoadTests",		//Jacob
				"tests.model.CanBuildSettlementTests",	//Jacob
				"tests.model.CanBuildCityTests",		//Jacob
				"tests.model.CanOfferTradeTests",
				"tests.model.CanAcceptTradeTests",
				"tests.model.CanMaritimeTradeTests",	//Jacob
				"tests.model.CanDiscardCardsTests"
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
