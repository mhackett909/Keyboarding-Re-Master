/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === javafx imports === //
import com.monkygames.kbmaster.controller.driver.AssignInputUIController;
import com.monkygames.kbmaster.controller.driver.AssignJoystickUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.util.PopupManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class DriverUIController implements Initializable {
	
	// ============= Class variables ============== //
	@FXML
	private Pane rootPane;
	private AssignInputUIController assignInputUIController = null;
	private AssignJoystickUIController assignJoystickUIController = null;
	// ============= Constructors ============== //
	// ============= Public Methods ============== //
	
	/**
	 * Adds a notification of popup (ok/cancel) specifically if the
	 * profile should be saved.
	 *
	 * @parma popupNotification the interface to be notified if the profile
	 * should be saved.
	 */
	public void addSaveNotification(PopupNotifyInterface popupNotification) {
		assignInputUIController.addNotification(popupNotification);
		assignJoystickUIController.addNotification(popupNotification);
	}
	
	/**
	 * Sets the selected keymap.
	 *
	 * @param keymap the selected keyamp.
	 */
	public void setSelectedKeymap(Keymap keymap) {
		assignInputUIController.setSelectedKeymap(keymap);
		assignJoystickUIController.setSelectedKeymap(keymap);
	}
	
	public void setDevice(Device device) {
		assignInputUIController.setDevice(device);
		assignJoystickUIController.setDevice(device);
	}
	
	// ============= Private Methods ============== //
	@FXML
	private void handleButtonAction(ActionEvent evt) {
		Object obj = evt.getSource();
		if (obj instanceof Button) {
			Button button = (Button) obj;
			int buttonID = Integer.parseInt(button.getText());
			openAssignInputPopup(buttonID);
		}
	}
	@FXML
	private void handleJoystickAction(ActionEvent evt) {
		Object obj = evt.getSource();
		if (obj instanceof Button) {
			Button button = (Button) obj;
			int buttonID = Integer.parseInt(button.getText());
			openAssignJoystickPopup(buttonID);
		}
	}
	
	/**
	 * Sets the assigned button and opens the AssignInputPopup.
	 * @param buttonID the id of the button to configure.
	 */
	private void openAssignInputPopup(int buttonID) {
		if (assignInputUIController.setAssignedConfig(buttonID))
			assignInputUIController.showStage();
	}
	/**
	 * Opens the AssignJoystickPopup.
	 * @param buttonID the id of the joystick to configure.
	 */
	private void openAssignJoystickPopup(int buttonID) {
		if (assignJoystickUIController.setAssignedConfig(buttonID))
			assignJoystickUIController.showStage();
	}
	
	// ============= Implemented Methods ============== //
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		if (assignInputUIController == null) {
			assignInputUIController = (AssignInputUIController) PopupManager.getPopupManager().openPopup("/com/monkygames/kbmaster/fxml/driver/AssignInputUI.fxml");
			if (assignInputUIController == null) return;
		}
		if (assignJoystickUIController == null) {
			assignJoystickUIController = (AssignJoystickUIController) PopupManager.getPopupManager().openPopup("/com/monkygames/kbmaster/fxml/driver/AssignJoystickUI.fxml");
			if (assignJoystickUIController == null) return;
		}
	}
	
	
}