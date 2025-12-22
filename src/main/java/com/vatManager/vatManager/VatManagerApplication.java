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
		

		String dbPath = DbInitializer.initializeDb("TDS-ClientManager");
		System.setProperty("DB_PATH", dbPath);
		
		
		
		
		
		//Automatically opens the angular front end stored in resources/static
//		openBrowser("http://localhost:8080");
	}
	

	
	//Automatically opens the angular front end stored in resources/static after the server is fully loaded.
	@EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup() {
		new Thread(() ->{
			try {
				Thread.sleep(4000);
		        openBrowser("http://localhost:8080");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}).start();
    }
	
//	 private static void openBrowser(String url) {
//	        try {
//	            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//	                Desktop.getDesktop().browse(new URI(url));
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }

	private static void openBrowser(String url) {
	    String os = System.getProperty("os.name").toLowerCase();
	    Runtime rt = Runtime.getRuntime();

	    try {
	        if (os.contains("win")) {
	            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
	        } else if (os.contains("mac")) {
	            rt.exec("open " + url);
	        } else if (os.contains("nix") || os.contains("nux")) {
	            // Linux variants
	            String[] browsers = {"xdg-open", "google-chrome", "firefox"};
	            String browser = null;
	            for (String b : browsers) {
	                if (Runtime.getRuntime().exec(new String[]{"which", b}).getInputStream().read() != -1) {
	                    browser = b;
	                    break;
	                }
	            }
	            if (browser != null) {
	                rt.exec(new String[]{browser, url});
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	
}
