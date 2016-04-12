package server.persistance.files;

import java.io.File;
import java.io.FilenameFilter;

import server.persistance.DatabaseException;
import server.persistance.IConnection;

public class FileConnection implements IConnection {

	@Override
	public void safeClose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void commit() throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startTransaction() throws DatabaseException {
		// TODO Auto-generated method stub

	}
	
	public static void clearAllFiles() {
		File dir = new File("java/data/command/");

    	File[] files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );   	
    	if (files!= null) for (File f: files) {
    		f.delete();
    	}
		dir = new File("java/data/game/");

    	files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );   	
    	if (files!= null) for (File f: files) {
    		f.delete();
    	}
		dir = new File("java/data/user/");

    	files = dir.listFiles(new FilenameFilter() { 
    	         public boolean accept(File dir, String filename)
    	              { return filename.endsWith(".txt"); }
    	} );   	
    	if (files!= null) for (File f: files) {
    		f.delete();
    	}
	}

}
