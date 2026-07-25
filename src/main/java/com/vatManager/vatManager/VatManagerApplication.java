package com.vatManager.vatManager;

import java.io.IOException;
import java.net.ServerSocket; // 1. Added missing import

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

import com.vatManager.vatManager.config.DbInitializer;

@SpringBootApplication
public class VatManagerApplication {

    public static void main(String[] args) throws IOException {

        int port = 8080; // your app port
        
        // 1. Port Guard Check
        try (ServerSocket socket = new ServerSocket(port)) {
            // Port is free, proceed with startup
        } catch (Exception e) {
            System.err.println("vatManager is already running on port " + port + ". Exiting.");
            System.exit(1);
        }

        // 2. DB & System Properties Setup
        String dbPath = DbInitializer.initializeDb("TDS-ClientManager");
        System.setProperty("DB_PATH", dbPath);
        
        // macOS Dock/tray setting
        System.setProperty("apple.awt.UIElement", "true");
        
        // 3. Start Spring Application & capture Context
        ConfigurableApplicationContext context = new SpringApplicationBuilder(VatManagerApplication.class)
                .headless(false)
                .run(args);
        
        // 4. Correct Shutdown Hook syntax
        Runtime.getRuntime().addShutdownHook(new Thread(context::close));
    }

    // Automatically opens the browser once the server is 100% up
    @EventListener(ApplicationReadyEvent.class)
    public void openBrowserAfterStartup() {
        // ApplicationReadyEvent fires ONLY when the server is fully initialized,
        // so you don't even need Thread.sleep(4000)!
        openBrowser("http://localhost:8080");
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
                String[] browsers = {"xdg-open", "google-chrome", "firefox"};
                String browser = null;
                for (String b : browsers) {
                    if (rt.exec(new String[]{"which", b}).getInputStream().read() != -1) {
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
