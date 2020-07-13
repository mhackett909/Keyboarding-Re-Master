/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.login;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.cloud.CloudAccount;
import com.monkygames.kbmaster.cloud.DropBoxAccount;
import com.monkygames.kbmaster.cloud.DropBoxApp;
import com.monkygames.kbmaster.cloud.UserSettings;
import com.monkygames.kbmaster.controller.ButtonController;
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import com.monkygames.kbmaster.util.RepeatManager;
import com.monkygames.kbmaster.util.WindowUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 */
public class LoginUIController implements Initializable {

    // === variables === //
    @FXML
    public ChoiceBox accessCB;
    @FXML
    public Button loginB;
    @FXML
    public Button close;
    @FXML
    public Pane loginPane;
	@FXML
	public CheckBox rememberEmailCB;

	public static final int LOGIN_LOCAL = 0;
	public static final int LOGIN_DROPBOX = 1;

	/**
	 * Used to call close() method.
	 * @return
	 */

    /**
     * Used for setting animation effects.
     */
    private ButtonController buttonController;
    private Parent root;

    /**
     * Used for closing the window.
     */
    private Stage loginStage;
    private Stage deviceMenuStage;
	private Stage dropBoxStage;
	/**
	 * Controller for device menu.
	 */
    private DeviceMenuUIController deviceMenuController;
	/**
	 * Controller for dropbox menu.
	 */
	private DropBoxUIController dropBoxController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		buttonController = new ButtonController();
		buttonController.addNode(loginB);
		buttonController.addNode(close);
		accessCB.setItems(FXCollections.observableArrayList("Local","Dropbox"));
		accessCB.getSelectionModel().selectFirst();
		// so that stages can hide without being terminated.
		Platform.setImplicitExit(false);
    }

    @FXML
    public void loginEventFired(ActionEvent evt) {
		loginStage.hide();
		switch (accessCB.getSelectionModel().getSelectedIndex()) {
			case LOGIN_LOCAL:
				showDeviceMenuFromLogin(null, true);
				break;
			case LOGIN_DROPBOX:
				if (DropBoxApp.ACCESS_TOKEN != "" && DropBoxApp.ACCESS_TOKEN != null) {
					DropBoxAccount dropBoxAccount = new DropBoxAccount(DropBoxApp.ACCESS_TOKEN);
					KeyboardingMaster.getInstance().startDropboxSync(dropBoxAccount, true);
				}
				else {
					dropBoxController = null;
					try {
						URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/login/DropBoxUI.fxml");
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(location);
						fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
						Parent dropBoxRoot = (Parent) fxmlLoader.load(location.openStream());
						dropBoxController = (DropBoxUIController) fxmlLoader.getController();
						dropBoxStage = WindowUtil.createStage(dropBoxRoot);
						dropBoxController.setStage(dropBoxStage);
						dropBoxController.setLoginController(this);
						dropBoxStage.show();
					} catch (IOException ex) {
						Logger.getLogger(LoginUIController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
		}
	}

    /**
     * Closes the login window and exits the program.
     */
    @FXML
    public void closeEventFired(ActionEvent evt){
    	KeyboardingMaster.getInstance().exit();
    }

    public void setStage(Stage loginStage){
		this.loginStage = loginStage;
    }

	/**
	 * Show the login.
	 */
	public void showStage(){
		loginStage.show();
	}

    /**
     * The device controller has called to be hiden.
     * @param showLogin true if the login should also be shown and false otherwise.
     */
    public void hideDeviceMenu(boolean showLogin) {
		deviceMenuStage.hide();
		resetLoginUI();
		if (showLogin) loginStage.show();
	}
	public void resetLoginUI() {
		rememberEmailCB.setSelected(KeyboardingMaster.getUserSettings().isRemember);
		accessCB.getSelectionModel().select(KeyboardingMaster.getUserSettings().loginMethod);
	}
    /**
     * Shows the device menu.
     */
    public void showDeviceMenuFromNonJavaFXThread() {
		try {
			deviceMenuStage.show();
		} catch (Exception e) {
			Platform.runLater(() -> deviceMenuStage.show());
		}
	}

	/**
	 * Open the device menu from the login page (either this page or network pages).
	 * @param dropBoxAccount the cloud account used to login
	 */
	public void showDeviceMenuFromLogin(DropBoxAccount dropBoxAccount, boolean checkRemember){
		// check if remember has been selected
		UserSettings userSettings = KeyboardingMaster.getUserSettings();
		if(checkRemember){
			// get the user settings
			userSettings.loginMethod = accessCB.getSelectionModel().getSelectedIndex();
			if(dropBoxAccount != null) userSettings.accessToken = dropBoxAccount.getAccessToken();

			if(rememberEmailCB.isSelected()){
				// save the state and also save the cloud account
				userSettings.isRemember = true;
			}else{
				// reset the settings
				userSettings.isRemember = false;
			}
			// save the settings
			KeyboardingMaster.saveUserSettings();
		}

		deviceMenuController = null;
		try {
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/DeviceMenuUI.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			root = (Parent)fxmlLoader.load(location.openStream());
			deviceMenuController = (DeviceMenuUIController) fxmlLoader.getController();
			deviceMenuStage = WindowUtil.createStage(root);
			deviceMenuController.setLoginController(this);
		} catch (IOException ex) {
			Logger.getLogger(LoginUIController.class.getName()).log(Level.SEVERE, null, ex);
		}

		// check if the cloud account -- if so, pop sync display plus thread
		deviceMenuController.initResources(userSettings,dropBoxAccount);
		deviceMenuStage.setOnCloseRequest(event -> { RepeatManager.setRepeat(true); });
		deviceMenuStage.show();
	}
}
