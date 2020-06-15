/* 
 * See COPYING in top-level directory.
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
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
// === kbmaster imports === //
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.io.BindingPDFWriter;
import com.monkygames.kbmaster.io.GenerateBindingsImage;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.util.KeymapUIManager;
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
public class ProfileUIController implements Initializable, ChangeListener<String>, PopupNotifyInterface{
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
    private File profileDir;
    public static final String profileDirS = "profiles";
    private Device device;
    /**
     * Used for selecting a file to write a pdf binding.
     */
    private FileChooser pdfChooser;
    private TabPane keymapTabPane;
    private KeymapUIManager keymapUIManager;
    /**
     * The currently used profile.
     */
    private Profile currentProfile;
    /**
     * True if its the first time a new device has been set.
     * Used to not re-initialize the device GUI.
     */
    private boolean isNewDevice = true;
    private DeviceMenuUIController deviceMenuController;
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
    /**
     * Used for importing/exporting files.
     */
    private FileChooser kmpFileChooser;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void profileEventFired(ActionEvent evt){
	Object src = evt.getSource();
	if(src == newProfileB){
	    openNewProfilePopup();
	}else if(src == newAppB){
	    openNewProgramPopup();
	}else if(src == cloneProfileB){
	    openCloneProfilePopup();
	}else if(src == importProfileB){
	    importProfile();
	}else if(src == exportProfileB){
	    exportProfile();
	}else if(src == printPDFB){
	    openPDFPopup();
	}else if(src == deleteProfileB){
	    openDeleteProfilePopup();
	}else if(src == deleteProgramB){
	    openDeleteProgramPopup();
	}
	
    }
    public void setKeymapTabPane(TabPane keymapTabPane){
	this.keymapTabPane = keymapTabPane;
	keymapUIManager.setTabPane(keymapTabPane);
    }
    /**
     * Sets the device in order to get device name and to be used in
     * popups for creating profiles.
     * @param device the device to be set.
     */
    public void setDevice(Device device){
	// first clear everything

	if (this.device == device) return;
	resetAppUIInfo();
	resetProfileUIInfo();
	appsCB.valueProperty().removeListener(appChangeListener);
	appsCB.setItems(FXCollections.observableArrayList());
	profileCB.valueProperty().removeListener(profileChangeListener);
	profileCB.setItems(FXCollections.observableArrayList());
	appsCB.valueProperty().addListener(appChangeListener);
	profileCB.valueProperty().addListener(profileChangeListener);

	this.device = device;
	currentProfile = device.getProfile();
	isNewDevice = true;
	String deviceName = device.getDeviceInformation().getProfileName();
	profileManager = new ProfileManager(profileDir+File.separator+deviceName);
	profileManager.setUIController(this);
	if(newProfileUIController != null){
	    newProfileUIController.setProfileManager(profileManager);
	    newProfileUIController.setDevice(device);
	}

	typeCB.valueProperty().removeListener(this);
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	if (currentProfile == null)	typeCB.getSelectionModel().selectFirst();
	else {
		switch (currentProfile.getAppInfo().getAppType()) {
			case GAME:
				typeCB.getSelectionModel().select(0);
				break;
			default:
				typeCB.getSelectionModel().select(1);
		}
	}
	typeCB.valueProperty().addListener(this);

	//  set profile on the keymaps
	// set device is required before calling initialize tabs.

	keymapUIManager.setDevice(device);
	keymapUIManager.initializeTabs();
	keymapUIManager.setProfile(currentProfile);
	keymapUIManager.setProfileUIController(this);
	keymapUIManager.addSaveNotification(this);
	updateComboBoxes();
    }
    /**
     * Set the description label for keymaps.
     */
    public void setDescriptionLabel(Label descriptionLabel){ keymapUIManager.setLabel(descriptionLabel); }

	/**
	 * Set the description for the currently selected keymap.
	 * @param keymapID the id of the keymap to set the description.
	 * @param description the description of the keymap.
	 */
	public void setKeymapDescription(int keymapID, String description){
		if(currentProfile != null){
			currentProfile.getKeymap(keymapID).setDescription(description);
			keymapUIManager.setDescriptionText(description);
		}
		else PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
	}
	/**
	 * Returns the KeymapUIManager
	 */
	public KeymapUIManager getKeymapUIManager() { return keymapUIManager; }
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
	 * Note, this needs to be called before other methods.
	 */
	public void setDeviceMenuController(DeviceMenuUIController deviceMenuController){
		this.deviceMenuController = deviceMenuController;
	}
	/**
	 * The profiles combo box selected a new profile.
	 */
    public void profileSelected(){
		Profile selectedProfile;
		selectedProfile = (Profile)profileCB.getSelectionModel().getSelectedItem();
		if (selectedProfile != currentProfile) saveProfile();
	   	currentProfile = selectedProfile;
		device.setProfile(currentProfile);
	    keymapUIManager.setProfile(currentProfile);
	    updateProfileUIInfo(currentProfile);
		deviceMenuController.setActiveProfile(device, currentProfile);
    }
	/**
	 * Updates only the profile ComboBox.
	 */
	public void updateProfilesComboBox(){
		App app;
		ObservableList<Profile> profiles;
		app = (App)appsCB.getSelectionModel().getSelectedItem();
		if(app == null) {
			resetAppUIInfo();
			resetProfileUIInfo();
			profileSelected();
			return;
		}
		updateAppUIInfo(app);
		profileCB.valueProperty().removeListener(profileChangeListener);
		profiles = FXCollections.observableArrayList(app.getProfiles());
		if(profiles.size() > 0){
			profileCB.setItems(profiles);
			profileCB.getSelectionModel().selectFirst();
		}
		else resetProfileUIInfo();
		profileSelected();
		profileCB.valueProperty().addListener(profileChangeListener);
	}
    /**
     * Updates the type, programs, and profiles combo boxes.
     */
    public void updateComboBoxes(){
		if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    	updateComboBoxesOnType(AppType.GAME);
		}else{
	   	 	updateComboBoxesOnType(AppType.APPLICATION);
		}
    }

	/**
	 * Saves the current profile to disk.
	 */
	public void saveProfile() {
		if (currentProfile != null) currentProfile.setDefaultKeymap(keymapTabPane.getSelectionModel().getSelectedIndex());
		device.setProfile(currentProfile);
		profileManager.updateProfile(currentProfile);
		deviceMenuController.setActiveProfile(device, currentProfile);
    }

// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Updates the combo box by type and always selects the first program
     * to populate the profiles list.
     * @param type the type of profile to sort on.
     */
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	ObservableList<Profile> profiles;
	Root root = profileManager.getRoot(type);
	if(root.getList().isEmpty()){
	    // clear the apps combo box
	    appsCB.valueProperty().removeListener(appChangeListener);
	    appsCB.setItems(FXCollections.observableArrayList());
	    profileCB.valueProperty().removeListener(profileChangeListener);
	    profileCB.setItems(FXCollections.observableArrayList());
	    profileCB.valueProperty().addListener(profileChangeListener);
	    appsCB.valueProperty().addListener(appChangeListener);
	    resetAppUIInfo();
	    resetProfileUIInfo();
	    profileSelected();
	    return;
	}
	apps = FXCollections.observableArrayList(root.getList());
	appsCB.valueProperty().removeListener(appChangeListener);

	if(apps.size() > 0){
		appsCB.setItems(apps);
		int selectedIndex = -1;
		if (currentProfile != null) {
			for (int tracker = 0; tracker < apps.size(); tracker++)
				if (apps.get(tracker).getName().equals(currentProfile.getAppInfo().getName())) {
					selectedIndex = tracker;
					break;
				}
		}
		if (selectedIndex == -1) {
			currentProfile = null;
			selectedIndex = 0;
		}
		appsCB.valueProperty().addListener(appChangeListener);
		appsCB.getSelectionModel().select(selectedIndex);
	}else {
		resetAppUIInfo();
		resetProfileUIInfo();
		profileSelected();
		return;
	}
	/**
	profileCB.valueProperty().removeListener(profileChangeListener);
	if(profiles.size() == 0) {
		resetAppUIInfo();
		resetProfileUIInfo();
	}
	else{
		profileCB.setItems(profiles);
		if (currentProfile == null) profileCB.getSelectionModel().selectFirst();
		else profileCB.getSelectionModel().select(currentProfile);
	}
	profileSelected();
	profileCB.valueProperty().addListener(profileChangeListener);

	 */
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
    private void openNewProgramPopup(){
	if(!checkDevice()) return;
	if(newProgramUIController == null){
	    try {
		// pop open add new device
		URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewProgramUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = (Parent)fxmlLoader.load(location.openStream());
		newProgramUIController = (NewProgramUIController) fxmlLoader.getController();
		Stage stage = WindowUtil.createStage(root);
		newProgramUIController.setStage(stage);
		newProgramUIController.addNotification(this);
	    } catch (IOException ex) {
		return;
	    }
	}
	newProgramUIController.setProfileManager(profileManager);
	newProgramUIController.showStage();
	newProgramUIController.setAppType(typeCB.getSelectionModel().getSelectedIndex());

    }
    /**
     * Opens a new profile popup.
     */
    private void openNewProfilePopup(){
	if(!checkDevice()) return;
	// check if an app is selected
	if(appsCB.getSelectionModel().getSelectedItem() == null){
	    PopupManager.getPopupManager().showError("No App selected.\nMaybe create a new App?");
	    return;
	}
	if(newProfileUIController == null){
	    newProfileUIController = (NewProfileUIController)openPopup("/com/monkygames/kbmaster/fxml/popup/NewProfileUI.fxml");
	}
	newProfileUIController.setDevice(device);
	newProfileUIController.setProfileManager(profileManager);
	newProfileUIController.showStage();
	newProfileUIController.setAppType(typeCB.getSelectionModel().getSelectedIndex());
	newProfileUIController.setApp(appsCB.getSelectionModel().getSelectedIndex());
    }
    private void openCloneProfilePopup(){
	if(!checkDevice()) return;
	// check if a profile has been selected
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	if(cloneProfileUIController == null){
	    cloneProfileUIController = (CloneProfileUIController) openPopup("/com/monkygames/kbmaster/fxml/popup/CloneProfileUI.fxml");
	}
	cloneProfileUIController.setDevice(device);
	cloneProfileUIController.setProfileManager(profileManager);
	cloneProfileUIController.showStage();
    }
    private void openPDFPopup(){
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	File file = pdfChooser.showSaveDialog(null);
	if(file != null){
	    GenerateBindingsImage generator = new GenerateBindingsImage(device);
	    String header = profile.getAppInfo().getName() + " / " + profile.getProfileName();
	    String footer = device.getDeviceInformation().getMake()+" "+
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
    private void openDeleteProfilePopup(){
	if(!checkDevice()) return;
	Profile profile = (Profile)profileCB.getSelectionModel().getSelectedItem();
	if(profile == null){
	    PopupManager.getPopupManager().showError("No Profile selected");
	    return; 
	}
	if(deleteProfileUIController == null){
	    deleteProfileUIController = (DeleteProfileUIController)openPopup("/com/monkygames/kbmaster/fxml/popup/DeleteProfileUI.fxml");
	}
	deleteProfileUIController.setProfile(profile);
	deleteProfileUIController.setProfileManager(profileManager);
	deleteProfileUIController.showStage();
    }
    private void openDeleteProgramPopup(){
	if(!checkDevice()) return;
	App app = (App)appsCB.getSelectionModel().getSelectedItem();
	if(app == null){
	    PopupManager.getPopupManager().showError("No App selected");
	    return; 
	}
	if(deleteProgramUIController == null){
	    deleteProgramUIController = (DeleteProgramUIController)openPopup("/com/monkygames/kbmaster/fxml/popup/DeleteProgramUI.fxml");
	}
	deleteProgramUIController.setProfileManager(profileManager);
	deleteProgramUIController.setApp(app);
	deleteProgramUIController.showStage();
    }
    /**
     * Opens a popup specified by the url.
     * @param fxmlURL the url of the fxml file to open.
     * @return the controller associated with the fxml file.
     */
    private PopupController openPopup(String fxmlURL){
	PopupController popupController = PopupManager.getPopupManager().openPopup(fxmlURL);
	if(popupController == null) return null;
	popupController.addNotification(this);
	return popupController;
    }
    /**
     * Checks if a device has been selected and pops an error if it has not.
     * @return true if the device has been selected and false if no device has been selected.
     */
    private boolean checkDevice(){
	if(device == null){
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
	    if(app.getAppLogo() == null){
		appLogoIV.setImage(defaultAppLogoImage);
	    }else{
		appLogoIV.setImage(app.getAppLogo());
	    }
	    if(app.getDevLogo() == null){
		devLogoIV.setImage(defaultDevLogoImage);
	    }else{
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
		appsCB.setItems(FXCollections.observableArrayList());
		appsCB.getSelectionModel().select(null);
    }
    /**
     * Updates the UI with the profile information.
     * @param profile the information to update the UI with.
     */
    private void updateProfileUIInfo(Profile profile){
    	if (profile == null) {
    		resetProfileUIInfo();
    		return;
		}
		infoTA.setText(profile.getInfo());
		authorL.setText(profile.getAuthor());
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(profile.getLastUpdatedDate());
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd");
		updatedL.setText(date_format.format(cal.getTime()));
    }
    /**
     * Resets the profile information.
     */
    private void resetProfileUIInfo(){
		infoTA.setText("");
		authorL.setText("");
		updatedL.setText("");
		profileCB.setItems(FXCollections.observableArrayList());
		profileCB.getSelectionModel().select(null);
		keymapUIManager.setDescriptionText("");

    }
    private void createChangeListeners(){
	appChangeListener = (ov, previousValue, newValue) -> {
		if(ov == appsCB.valueProperty()) updateProfilesComboBox();
	};
	profileChangeListener = (ov, previousValue, newValue) -> {
		if(ov == profileCB.valueProperty()) profileSelected();
	};
    }
	@Override
	public void changed(ObservableValue<? extends String> ov,  String previousValue, String newValue) {
		if(ov == typeCB.valueProperty()) updateComboBoxes();
	}
    /**
     * Opens a file selector and writes the profile out.
     */
    private void exportProfile(){
	File file = kmpFileChooser.showSaveDialog(null);
	if(file != null){
	    profileManager.exportProfile(file, currentProfile);
	}

    }
    /**
     * Opens a file selector for importing a profile.
     */
    private void importProfile(){
	File file = kmpFileChooser.showOpenDialog(null);
	if(file != null){
	    if(!profileManager.importProfile(file)){
		PopupManager.getPopupManager().showError("Import failed");
	    }
	    // update comboboxes
	    updateComboBoxes();
	}else{
	    PopupManager.getPopupManager().showError("Import failed: can't find file");
	}
    }
	/**
	 * Finds and returns an application. Null if no matching application found.
	 *
	 */
	 public App getAppByName(String appName) {
	 	for (Object app : appsCB.getItems()) {
			if (((App) app).getName().equals(appName))
				return (App) app;
		}
		return null;
	 }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	//profileManager = new ProfileManager("local.db4o");
	defaultAppLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/app_logo.png");
	defaultDevLogoImage = new Image("/com/monkygames/kbmaster/fxml/resources/profile/dev_logo.png");
	
	typeCB.setItems(FXCollections.observableArrayList());
	profileCB.setItems(FXCollections.observableArrayList());
	appsCB.setItems(FXCollections.observableArrayList());
	createChangeListeners();

	profileDir = new File(profileDirS);
	if(!profileDir.exists()){
	    profileDir.mkdir();
	}
	
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

	keymapUIManager = new KeymapUIManager();

	kmpFileChooser = new FileChooser();
	FileChooser.ExtensionFilter kmpExtFilter = new FileChooser.ExtensionFilter("kbmaster profiles (*.xml)","*.xml");
	kmpFileChooser.getExtensionFilters().add(kmpExtFilter);

    }



// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void onOK(Object src, String message) {
		int index = 0;
		String[] objectNames = message.split("`");
		if (objectNames[0].equals("Save")) saveProfile();
		else if (objectNames[0].equals("AddApp")) {
			updateComboBoxes();
			String currentAppType = typeCB.getSelectionModel().getSelectedItem().toString().toLowerCase();
			if (!objectNames[1].toLowerCase().equals(currentAppType)) {
				switch (typeCB.getSelectionModel().getSelectedIndex()) {
					case 0:
						typeCB.getSelectionModel().select(1);
						break;
					default:
						typeCB.getSelectionModel().select(0);
				}
			}
			App appName = getAppByName(objectNames[2]);
			for (; index < appsCB.getItems().size(); index++)
				if (appsCB.getItems().get(index) == appName)
					break;
			appsCB.getSelectionModel().select(index);
		} else if (objectNames[0].equals("AddProfile")) {
			updateProfilesComboBox();
			String currentAppType = typeCB.getSelectionModel().getSelectedItem().toString().toLowerCase();
			if (!objectNames[1].toLowerCase().equals(currentAppType)) {
				switch (typeCB.getSelectionModel().getSelectedIndex()) {
					case 0:
						typeCB.getSelectionModel().select(1);
						break;
					default:
						typeCB.getSelectionModel().select(0);
				}
			}
			App currentApp = (App) appsCB.getSelectionModel().getSelectedItem();
			App appName = getAppByName(objectNames[2]);
			if (currentApp != appName) {
				for (; index < appsCB.getItems().size(); index++)
					if (appsCB.getItems().get(index) == appName)
						break;
				appsCB.getSelectionModel().select(index);
			}
			index = 0;
			for (; index < profileCB.getItems().size(); index++)
				if (profileCB.getItems().get(index).toString().equals(objectNames[3]))
					break;
			profileCB.getSelectionModel().select(index);
		} else if (objectNames[0].equals("DelApp")) {
			updateComboBoxes();
			appsCB.getSelectionModel().select(index);
		} else if (objectNames[0].equals("DelProfile")) {
			updateProfilesComboBox();
			profileCB.getSelectionModel().select(index);
		}
		else System.out.println("OK " + message + " : " + objectNames[0]);
    }

    @Override
    public void onCancel(Object src, String message) {
	// do nothing for now
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