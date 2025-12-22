package com.vatManager.vatManager.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DbInitializer {

	
	
	public static String initializeDb(String appName) throws IOException{
		
		String userHome = System.getProperty("user.home");
		String os = System.getProperty("os.name").toLowerCase();
		
		String dbFolder;
		if(os.contains("win")) {
			dbFolder = System.getenv("LOCALAPPDATA") + "/" + appName;	
		} else if(os.contains("mac")) {
			dbFolder = userHome + "/Library/Application Support/" + appName;
		} else { //for linux
			dbFolder = userHome + "/." + appName;
		}
	
		//it will create a directory of the DbFolder as per the OS.
		//    " userHome(os-wise)/TDS-ClientManager/ "
		Files.createDirectories(Paths.get(dbFolder));
		
		// " userHome/TDS-ClientManager/db.sqlite "
		String dbPath = dbFolder + "/database.sqlite";
		File dbFile = new File(dbPath);
		
		if(!dbFile.exists()) {
			try(InputStream is = DbInitializer.class.getResourceAsStream("/database.sqlite")) {
				Files.copy(is, Paths.get(dbPath), StandardCopyOption.REPLACE_EXISTING);
			}
		}//ends-if

		return dbPath;
	}//ends method
	
	
	
}//ends class.
