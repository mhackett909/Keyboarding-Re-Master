/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

// === javafx imports === //
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class MouseButtonController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    @FXML
    private ComboBox buttonCB;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Selects the item in the combo box based on the keycode.
     * @param keycode the keycode of the mouse to select.
     */
    public void setSelectedMouse(int keycode){
	for(Object obj: buttonCB.getItems()){
	    OutputMouse listItem = (OutputMouse)obj;
	    if(listItem.getKeycode() == keycode){
		buttonCB.getSelectionModel().select(obj);
		return;
	    }
	}
    }
    /**
     * Returns the configured output based on the user's selection
     * or pre-configured selection.
     */
    public Output getConfiguredOutput(){
	Output output = (Output)buttonCB.getSelectionModel().getSelectedItem();
	Output clone = (Output)output.clone();
	return clone;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
    }
    private void setupButtonComboBox(){
	// create MouseOutput events for each menu item.
	buttonCB.setItems(
	    FXCollections.observableArrayList(
		createMouseEvent("Click",MouseType.MouseClick),
		createMouseEvent("Double-Click",MouseType.MouseDoubleClick),
		createMouseEvent("Middle-Click",MouseType.MouseClick),
		createMouseEvent("Right-Click",MouseType.MouseClick),
		createMouseEvent("Scroll Up",MouseType.MouseWheel),
		createMouseEvent("Scroll Down",MouseType.MouseWheel)
	    )
	);
    }

    /**
     * Creates a mouse event with the specified name and type.
     * @param name the name of the mouse event.
     * @param type the type of the mouse event.
     * @return the new mouse event.
     */
    private OutputMouse createMouseEvent(String name,MouseType type){
	return new OutputMouse(name,getKeyCode(name),type);
    }

    /**
     * Returns the keycode based on the name of the mouse event.
     * @param name the name of the mouse event as specified in the combo box.
     * @Return the ID of the mouse event which will be passed to the OS when
     * this mouse event is fired.
     */
    private int getKeyCode(String name){
	if(name.equals("Click")){
	    return InputEvent.BUTTON1_MASK;
	}else if(name.equals("Double-Click")){
	    return InputEvent.BUTTON1_MASK;
	}else if(name.equals("Middle-Click")){
	    return InputEvent.BUTTON2_MASK;
	}else if(name.equals("Right-Click")){
	    return InputEvent.BUTTON3_MASK;
	}else if(name.equals("Scroll Up")){
	    return -1;
	}else if(name.equals("Scroll Down")){
	    return 1;
	}
	return 0;
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	setupButtonComboBox();
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
