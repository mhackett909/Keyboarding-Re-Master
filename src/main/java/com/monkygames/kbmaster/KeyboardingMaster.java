/*
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster;

import com.monkygames.kbmaster.cloud.DropBoxAccount;
import com.monkygames.kbmaster.cloud.DropBoxApp;
import com.monkygames.kbmaster.cloud.UserSettings;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.util.WindowUtil;
import com.monkygames.kbmaster.cloud.thread.DropboxSyncTask;
import com.monkygames.kbmaster.cloud.thread.SyncEventHandler;
import com.monkygames.kbmaster.cloud.thread.SyncEventOnExitHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main class for loading the Keyboard Master GUI.
 */
public class KeyboardingMaster extends Application {


    // === variables === //
    private LoginUIController controller;
    private DropboxSyncTask syncTask;
    public static final String VERSION = "0.5.3";

    /**
     * Reference to this object.
     */
    private static KeyboardingMaster _instance;


    /**
     * The user's settings.
     */
    private UserSettings userSettings;

    /**
     * The stage for the dropbox sync UI.
     */
    private Stage dropboxSyncStage;

    /**
     * Opens the default web browser.
     * @url the url to open.
     */
    public static void gotoWeb(String url){
        new Thread(()-> {
            try { Desktop.getDesktop().browse(new URI(url)); }
            catch (IOException ioException) { ioException.printStackTrace(); }
            catch (URISyntaxException uriSyntaxException) {	uriSyntaxException.printStackTrace(); }
        }).start();
    }

    /**
     * Returns the user settings.
     * @return the user settings.
     */
    public static UserSettings getUserSettings(){
        return _instance.userSettings;
    }

    /**
     * Save the user settings.
     */
    public static void saveUserSettings(){
        XStreamManager.getStreamManager().writeUserSettings(getUserSettings());
    }

    @Override
    public void start(Stage stage) {
        Parent root;
        KeyboardingMaster._instance = this;

        this.userSettings = XStreamManager.getStreamManager().readUserSettings();

        // initialize the login ui
        try {
            URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/login/LoginUI.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            root = (Parent) fxmlLoader.load(location.openStream());
            controller = (LoginUIController) fxmlLoader.getController();
            controller.setStage(stage);
            AnchorPane pane = (AnchorPane) root;
            WindowUtil.configureStage(pane.prefWidthProperty().doubleValue(),
                    pane.prefHeightProperty().doubleValue(),
                    root, stage);

        } catch (IOException ex) {
            Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        // initialize the dropbox sync ui
        try {
            URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/DropboxSync.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            root = (Parent) fxmlLoader.load(location.openStream());
            dropboxSyncStage = WindowUtil.createStage(root);

        } catch (IOException ex) {
            Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (userSettings.isRemember) {
            switch (userSettings.loginMethod) {
                case LoginUIController.LOGIN_LOCAL:
                    controller.showDeviceMenuFromLogin(null, false);
                    break;
                case LoginUIController.LOGIN_DROPBOX:
                    String accessToken = DropBoxApp.ACCESS_TOKEN;
                    if (accessToken != null && !accessToken.equals(""))
                        startDropboxSync(new DropBoxAccount(accessToken), false);
                    else {
                        controller.resetLoginUI();
                        controller.showStage();
                    }
                    break;
            }
        } else controller.showStage();
    }
    /**
     * Opens the dropbox sync UI and created a thread to start syncing.
     * @param dropBoxAccount the dropbox account to sync.
     * @param checkRemember true if the settings should be saved and false otherwise.
     */
    public void startDropboxSync(DropBoxAccount dropBoxAccount, boolean checkRemember){
        dropboxSyncStage.show();
        SyncEventHandler handler = new SyncEventHandler (dropBoxAccount, checkRemember, controller, dropboxSyncStage);
        syncTask = new DropboxSyncTask(dropBoxAccount);
        syncTask.setOnSucceeded(handler);
        syncTask.setOnFailed(handler);
        new Thread(syncTask).start();
    }

    /**
     * Initiates a dropbox sync on logout or exit.
     * @param isOnLogout true if after the dropbox sync, return to the login screen
     * @param dropBoxAccount the account used to connect to dropbox.
     * else exit the program.
     */
    public void endDropboxSync(boolean isOnLogout, DropBoxAccount dropBoxAccount) {
        dropboxSyncStage.show();
        SyncEventOnExitHandler handler = new SyncEventOnExitHandler(dropboxSyncStage, isOnLogout);
        syncTask = new DropboxSyncTask(dropBoxAccount);
        syncTask.setOnSucceeded(handler);
        syncTask.setOnFailed(handler);
        new Thread(syncTask).start();
    }

    public static KeyboardingMaster getInstance(){
        return _instance;
    }

    /**
     * Exit the program.
     */
    public void exit(){ System.exit(0); }

    public void logout(){
        controller.showStage();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}