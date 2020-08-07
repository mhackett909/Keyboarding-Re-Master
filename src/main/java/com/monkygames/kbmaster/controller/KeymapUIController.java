/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.Profile;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Handles managing Keymap UI Panels for the device.
 */
public class KeymapUIController implements ChangeListener{

// ============= Class variables ============== //
    private Device device;
    private TabPane tabPane;
    private DriverUIController[] driverUIController;
    private Profile profile;
    private Label keymapDescription;
// ============= Constructors ============== //
    public KeymapUIController(){
	driverUIController = new DriverUIController[8];
    }
// ============= Public Methods ============== //
    /**
     * Sets the text for the description.
     * @param message the text to set.
     */
    public void setDescriptionText(String message){ keymapDescription.setText(message); }
    public int getSelectedIndex() { return tabPane.getSelectionModel().getSelectedIndex(); }
    public void setLabel(Label keymapDescription){ this.keymapDescription = keymapDescription; }
    /**
     * Sets the device to be used for the keymap and all of the DriverUIControllers.
     * @param device the device to used.
     */
    public void setDevice(Device device){
		this.device = device;
    }
    /**
     * Sets the profile to be used/configured.
     * @param profile the profile to be used.
     */
    public void setProfile(Profile profile){
   		this.profile = profile;
		if (profile == null) {
			tabPane.getSelectionModel().select(0);
			return;
		}
		if (tabPane.getSelectionModel().getSelectedIndex() == profile.getDefaultKeymap()) changedEvent();
		else tabPane.getSelectionModel().select(profile.getDefaultKeymap());
		for(int i = 0; i < driverUIController.length; i++){
			driverUIController[i].setSelectedKeymap(profile.getKeymap(i));
		}
    }
	/**
	 * Gets the DriverUIController. Used for resetting keymap to default.
	 */
	 public DriverUIController getDriverUIController(int i) { return driverUIController[i]; }
	/**
     * Adds notification if a profile should be saved.
     * @param notification the interface to listen for save events.
     */
    public void addSaveNotification(PopupNotifyInterface notification){
	for(int i = 0; i < driverUIController.length; i++){
		driverUIController[i].addSaveNotification(notification);
	}
    }
    public void setTabPane(TabPane tabPane){
	this.tabPane = tabPane;
	tabPane.getSelectionModel().selectedItemProperty().addListener(this);
    }
    /**
     * Populates tabs with the UI from the device.
     */
    public void initializeTabs(){
	ObservableList list = tabPane.getTabs();
	for(int i = 0; i < list.size(); i++){
	    initializeTab(i,(Tab)list.get(i));
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private boolean initializeTab(int index,Tab tab){
	if(device == null) return false;
	Parent root = null;
	try {
	    // pop open add new device
	    URL location = getClass().getResource(device.getDeviceInformation().getUIFXMLURL());
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    root = (Parent)fxmlLoader.load(location.openStream());
	    driverUIController[index] = (DriverUIController) fxmlLoader.getController();
	    driverUIController[index].setDevice(device);
	    tab.setContent(root);

	    //newProgramUIController.setStage(stage);
	    //newProgramUIController.setProfileManager(profileManager);
	    //newProgramUIController.addNotification(this);
	    //newProgramUIController.setNewProfileController(this);
	} catch (IOException ex) {
	    Logger.getLogger(KeymapUIController.class.getName()).log(Level.SEVERE, null, ex);
	    return false;
	}
	return true;
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void changed(ObservableValue ov, Object oldTab, Object newTab) {
    	if (profile == null) {
			keymapDescription.setText("");
			return;
		}
		changedEvent();
    }
    public void changedEvent() {
		int selectedIndex = tabPane.getSelectionModel().getSelectedIndex();
		profile.setDefaultKeymap(selectedIndex);
		String desc = profile.getKeymap(selectedIndex).getDescription();
		//Solves a threading issue when hot swapping keymaps
		Platform.runLater(() -> keymapDescription.setText(desc));
    }
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
