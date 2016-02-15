package tests;
public class PhaseOneTest {
	
	public static void main(String[] args) {
		String[] tests = {
				// "poller.PollerTest",
				//"proxy.ProxyTest",

				"model.ModelTest",
				
				"model.can.CanSendChatTest",
				"model.can.CanBuyDevCardTest",
				
				"model.can.roll.CanRollNumberTest",
				"model.can.placerobber.CanPlaceRobberTest",
				"model.can.finishturn.CanFinishTurnTest",
				"model.can.discardcards.CanDiscardCardsTest",
				
				"model.can.trade.offer.CanOfferTradeTest",
				"model.can.trade.accept.CanAcceptTradeTest",
				"model.can.trade.maritime.CanMaritimeTradeTest",
				
				"model.can.use.yearofplenty.CanUseYearOfPlentyTest",
				"model.can.use.roadbuilding.CanUseRoadBuildingTest",
				"model.can.use.soldier.CanUseSoldierTest",
				"model.can.use.monopoly.CanUseMonopolyTest",
				"model.can.use.monument.CanUseMonumentTest",
				
				"model.can.build.road.CanBuildRoadTest",
				"model.can.build.settlement.CanBuildSettlementTest",
				"model.can.build.city.CanBuildCityTest"
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
