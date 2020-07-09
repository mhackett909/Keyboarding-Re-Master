/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.ProfileManager;
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
    private Device device;
    private App app;
    private Profile profile;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void setProfile(Device device, App app, Profile profile) {
        this.device = device;
        this.app = app;
        this.profile = profile;
        typeL.setText(ProfileTypeNames.getProfileTypeName(app.getAppType()));
        programL.setText(app.getName());
        profileL.setText(profile.getProfileName());
    }
    public void okEventFired(ActionEvent evt) {
        profileManager.removeProfile(device, app, profile);
        notifyOK("DelProfile`" + profile.getAppInfo().getAppType().toString() + "`" + profile.getAppInfo().getName() + "`" + profile.getProfileName());
        reset();
    }
    public void cancelEventFired(ActionEvent evt) {
        reset();
        notifyCancel(null);
    }
// ============= Private Methods ============== //
    private void reset() {
        typeL.setText("");
        programL.setText("");
        profileL.setText("");
        hideStage();
    }
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
