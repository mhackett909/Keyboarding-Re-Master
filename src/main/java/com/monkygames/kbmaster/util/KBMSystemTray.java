/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javafx.application.Platform;
import javax.swing.ImageIcon;

/**
 * [about]
 * @version 1.0
 */
public class KBMSystemTray implements ActionListener{

// ============= Class variables ============== //
    private PopupMenu popupMenu;
    private TrayIcon trayIcon;
    private SystemTray tray;
    private MenuItem aboutItem;
    private MenuItem displayItem;
    private MenuItem exitItem;
    private static final String iconPath = 
    "/com/monkygames/kbmaster/fxml/resources/systray.png";
    private DeviceMenuUIController controller;
// ============= Constructors ============== //
    public KBMSystemTray(DeviceMenuUIController controller){
	this.controller = controller;
	if (!SystemTray.isSupported()) {
	    System.out.println("SystemTray is not supported");
	    return;
	}
	popupMenu = new PopupMenu();
        trayIcon = new TrayIcon(createImage(iconPath, "tray icon"));
	tray = SystemTray.getSystemTray();

        // Create a popupMenu menu components
        aboutItem = new MenuItem("About");
	displayItem = new MenuItem("Show");
        exitItem = new MenuItem("Exit");

	aboutItem.addActionListener(this);
	displayItem.addActionListener(this);
	exitItem.addActionListener(this);
       
        //Add components to popupMenu menu
        popupMenu.add(aboutItem);
	popupMenu.add(displayItem);
	popupMenu.addSeparator();
        popupMenu.add(exitItem);
       
        trayIcon.setPopupMenu(popupMenu);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private Image createImage(String path, String description) {
        URL imageURL = KBMSystemTray.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
	    ImageIcon imageIcon = new ImageIcon(imageURL,description);
            return imageIcon.getImage();
        }
    }
    public void showAboutPopup(){
	// need to use this since an awt is potentially calling
	// javafx outside of the javafx thread
	controller.showKBMAboutFromNonJavaFXThread();
    }
    private void showDeviceMenu(){
	controller.showDeviceUI();
    }
    private void exitApplication(){
	controller.exitApplication();
    }
// ============= Implemented Methods ============== //
    @Override
    public void actionPerformed(ActionEvent ae) {
	Object src = ae.getSource();
	if(src == aboutItem){
	    showAboutPopup();
	}else if(src == displayItem){
	    showDeviceMenu();
	}else if(src == exitItem){
	    exitApplication();
	}
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    /**
     * Checks if the Java System Tray is supported.
     * @return true if supported and false otherwise.
     */
    public static boolean isSupported(){
	return SystemTray.isSupported();
    }

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
