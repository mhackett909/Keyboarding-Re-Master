/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster;

import com.monkygames.kbmaster.account.CloudAccount;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.account.UserSettings;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.util.WindowUtil;
import com.monkygames.kbmaster.util.thread.DropboxSyncTask;
import com.monkygames.kbmaster.util.thread.SyncEventHandler;
import com.monkygames.kbmaster.util.thread.SyncEventOnExitHandler;
import com.monkygames.kbmaster.util.thread.SyncEventOnLogoutHandler;
import java.io.IOException;
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
    public static final String VERSION = "0.5.0";

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
        _instance.getHostServices().showDocument(url);
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
            root = (Parent)fxmlLoader.load(location.openStream());
            controller = (LoginUIController) fxmlLoader.getController();
            controller.setStage(stage);
            AnchorPane pane = (AnchorPane)root;
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
            root = (Parent)fxmlLoader.load(location.openStream());
            dropboxSyncStage = WindowUtil.createStage(root);
        } catch (IOException ex) {
            Logger.getLogger(KeyboardingMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
        controller.showDeviceMenuFromLogin(null,false);
        //TODO finish cloud account code and test user settings
        /**
        if(userSettings.isRemember){
            switch(userSettings.loginMethod){
                case LoginUIController.LOGIN_LOCAL:
                    controller.showDeviceMenuFromLogin(null,false);
                    break;
                case LoginUIController.LOGIN_DROPBOX:
                    // get accesstoken
                    if(userSettings.accessToken != null && !userSettings.accessToken.equals("")){
                            startDropboxSync(new DropBoxAccount(userSettings.accessToken),false);
                    }else{
                            controller.showDeviceMenuFromLogin(null,false);
                    }
                    break;
            }
        }else{
            controller.showStage();
        }
        */
    }

    /**
     * Opens the dropbox sync UI and created a thread to start syncing.
     * @param dropBoxAccount the dropbox account to sync.
     * @param checkRemember true if the settings should be saved and false otherwise.
     */
    public void startDropboxSync(CloudAccount dropBoxAccount, boolean checkRemember){
        // show the sync 
        dropboxSyncStage.show();
        SyncEventHandler handler = new SyncEventHandler (dropBoxAccount, checkRemember, controller, dropboxSyncStage);
        DropboxSyncTask syncTask = new DropboxSyncTask(dropBoxAccount);
        syncTask.setOnSucceeded(handler);
        syncTask.setOnFailed(handler);
        new Thread(syncTask).start();
    }

    /**
     * Initiates a dropbox sync on logout or exit.
     * @param isOnLogout true if after the dropbox sync, return to the login screen
     * @param cloudAccount the account used to connect to dropbox.
     * else exit the program.
     */
    public void endDropboxSync(boolean isOnLogout, CloudAccount cloudAccount){
        dropboxSyncStage.show();
        if(isOnLogout){
            SyncEventOnLogoutHandler handler = new SyncEventOnLogoutHandler (dropboxSyncStage);
            DropboxSyncTask syncTask = new DropboxSyncTask(cloudAccount);
            syncTask.setOnSucceeded(handler);
            syncTask.setOnFailed(handler);
            new Thread(syncTask).start();

        }else{
            SyncEventOnExitHandler handler = new SyncEventOnExitHandler (dropboxSyncStage);
            DropboxSyncTask syncTask = new DropboxSyncTask(cloudAccount);
            syncTask.setOnSucceeded(handler);
            syncTask.setOnFailed(handler);
            new Thread(syncTask).start();
        }
    }

    public static KeyboardingMaster getInstance(){
        return _instance;
    }

    /**
     * Exit the program.
     */
    public void exit(){
        System.exit(1);
    }

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
    /**
     * Print the version to be consumed by the installer.
     */
    public static void printVersion(){
        System.out.println(VERSION);
    }
}