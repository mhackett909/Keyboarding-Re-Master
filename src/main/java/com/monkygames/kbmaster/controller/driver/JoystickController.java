package com.monkygames.kbmaster.controller.driver;

import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputJoystick;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;

public class JoystickController implements Initializable {
	@FXML
	private ComboBox buttonCB;
	private OutputJoystick joystick;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buttonCB.setItems(FXCollections.observableArrayList("D-Pad","Mouse"));
	}
	protected void setConfiguredOutput(Output output) {
		if (output instanceof OutputJoystick) {
			joystick = (OutputJoystick) output;
			buttonCB.getSelectionModel().select((joystick.getJoystickType() == OutputJoystick.JoystickType.DPAD ? 0 : 1));
			//TODO Finish populating window
		}
	}
	/**
	 * Returns the configured output based on the user's selection
	 * or pre-configured selection.
	 */
	public Output getConfiguredOutput() {
		if (buttonCB.getSelectionModel().getSelectedIndex() == 0) joystick.setJoystickType(OutputJoystick.JoystickType.DPAD);
		else joystick.setJoystickType(OutputJoystick.JoystickType.MOUSE);
		return joystick;
	}
	public void reset() {
		buttonCB.getSelectionModel().select(0);
	}

}
