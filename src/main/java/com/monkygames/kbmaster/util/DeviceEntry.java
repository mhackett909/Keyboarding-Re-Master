/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.Profile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;

/**
 * Used for setting devices in a TableView.
 * @version 1.0
 */
public class DeviceEntry{

// ============= Class variables ============== //
    /**
     * The name of the device.
     */
    private StringProperty deviceName;
    /**
     * The currently selected profile name to use.
     */
    private StringProperty profileName;
    /**
     * Yes if connected and no otherwise.
     */
    private StringProperty isConnected;
    /**
     * True if the device should be enabled and false otherwise.
     */
    private BooleanProperty enabled;
    private Device device;
// ============= Constructors ============== //
    public DeviceEntry(Device device){
	this.device = device;
	enabled = new SimpleBooleanProperty(device.isEnabled());
    }
// ============= Public Methods ============== //
    public BooleanProperty enabledProperty(){
	return enabled;
    }
    /**
     * Returns the name of this device.
     * @return the name of this device.
     */
    public String getDeviceName(){
	return device.getDeviceInformation().getName();
    }
    /**
     * Returns the app + profile name.
     * @return the app + profile name used in the table view.
     */
    public String getProfileName(){
	Profile profile = device.getProfile();
	if(profile == null){
	    return "None Selected";
	}
	return profile.getApp().getName()+":"+profile.getProfileName();
    }
    /**
     * Returns a string representation if the device is connected.
     * @return Yes if connected and No otherwise.
     */
    public String getIsConnected(){
	if(device.isConnected()){
	    return "Yes";
	}
	return "No";
    }
    /**
     * Returns true if the device is enabled and false otherwise.
     */
    public boolean isEnabled() {
	return device.isEnabled();
    }
    public void setEnabled(boolean enabled){
	this.enabled.setValue(enabled); 
    }
    /**
     * Returns the device associated with this device entry.
     * @return the device.
     */
    public Device getDevice(){
	return device;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
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
