package tests;
public class PhaseOneTests {
	
	public static void main(String[] args) {
		String[] tests = {
				"tests.poller.PollerTests",
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
				//"tests.model.CanBuildSettlementTests",
				//"tests.model.CanBuildCityTests",
				"tests.model.CanOfferTradeTests"/*,
				"tests.model.CanAcceptTradeTests",
				"tests.model.CanMaritimeTradeTests",
				"tests.model.CanDiscardCardsTests"*/
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}

/*
Rest of test cases:


"tests.model.CanAcceptTradeTests"
1 – no model
2 – you have not been offered a domestic trade
3 – you don’t have the required resources
4 – working:
1.	initModel()
2.	tradeOffer – to player 0 
3.	wheat = 1


"tests.model.CanMaritimeTradeTests"
1 – no model
2 – it is not your turn
3 – model is not playing
4 – you don’t have the resources you are giving
5 – you don’t have port for a ratio < 4
6 – Working: ratio – 3 (You have the port)
1.	initModel()
2.	turn = 0
3.	Status = playing
4.	Brick = 1
5.	Settlement (0,2,NE)->(0,2,SE)

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