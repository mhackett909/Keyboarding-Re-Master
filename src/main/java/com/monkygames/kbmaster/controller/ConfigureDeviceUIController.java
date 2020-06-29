/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === kbmaster imports === //
import com.monkygames.kbmaster.driver.Device;

import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.util.PopupManager;
// === java imports === //
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Handles UI Events for configuring devices.
 * @version 1.0
 */
public class ConfigureDeviceUIController implements Initializable, PopupNotifyInterface{


// ============= Class variables ============== //
    @FXML
    private Label driverVersionL;
    @FXML
    private Label driverStatusL;
    @FXML
    private TabPane driverTabPane;
    @FXML
    private ImageView deviceIV;
    @FXML
    private Pane profilePane;
    @FXML
    private Button descriptionB;
    @FXML
    private Label keymapDescriptionL;
    private ProfileUIController profileUIController;
    @FXML
    private Button displayKeymapB;
    @FXML
    private Button eraseKeymapB;
    @FXML
    private Button hideB;
    private Stage stage;
    private Device device;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public ProfileUIController getProfileUIController() {
	return profileUIController;
    }

    /**
     * Sets the device to be configured.
     * @param device the device to be configured.
     */
    public void setDevice(Device device){
		this.device = device;
		updateDeviceDetails(device);
    }
    public void setStage(Stage stage){
		stage.setOnCloseRequest(event -> { profileUIController.saveProfile(); });
		this.stage = stage;
    }
    /**
     * Updates the device details.
     */
    public void updateDeviceDetails(){

	if(device != null){
	    //updateDeviceDetails(device);
	    String status;
	    if(device.isConnected()){
		status = "Connected";
	    }else{
		status = "Disconnected";
	    }
	    try{
		driverStatusL.setText(status);
	    }catch(Exception e){
		handleNonFXThread(status);
	    }
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Update the details on the UI from the specified device.
     * @param device the device's information to be updated from.
     */
    private void updateDeviceDetails(Device device){
    driverVersionL.setText(device.getDeviceInformation().getVersion());
	String status;
	if(device.isConnected()){
	    status = "Connected";
	}else{
	    status = "Disconnected";
	}
	driverStatusL.setText(status);
	deviceIV.setImage(new Image(device.getDeviceInformation().getDeviceIcon()));
	profileUIController.setDevice(device);
    }
    private void handleNonFXThread(final String status){
	Platform.runLater(new Runnable() {
	    @Override
	    public void run() {
		driverStatusL.setText(status+" ");
	    }
	});
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/ProfileUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent profileRoot = (Parent)fxmlLoader.load(location.openStream());
	    profileUIController = (ProfileUIController) fxmlLoader.getController();
	    profilePane.getChildren().add(profileRoot);
	    profileUIController.setKeymapTabPane(driverTabPane);
	    profileUIController.setDescriptionLabel(keymapDescriptionL);
	} catch (IOException ex) {
	    Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
	// handle the description button action
	if(obj == descriptionB){
	    PopupController descriptionController = PopupManager.getPopupManager().openPopup("/com/monkygames/kbmaster/fxml/popup/SetDescriptionUI.fxml");
	    descriptionController.addNotification(this);
	    descriptionController.showStage();
	}else if(obj == displayKeymapB){
	    profileUIController.openDisplayKeymapPopup(driverTabPane.getSelectionModel().getSelectedIndex());
	}else if(obj == eraseKeymapB){
		int selectedKeymap = driverTabPane.getSelectionModel().getSelectedIndex();
		device.setDefaultKeymap(device.getProfile(),selectedKeymap);
		profileUIController.setKeymapDescription(selectedKeymap, "");
		DriverUIController driverUIController = profileUIController.getKeymapUIManager().getDriverUIController(selectedKeymap);
		driverUIController.setSelectedKeymap(device.getProfile().getKeymap(selectedKeymap));

	}else if(obj == hideB){
		profileUIController.saveProfile();
	    stage.hide();
	    // TODO sync with cloud
		}
    }

    /*
    @Override
    public void changed(ObservableValue<? extends Image> ov, Image t, Image t1) {
	if(ov == driverComboBox.valueProperty()){
	    int index = driverComboBox.getSelectionModel().getSelectedIndex();
	    // this means a driver has been selected, so we need to populate
	    // the profiles and keymaps entries
	    Device device = globalAccount.getInstalledDevices().get(index-2);
	    profileUIController.setDevice(device);
	    updateDeviceDetails(device);
	}
    }
    */
    @Override
    public void onOK(Object src, String message){
	// description for keymap has been set
	profileUIController.setKeymapDescription(driverTabPane.getSelectionModel().getSelectedIndex(),message);
    }
    @Override
    public void onCancel(Object src, String message){

    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
