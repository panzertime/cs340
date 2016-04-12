package server.persistance.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import server.persistance.DatabaseException;
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
	
	@Override
	public void safeClose() {
		// TODO Auto-generated method stub
		// SEE startTransaction for more info
		
	}

	@Override
	public void commit() throws DatabaseException {
		// TODO Auto-generated method stub
		// SEE startTransaction for more info
		
	}

	@Override
	public void rollback() throws DatabaseException {
		// TODO Auto-generated method stub
		// SEE startTransaction for more info
		
	}

	@Override
	public void startTransaction() throws DatabaseException {
		// TODO Auto-generated method stub
		// Atomic transactions across collections in mongo are extraordinarily difficult because of
		// how phase four has asked us to solve the persistance problem
		// within Monog you would normally use TWO PHASE COMMITS
		// that particular method requires knowledge about the particular transaction
		
		// see https://docs.mongodb.org/manual/tutorial/perform-two-phase-commits/ 
		
		// this from of atomic changes (transactions) is a very SQL based concept
		// this general approach of SQL transactions inherently disagrees with the Mongo design
		// I honnestly would not know how to implement a resonable dependany injection pattern that involved both SQL and Mongo
		// that also allowed for the kind of multi document atomic transaction we do with MoveCommands and Models
		
	}
}
