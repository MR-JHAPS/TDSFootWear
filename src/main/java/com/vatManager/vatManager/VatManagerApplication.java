package com.vatManager.vatManager;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.vatManager.vatManager.config.DbInitializer;

@SpringBootApplication
public class VatManagerApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(VatManagerApplication.class, args);
		
//		String dbPath = DbInitializer.initializeDb("TDS-ClientManager");
		

		DbInitializer.initializeDb("TDS-ClientManager");
		
		
		
		
		//Automatically opens the angular front end stored in resources/static
//		openBrowser("http://localhost:8080");
	}
	

	
	//Automatically opens the angular front end stored in resources/static after the server is fully loaded.
	@EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup() {
        openBrowser("http://localhost:8080");
    }
	
	 private static void openBrowser(String url) {
	        try {
	            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
	                Desktop.getDesktop().browse(new URI(url));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
