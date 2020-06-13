/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.util.PopupManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Handles the deleting a program and all of its profiles.
 * @version 1.0
 */
public class DeleteProgramUIController extends PopupController {

// ============= Class variables ============== //
    @FXML
    private Label typeL;
    @FXML
    private Label programL;
    private ProfileManager profileManager;
    private App app;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void setApp(App app){
	this.app = app;
	typeL.setText(app.getAppType().name());
	programL.setText(app.getName());
    }
    public void okEventFired(ActionEvent evt){
	try{
	    if(app != null){
		profileManager.removeApp(app);
		notifyOK(app.getName());
	    }else{
		PopupManager.getPopupManager().showError("Invalid App");
	    }
	}finally{
	    reset();
	}
    }
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void reset(){
	typeL.setText("");
	programL.setText("");
	hideStage();
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
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
