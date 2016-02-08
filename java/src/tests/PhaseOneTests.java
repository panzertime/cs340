package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				//"tests.poller.PollerTests",
				//"tests.proxy.ProxyTests",

				//"tests.model.ModelTests",
				//"tests.model.CanSendChatTests",
				//"tests.model.CanRollNumberTests",
				//"tests.model.CanPlaceRobberTests",
				//"tests.model.CanFinishTurnTests",
				//"tests.model.CanBuyDevCardTests",
				//"tests.model.CanUseYearOfPlentyTests",
				//"tests.model.CanUseRoadBuilderTests", //TODO
				//"tests.model.CanUseSoldierTests",	
				//"tests.model.CanUseMonopolyTests",
				//"tests.model.CanUseMonumentTests",
				//"tests.model.CanBuildRoadTests",
				//"tests.model.CanBuildSettlementTests",//TODO
				//"tests.model.CanBuildCityTests",
				//"tests.model.CanOfferTradeTests",
				//"tests.model.CanAcceptTradeTests",
				"tests.model.CanMaritimeTradeTests"/*,
				"tests.model.CanDiscardCardsTests"*/
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}

/*
Rest of test cases:

"tests.model.CanDiscardCardsTests"
1 – no model
2 – status is not Discarding
3 – you have less than 7 cards
4 – You don’t have the cards you choosing to discard
5 – working:
1.	initModel
2.	status = discarding
3.	cards = 7
4.	discard 1 - brick
*/