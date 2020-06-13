/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.driver;

// === javafx imports === //
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
// === jinput imports === //

/**
 * Handles UI Events for the main window.
 * @version 1.0
 */
public class KeymapController implements Initializable{


// ============= Class variables ============== //
    @FXML
    private Pane rootPane;
    @FXML
    private ComboBox keymapCB;
    @FXML
    private CheckBox switchBackCB;
    
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Selects the keymap in the combo box based on the id.
     * @param keymapID the id of the keymap to select.
     * @param isSwitchOnRelease true if switch on release and false otherwise.
     */
    public void setConfiguredOutput(int keymapID, boolean isSwitchOnRelease){
	keymapCB.getSelectionModel().select(keymapID);
    }
    /**
     * Returns the configured output based on the user's selection
     * or pre-configured selection.
     */
    public Output getConfiguredOutput(){
	OutputKeymapSwitch outputKeymapSwitch = (OutputKeymapSwitch)keymapCB.getSelectionModel().getSelectedItem();
	// clone so that a new instance is created in order to not effect
	// the instance in the list.
	OutputKeymapSwitch output = (OutputKeymapSwitch)outputKeymapSwitch.clone();
	if(switchBackCB.isSelected()){
	    output.setIsSwitchOnRelease(true);
	}else{
	    output.setIsSwitchOnRelease(false);
	}
	return output;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    @FXML
    private void handleButtonAction(ActionEvent evt){ Object obj = evt.getSource();  }
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// set the items for the keymap (1 - 8);
	FXCollections.observableArrayList();
	ObservableList<OutputKeymapSwitch> list = FXCollections.observableArrayList();
	for(int i = 1; i <= 8; i++){
	   list.add(new OutputKeymapSwitch("Keymap "+i,i,false));
	}
	keymapCB.setItems(list);
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
