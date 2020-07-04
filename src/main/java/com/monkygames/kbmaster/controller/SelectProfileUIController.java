/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === kbmaster imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.io.File;
// === java imports === //
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Handles UI Events for configuring devices.
 * @version 1.0
 */
public class SelectProfileUIController implements Initializable, ChangeListener<String>{


// ============= Class variables ============== //
    @FXML 
    private ImageView deviceIV;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox appCB;
    @FXML
    private ComboBox profileCB;
    @FXML
    private Button okB;
    @FXML
    private Button cancelB;
    @FXML
    private TextArea infoTA;
    @FXML
    private Label authorL;
    @FXML
    private Label updatedL;
    @FXML
    private TextArea appInfoTA;
    @FXML 
    private ImageView appLogoIV;
    @FXML
    private ImageView developerIV;
    private Stage stage;
    private ProfileManager profileManager;
    private File profileDir;
    private Profile currentProfile;
    private Device device;
    private DeviceMenuUIController deviceMenuController;
    /**
     * The default image to be used if the app has not set a logo.
     */
    private Image defaultAppLogoImage;
    /**
     * The default image to be used if the app has not set a logo.
     */
    private Image defaultDevLogoImage;
// ============= Constructors ============== //
// ============= Public Methods ============== //

    /**
     * Sets the device to be configured.
     * @param device the device to be configured.
     */
    public void setDevice(Device device){
	this.device = device;
	updateDeviceDetails(device);
	reset();
    }
    public void setStage(Stage stage){
	this.stage = stage;
    }
    public void show(){
	stage.show();
    }
    public void setDeviceMenuController(DeviceMenuUIController deviceMenuController){
	this.deviceMenuController = deviceMenuController;
    }
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Update the details on the UI from the specified device.
     * @param device the device's information to be updated from.
     */
    private void updateDeviceDetails(Device device){
	String deviceName = device.getDeviceInformation().getProfileName();
	if(profileManager != null){
	    profileManager.close();
	}
	profileManager = new ProfileManager(profileDir+File.separator+deviceName);
	deviceIV.setImage(new Image(device.getDeviceInformation().getDeviceIcon()));
    }
    private void reset(){
	typeCB.valueProperty().removeListener(this);
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().clearSelection();
	typeCB.valueProperty().addListener(this);
	// remove all from appCB
	appCB.valueProperty().removeListener(this);
	appCB.setItems(FXCollections.observableArrayList());
	// remove all from profileCB
	profileCB.valueProperty().removeListener(this);
	profileCB.setItems(FXCollections.observableArrayList());

	infoTA.setText("");
	authorL.setText("");
	updatedL.setText("");
	developerIV.setImage(new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png"));
	appLogoIV.setImage(new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png"));
	this.appInfoTA.setText("");
    }
    /**
     * Updates the type, programs, and profiles combo boxes.
     */
    private void updateComboBoxes(){
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    updateComboBoxesOnType(AppType.GAME);
	}else{
	    updateComboBoxesOnType(AppType.APPLICATION);
	}
    }
    /**
     * Updates the combo box by type and always selects the first program
     * to populate the profiles list.
     * @param type the type of profile to sort on.
     */
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	ObservableList<Profile> profiles = null;
	if(type == AppType.APPLICATION){
	    apps = FXCollections.observableArrayList(profileManager.getAppsRoot().getList());
	}else{
	    apps = FXCollections.observableArrayList(profileManager.getGamesRoot().getList());
	}
	if(apps.size() > 0 && apps.get(0) != null){
	    profiles = FXCollections.observableArrayList(apps.get(0).getProfiles());
	}

	appCB.valueProperty().removeListener(this);
	appCB.setItems(apps);
	if(apps.size() > 0){
	    appCB.getSelectionModel().selectFirst();
	    // set app ui
	    updateAppUIInfo((App)appCB.getSelectionModel().getSelectedItem());
	}
	if(profiles == null){
	    profileCB.setItems(FXCollections.observableArrayList());
	}else{
	    profileCB.setItems(profiles);
	    profileCB.getSelectionModel().selectFirst();
	    profileSelected();
	}
	// I usually have a listener for this class but
	// typeCB and profileCB both contain Strings while programCB contains Apps.
	// So its necessary to extend a generic listener here
	appCB.valueProperty().addListener(new ChangeListener<App>(){
	    @Override
	    public void changed(ObservableValue<? extends App> ov, App previousValue, App newValue) {
		if(ov == appCB.valueProperty()){
		    updateProfilesComboBox();
		}
	    }
	});
    }
    /**
     * Only updates the profiles combo box.
     */
    private void updateProfilesComboBox(){
	App app;
	ObservableList<Profile> profiles = null;
	app = (App)appCB.getSelectionModel().getSelectedItem();
	if(app == null){
	    return;
	}
	updateAppUIInfo(app);

	profiles = FXCollections.observableArrayList(app.getProfiles());
	profileCB.setItems(profiles);
	profileCB.getSelectionModel().selectFirst();
	if(profiles.size() > 0 ){
		if (device.getProfile() != null) currentProfile = device.getProfile();
	    else currentProfile = profiles.get(0);
	    updateProfileUIInfo(currentProfile);
	}
    }
    /**
     * The profiles combo box selected a new profile.
     */
    public void profileSelected(){
	Profile selectedProfile;
	App app = (App)appCB.getSelectionModel().getSelectedItem();
	if(app == null){
	    return;
	}
	selectedProfile = (Profile)profileCB.getSelectionModel().getSelectedItem();

	if(selectedProfile != null && selectedProfile != currentProfile){
	    currentProfile = selectedProfile;
	    updateProfileUIInfo(selectedProfile);
		deviceMenuController.setActiveProfile(device, selectedProfile);
	}
    }
    /**
     * Updates the UI with the profile information.
     * @param profile the information to update the UI with.
     */
    private void updateProfileUIInfo(Profile profile){
	infoTA.setText(profile.getInfo());
	authorL.setText(profile.getAuthor());
	Calendar cal = Calendar.getInstance();
	cal.setTimeInMillis(profile.getLastUpdatedDate());
	SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
	updatedL.setText(date_format.format(cal.getTime()));
    }
    /**
     * Updates the UI with the app information.
     * @param app the app to be updated.
     */
    private void updateAppUIInfo(App app){
	appInfoTA.setText(app.getInfo());
	if(app.getAppLogo() == null){
	    appLogoIV.setImage(defaultAppLogoImage);
	}else{
	    appLogoIV.setImage(app.getAppLogo());
	}
	if(app.getDevLogo() == null){
	    developerIV.setImage(defaultDevLogoImage);
	}else{
	    developerIV.setImage(app.getDevLogo());
	}
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	profileDir = new File("profiles");
	defaultAppLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png");
	defaultDevLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png");
    }

    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
	// handle the description button action
	if(obj == okB){
	    //set the profile to the keymap controller
	    profileSelected();
	    stage.hide();
	}else if(obj == cancelB){
	    stage.hide();
	}
	// free up profile manager
	if(profileManager != null) profileManager.close();

    }

    @Override
    public void changed(ObservableValue<? extends String> ov,  String previousValue, String newValue) {
	if(ov == typeCB.valueProperty()){
	    updateComboBoxes();
	}else if(ov == profileCB.valueProperty()){
	    // load new profile
	    // set configurations!
	    profileSelected();
	}
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
