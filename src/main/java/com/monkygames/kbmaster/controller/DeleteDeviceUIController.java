/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.util.PopupManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Handles the deleting a profile.
 * @version 1.0
 */
public class DeleteDeviceUIController extends PopupController {

// ============= Class variables ============== //
    @FXML
    private Label typeL;
    @FXML
    private Label makeL;
    @FXML
    private Label modelL;
    private DeviceMenuUIController controller;
    private Device device;
// ============= Constructors ============== //
// ============= Public Methods ============== //
    public void setDevice(Device device){
	this.device = device;
	typeL.setText(device.getDeviceInformation().getDeviceType().name());
	makeL.setText(device.getDeviceInformation().getMake());
	modelL.setText(device.getDeviceInformation().getModel());
	showStage();
    }
    public void setController(DeviceMenuUIController controller){
	this.controller = controller;
    }
    public void okEventFired(ActionEvent evt){
	try{
	    if(device != null){
		controller.removeDevice(device);
		notifyOK(device.getDeviceInformation().getName());
	    }else{
		PopupManager.getPopupManager().showError("Invalid Device");
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
	makeL.setText("");
	modelL.setText("");
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
