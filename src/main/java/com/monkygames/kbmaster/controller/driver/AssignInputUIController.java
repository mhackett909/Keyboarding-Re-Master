/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.util.PopupManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class AssignInputUIController extends PopupController implements ChangeListener<String>{
// ============= Class variables ============== //
    @FXML
    private ComboBox mappingCB;
    @FXML
    private TextField descriptionTF;
    @FXML
    private Pane settingsPane;
    private Device device;
    private SingleKeyController singleKeyController;
    private MouseButtonController mouseButtonController;
    private KeymapController keymapController;
    private Parent singleKeyParent, mouseButtonParent, keymapParent, disabledParent;
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
    private static final String SINGLE_KEY = "Single Key";
    private static final String MOUSE_BUTTON = "Mouse Button";
    private static final String KEYMAP = "Keymap";
    private static final String DISABLED = "Disabled";
    private static final int MOUSE_NULL = 0;
	private static final int KEYMAP_NULL = -1;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setDevice(Device device){
	this.device = device;
    }
    public void okEventFired(ActionEvent evt){
	// set the mapping
	if(currentParent == singleKeyParent){
		if (singleKeyController.getConfiguredOutput().getName().equals("Unassigned")) {
			PopupManager.getPopupManager().showError("No key assigned.");
			return;
		}
	    currentMapping.setOutput(singleKeyController.getConfiguredOutput());
	}else if(currentParent == mouseButtonParent){
		if (mouseButtonController.getSelectedMouse() == -1) {
			PopupManager.getPopupManager().showError("No mouse action selected.");
			return;
		}
	    currentMapping.setOutput(mouseButtonController.getConfiguredOutput());
	}else if(currentParent == keymapParent){
		if (keymapController.keymapSelected() == -1) {
			PopupManager.getPopupManager().showError("No keymap selected.");
			return;
		}
	    currentMapping.setOutput(keymapController.getConfiguredOutput());
	}else if(currentParent == disabledParent){
	    currentMapping.setMapping(false);
	    currentMapping.setOutput(new OutputDisabled());
	}
	// save description
	currentMapping.getOutput().setDescription(descriptionTF.getText());
	// save profile
	this.notifyOK("Save");
	reset();
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
    /**
     * Sets the selected keymap to be configured.
     * @param keymap the keymap to be configured.
     */
    public void setSelectedKeymap(Keymap keymap){
		this.keymap = keymap;
    }
    /**
     * Set the configuration for the specified button id.
     * @param buttonID the unique id of the button to be configured.
     * @return true if its a success and false otherwise.
     */
    public boolean setAssignedConfig(int buttonID){
	// pop a message asking to create a profile
	if(device.getProfile() == null){
	    PopupManager.getPopupManager().showError("No profile selected.\nPlease select or create a profile.");
	    return false;
	}
	currentMapping = device.getMapping(buttonID, keymap);
	currentOutput = currentMapping.getOutput();

	if(currentParent != null){
	    settingsPane.getChildren().remove(currentParent);
	}

	int selectionID = 0;
	// update the configurations
	if(currentOutput instanceof OutputKey){
	    currentParent = singleKeyParent;
	    singleKeyController.setConfiguredOutput(currentOutput);
	    singleKeyController.setEnabled(true);
	    selectionID = 0;
	}else if(currentOutput instanceof OutputMouse){
	    currentParent = mouseButtonParent;
	    mouseButtonController.setSelectedMouse(currentOutput.getKeycode());
	    selectionID = 1;
	}else if(currentOutput instanceof OutputKeymapSwitch){
	    currentParent = keymapParent;
	    OutputKeymapSwitch keymapSwitch = (OutputKeymapSwitch)currentOutput;
	    // note, we subtract one from the keycode since the range is valid from 1 - 8 inclusive.
	    keymapController.setConfiguredOutput(keymapSwitch.getKeycode()-1,keymapSwitch.isIsSwitchOnRelease());
	    selectionID = 2;
	}else if(currentOutput instanceof OutputDisabled){
	    currentParent = disabledParent;
	    selectionID = 3;
	}
	if(currentParent != null){
	    settingsPane.getChildren().add(currentParent);
	    mappingCB.valueProperty().removeListener(this);
	    mappingCB.getSelectionModel().select(selectionID);
	    mappingCB.valueProperty().addListener(this);
	    resetUI(selectionID);
	}
	// update the description
	descriptionTF.setText(currentOutput.getDescription());
	return true;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void reset(){
	hideStage();
    }
    private void resetUI(int id) {
    	switch (id) {
			case 0:
				mouseButtonController.setSelectedMouse(MOUSE_NULL);
				keymapController.setConfiguredOutput(KEYMAP_NULL,false);
				break;
			case 1:
				singleKeyController.setConfiguredOutput(new OutputKey("Unassigned",0,0));
				keymapController.setConfiguredOutput(KEYMAP_NULL,false);
				break;
			case 2:
				singleKeyController.setConfiguredOutput(new OutputKey("Unassigned",0,0));
				mouseButtonController.setSelectedMouse(MOUSE_NULL);
				break;
			default:
				singleKeyController.setConfiguredOutput(new OutputKey("Unassigned",0,0));
				keymapController.setConfiguredOutput(KEYMAP_NULL,false);
				mouseButtonController.setSelectedMouse(MOUSE_NULL);
		}
	}
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// set up mapping cb
	mappingCB.setItems(FXCollections.observableArrayList(SINGLE_KEY,MOUSE_BUTTON,KEYMAP,DISABLED));
	//mappingCB.getSelectionModel().selectFirst();
	mappingCB.valueProperty().addListener(this);
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/SingleKeyPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    singleKeyParent = (Parent)fxmlLoader.load(location.openStream());
	    singleKeyController = (SingleKeyController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/MouseButtonPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    mouseButtonParent = (Parent)fxmlLoader.load(location.openStream());
	    mouseButtonController = (MouseButtonController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/KeymapPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    keymapParent = (Parent)fxmlLoader.load(location.openStream());
	    keymapController = (KeymapController) fxmlLoader.getController();
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	try {
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/driver/DisabledPane.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader(location);
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    disabledParent = (Parent)fxmlLoader.load(location.openStream());
	} catch (IOException ex) {
	    Logger.getLogger(AssignInputUIController.class.getName()).log(Level.SEVERE, null, ex);
	}
	Tooltip tooltip = new Tooltip();
	tooltip.setText("Mouse Click to type text & Type Enter to exit typing mode");
	descriptionTF.setTooltip(tooltip);
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
	if(currentParent != null){
	    settingsPane.getChildren().remove(currentParent);
	}
	if(newValue.equals(SINGLE_KEY)){
	    currentParent = singleKeyParent;
	}else if(newValue.equals(MOUSE_BUTTON)){
	    currentParent = mouseButtonParent;
	}else if(newValue.equals(KEYMAP)){
	    currentParent = keymapParent;
	}else if(newValue.equals(DISABLED)){
	    currentParent = disabledParent;
	}
	if(currentParent != null){
	    settingsPane.getChildren().add(currentParent);
	    if (!descriptionTF.isFocused()) {
			descriptionTF.setEditable(false);
			singleKeyController.setEnabled(true);
		}
	}
    }
    /**
     * Allows the user to edit the description field.
     */
    public void handleDescriptionClicked(MouseEvent event) {
	descriptionTF.setEditable(true);
	singleKeyController.setEnabled(false);
    }
    /**
     * Allows the user to exit the description field.
     */
    public void handleDescriptionEntered(KeyEvent event){
	if(event.getCode().equals(KeyCode.ENTER)){
		descriptionTF.setEditable(false);
		descriptionTF.deselect();
	    singleKeyController.setEnabled(true);
	}
    }
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void showStage(){
	super.showStage();
    }
    @Override
    public void setStage(Stage stage){
	super.setStage(stage);
	singleKeyController.setStage(stage);
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
