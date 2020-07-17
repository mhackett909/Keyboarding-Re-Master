/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.cloud.DropBoxAccount;
import com.monkygames.kbmaster.cloud.DropBoxApp;
import com.monkygames.kbmaster.driver.DeviceManager;
import com.monkygames.kbmaster.cloud.UserSettings;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.engine.HardwareManager;
import com.monkygames.kbmaster.util.GenerateBindingsImage;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.util.DeviceEntry;
import com.monkygames.kbmaster.util.KBMSystemTray;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.RepeatManager;
import com.monkygames.kbmaster.util.WindowUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Handles UI Events for managing devices.
 * @version 1.0
 */
public class DeviceMenuUIController implements Initializable, EventHandler<ActionEvent>{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    // table
    @FXML
    private TableView<DeviceEntry> deviceTV;
    @FXML
    private TableColumn<DeviceEntry, String> deviceNameCol;
    @FXML
    private TableColumn<DeviceEntry, String> profileNameCol;
    @FXML
    private TableColumn<DeviceEntry, String> isConnectedCol;
    @FXML
    private TableColumn<DeviceEntry, Boolean> isEnabledCol;
    //Field in controller
    private ObservableList deviceList;
    // buttons
    @FXML
    private Button addDeviceB, configureB, detailsB, exitB, logoutB, 
	    setProfileB, hideB, removeDeviceB;
    @FXML
    private CheckBox keysRepeatCB;
    @FXML
    private Label versionL;
    @FXML
    private ImageView kbmIV, linuxGamerIV, javaIV, javafxIV, jinputIV, 
			installBuilderIV, xstreamIV;
    @FXML
    private ImageView accountIcon;
    @FXML
	private Hyperlink updateLink;
    /**
     * Used for displaying a new device popup.
     */
    private Stage newDeviceStage;
    /**
     * Used for displaying a configuration for a device.
     */
    private Stage configureDeviceStage;
    /**
     * Contains the device information.
     */
    private DeviceManager deviceManager;
    /**
     * Used for configuring devices.
     */
    private ConfigureDeviceUIController configureDeviceController;
    /**
     * Used for selecting profiles without having to open the
     * configuration ui.
     */
    private SelectProfileUIController selectProfileController;
    /**
     * A popup for deleting the device.
     */
    private DeleteDeviceUIController deleteDeviceController;
    /**
     * A popup for displaying device information
     */
    private DisplayProfileController displayProfileController;
    /**
     * Used for managing engines (which do the work of remapping outputs).
     */
    private HardwareManager hardwareManager;
    private ProfileManager profileManager;
    private LoginUIController loginController;
    private AboutUIController aboutController;
    private KBMSystemTray systemTray;
    private UserSettings userSettings;
    private DropBoxAccount dropBoxAccount;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setLoginController(LoginUIController loginController){
	this.loginController = loginController;
    }
    /**
     * Adds a device from the NewDeviceUI.
     * @param device the device to be added.
     */
    public void addDevice(Device device){
		if(!deviceManager.downloadDevice(device.getDeviceInformation().getPackageName())){
	    	PopupManager.getPopupManager().showError("Unable to add device. Is it already added?");
	    	return;
		}
		profileManager.addManagedDevice(device);
		updateDevices();
		deviceManager.save();
    }
    /**
     * Removes the device and updates the table.
     */
    public void removeDevice(Device device){
		for (Object entry : deviceList) {
			DeviceEntry deviceEntry = (DeviceEntry) entry;
			if (deviceEntry.getDevice() == device) {
				deviceList.remove(deviceEntry);
				break;
			}
		}
		hardwareManager.removeDevice(device);
		profileManager.removeDevice(device);
		deviceManager.removeDownloadedDevice(device);
		updateDevices();
		deviceManager.save();
    }
    /**
     * Sets the active profile for the specified device.
     */
    public void setActiveProfile(Device device, Profile profile){
		device.setProfile(profile);
		updateDevices();
		deviceManager.save();
		hardwareManager.startPollingDevice(device, profile);
    }
	/**
	 * Returns the device manager. Used in saving.
	 */
	public DeviceManager getDeviceManager() { return deviceManager; }
	/**
	 * Returns the profile manager.
	 */
	public ProfileManager getProfileManager() { return profileManager; }
	/**
	 * Returns the ProfileUIController. Used to bridge engine to UI.
	 */
	public ProfileUIController getProfileUIController() {
	 	return configureDeviceController.getProfileUIController();
	 }
	/**
     * Prepares the gui and databases to populate device list.
     * @param userSettings the settings for this menu
     */
    public void initResources(UserSettings userSettings, DropBoxAccount dropBoxAccount) {
		this.userSettings = userSettings;
		this.dropBoxAccount = dropBoxAccount;
		Image image;
		// manage the icons
		switch (userSettings.loginMethod) {
			case LoginUIController.LOGIN_LOCAL:
				image = new Image("/com/monkygames/kbmaster/fxml/resources/icons/hdd.png");
				accountIcon.setImage(image);
				break;
			case LoginUIController.LOGIN_DROPBOX:
				image = new Image("/com/monkygames/kbmaster/fxml/resources/icons/dropbox.png");
				accountIcon.setImage(image);
				break;
		}
		deviceManager = new DeviceManager(this);
		deviceList = FXCollections.observableArrayList();
		checkUpdates();
		updateDevices();
	}
	public void handleUpdateLink(ActionEvent e) {
		updateLink.setVisited(false);
		KeyboardingMaster.gotoWeb("https://bitbucket.org/vapula87/keyboarding-re-master/src/master");
	}
	private void checkUpdates() {
		try {
			URL updateCheck = new URL("https://bitbucket.org/vapula87/keyboarding-re-master/src/master/VERSION");
			BufferedReader read = new BufferedReader(new InputStreamReader(updateCheck.openStream()));
			String readURL, latestVersion = null;
			while ((readURL = read.readLine()) != null) {
				Pattern regexPattern = Pattern.compile("\\d.*.*\\d");
				Matcher regexMatcher = regexPattern.matcher(readURL);
				if (regexMatcher.find()) latestVersion = regexMatcher.group();
				break;
			}
			read.close();
			if (!latestVersion.equals(KeyboardingMaster.VERSION)) updateLink.setText("Updates Available");
		} catch (MalformedURLException e) {
			System.out.println("Update check failed: Incorrect URL.");
		} catch (IOException e) {
			System.out.println("Update check failed: Error reading remote file.");
		} catch (NullPointerException e) {
			System.out.println("Update check failed: Could not retrieve latest version.");
		}
	}
    /**
     * One or more devices has changed status and the UI
     * will be updated to reflect these changes. Also updates
	 * the device descriptors and adds managed devices to the
	 * hardware manager.
     */
	public void updateDevices() {
		for (Device device : deviceManager.getInstalledDevices()) {
			if(!hardwareManager.isDeviceManaged(device)) {
				hardwareManager.addManagedDevice(device);
				deviceList.add(new DeviceEntry(device));
			}
			deviceManager.updateDescriptor(device);
		}
		for (Object entry : deviceList) {
			DeviceEntry deviceEntry = (DeviceEntry) entry;
			Device entryDevice = deviceEntry.getDevice();
			String entryConnected = entryDevice.isConnected() ? "Yes" : "No", entryProfile;
			try {
				entryProfile = entryDevice.getProfile().getProfileName();
			}catch (NullPointerException e) { entryProfile = "None Selected"; }
			boolean isModified = false;
			if (!deviceEntry.getRealProfileName().equals(entryProfile)) {
				deviceEntry.setProfile(entryDevice.getProfile());
				isModified = true;
			}
			if (deviceEntry.enabledProperty().getValue() != entryDevice.isEnabled()) {
				deviceEntry.setEnabled(entryDevice.isEnabled());
				isModified = true;
			}
			if (!deviceEntry.getIsConnected().equals(entryConnected)) {
				deviceEntry.setConnected(entryConnected);
				isModified = true;
			}
			if (isModified) deviceTV.refresh();
		}
		deviceTV.setItems(deviceList);
	}
    /**
     * Shutsdown all engines and exits the program.
     */
    public void exitApplication() {
		cleanUp();
		if (dropBoxAccount != null) {
			loginController.hideDeviceMenu(false);
			// pop cloud sync UI
			KeyboardingMaster.getInstance().endDropboxSync(false, dropBoxAccount);
		} else {
			KeyboardingMaster.getInstance().exit();
		}
	}
    public void showKBMAboutFromNonJavaFXThread() {
		Platform.runLater(() -> {
			initializeAboutController();
			aboutController.showAbout(AboutUIController.AboutType.KBM);
		});
	}
    public void showDeviceUI() {
		loginController.showDeviceMenuFromNonJavaFXThread();
	}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt) {
		Object src = evt.getSource();
		if (src == addDeviceB) 			openNewDeviceUI();
		 else if (src == removeDeviceB) openRemoveDeviceUI();
		 else if (src == configureB) 	openConfigureDeviceUI();
		 else if (src == keysRepeatCB) 	handleKeysRepeat();
		 else if (src == exitB) 		exitApplication();
		 else if (src == setProfileB) 	openSelectProfileUI();
		 else if (src == detailsB)		openDetailsUI();
		 else if (src == logoutB) 		logout();
		 else if (src == hideB) {
			if (KBMSystemTray.isSupported()) loginController.hideDeviceMenu(false);
			else PopupManager.getPopupManager().showError("KBM SystemTray not supported.");
		}
	}
    @FXML
    public void handleAboutEvent(MouseEvent evt) {
		initializeAboutController();
		Object src = evt.getSource();
		if (src == kbmIV)
			aboutController.showAbout(AboutUIController.AboutType.KBM);
		else if (src == linuxGamerIV)
			aboutController.showAbout(AboutUIController.AboutType.LINUXGAMER);
		else if (src == javaIV)
			aboutController.showAbout(AboutUIController.AboutType.JAVA);
		else if (src == javafxIV)
			aboutController.showAbout(AboutUIController.AboutType.JAVAFX);
		else if (src == jinputIV)
			aboutController.showAbout(AboutUIController.AboutType.JINPUT);
		else if (src == installBuilderIV)
			aboutController.showAbout(AboutUIController.AboutType.INSTALLBUILDER);
		else if (src == xstreamIV)
			aboutController.showAbout(AboutUIController.AboutType.XSTREAM);
	}
    /**
     * Initializes the about controller if it doesn't already exist.
     */
    private void initializeAboutController() {
		if (aboutController == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/AboutUI.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = fxmlLoader.load(location.openStream());
				aboutController = fxmlLoader.getController();
				Stage stage = WindowUtil.createStage(root);
				aboutController.setStage(stage);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
    /**
     * Sets the xset r to on or off depending on the value of the
     * checkbox.
     */
    private void handleKeysRepeat() {
		RepeatManager.setRepeat(keysRepeatCB.isSelected());
	}
    /**
     * Opens a new device UI for adding a new device.
     */
    private void openNewDeviceUI() {
		if (newDeviceStage == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/NewDeviceUI.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = fxmlLoader.load(location.openStream());
				NewDeviceUIController controller = fxmlLoader.getController();
				newDeviceStage = WindowUtil.createStage(root);
				controller.setStage(newDeviceStage);
				controller.setDeviceMenuUIController(this);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		newDeviceStage.show();
	}
    /**
     * Opens a UI for removing the device.
     */
    private void openRemoveDeviceUI() {
		DeviceEntry deviceEntry = getDeviceEntry();
		if (deviceEntry == null) {
			PopupManager.getPopupManager().showError("No device selected");
			return;
		}
		if (deleteDeviceController == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/DeleteDeviceUI.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = fxmlLoader.load(location.openStream());
				deleteDeviceController = fxmlLoader.getController();
				Stage stage = WindowUtil.createStage(root);
				deleteDeviceController.setStage(stage);
				deleteDeviceController.setController(this);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		if (deviceEntry.getDevice() != null)
			deleteDeviceController.setDevice(deviceEntry.getDevice());

	}
    private void openConfigureDeviceUI() {
		DeviceEntry deviceEntry = getDeviceEntry();
		if (deviceEntry == null) {
			PopupManager.getPopupManager().showError("No device selected");
			return;
		}
		if (configureDeviceStage == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/ConfigureDeviceUI.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = fxmlLoader.load(location.openStream());
				configureDeviceController = fxmlLoader.getController();
				configureDeviceStage = WindowUtil.createStage(root);
				configureDeviceController.setStage(configureDeviceStage);
				configureDeviceController.getProfileUIController().setProfileManager(profileManager);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		configureDeviceController.setDevice(deviceEntry.getDevice());
		configureDeviceStage.show();

	}
    private void openSelectProfileUI() {
		DeviceEntry deviceEntry = getDeviceEntry();
		if (deviceEntry == null) {
			PopupManager.getPopupManager().showError("No device selected");
			return;
		}
		if (selectProfileController == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/SelectProfile.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = fxmlLoader.load(location.openStream());
				selectProfileController = fxmlLoader.getController();
				Stage stage = WindowUtil.createStage(root);
				selectProfileController.setStage(stage);
				selectProfileController.setProfileManager(profileManager);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		selectProfileController.setDevice(deviceEntry.getDevice());
		selectProfileController.show();
	}
    private void openDetailsUI() {
		DeviceEntry deviceEntry = getDeviceEntry();
		if (deviceEntry == null) {
			PopupManager.getPopupManager().showError("No device selected");
			return;
		}
		if (displayProfileController == null) {
			try {
				URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/DisplayProfile.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(location);
				fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
				Parent root = (Parent) fxmlLoader.load(location.openStream());
				displayProfileController = fxmlLoader.getController();
				Stage stage = WindowUtil.createStage(root);
				displayProfileController.setStage(stage);
			} catch (IOException ex) {
				Logger.getLogger(ConfigureDeviceUIController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		GenerateBindingsImage generator = new GenerateBindingsImage(deviceEntry.getDevice());
		displayProfileController.setGenerateBindingsImage(generator);
		ObservableList<App> apps;
		if (deviceEntry.getDevice().getProfile() == null) {
			PopupManager.getPopupManager().showError("No profile selected");
			return;
		}
		if (deviceEntry.getDevice().getProfile().getAppInfo().getAppType() == AppType.APPLICATION)
			apps = FXCollections.observableArrayList(profileManager.getAppsRoot(deviceEntry.getDevice()).getList());
		else apps = FXCollections.observableArrayList(profileManager.getGamesRoot(deviceEntry.getDevice()).getList());
		App appName = null;
		for (App app : apps) {
			if (app.toString().equals(deviceEntry.getDevice().getProfile().getAppInfo().getName())) {
				appName = app;
				break;
			}
		}
		displayProfileController.displayDevice(deviceEntry.getDevice(), appName);
	}

// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		versionL.setText(KeyboardingMaster.VERSION);
		hardwareManager = new HardwareManager(this);
		profileManager = new ProfileManager(this);
		deviceNameCol.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
		profileNameCol.setCellValueFactory(new PropertyValueFactory<>("profileName"));
		isConnectedCol.setCellValueFactory(new PropertyValueFactory<>("isConnected"));
		isEnabledCol.setCellValueFactory(new PropertyValueFactory<>("enabled"));

		CheckboxCallback callback = new CheckboxCallback();
		callback.setCheckboxHandler(this);
		isEnabledCol.setCellFactory(callback);
		isEnabledCol.setEditable(true);

		// set the table cell to center for isConnected
		isConnectedCol.setCellFactory(param -> {
			TableCell cell = new TableCell() {
				@Override
				public void updateItem(Object item, boolean empty) {
					if (item != null) setText(item.toString());

				}
			};

			//adding style class for the cell
			cell.getStyleClass().add("table-cell-center");
			return cell;
		});
		deviceTV.setEditable(true);
		systemTray = new KBMSystemTray(this);
	}

	/**
	 * Returns the selected device. If no device is selected, returns
	 * the first managed device. If no managed device exists, returns
	 * null.<br><br>
	 *
	 * Patch by FZ (modified)
	 */
	 private DeviceEntry getDeviceEntry() {
		 DeviceEntry deviceEntry = deviceTV.getSelectionModel().getSelectedItem();
		 if (deviceEntry == null && deviceTV.getItems().size() > 0) deviceEntry = deviceTV.getItems().get(0);
		 return deviceEntry;
	 }
	/**
     * Logs out of this device menu.
     * Disables all devices.
     */
    private void logout() {
		cleanUp();
		keysRepeatCB.setSelected(true);
		if (dropBoxAccount != null) {
			clearAccessToken();
			loginController.hideDeviceMenu(false);
			KeyboardingMaster.getInstance().endDropboxSync(true, dropBoxAccount);
		} else loginController.hideDeviceMenu(true);
	}
	private void clearAccessToken() {
		DropBoxApp.ACCESS_TOKEN = "";
		KeyboardingMaster.getUserSettings().accessToken = "";
		KeyboardingMaster.saveUserSettings();
	}
    /**
     * Closes all databases, frees memory, and prepares this gui to be closed.
     */
    private void cleanUp(){
		RepeatManager.setRepeat(true);
		hardwareManager.close();
    	profileManager.close();
    	deviceManager.close();
    }
// ============= Extended Methods ============== //
    @Override
    public void handle(ActionEvent t) {
		CheckBoxTableCell cell = (CheckBoxTableCell) t.getSource();
		Parent parent = cell.getParent();
		if (!(parent instanceof TableRow)) return;
		TableRow row = (TableRow) parent;
		DeviceEntry deviceEntry = (DeviceEntry) row.getItem();
		Device device = deviceEntry.getDevice();
		// traverse through scene graph to get checkbox.
		Node node = cell.getChildrenUnmodifiable().get(0);
		if (node == null && !(node instanceof CheckBox)) return;
		CheckBox checkBox = (CheckBox) node;
		if (!device.isEnabled()) {
			if (device.getProfile() == null) {
				PopupManager.getPopupManager().showError("No profile selected");
				checkBox.selectedProperty().set(false);
				return;
			} else if (!device.isConnected()) {
				PopupManager.getPopupManager().showError("Device not connected.");
				checkBox.selectedProperty().set(false);
				return;
			}
			device.setEnabled(true);
			hardwareManager.startPollingDevice(device, device.getProfile());
		} else {
			device.setEnabled(false);
			hardwareManager.disableDevice(device);
		}
		// something has changed
		updateDevices();
		deviceManager.save();
	}
// ============= Internal Classes ============== //
    public class CheckboxCallback implements Callback<TableColumn<DeviceEntry,Boolean>, TableCell<DeviceEntry,Boolean>> {
	private EventHandler checkBoxHandler;

	@Override
	public TableCell call(TableColumn<DeviceEntry, Boolean> param) {
	    CheckBoxTableCell cell = new CheckBoxTableCell(){
		public CheckBox checkBox;
	    };
	    //adding style class for the cell
	    cell.getStyleClass().add("table-cell-center");
	    cell.addEventHandler(ActionEvent.ACTION, checkBoxHandler);
	    return cell;
	}
	public void setCheckboxHandler(EventHandler checkBoxHandler){
	    this.checkBoxHandler = checkBoxHandler;
	}
    }
    public class CheckboxValueCallback implements Callback<Integer, ObservableValue<Boolean>> { 
	private TableColumn<DeviceEntry, Boolean> tableColumn;
	@Override
	public ObservableValue<Boolean> call(Integer p) {
	    return tableColumn.getCellObservableValue(p);
	}
    }
}
