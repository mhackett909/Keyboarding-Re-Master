/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.controller.profile;

// === kbmaster imports === //
import com.monkygames.kbmaster.controller.PopupController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.util.PopupManager;
import com.monkygames.kbmaster.util.ProfileTypeNames;
// === java imports === //
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Calendar;
import java.util.List;
// === javafx imports === //
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

/**
 * A controller for the New Profile UI.
 * @version 1.0
 */
public class NewProfileUIController extends PopupController implements ChangeListener<String>{

// ============= Class variables ============== //
    @FXML
    private Button okB;
    @FXML
    private Button cancelB;
    @FXML
    private ComboBox typeCB;
    @FXML
    private ComboBox programCB;
    @FXML
    private TextField profileTF;
    @FXML
    private TextField authorTF;
    @FXML
    private TextArea infoTA;
    private ProfileManager profileManager;
    private Device device;
    private List<App> appsList;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setProfileManager(ProfileManager profileManager){
	this.profileManager = profileManager;
    }
    public void setDevice(Device device){
	this.device = device;
    }
    /**
     * Re-populate the combo boxes based on the type selected.
     */
    public void updateLists(){
	updateComboBoxesOnType(getProfileType());
    }
    public void okEventFired(ActionEvent evt) {
		try {
			App app = (App) programCB.getSelectionModel().getSelectedItem();
			String profileName = profileTF.getText();
			// check for a valid program name
			if (app == null) {
				PopupManager.getPopupManager().showError("Invalid program");
				return;
			}
			// check for valid profileName
			if (profileName == null || profileName.equals("")) {
				PopupManager.getPopupManager().showError("Invalid profile name");
				return;
			}
			//For using ` as delimiter
			char[] charArr = profileName.toCharArray();
			for (int index = 0; index < charArr.length; index++)
				if (charArr[index] == '`') charArr[index] = '\'';
			profileName = new String(charArr);
			// check for existing profile names
			if (app.doesProfileExist(profileName)) {
				PopupManager.getPopupManager().showError("Profile name already exists");
				return;
			}
			String author = authorTF.getText();
			if (author == null) author = "";

			String info = infoTA.getText();
			if (info == null) info = "";

			// get the current time
			long time = Calendar.getInstance().getTimeInMillis();
			Profile profile = new Profile(app, profileName, author, info, time, 0);
			device.setDefaultKeymaps(profile);
			// save the profile
			profileManager.addProfile(device, app, profile);
			notifyOK("AddProfile`" + getProfileType().toString() + "`" + app.getName() + "`" + profileName);
		} finally { reset(); }
	}
    public void cancelEventFired(ActionEvent evt){;
	reset();
	notifyCancel(null);
    }
	public void setAppType(int index) { typeCB.getSelectionModel().select(index); }
	public void setApp(int index) { programCB.getSelectionModel().select(index); }
// ============= Private Methods ============== //
    /**
     * Reset to the defaults.
     */
    private void reset() {
		profileTF.setText("");
		authorTF.setText("");
		infoTA.setText("");
		//programCB.getSelectionModel().selectFirst();
		hideStage();
	}
    private void updateComboBoxesOnType(AppType type) {
		ObservableList<App> apps;
		appsList = profileManager.getRoot(device, type).getList();
		apps = FXCollections.observableArrayList(appsList);
		programCB.setItems(apps);
	}
    /**
     * Returns the profile type based on the type combo box.
     */
    private AppType getProfileType() {
		AppType type;
		if (typeCB.getSelectionModel().getSelectedIndex() == 0) {
			type = AppType.GAME;
		} else {
			type = AppType.APPLICATION;
		}
		return type;
	}
// ============= Implemented Methods ============== //
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
// ============= Extended Methods ============== //
    @Override
    public void showStage(){
	super.showStage();
	updateLists();
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
