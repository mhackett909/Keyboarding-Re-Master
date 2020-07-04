/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.Profile;
import com.thoughtworks.xstream.mapper.Mapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
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
	    isConnected = new SimpleStringProperty(device.isConnected() ? "Yes" : "No");
	    deviceName = new SimpleStringProperty(device.getDeviceInformation().getName());
	    profileName = new SimpleStringProperty();
	    try {
	        profileName.setValue(device.getProfile().getProfileName());
        }catch (NullPointerException e) { }
    }
// ============= Public Methods ============== //
    public BooleanProperty enabledProperty(){ return enabled; }
    /**
     * Returns the name of this device.
     * @return the name of this device.
     */
    public String getDeviceName(){ return device.getDeviceInformation().getName();  }
    /**
     * Returns the app + profile name.
     * @return the app + profile name used in the table view.
     */
    public String getProfileName(){
	    Profile profile = device.getProfile();
	    if(profile == null) return "None Selected";
	    return profile.getAppInfo().getName()+":"+profile.getProfileName();
    }
    /**
     * Returns a string representation if the device is connected.
     * @return Yes if connected and No otherwise.
     */
    public String getIsConnected(){
	    if(device.isConnected()) return "Yes";
	    return "No";
    }
    /**
     * Returns the device associated with this device entry.
     * @return the device.
     */
    public Device getDevice(){ return device; }
    public void setEnabled(boolean enabled){
        this.enabled.setValue(enabled);
    }
    public void setConnected(String isConnected) {
         this.isConnected.setValue(isConnected);
    }
    public void setProfile(String profile) {
        this.profileName.setValue(profile);
    }

}
