/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.io.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class DeleteProfileUIController extends PopupController {

// ============= Class variables ============== //
    @FXML
    private Label typeL;
    @FXML
    private Label programL;
    @FXML
    private Label profileL;
    private ProfileManager profileManager;
    private Profile profile;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void setProfile(Profile profile){
	this.profile = profile;
	typeL.setText(ProfileTypeNames.getProfileTypeName(profile.getApp().getAppType()));
	programL.setText(profile.getApp().getName());
	profileL.setText(profile.getProfileName());
    }
    public void okEventFired(ActionEvent evt){
	try{
	    if(profile != null){
		profileManager.removeProfile(profile);
		notifyOK(profile.getProfileName());
	    }else{
		PopupManager.getPopupManager().showError("Invalid Profile");
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
	profileL.setText("");
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
