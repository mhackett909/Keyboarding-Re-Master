/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Displays an error message.
 * Note, must set the stage.
 * @version 1.0
 */
public class SetDescriptionUIController extends PopupController{

    @FXML
    private TextField descriptionTF;

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    @FXML
    public void okEventFired(ActionEvent evt){
	this.notifyOK(descriptionTF.getText());
	hideStage();
    }
    @FXML
    public void cancelEventFired(ActionEvent evt){
	descriptionTF.setText("");;
	notifyCancel("");
	hideStage();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	descriptionTF.setText("");
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
