/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import com.monkygames.kbmaster.controller.profile.CloneProfileUIController;
import com.monkygames.kbmaster.controller.profile.DeleteProfileUIController;
import com.monkygames.kbmaster.controller.profile.DeleteProgramUIController;
import com.monkygames.kbmaster.controller.profile.NewProfileUIController;
import com.monkygames.kbmaster.controller.profile.NewProgramUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
// === kbmaster imports === //
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.io.BindingPDFWriter;
import com.monkygames.kbmaster.util.GenerateBindingsImage;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import com.monkygames.kbmaster.util.WindowUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class ProfileUIController implements Initializable, PopupNotifyInterface{
// ============= Class variables ============== //
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox appsCB;
    @FXML
    private ComboBox profileCB;
    @FXML
    private Button newAppB;
    @FXML
    private Button newProfileB;
    @FXML
    private Button cloneProfileB;
    @FXML
    private Button importProfileB;
    @FXML
    private Button exportProfileB;
    @FXML
    private Button printPDFB;
    @FXML
    private Button deleteProfileB;
    @FXML
    private Button deleteProgramB;
    @FXML
    private TextArea infoTA;
    @FXML
    private TextArea appInfoTA;
    @FXML
    private ImageView appLogoIV;
    @FXML
    private ImageView devLogoIV;
    @FXML
    private Label authorL;
    @FXML
    private Label updatedL;
    private ProfileManager profileManager;
    private NewProgramUIController newProgramUIController;
    private NewProfileUIController newProfileUIController;
    private CloneProfileUIController cloneProfileUIController;
    private DeleteProfileUIController deleteProfileUIController;
    private DeleteProgramUIController deleteProgramUIController;
    private DisplayKeymapUIController displayKeymapUIController;
	private ResetKeymapUIController resetKeymapUIController;
    private Device device;
    /**
     * Used for selecting a file to write a pdf binding.
     */
    private FileChooser pdfChooser;
    private TabPane keymapTabPane;
    private KeymapUIController keymapUIController;
    /**
     * The currently used profile.
     */
    private Profile currentProfile;
    private ChangeListener<Profile> profileChangeListener;
    /**
     * The default image to be used if the app has not set a logo.
     */
    private Image defaultAppLogoImage;
    /**
     * The default image to be used if the app has not set a logo.
     */
    private Image defaultDevLogoImage;
    /**
     * Used for importing/exporting files.
     */
    private FileChooser kmpFileChooser;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void profileEventFired(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == newProfileB) {
			openNewProfilePopup();
		} else if (src == newAppB) {
			openNewProgramPopup();
		} else if (src == cloneProfileB) {
			openCloneProfilePopup();
		} else if (src == importProfileB) {
			importProfile();
		} else if (src == exportProfileB) {
			exportProfile();
		} else if (src == printPDFB) {
			openPDFPopup();
		} else if (src == deleteProfileB) {
			openDeleteProfilePopup();
		} else if (src == deleteProgramB) {
			openDeleteProgramPopup();
		}

	}
    public void setKeymapTabPane(TabPane keymapTabPane) {
		this.keymapTabPane = keymapTabPane;
		keymapUIController.setTabPane(keymapTabPane);
	}
    /**
     * Sets the device in order to get device name and to be used in
     * popups for creating profiles.
     * @param device the device to be set.
     */
    public void setDevice(Device device) {
		if (this.device == device) {
			if (currentProfile == device.getProfile()) return;
		 	else saveProfile();
		}
		this.device = device;
		currentProfile = device.getProfile();
		keymapUIController.setDevice(device);
		keymapUIController.initializeTabs();
		keymapUIController.setProfile(currentProfile);
		keymapUIController.addSaveNotification(this);
		if(newProfileUIController != null)
	    	newProfileUIController.setDevice(device);
		typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
		resetAppUIInfo();
		resetProfileUIInfo();
		AppType appType;
		if (currentProfile == null) {
			typeCB.getSelectionModel().selectFirst();
			appType = AppType.GAME;
		}
		else {
		 	appType = currentProfile.getAppInfo().getAppType();
			switch (appType) {
				case GAME:
					typeCB.getSelectionModel().select(0);
					break;
				default:
					typeCB.getSelectionModel().select(1);
			}
		}
		updateComboBoxes(appType);
    }
    /**
     * Set the description label for keymaps.
     */
    public void setDescriptionLabel(Label descriptionLabel){ keymapUIController.setLabel(descriptionLabel); }

	/**
	 * Set the description for the currently selected keymap.
	 * @param keymapID the id of the keymap to set the description.
	 * @param description the description of the keymap.
	 */
	public void setKeymapDescription(int keymapID, String description){
		if(currentProfile != null){
			keymapUIController.setDescriptionText(description);
			currentProfile.getKeymap(keymapID).setDescription(description);
			currentProfile.setLastUpdatedDate(Calendar.getInstance().getTimeInMillis());
			saveProfile();
		}
		else PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
	}
	/**
	 * Returns the current profile. Used by the engine/UI communication process.
	 */
	 public Profile getCurrentProfile() { return currentProfile; }
	/**
	 * Opens the display keymap popup with the specified keymap id.
	 * @param keymapID from 0 to 7.
	 */
	public void openDisplayKeymapPopup(int keymapID){
		if (!checkDevice()) return;
		if (currentProfile == null) {
			PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
			return;
		}
		try{
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/DisplayKeymapUI.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = (Parent)fxmlLoader.load(location.openStream());
			displayKeymapUIController = (DisplayKeymapUIController) fxmlLoader.getController();
			Stage stage = WindowUtil.createStage(root);
			displayKeymapUIController.setStage(stage);
		}catch(IOException e){}
		GenerateBindingsImage generator = new GenerateBindingsImage(device);
		displayKeymapUIController.setGenerateBindingsImage(generator);
		displayKeymapUIController.displayKeymap(currentProfile.getKeymaps(),keymapID);
	}
	/**
	 * Opens the reset keymap popup with the specified keymap id.
	 * @param keymapID from 0 to 7.
	 */
	public void openResetKeymapPopup(int keymapID){
		if (!checkDevice()) return;
		if (currentProfile == null) {
			PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
			return;
		}
		try{
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/ResetKeymapUI.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			Parent root = fxmlLoader.load(location.openStream());
			resetKeymapUIController = fxmlLoader.getController();
			Stage stage = WindowUtil.createStage(root);
			resetKeymapUIController.setStage(stage);
			resetKeymapUIController.setUI(keymapID, currentProfile.getProfileName());
			resetKeymapUIController.addNotification(this);
			stage.show();
		}catch(IOException e){}
	}
	/**
	 * Note, this needs to be called before other methods.
	 */
	public void setProfileManager(ProfileManager profileManager){
		this.profileManager = profileManager;
	}
	public Device getDevice() { return device; }
	/**
	 * Returns the selected application type.
	 */
	public AppType getAppType() {
		if(typeCB.getSelectionModel().getSelectedIndex() == 0) return AppType.GAME;
		else return AppType.APPLICATION;
	}
	/**
	 * Returns the keymap tab pane. Needed for linking engine with UI.
	 */
	 public TabPane getKeymapTabPane() { return keymapTabPane; }
	/**
	 * Saves the current profile to disk.
	 */
	public void saveProfile() {
		if (currentProfile != null) {
			currentProfile.setDefaultKeymap(keymapTabPane.getSelectionModel().getSelectedIndex());
			currentProfile.setInfo(infoTA.getText());
		}
		App selectedApp = (App) appsCB.getSelectionModel().getSelectedItem();
		if (selectedApp != null) selectedApp.setInfo(appInfoTA.getText());
		profileManager.saveProfile(device);
	}
	/**
	 * The profiles combo box selected a new profile.
	 */
    public void profileSelected(Profile selectedProfile){
		if (selectedProfile != currentProfile) saveProfile();
	   	currentProfile = selectedProfile;
	    keymapUIController.setProfile(selectedProfile);
		profileManager.setActiveProfile(device, selectedProfile);
    }
    public void onTypeChange() {
		if (currentProfile != null) {
			String currentType = currentProfile.getAppInfo().getAppType().toString().toLowerCase();
			String newType = ((String) typeCB.getSelectionModel().getSelectedItem()).toLowerCase();
			if (currentType.equals(newType)) return;
		}
		Profile getProf = null;
		try {
			if (getAppType() == AppType.APPLICATION) getProf = profileManager.getAppsRoot(device).getList().get(0).getProfiles().get(0);
			else getProf = profileManager.getGamesRoot(device).getList().get(0).getProfiles().get(0);
		}
		catch (Exception e) { }
		profileSelected(getProf);
		updateComboBoxes(getAppType());
	}
	public void onAppChange() {
		if (currentProfile != null) {
			String currentApp = currentProfile.getAppInfo().getName();
			String newApp = appsCB.getSelectionModel().getSelectedItem().toString();
			if (currentApp.equals(newApp)) return;
		}
		Profile getProf = null;
		try {
			getProf = ((App) appsCB.getSelectionModel().getSelectedItem()).getProfiles().get(0);
		} catch (Exception e) {	}
		profileSelected(getProf);
		updateComboBoxes(getAppType());
	}

// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Repopulates and updates the combo boxes.
     *
     * @param type the type of profile to sort on.
     */
    private void updateComboBoxes(AppType type){
		Root root = profileManager.getRoot(device, type);
		if(root.getList().isEmpty()){
	   		resetAppUIInfo();
	    	resetProfileUIInfo();
	    	return;
		}
		ObservableList<App> apps = FXCollections.observableArrayList(root.getList());
		appsCB.setItems(apps);
		App app;
		if (currentProfile != null) app = profileManager.getAppByName(device,
				currentProfile.getAppInfo().getAppType().toString(), currentProfile.getAppInfo().getName());
		else app = (App) appsCB.getItems().get(0);
		updateAppUIInfo(app);
		appsCB.getSelectionModel().select(app);
		ObservableList<Profile> profiles;
		updateProfileUIInfo(currentProfile);
		try {
			profileCB.valueProperty().removeListener(profileChangeListener);
			profiles = FXCollections.observableArrayList(app.getProfiles());
			profileCB.setItems(profiles);
			int index = 0;
			for (; index < profiles.size(); index++) {
				if (profiles.get(index).getProfileName().equals(currentProfile.getProfileName())) break;
			}
			profileCB.getSelectionModel().select(index);
			profileCB.valueProperty().addListener(profileChangeListener);
		}
		catch (NullPointerException e) {
			//Must ensure profileChangeListener is not disabled
			//Remove it first to ensure two listeners are not somehow enabled
			profileCB.valueProperty().removeListener(profileChangeListener);
			profileCB.valueProperty().addListener(profileChangeListener);
		}
    }
    /**
     * Sets the tool tip with the string by the specified information.
     * @param button the button to set the tooltip.
     * @param toolString the string to on the tooltip.
     */
    private void setButtonToolTip(Button button, String toolString){
	Tooltip tooltip = new Tooltip();
	tooltip.setText(toolString);
	button.setTooltip(tooltip);
    }
    /**
     * Opens the new Program Popup UI.
     */
    private void openNewProgramPopup() {
		if (!checkDevice()) return;
		if (newProgramUIController == null) {
			try {
				// pop open add new device
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewProgramUI.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = (Parent) fxmlLoader.load(location.openStream());
				newProgramUIController = (NewProgramUIController) fxmlLoader.getController();
				Stage stage = WindowUtil.createStage(root);
				newProgramUIController.setStage(stage);
				newProgramUIController.addNotification(this);
			} catch (IOException ex) {
				return;
			}
		}
		newProgramUIController.setProfileManager(profileManager);
		newProgramUIController.setDevice(device);
		newProgramUIController.showStage();
		newProgramUIController.setAppType(typeCB.getSelectionModel().getSelectedIndex());

	}
    /**
     * Opens a new profile popup.
     */
    private void openNewProfilePopup() {
		if (!checkDevice()) return;
		// check if an app is selected
		if (appsCB.getSelectionModel().getSelectedItem() == null) {
			PopupManager.getPopupManager().showError("No App selected.\nMaybe create a new App?");
			return;
		}
		if (newProfileUIController == null)
			newProfileUIController = (NewProfileUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/NewProfileUI.fxml");
		newProfileUIController.setDevice(device);
		newProfileUIController.setProfileManager(profileManager);
		newProfileUIController.showStage();
		newProfileUIController.setAppType(typeCB.getSelectionModel().getSelectedIndex());
		newProfileUIController.setApp(appsCB.getSelectionModel().getSelectedIndex());
	}
    private void openCloneProfilePopup() {
		if (!checkDevice()) return;
		// check if a profile has been selected
		Profile profile = (Profile) profileCB.getSelectionModel().getSelectedItem();
		if (profile == null) {
			PopupManager.getPopupManager().showError("No Profile selected");
			return;
		}
		if (cloneProfileUIController == null) {
			cloneProfileUIController = (CloneProfileUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/CloneProfileUI.fxml");
		}
		cloneProfileUIController.setDevice(device);
		cloneProfileUIController.setProfileManager(profileManager);
		cloneProfileUIController.setLabel(currentProfile.getProfileName());
		cloneProfileUIController.setAppType(typeCB.getSelectionModel().getSelectedIndex());
		cloneProfileUIController.setProgram(appsCB.getSelectionModel().getSelectedIndex());
		cloneProfileUIController.showStage();

	}
    private void openPDFPopup() {
		Profile profile = (Profile) profileCB.getSelectionModel().getSelectedItem();
		if (profile == null) {
			PopupManager.getPopupManager().showError("No Profile selected");
			return;
		}
		File file = pdfChooser.showSaveDialog(null);
		if (file != null) {
			GenerateBindingsImage generator = new GenerateBindingsImage(device);
			String header = profile.getAppInfo().getName() + " / " + profile.getProfileName();
			String footer = device.getDeviceInformation().getMake() + " " +
					device.getDeviceInformation().getModel();
			BindingPDFWriter pdfWriter = new BindingPDFWriter(generator.generateImages(profile),
					header,
					profile.getInfo(),
					"keyboard mice gaming",
					header,
					footer,
					file.getPath());
		}
	}
    private void openDeleteProfilePopup() {
		if (!checkDevice()) return;
		Profile profile = (Profile) profileCB.getSelectionModel().getSelectedItem();
		if (profile == null) {
			PopupManager.getPopupManager().showError("No Profile selected");
			return;
		}
		App app = (App) appsCB.getSelectionModel().getSelectedItem();
		if (deleteProfileUIController == null) {
			deleteProfileUIController = (DeleteProfileUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/DeleteProfileUI.fxml");
		}
		deleteProfileUIController.setProfile(device, app, profile);
		deleteProfileUIController.setProfileManager(profileManager);
		deleteProfileUIController.showStage();
	}
    private void openDeleteProgramPopup() {
		if (!checkDevice()) return;
		App app = (App) appsCB.getSelectionModel().getSelectedItem();
		if (app == null) {
			PopupManager.getPopupManager().showError("No App selected");
			return;
		}
		if (deleteProgramUIController == null) {
			deleteProgramUIController = (DeleteProgramUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/DeleteProgramUI.fxml");
		}
		deleteProgramUIController.setProfileManager(profileManager);
		deleteProgramUIController.setDevice(device);
		deleteProgramUIController.setApp(app);
		deleteProgramUIController.showStage();
	}
    /**
     * Opens a popup specified by the url.
     * @param fxmlURL the url of the fxml file to open.
     * @return the controller associated with the fxml file.
     */
    private PopupController openPopup(String fxmlURL) {
		PopupController popupController = PopupManager.getPopupManager().openPopup(fxmlURL);
		if (popupController == null) return null;
		popupController.addNotification(this);
		return popupController;
	}
    /**
     * Checks if a device has been selected and pops an error if it has not.
     * @return true if the device has been selected and false if no device has been selected.
     */
    private boolean checkDevice() {
		if (device == null) {
			PopupManager.getPopupManager().showError("No device selected");
			return false;
		}
		return true;
	}
    /**
     * Updates the UI with the app information.
     * @param app the app to be updated.
     */
    private void updateAppUIInfo(App app){
		if(app != null){
	   		appInfoTA.setText(app.getInfo());
	    	if(app.getAppLogo() == null) appLogoIV.setImage(defaultAppLogoImage);
	    	else appLogoIV.setImage(app.getAppLogo());
	    	if(app.getDevLogo() == null) devLogoIV.setImage(defaultDevLogoImage);
	    	else devLogoIV.setImage(app.getDevLogo());
		}else resetAppUIInfo();
    }
    /**
     * Resets the app ui information.
     */
    private void resetAppUIInfo(){
		appInfoTA.setText("");
		appLogoIV.setImage(defaultAppLogoImage);
		devLogoIV.setImage(defaultDevLogoImage);
		appsCB.getSelectionModel().clearSelection();
		appsCB.setItems(FXCollections.observableArrayList());
    }
    /**
     * Updates the UI with the profile information.
     * @param profile the information to update the UI with.
     */
    private void updateProfileUIInfo(Profile profile){
    	if (profile == null) resetProfileUIInfo();
   		else {
			infoTA.setText(profile.getInfo());
			authorL.setText(profile.getAuthor());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(profile.getLastUpdatedDate());
			SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
			updatedL.setText(date_format.format(cal.getTime()));
			keymapUIController.setDescriptionText(profile.getKeymap(keymapUIController.getSelectedIndex()).getDescription());
		}
    }
    /**
     * Resets the profile information.
     */
    private void resetProfileUIInfo(){
		infoTA.setText("");
		authorL.setText("");
		updatedL.setText("");
		keymapUIController.setDescriptionText("");
		profileCB.valueProperty().removeListener(profileChangeListener);
		profileCB.getSelectionModel().clearSelection();
		profileCB.setItems(FXCollections.observableArrayList());
		profileCB.valueProperty().addListener(profileChangeListener);
    }
    private void createChangeListeners() {
		profileChangeListener = (ov, previousValue, newValue) -> {
			if (ov == profileCB.valueProperty()) {
				Profile getProf = null;
				try {
					getProf = (Profile) profileCB.getSelectionModel().getSelectedItem();
				} catch (Exception e) {
				}
				profileSelected(getProf);
				updateComboBoxes(getAppType());
			}
		};
	}
	/**
	 * Export the profile to a unique file location.
	 */
    private void exportProfile() {
		if (currentProfile == null) {
			PopupManager.getPopupManager().showError("Export failed: No profile selected");
			return;
		}
		File file = kmpFileChooser.showSaveDialog(null);
		if (file != null) {
			String fileName = file.getAbsolutePath(), extension = "";
			int lastIndex = fileName.lastIndexOf(".");
			if (lastIndex > -1) extension = fileName.substring(lastIndex);
			// export to an xml file only
			if (!extension.equals(".xml")) {
				PopupManager.getPopupManager().showError("Export failed. Please save as .xml");
				return;
			}
			if (file.exists()) file.delete();
			//Ensure the currently selected keymap is saved before export
			saveProfile();
			if (!XStreamManager.getStreamManager().writeProfile(file.getAbsolutePath(), currentProfile))
				PopupManager.getPopupManager().showError("Export failed.");
		}else PopupManager.getPopupManager().showError("Export failed: can't find file");
	}
	/**
	 * Imports the profile into the project (if possible).
	 */
    private void importProfile(){
		File file = kmpFileChooser.showOpenDialog(null);
		if(file != null){
			if (!file.exists()) {
				PopupManager.getPopupManager().showError("Import failed: file does not exist.");
				return;
			}
			try {
				Profile profile = XStreamManager.getStreamManager().readProfile(file.getAbsolutePath());
				AppType appType = profile.getAppInfo().getAppType();
				String appName = profile.getAppInfo().getName();
				String deviceName = profile.getAppInfo().getDeviceName();
				//Ensures the profile is being imported to the proper device
				if (!deviceName.equals(getDevice().getDeviceInformation().getName())) {
					PopupManager.getPopupManager().showError("Import failed: wrong device selected.");
					return;
				}
				App app = profileManager.getAppByName(device, appType.toString(), appName);
				if (app == null) {
					profileManager.addApp(device, new App("", null, null, appName, deviceName, appType));
					onOK(null, "AddApp`" + appType.toString() + "`" + appName);
					app = profileManager.getAppByName(device, appType.toString(), appName);
				}
				if (!app.doesProfileExist(profile.getProfileName())) {
					profileManager.addProfile(device, app, profile);
					onOK(null, "AddProfile`" + appType.toString() + "`" + appName + "`" + profile.getProfileName());
				} else PopupManager.getPopupManager().showError("Import failed: profile name exists.");
			}catch (Exception e) {  PopupManager.getPopupManager().showError("Import failed."); }
		}else PopupManager.getPopupManager().showError("Import failed: can't find file");
    }

// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

		defaultAppLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png");
		defaultDevLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png");

		typeCB.setItems(FXCollections.observableArrayList());
		profileCB.setItems(FXCollections.observableArrayList());
		appsCB.setItems(FXCollections.observableArrayList());
		createChangeListeners();

		setButtonToolTip(newAppB, "New Program");
		setButtonToolTip(newProfileB, "New Profile");
		setButtonToolTip(cloneProfileB, "Clone Profile");
		setButtonToolTip(importProfileB, "Import Profile");
		setButtonToolTip(exportProfileB, "Export Profile");
		setButtonToolTip(printPDFB, "Print PDF");
		setButtonToolTip(deleteProfileB, "Delete Profile");
		setButtonToolTip(deleteProgramB, "Delete App");

		pdfChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		pdfChooser.getExtensionFilters().add(extFilter);

		keymapUIController = new KeymapUIController();

		kmpFileChooser = new FileChooser();
		FileChooser.ExtensionFilter kmpExtFilter = new FileChooser.ExtensionFilter("kbmaster profiles (*.xml)", "*.xml");
		kmpFileChooser.getExtensionFilters().add(kmpExtFilter);
	}
    @Override
    public void onOK(Object src, String message) {
		String[] objectNames = message.split("`");
		App appName, currentApp;
		AppType appType, currentAppType;
		switch (objectNames[0]) {
			case "AddApp":
				profileSelected(null);
				appName = profileManager.getAppByName(device, objectNames[1],objectNames[2]);
				appType = appName.getAppType();
				currentAppType = getAppType();
				if (appType != currentAppType)
					typeCB.getSelectionModel().select(typeCB.getSelectionModel().getSelectedIndex() == 0 ? 1 : 0);
				updateComboBoxes(appType);
				appsCB.getSelectionModel().select(appName);
				updateAppUIInfo(appName);
			break;
			case "AddProfile":
				appName = profileManager.getAppByName(device, objectNames[1], objectNames[2]);
				currentApp = (App) appsCB.getSelectionModel().getSelectedItem();
				appType = appName.getAppType();
				currentAppType = getAppType();
				Profile getProf = null;
				int appIndex = profileManager.getRoot(device, appType).getList().indexOf(appName);
				for (Profile profile : profileManager.getRoot(device, appType).getList().get(appIndex).getProfiles()) {
					if (profile.toString().equals(objectNames[3])) {
						getProf = profile;
						break;
					}
				}
				profileSelected(getProf);
				if (appType != currentAppType)
					typeCB.getSelectionModel().select(typeCB.getSelectionModel().getSelectedIndex() == 0 ? 1 : 0);
				updateComboBoxes(appType);
				if (currentApp != appName) {
					appsCB.getSelectionModel().select(appName);
					updateAppUIInfo(appName);
				}
				profileCB.valueProperty().removeListener(profileChangeListener);
				profileCB.getSelectionModel().select(getProf);
				profileCB.valueProperty().addListener(profileChangeListener);
				updateProfileUIInfo(getProf);
			break;
			case "DelApp":
				profileSelected(null);
				updateComboBoxes(getAppType());
			break;
			case "DelProfile":
				profileSelected(null);
				updateComboBoxes(getAppType());
				App app = profileManager.getAppByName(device, objectNames[1],objectNames[2]);
				appsCB.getSelectionModel().select(app);
			break;
			case "ResetKeymap":
				int selectedKeymap = keymapTabPane.getSelectionModel().getSelectedIndex();
				device.setDefaultKeymap(device.getProfile(), selectedKeymap);
				setKeymapDescription(selectedKeymap, "");
				DriverUIController driverUIController = keymapUIController.getDriverUIController(selectedKeymap);
				driverUIController.setSelectedKeymap(device.getProfile().getKeymap(selectedKeymap));
				//break missing on purpose
			case "Save":
				currentProfile.setLastUpdatedDate(Calendar.getInstance().getTimeInMillis());
				saveProfile();
			break;
			default:
				System.out.println("OK "+objectNames[0]);
		}
    }
    @Override
    public void onCancel(Object src, String message) { }
}