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
import javafx.stage.Stage;

/**
 * Displays an error message.
 * Note, must set the stage.
 * @version 1.0
 */
public class ErrorUIController implements Initializable{

    @FXML
    private Label textL;
    @FXML
    private Button okB;
    private Stage stage;

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setStage(Stage stage){
	this.stage = stage;
    }
    /**
     * Opens this error popup with the specified error message.
     * @param errorMessage the error message to display.
     */
    public void showError(String errorMessage){
	textL.setText(errorMessage);
	stage.show();

    }
    public void okEventFired(ActionEvent evt){
	stage.hide();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	textL.setText("");
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
