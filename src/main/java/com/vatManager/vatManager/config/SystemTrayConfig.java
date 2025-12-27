package com.vatManager.vatManager.config;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SystemTrayConfig {

	
	@PostConstruct
	public void init() {
		EventQueue.invokeLater(this::setupSystemTray);
	}
	
	private void setupSystemTray() {
		if(!SystemTray.isSupported()) {
			System.out.println("System Tray is not Supported");
			return;
		}
		
		try {
            SystemTray tray = SystemTray.getSystemTray();

            // Use getResourceAsStream + ImageIO for better JAR compatibility
            InputStream is = getClass().getResourceAsStream("/static/TDSLogo.png");
            if (is == null) {
                System.err.println("Could not find tray icon at /static/TDSLogo.png");
                return;
            }
            Image image = ImageIO.read(is);

            // Create the Menu
            PopupMenu menu = new PopupMenu();
            
            // Add an 'Open Dashboard' option (Optional but helpful)
            MenuItem openItem = new MenuItem("Open Dashboard");
            openItem.addActionListener(e -> openBrowser("http://localhost:8080"));
            menu.add(openItem);
            
            menu.addSeparator();

            // Exit Option
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(e -> System.exit(0));
            menu.add(exitItem);

            // Create and Add the TrayIcon
            TrayIcon trayIcon = new TrayIcon(image, "TDS Manager", menu);
            trayIcon.setImageAutoSize(true);
            
            // Optional: Double-click icon to open dashboard
            trayIcon.addActionListener(e -> openBrowser("http://localhost:8080"));

            tray.add(trayIcon);
            
        } catch (Exception e) {
            System.err.println("Error setting up System Tray: " + e.getMessage());
            e.printStackTrace();
        }
		
//		SystemTray tray = SystemTray.getSystemTray();
//		
//		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/static/TDSLogo.png"));
//		
//		PopupMenu menu = new PopupMenu();
//		MenuItem exitItem = new MenuItem("Exit");
//		exitItem.addActionListener(e -> System.exit(0));
//		menu.add(exitItem);
//		
//		TrayIcon trayIcon = new TrayIcon(image, "TDS Manager", menu);
//		trayIcon.setImageAutoSize(true);
//		
//		try {
//			tray.add(trayIcon);
//		} catch (AWTException e) {
//			e.printStackTrace();
//		}//ends try-catch.
	}//ends method.
	
	
	
	private void openBrowser(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
}//ends class
