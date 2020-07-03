package com.monkygames.kbmaster.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResetKeymapUIController extends PopupController {
    @FXML
    private Label idL;
    @FXML
    private Label profileL;
    @FXML
    private Label noteL;
    public void okEventFired(ActionEvent evt){
        notifyOK("ResetKeymap");
        reset();
    }
    public void cancelEventFired(ActionEvent evt){
        reset();
        notifyCancel(null);
    }
    public void setUI(int id, String profileName) {
        idL.setText(Integer.toString(id+1));
        profileL.setText(profileName);
    }
    private void reset(){
        idL.setText("");
        profileL.setText("");
        noteL.setText("");
        hideStage();
    }
}
