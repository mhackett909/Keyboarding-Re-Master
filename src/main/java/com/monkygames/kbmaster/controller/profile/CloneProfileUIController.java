/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.PopupNotifyInterface;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class CloneProfileUIController extends PopupController implements ChangeListener<String>, PopupNotifyInterface{

// ============= Class variables ============== //
    @FXML
    private Label profileNameL;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
    @FXML
    private TextField profileTF;
    private ProfileManager profileManager;
    private Device device;
    private NewProgramUIController newProgramUIController;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
	updateComboBoxesOnType(AppType.GAME);
    }
    public void setDevice(Device device){
	this.device = device;
    }
    public void okEventFired(ActionEvent evt){
	try{
	    AppType type = getProfileType();

	    if(programCB.getSelectionModel().getSelectedIndex() == 0){
		this.openNewProgramPopup();
		return;
	    }

	    // check for a valid app name
	    App app = (App)programCB.getSelectionModel().getSelectedItem();
	    if(app == null){
		PopupManager.getPopupManager().showError("Invalid App");
		return;
	    }

	    // check for a valid name
	    String newProfileName = profileTF.getText();
	    if(newProfileName == null || newProfileName.equals("")){
		PopupManager.getPopupManager().showError("Invalid profile name");
		return;
	    }
	    
	    // check for a redundant name
	    if(profileManager.doesProfileNameExists(app, newProfileName)){
		PopupManager.getPopupManager().showError("Profile name already exists");
		return;
	    }
	    Profile profile = new Profile(app,newProfileName);
	    device.setDefaultKeymaps(profile);
	    profileManager.addProfile(profile);
	    notifyOK(newProfileName);

	}finally{
	    reset();
	}
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	apps = FXCollections.observableArrayList(profileManager.getRoot(type).getList());
	apps.add(0, new App("",null,null,"New",AppType.APPLICATION));
	programCB.setItems(apps);
    }
    private void reset(){
	profileTF.setText("");
	hideStage();
    }
    /**
     * Returns the profile type based on the type combo box.
     */
    private AppType getProfileType(){
	AppType type;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = AppType.GAME;
	}else{
	    type = AppType.APPLICATION;
	}
	return type;
    }
    private void openNewProgramPopup(){
	if(newProgramUIController == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewProgramUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		newProgramUIController = (NewProgramUIController) fxmlLoader.getController();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		newProgramUIController.setStage(stage);
		newProgramUIController.setProfileManager(profileManager);
		newProgramUIController.addNotification(this);
	    } catch (IOException ex) {
		Logger.getLogger(CloneProfileUIController.class.getName()).log(Level.SEVERE, null, ex);
		return;
	    }
	}
	newProgramUIController.showStage();
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);
	programCB.setItems(FXCollections.observableArrayList());
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	if(ov == typeCB){
	    updateComboBoxesOnType(getProfileType());
	}
    }
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src, String message) {
	if(src == newProgramUIController){
	    this.showStage();
	    if(message != null || !message.equals("")){
		programCB.getSelectionModel().select(message);
	    }
	}
    }

    @Override
    public void onCancel(Object src, String message) {
	// do nothing for now
    }
    @Override
    public void showStage(){
	super.showStage();
	updateComboBoxesOnType(getProfileType());
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
