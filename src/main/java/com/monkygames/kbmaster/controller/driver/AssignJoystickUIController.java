package com.monkygames.kbmaster.controller.driver;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.util.PopupManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignJoystickUIController extends PopupController implements ChangeListener<String> {
	
	// ============= Class variables ============== //
	@FXML
	private ComboBox mappingCB;
	@FXML
	private TextField descriptionTF;
	@FXML
	private Pane settingsPane;
	private Device device;
	private JoystickController joystickController;
	private Parent joystickParent, disabledParent;
	private Parent currentParent;
	/**
	 * The selected keymap.
	 */
	private Keymap keymap;
	/**
	 * The current button mapping for the selected index.
	 */
	private Output currentOutput;
	/**
	 * The current mapping that is being configured;
	 */
	private Mapping currentMapping;
	private static final String JOYSTICK = "Joystick";
	private static final String DISABLED = "Disabled";
	
	// ============= Public Methods ============== //
	public void setDevice(Device device) {
		this.device = device;
	}
	
	public void okEventFired(ActionEvent evt) {
		// set the mapping
		if (currentParent == joystickParent) {
			currentMapping.setMapping(true);
			currentMapping.setOutput(joystickController.getConfiguredOutput());
		}
		else if (currentParent == disabledParent) {
			currentMapping.setMapping(false);
			currentMapping.setOutput(new OutputDisabled());
		}
		// save description
		currentMapping.getOutput().setDescription(descriptionTF.getText());
		// save profile
		this.notifyOK("Save");
		reset();
	}
	
	public void cancelEventFired(ActionEvent evt) {
		reset();
		notifyCancel(null);
	}
	
	/**
	 * Sets the selected keymap to be configured.
	 *
	 * @param keymap the keymap to be configured.
	 */
	public void setSelectedKeymap(Keymap keymap) {
		this.keymap = keymap;
	}
	
	/**
	 * Set the configuration for the specified button id.
	 *
	 * @param buttonID the unique id of the button to be configured.
	 * @return true if its a success and false otherwise.
	 */
	public boolean setAssignedConfig(int buttonID) {
		// pop a message asking to create a profile
		if (device.getProfile() == null) {
			PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
			return false;
		}
		currentMapping = device.getJoystickMapping(buttonID, keymap);
		currentOutput = currentMapping.getOutput();
		if (currentParent != null) settingsPane.getChildren().remove(currentParent);
		int selectionID = 0;
		// update the configurations
		if (currentOutput instanceof OutputJoystick) {
			currentParent = joystickParent;
			joystickController.setConfiguredOutput(currentOutput);
			selectionID = 0;
		} else if (currentOutput instanceof OutputDisabled) {
			currentParent = disabledParent;
			selectionID = 1;
		}
		if (currentParent != null) {
			settingsPane.getChildren().add(currentParent);
			mappingCB.valueProperty().removeListener(this);
			mappingCB.getSelectionModel().select(selectionID);
			mappingCB.valueProperty().addListener(this);
			resetUI(selectionID);
		}
		descriptionTF.setText(currentOutput.getDescription());
		return true;
	}
	
	// ============= Private Methods ============== //
	private void reset() {
		hideStage();
	}
	
	private void resetUI(int id) {
		System.out.println("Reset UI: "+id);
		//joystickController.setSelectedJoystick(JOYSTICK_NULL);
	}

	// ============= Extended Methods ============== //
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// set up mapping cb
		mappingCB.setItems(FXCollections.observableArrayList(JOYSTICK, DISABLED));
		//mappingCB.getSelectionModel().selectFirst();
		mappingCB.valueProperty().addListener(this);
		try {
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/JoystickPane.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(location);
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			joystickParent = fxmlLoader.load(location.openStream());
			joystickController = fxmlLoader.getController();
		} catch (IOException ex) {
			Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
			URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/DisabledPane.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(location);
			fxmlLoader.setLocation(location);
			fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
			disabledParent = (Parent) fxmlLoader.load(location.openStream());
		} catch (IOException ex) {
			Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
		}
		Tooltip tooltip = new Tooltip();
		tooltip.setText("Mouse Click to type text & Type Enter to exit typing mode");
		descriptionTF.setTooltip(tooltip);
	}
	
	@Override
	public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
		if (currentParent != null) settingsPane.getChildren().remove(currentParent);
		if (newValue.equals(JOYSTICK)) {
			currentParent = joystickParent;
			joystickController.reset();
		}else if (newValue.equals(DISABLED))	currentParent = disabledParent;
		if (currentParent != null) {
			settingsPane.getChildren().add(currentParent);
			if (!descriptionTF.isFocused()) descriptionTF.setEditable(false);
		}
	}
	
	/**
	 * Allows the user to edit the description field.
	 */
	public void handleDescriptionClicked(MouseEvent event) {
		descriptionTF.setEditable(true);

	}
	
	/**
	 * Allows the user to exit the description field.
	 */
	public void handleDescriptionEntered(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			descriptionTF.setEditable(false);
			descriptionTF.deselect();

		}
	}
	
	@Override
	public void showStage() {
		super.showStage();
	}
	
	@Override
	public void setStage(Stage stage) {
		super.setStage(stage);
	
	}
}
