package server.persistance;

public interface IConnection {

	void safeClose();

	void commit() throws DatabaseException;

	void rollback() throws DatabaseException;

	void startTransaction() throws DatabaseException;

}
