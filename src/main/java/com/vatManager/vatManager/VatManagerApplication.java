package com.vatManager.vatManager;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.vatManager.vatManager.config.DbInitializer;

@SpringBootApplication
public class VatManagerApplication {

	public static void main(String[] args) throws IOException{
		
		String dbPath = DbInitializer.initializeDb("TDS-ClientManager");
		System.setProperty("DB_PATH", dbPath);
		
//		This is for the macos to show the coffee in Dock/tray.
		System.setProperty("apple.awt.UIElement", "true");
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(VatManagerApplication.class);
		// This is the "magic" line that allows the System Tray to work
	    builder.headless(false).run(args);
		
//		SpringApplication.run(VatManagerApplication.class, args);
		
		
		
		
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
