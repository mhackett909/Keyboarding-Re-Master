/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.controller.PopupNotifyInterface;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Handles the cloning feature.
 * @version 1.0
 */
public class CloneProfileUIController extends PopupController implements ChangeListener<String>, PopupNotifyInterface{

// ============= Class variables ============== //
    @FXML
    private Label profileNameL;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
    @FXML
    private TextField profileTF;
    private ProfileManager profileManager;
    private Device device;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
	updateComboBoxesOnType(AppType.GAME);
    }
    public void setDevice(Device device){
	this.device = device;
    }
	public void setLabel(String label) { profileNameL.setText(label); }
	public void setAppType(int i) { typeCB.getSelectionModel().select(i); }
	public void setProgram(int i) {
		switch (typeCB.getSelectionModel().getSelectedIndex()) {
			case 0:
				updateComboBoxesOnType(AppType.GAME);
				break;
			default:
			updateComboBoxesOnType(AppType.APPLICATION);
		}
		programCB.getSelectionModel().select(i);
	}
    public void okEventFired(ActionEvent evt) {
		try {
			AppType type = getProfileType();

			// check for a valid app name
			App app = (App) programCB.getSelectionModel().getSelectedItem();
			if (app == null) {
				PopupManager.getPopupManager().showError("Invalid App");
				return;
			}

			// check for a valid name
			String newProfileName = profileTF.getText();
			if (newProfileName == null || newProfileName.equals("")) {
				PopupManager.getPopupManager().showError("Invalid profile name");
				return;
			}
			//For using ` as delimiter
			char[] charArr = newProfileName.toCharArray();
			for (int index = 0; index < charArr.length; index++)
				if (charArr[index] == '`') charArr[index] = '\'';
			newProfileName = new String(charArr);
			// check for a redundant name
			if (app.doesProfileExist(newProfileName)) {
				PopupManager.getPopupManager().showError("Profile name already exists");
				return;
			}
			Profile profile = new Profile(app, newProfileName);
			device.setDefaultKeymaps(profile);
			profileManager.addProfile(device, app, profile);
			profile = device.getProfile().cloneProfile(profile, app);
			profile.setAppInfo(app);
			notifyOK("AddProfile`" + type.toString() + "`" + app.getName() + "`" + newProfileName);

		} finally { reset(); }
	}
    public void cancelEventFired(ActionEvent evt){
	reset();
	notifyCancel(null);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void updateComboBoxesOnType(AppType type){
	ObservableList<App> apps;
	apps = FXCollections.observableArrayList(profileManager.getRoot(device, type).getList());
	programCB.setItems(apps);
    }
    private void reset(){
	profileTF.setText("");
	hideStage();
    }
    /**
     * Returns the profile type based on the type combo box.
     */
    private AppType getProfileType(){
	AppType type;
	if(typeCB.getSelectionModel().getSelectedIndex() == 0){
	    type = AppType.GAME;
	}else{
	    type = AppType.APPLICATION;
	}
	return type;
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	typeCB.setItems(FXCollections.observableArrayList(ProfileTypeNames.getProfileTypeName(AppType.GAME),
							  ProfileTypeNames.getProfileTypeName(AppType.APPLICATION)));
	typeCB.getSelectionModel().selectFirst();
	typeCB.valueProperty().addListener(this);
	programCB.setItems(FXCollections.observableArrayList());
    }
    @Override
    public void changed(ObservableValue<? extends String> ov, String t, String t1) {
	if(ov == typeCB.valueProperty()){
	    updateComboBoxesOnType(getProfileType());
	}
    }
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    @Override
    public void showStage(){
	super.showStage();
	updateComboBoxesOnType(getProfileType());
    }

	@Override
	public void onOK(Object src, String message) {

	}

	@Override
	public void onCancel(Object src, String message) {

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
