package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				//"tests.poller.PollerTests",				//RT
				//"tests.proxy.ProxyTests",				//RT

				"tests.model.ModelTests",
				"tests.model.CanSendChatTests",
				"tests.model.CanRollNumberTests",
				"tests.model.CanPlaceRobberTests",
				"tests.model.CanFinishTurnTests",
				"tests.model.CanBuyDevCardTests",
				"tests.model.CanUseYearOfPlentyTests",
				"tests.model.CanUseRoadBuilderTests",	//Jacob
				"tests.model.CanUseSoldierTests",
				"tests.model.CanUseMonopolyTests",
				"tests.model.CanUseMonumentTests",
				"tests.model.CanBuildRoadTests",
				"tests.model.CanBuildSettlementTests",
				"tests.model.CanBuildCityTests",
				"tests.model.CanOfferTradeTests",
				"tests.model.CanAcceptTradeTests",
				"tests.model.CanMaritimeTradeTests",
				"tests.model.CanDiscardCardsTests"
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
