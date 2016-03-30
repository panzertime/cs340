package test;
public class Test {
	
	public static void main(String[] args) {
		
		
		String[] tests = {
				
			// Phase One tests
				//"poller.PollerTest",
				
				//"proxy.GamesTest",
				//"proxy.GameTest",
				//"proxy.LoginTest",

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
				"model.can.build.city.CanBuildCityTest",
				
			// Phase Three tests
				"model.execute.send.DoSendChatTest",
				//"model.execute.DoBuyDevCardTest",
				
				"model.execute.roll.DoRollNumberTest",
				/*"model.execute.placerobber.DoPlaceRobberTest",
				"model.execute.finishturn.DoFinishTurnTest",*/
				"model.execute.discardcards.DoDiscardCardsTest",
				
				//"model.execute.trade.offer.DoOfferTradeTest",
				"model.execute.trade.accept.DoAcceptTradeTest",
				/*"model.execute.trade.maritime.DoMaritimeTradeTest",
				
				"model.execute.use.yearofplenty.DoUseYearOfPlentyTest",
				"model.execute.use.roadbuilding.DoUseRoadBuildingTest",
				"model.execute.use.soldier.DoUseSoldierTest",
				"model.execute.use.monopoly.DoUseMonopolyTest",
				"model.execute.use.monument.DoUseMonumentTest",*/
				
				"model.execute.build.road.DoBuildRoadTest",
				"model.execute.build.settlement.DoBuildSettlementTest",
				"model.execute.build.city.DoBuildCityTest",
				
				// server tests
				"server.command.games.createTest",
				"server.command.games.joinTest",
				"server.command.games.listTest",
				
				"server.command.game.addAITest",
				"server.command.game.listAITest",
				"server.command.game.modelTest",

				"server.command.moves.build.city.buildCityTest",
				"server.command.moves.build.road.buildRoadTest",
				"server.command.moves.build.settlement.buildSettlementTest",
				
				"server.command.moves.accepttrade.acceptTradeTest",
				"server.command.moves.buydevcard.buyDevCardTest",
				"server.command.moves.discard.discardCardsTest",
				"server.command.moves.maritimetrade.maritimeTradeTest",
				"server.command.moves.monopoly.MonopolyTest",
				"server.command.moves.monument.MonumentTest",
				"server.command.moves.roadbuilding.RoadBuildingTest",
				"server.command.moves.robplayer.robPlayerTest",
				"server.command.moves.roll.rollNumberTest",
				"server.command.moves.soldier.SoldierTest",
				"server.command.moves.yearofplenty.YearOfPlentyTest",
				"server.command.moves.offerTradeTest",
				"server.command.moves.sendChatTest",
				"server.command.moves.finishTurnTest",
				
				"server.command.user.loginTest",
				"server.command.user.registerTest",
				
				
				// model tests
				"server.model.ToJSONTest",
				
				// server kernal tests
				//"server.kernel.ServerKernelTests",
				"server.utils.CatanCookieTest",
				
				//mock commands
				"server.command.mock.game.addAITest"
				
				
				
		};
		
		org.junit.runner.JUnitCore.main(tests);
	}
}
