/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === kbmaster imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;

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
public class SelectProfileUIController implements Initializable, ChangeListener<String> {


	// ============= Class variables ============== //
	@FXML
	private ImageView deviceIV;
	@FXML
	private ComboBox typeCB;
	@FXML
	private ComboBox appsCB;
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
	private ImageView devLogoIV;
	private Stage stage;
	private ProfileManager profileManager;
	private Profile currentProfile;
	private Device device;
	private ChangeListener<App> appChangeListener;
	private ChangeListener<Profile> profileChangeListener;
	/**
	 * The default image to be used if the app has not set a logo.
	 */
	private Image defaultAppLogoImage;
	/**
	 * The default image to be used if the app has not set a logo.
	 */
	private Image defaultDevLogoImage;
// ============= Public Methods ============== //

	/**
	 * Sets the device to be configured.
	 *
	 * @param device the device to be configured.
	 */
	public void setDevice(Device device) {
		if (this.device == device) return;
		this.device = device;
		updateDeviceDetails(device);
		reset();
	}
	public void reset() {
		typeCB.valueProperty().removeListener(this);
		typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
				ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
		typeCB.getSelectionModel().selectFirst();
		typeCB.valueProperty().addListener(this);
		resetProfileUIInfo();
		resetAppUIInfo();
		updateComboBoxes(getAppType());
	}
	/**
	 * The profiles combo box selected a new profile.
	 */
	public void profileSelected() {
		currentProfile = (Profile) profileCB.getSelectionModel().getSelectedItem();
		updateProfileUIInfo(currentProfile);
	}
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}
	/**
	 * Returns the selected application type.
	 */
	public AppType getAppType() {
		if(typeCB.getSelectionModel().getSelectedIndex() == 0) return AppType.GAME;
		else return AppType.APPLICATION;
	}
	public void setStage(Stage stage) {	this.stage = stage; }
	public void show() { stage.show(); }

// ============= Protected Methods ============== //
// ============= Private Methods ============== //

	/**
	 * Update the details on the UI from the specified device.
	 *
	 * @param device the device's information to be updated from.
	 */
	private void updateDeviceDetails(Device device) {
		deviceIV.setImage(new Image(device.getDeviceInformation().getDeviceIcon()));
	}
	/**
	 * Updates the combo box by type and always selects the first program
	 * to populate the profiles list.
	 *
	 * @param type the type of profile to sort on.
	 */
	private void updateComboBoxes(AppType type) {
		ObservableList<App> apps;
		ObservableList<Profile> profiles;
		if (type == AppType.APPLICATION) apps = FXCollections.observableArrayList(profileManager.getAppsRoot(device).getList());
		else apps = FXCollections.observableArrayList(profileManager.getGamesRoot(device).getList());
		appsCB.valueProperty().removeListener(appChangeListener);
		profileCB.valueProperty().removeListener(profileChangeListener);
		appsCB.setItems(apps);
		App app = (App) appsCB.getSelectionModel().getSelectedItem();
		appsCB.getSelectionModel().clearSelection();
		if (apps.size() > 0) {
			if (app != null) {
				if (app.getAppType() != type) {
					appsCB.getSelectionModel().selectFirst();
					app = (App) appsCB.getSelectionModel().getSelectedItem();

				}
				else appsCB.getSelectionModel().select(app);
			}
			else {
				appsCB.getSelectionModel().selectFirst();
				app = (App) appsCB.getSelectionModel().getSelectedItem();
			}
			updateAppUIInfo(app);
			profiles = FXCollections.observableArrayList(app.getProfiles());
		}
		else {
			appsCB.getSelectionModel().clearSelection();
			resetAppUIInfo();
			profiles = FXCollections.observableArrayList();
		}
		profileCB.getItems().clear();
		profileCB.setItems(profiles);
		profileCB.getSelectionModel().clearSelection();
		if (profiles.size() > 0) profileCB.getSelectionModel().selectFirst();
		appsCB.valueProperty().addListener(appChangeListener);
		profileCB.valueProperty().addListener(profileChangeListener);
		profileSelected();
	}

	/**
	 * Updates the UI with the profile information.
	 *
	 * @param profile the information to update the UI with.
	 */
	private void updateProfileUIInfo(Profile profile) {
		if (profile == null) resetProfileUIInfo();
		else {
			infoTA.setText(profile.getInfo());
			authorL.setText(profile.getAuthor());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(profile.getLastUpdatedDate());
			SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
			updatedL.setText(date_format.format(cal.getTime()));
		}
	}
	private void resetProfileUIInfo(){
		infoTA.setText("");
		authorL.setText("");
		updatedL.setText("");
		profileCB.valueProperty().removeListener(profileChangeListener);
		profileCB.getSelectionModel().clearSelection();
		profileCB.setItems(FXCollections.observableArrayList());
		profileCB.valueProperty().addListener(profileChangeListener);
	}
	/**
	 * Updates the UI with the app information.
	 * @param app the app to be updated.
	 */
	private void updateAppUIInfo(App app) {
		if (app == null) resetAppUIInfo();
		else {
			appInfoTA.setText(app.getInfo());
			if (app.getAppLogo() == null) {
				appLogoIV.setImage(defaultAppLogoImage);
			} else {
				appLogoIV.setImage(app.getAppLogo());
			}
			if (app.getDevLogo() == null) {
				devLogoIV.setImage(defaultDevLogoImage);
			} else {
				devLogoIV.setImage(app.getDevLogo());
			}
		}
	}
	/**
	 * Resets the app ui information.
	 */
	private void resetAppUIInfo(){
		appInfoTA.setText("");
		appLogoIV.setImage(defaultAppLogoImage);
		devLogoIV.setImage(defaultDevLogoImage);
		appsCB.valueProperty().removeListener(appChangeListener);
		appsCB.getSelectionModel().clearSelection();
		appsCB.setItems(FXCollections.observableArrayList());
		appsCB.valueProperty().addListener(appChangeListener);
	}
	// ============= Implemented Methods ============== //
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		appChangeListener = (ov, previousValue, newValue) -> {
			if(ov == appsCB.valueProperty()) updateComboBoxes(getAppType());
		};
		profileChangeListener = (ov, previousValue, newValue) -> {
			if(ov == profileCB.valueProperty()) profileSelected();
		};
		defaultAppLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png");
		defaultDevLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png");
		devLogoIV = new ImageView();
		appLogoIV = new ImageView();
	}
	@FXML
	private void handleButtonAction(ActionEvent evt) {
		Object obj = evt.getSource();
		if (obj == okB) profileManager.setActiveProfile(device, currentProfile);
		stage.hide();
	}

	@Override
	public void changed(ObservableValue<? extends String> ov, String previousValue, String newValue) {
		if (ov == typeCB.valueProperty()) updateComboBoxes(getAppType());
	}

}
