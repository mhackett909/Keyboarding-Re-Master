/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.engine.HardwareEngine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;
import com.monkygames.kbmaster.util.PopupManager;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Handles UI Events for the profile panel.
 * @version 1.0
 */
public class NewDeviceUIController implements Initializable, ChangeListener<String>{

// ============= Class variables ============== //
    @FXML
    private ComboBox deviceTypeCB;
    @FXML
    private ComboBox deviceMakeCB;
    @FXML
    private ComboBox deviceNameCB;
    @FXML
    private HBox iconImageHBox;
    @FXML
    private TextArea deviceDescriptionTA;
    @FXML
    private Label driverStatusL;
    @FXML
    private Hyperlink amazonLink;

    private Stage stage;
    /**
     * Used for notifying the main application when a user selects a new
     * device to add to the local account.
     */
    private DeviceMenuUIController deviceMenuUIController;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setStage(Stage stage){
	this.stage = stage;
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	stage.hide();
    }
    public void addEventFired(ActionEvent evt) {
		int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
		String make = (String) deviceMakeCB.getSelectionModel().getSelectedItem();
		String model = (String) deviceNameCB.getSelectionModel().getSelectedItem();
		Device device;
		switch (index) {
			case 2:
				device = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.GAMEPAD, make, model);
				break;
			case 1:
				device = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.MOUSE, make, model);
				break;
			default:
				device = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.KEYBOARD, make, model);
		}
		if (device == null) {
			PopupManager.getPopupManager().showError("Device could not be found");
			return;
		}
		//notify main gui that a device should be added
		deviceMenuUIController.addDevice(device);
		reset();
		stage.hide();
	}
    public void setDeviceMenuUIController(DeviceMenuUIController deviceMenuUIController) {
		this.deviceMenuUIController = deviceMenuUIController;
	}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void resetDeviceInformation(){
	iconImageHBox.getChildren().clear();
	deviceDescriptionTA.setText("");
	driverStatusL.setText("Disconnected");
	amazonLink.setText("unavailable");
	amazonLink.setDisable(true);
	amazonLink.setVisited(false);

    }
    private void reset() {
		deviceTypeCB.valueProperty().removeListener(this);
		deviceMakeCB.valueProperty().removeListener(this);
		deviceNameCB.valueProperty().removeListener(this);

		ObservableList<String> typesList = FXCollections.observableArrayList("Keyboard", "Mouse", "Gamepad");
		deviceTypeCB.setItems(typesList);
		deviceMakeCB.setItems(FXCollections.observableArrayList());
		deviceNameCB.setItems(FXCollections.observableArrayList());

		// ensures that pulldown is not populated.
		deviceTypeCB.getSelectionModel().clearSelection();
		deviceMakeCB.getSelectionModel().clearSelection();

		resetDeviceInformation();

		deviceTypeCB.valueProperty().addListener(this);
		deviceMakeCB.valueProperty().addListener(this);
		deviceNameCB.valueProperty().addListener(this);
	}
    /**
     * Removes all items from the make combo box and populates with new list.
     * Also removes all items form the deviceName list.
     * @param list the new list of items to populate the combo box list.
     */
    private void setMakeComboBox(List<String> list) {
		deviceTypeCB.valueProperty().removeListener(this);
		deviceMakeCB.valueProperty().removeListener(this);
		deviceNameCB.valueProperty().removeListener(this);

		deviceMakeCB.getItems().removeAll();
		deviceNameCB.getItems().removeAll();
		deviceNameCB.setItems(FXCollections.observableArrayList());
		ObservableList<String> observableList = FXCollections.observableArrayList(list);
		deviceMakeCB.setItems(observableList);

		deviceMakeCB.getSelectionModel().clearSelection();

		resetDeviceInformation();

		deviceTypeCB.valueProperty().addListener(this);
		deviceMakeCB.valueProperty().addListener(this);
		deviceNameCB.valueProperty().addListener(this);
	}
    private void setModelComboBox(DeviceType type, String make) {
		deviceTypeCB.valueProperty().removeListener(this);
		deviceMakeCB.valueProperty().removeListener(this);
		deviceNameCB.valueProperty().removeListener(this);

		deviceTypeCB.getItems().removeAll();
		List<String> list = deviceMenuUIController.getDeviceManager().getDriverManager().getDevicesByMake(type, make);
		ObservableList<String> modelsList = FXCollections.observableArrayList(list);
		deviceNameCB.setItems(modelsList);

		resetDeviceInformation();

		deviceTypeCB.valueProperty().addListener(this);
		deviceMakeCB.valueProperty().addListener(this);
		deviceNameCB.valueProperty().addListener(this);
	}
    /**
     * Set the device information in the gui from the specified information.
     * @param type the type of device.
     * @param make the make of the device.
     * @param model the model of the device.
     * @param link the amazon associate link and null if doesn't exist
     */
    private void setDeviceInformation(DeviceType type, String make, String model, String link) {
		// find device
		Device device = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(type, make, model);
		if (device == null) {
			PopupManager.getPopupManager().showError("Unable to find device");
			return;
		}
		iconImageHBox.getChildren().clear();
		ImageView imageView = new ImageView(new Image(device.getDeviceInformation().getDeviceIcon()));
		iconImageHBox.getChildren().add(imageView);
		deviceDescriptionTA.setText(device.getDeviceInformation().getDeviceDescription());
		if (link == null) {
			amazonLink.setText("unavailable");
			amazonLink.setDisable(true);
		} else {
			amazonLink.setText("buy");
			amazonLink.setUserData(link);
			amazonLink.setDisable(false);
		}
		amazonLink.setVisited(false);
		net.java.games.input.Controller[] controllers = HardwareEngine.getControllers(true);
		boolean connected = false;
		for (net.java.games.input.Controller controller : controllers) {
			if (controller.getName().equals(device.getDeviceInformation().getJinputName()))
				connected = true;
		}
		driverStatusL.setText(connected ? "Connected" : "Disconnected");
	}
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
		deviceTypeCB.getItems().removeAll();
		deviceMakeCB.getItems().removeAll();
		deviceNameCB.getItems().removeAll();

		ObservableList<String> typesList = FXCollections.observableArrayList("Keyboard", "Mouse", "Gamepad");
		deviceTypeCB.setItems(typesList);
		deviceMakeCB.setItems(FXCollections.observableArrayList());
		deviceNameCB.setItems(FXCollections.observableArrayList());

		deviceTypeCB.valueProperty().addListener(this);
		deviceMakeCB.valueProperty().addListener(this);
		deviceNameCB.valueProperty().addListener(this);

	}

    @Override
    public void changed(ObservableValue<? extends String> ov, String previousValue, String newValue) {
	if(ov == deviceTypeCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    switch(index) {
			case 2:
				setMakeComboBox(deviceMenuUIController.getDeviceManager().getDriverManager().getGamepadMakes());
				break;
			case 1:
				setMakeComboBox(deviceMenuUIController.getDeviceManager().getDriverManager().getMouseMakes());
				break;
			case 0:
			default:
				setMakeComboBox(deviceMenuUIController.getDeviceManager().getDriverManager().getKeyboardMakes());
		}
	}else if(ov == deviceMakeCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    switch(index) {
			case 2:
				setModelComboBox(DeviceType.GAMEPAD, newValue);
				break;
			case 1:
				setModelComboBox(DeviceType.MOUSE, newValue);
				break;
			default:
				setModelComboBox(DeviceType.KEYBOARD, newValue);
		}
	}else if(ov == deviceNameCB.valueProperty()){
	    int index = deviceTypeCB.getSelectionModel().getSelectedIndex();
	    String make = (String)deviceMakeCB.getSelectionModel().getSelectedItem();
	    String link;
	    switch(index) {
			case 2:
				link = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.GAMEPAD, make, newValue).getDeviceInformation().getAmazonLink();
				setDeviceInformation(DeviceType.GAMEPAD, make, newValue, link);
				break;
			case 1:
				link = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.MOUSE, make, newValue).getDeviceInformation().getAmazonLink();
				setDeviceInformation(DeviceType.MOUSE, make, newValue, link);
				break;
			default:
				link = deviceMenuUIController.getDeviceManager().getDriverManager().getDeviceByType(DeviceType.KEYBOARD, make, newValue).getDeviceInformation().getAmazonLink();
				setDeviceInformation(DeviceType.KEYBOARD, make, newValue, link);
		}
	}
    }
    public void handleAmazonLink(ActionEvent e) {
    	amazonLink.setVisited(false);
    	KeyboardingMaster.gotoWeb((String)amazonLink.getUserData());
    }
}
