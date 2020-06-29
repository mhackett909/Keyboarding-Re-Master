/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

// === javafx imports === //
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.util.JavaFXToAwt;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class SingleKeyController implements Initializable, EventHandler{

// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    @FXML
    private TextField singleKeyTF;
    @FXML
    private Label shiftL, ctrlL, altL;
    private boolean ignoreModifierRelease = false;
    /**
     * Used for storing the output key to be assigned.
     */
    private OutputKey outputKey;
    private boolean isEnabled = false;
    private Stage stage;

// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Sets the single key for the specified output.
     * @parma output the output to be set for this key.
     */
    public void setConfiguredOutput(Output output){
	outputKey.setName(output.getName());
	outputKey.setKeycode(output.getKeycode());
	outputKey.setModifier(output.getModifier());
	singleKeyTF.setText(outputKey.getName());
    }
    public void setStage(Stage stage){
	// note, the event filter allows for all events to get generated
	// using the EventHandler might miss some key strokes like 
	// space, esc, and enter due to components in the scenegraph
	// taking focus and consuming the event before it can be passed
	// to this handler.
	//rootPane.addEventFilter(KeyEvent.KEY_RELEASED, this);
	this.stage = stage;
    }
    /**
     * Sets the key event filtered based on this.
     */
    public void setEnabled(boolean isEnabled){
	if(this.isEnabled == isEnabled){
	    return;
	}
	if(isEnabled){
	    stage.addEventFilter(KeyEvent.KEY_RELEASED, this);
	}else{
	    stage.removeEventFilter(KeyEvent.KEY_RELEASED, this);
	}
	this.isEnabled = isEnabled;
    }
    /**
     * Returns the configured output based on the user's selection
     * or pre-configured selection.
     */
    public Output getConfiguredOutput(){
	Output clone = (Output)outputKey.clone();
	return clone;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){
	Object obj = evt.getSource();
    }
    /**
     * Sets the key pressed from the key event.
     * @param keyEvent the key that was released.
     * @param type the type of event, could by KEY, CTRL, SHIFT, ALT, or META.
     */
    private void setKeyPressed(KeyEvent keyEvent, String type){
	int awtModifier = 0;
	if(type.equals("ALT")){
	    altL.setDisable(false);
	    ctrlL.setDisable(true);
	    shiftL.setDisable(true);
	    awtModifier = java.awt.event.KeyEvent.VK_ALT;
	}else if(type.equals("CTRL")){
	    altL.setDisable(true);
	    ctrlL.setDisable(false);
	    shiftL.setDisable(true);
	    awtModifier = java.awt.event.KeyEvent.VK_CONTROL;
	}else if(type.equals("SHIFT")) {
		altL.setDisable(true);
		ctrlL.setDisable(true);
		shiftL.setDisable(false);
		awtModifier = java.awt.event.KeyEvent.VK_SHIFT;
	}else{
	    altL.setDisable(true);
	    ctrlL.setDisable(true);
	    shiftL.setDisable(true);
	}
	String key = keyEvent.getCharacter();
	KeyCode code = keyEvent.getCode();

	int awtCode = JavaFXToAwt.getAWTKeyCode(keyEvent);
	//int awtModifier = JavaFXToAwt.getAWTModifiers(keyEvent);
	outputKey.setName(code.getName());
	outputKey.setKeycode(awtCode);
	outputKey.setModifier(awtModifier);

	singleKeyTF.setText(code.getName());
    }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	outputKey = new OutputKey("Unassigned",0,0);
    }
    @Override
    public void handle(Event event) {
	if(KeyEvent.KEY_TYPED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;
	    String key = keyEvent.getCharacter();
	    singleKeyTF.setText(key);
	} else if(KeyEvent.KEY_PRESSED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;
	    String key = keyEvent.getCharacter();
	} else if(KeyEvent.KEY_RELEASED.equals(event.getEventType())){
	    KeyEvent keyEvent = (KeyEvent)event;

	    if (keyEvent.getCode() == KeyCode.CONTROL && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isControlDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "CTRL");
	    }else if (keyEvent.getCode() == KeyCode.ALT && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isAltDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "ALT");
	    }else if (keyEvent.getCode() == KeyCode.SHIFT && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isShiftDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "SHIFT");
	    }else if (keyEvent.getCode() == KeyCode.META && !ignoreModifierRelease) {
		setKeyPressed(keyEvent, "KEY");
	    } else if (keyEvent.isMetaDown()) {
		ignoreModifierRelease = true;
		setKeyPressed(keyEvent, "META");
	    } else if(!ignoreModifierRelease){
		setKeyPressed(keyEvent, "KEY");
	    } else {
		ignoreModifierRelease = false;
	    }
	}
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
