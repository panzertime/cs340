package server.persistance.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import server.persistance.IConnection;

public class MongoConnection implements IConnection {

	private DB db;
	
	public MongoConnection() throws UnknownHostException {
		db = new MongoClient("localhost").getDB("Catan");
		if (!db.collectionExists("Users"))
			db.createCollection("Users", null);
		if (!db.collectionExists("Games"))
			db.createCollection("Games", null);
		if (!db.collectionExists("Commands"))
			db.createCollection("Commands", null);
	}
	
	public DBCollection getUsersCollection() {
		return db.getCollection("Users");
	}
	
	public DBCollection getGamesCollection() {
		return db.getCollection("Games");
	}
	
	public DBCollection getCommandsCollection() {
		return db.getCollection("Commands");
	}
}
