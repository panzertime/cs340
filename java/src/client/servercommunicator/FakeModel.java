package client.servercommunicator;

public class FakeModel {

private String content;

public FakeModel() {

	content = "{ \"deck\": { \"yearOfPlenty\": 2, \"monopoly\": 2, \"soldier\": 14,    \"roadBuilding\": 2,   \"monument\": 5  },  \"map\": {    \"hexes\": [     {        \"resource\": \"sheep\",        \"location\": {          \"x\": 0,          \"y\": -2        },        \"number\": 9      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": 1,          \"y\": -2        },        \"number\": 2      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 2,          \"y\": -2        },        \"number\": 4      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": -1,          \"y\": -1        },        \"number\": 11      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": 0,          \"y\": -1},\"number\": 5      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": 1,          \"y\": -1        },        \"number\": 4      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": 2,          \"y\": -1        },        \"number\": 5      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": -2,          \"y\": 0		},        \"number	\": 6      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": -1,          \"y\": 0        },        \"number\": 3      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": 0,          \"y\": 0        },        \"number\": 10      },      {        \"resource\": \"brick\",        \"location\": {          \"x\": 1,          \"y\": 0        },        \"number\": 8      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 2,          \"y\": 0        },        \"number\": 3      },      {        \"location\": {          \"x\": -2,          \"y\": 1        }      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": -1,          \"y\": 1        },        \"number\": 6      },      {        \"resource\": \"wood\",        \"location\": {          \"x\": 0,          \"y\": 1        },        \"number\": 11      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": 1,          \"y\": 1        },        \"number\": 12      },      {        \"resource\": \"ore\",        \"location\": {          \"x\": -2,          \"y\": 2        },        \"number\": 9      },      {        \"resource\": \"sheep\",        \"location\": {          \"x\": -1,          \"y\": 2        },        \"number\": 10      },      {        \"resource\": \"wheat\",        \"location\": {          \"x\": 0,          \"y\": 2        },        \"number\": 8      }    ],    \"roads\": [      {        \"owner\": 1,        \"location\": {          \"direction\": \"S\",          \"x\": 1,          \"y\": 0        }      }    ],    \"cities\": [],    \"settlements\": [],    \"radius\": 3,    \"ports\": [      {        \"ratio\": 3,        \"direction\": \"S\",        \"location\": {          \"x\": 1,          \"y\": -3        }      },      {        \"ratio\": 2,        \"resource\": \"sheep\",        \"direction\": \"SW\",        \"location\": {          \"x\": 3,          \"y\": -3        }      },      {        \"ratio\": 2,        \"resource\": \"wheat\",        \"direction\": \"NE\",        \"location\": {          \"x\": -3,          \"y\": 2        }      },      {        \"ratio\": 3,        \"direction\": \"SE\",        \"location\": {          \"x\": -3,          \"y\": 0       }      },      {        \"ratio\": 2,        \"resource\": \"brick\",        \"direction\": \"NW\",        \"location\": {          \"x\": 2,          \"y\": 1        }      },      {        \"ratio\": 3,        \"direction\": \"NE\",        \"location\": {          \"x\": -2,          \"y\": 3        }      },      {        \"ratio\": 2,        \"resource\": \"wood\",        \"direction\": \"NW\",        \"location\": {          \"x\": 3,          \"y\": -1        }      },      {        \"ratio\": 3,        \"direction\": \"N\",        \"location\": {          \"x\": 0,          \"y\": 3        }      },      {        \"ratio\": 2,        \"resource\": \"ore\",        \"direction\": \"S\",        \"location\": {          \"x\": -1,          \"y\": -2        }      }    ],    \"robber\": {      \"x\": -2,      \"y\": 1   }  },  \"players\": [    {      \"resources\": {        \"brick\": 0,        \"wood\": 0,        \"sheep\": 0,        \"wheat\": 0,        \"ore\": 0      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 15,      \"cities\": 4,      \"settlements\": 5,      \"soldiers\": 0,      \"victoryPoints\": 0,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": 0,      \"playerIndex\": 0,      \"name\": \"Sam\",      \"color\": \"puce\"    },    {      \"resources\": {        \"brick\": 0,        \"wood\": 0,        \"sheep\": 0,        \"wheat\": 0,        \"ore\": 0      },      \"oldDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"newDevCards\": {        \"yearOfPlenty\": 0,        \"monopoly\": 0,        \"soldier\": 0,        \"roadBuilding\": 0,        \"monument\": 0      },      \"roads\": 14,      \"cities\": 4,      \"settlements\": 5,      \"soldiers\": 0,      \"victoryPoints\": 0,      \"monuments\": 0,      \"playedDevCard\": false,      \"discarded\": false,      \"playerID\": -2,      \"playerIndex\": 1,      \"name\": \"Steve\",      \"color\": \"yellow\"    },    null,    null  ],  \"log\": {    \"lines\": [      {        \"source\": \"Steve\",        \"message\": \"Steve built a road\"      }    ]  },  \"chat\": {    \"lines\": []  },  \"bank\": {    \"brick\": 24,    \"wood\": 24,    \"sheep\": 24,    \"wheat\": 24,    \"ore\": 24  },  \"turnTracker\": {    \"status\": \"FirstRound\",    \"currentTurn\": 0,    \"longestRoad\": -1,    \"largestArmy\": -1  },  \"winner\": -1, \"version\": 1}";

}

@Override
public String toString(){
	return content;
}


}
