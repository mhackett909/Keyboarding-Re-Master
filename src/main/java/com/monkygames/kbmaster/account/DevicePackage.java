/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === kbmaster imports === //
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceState;

/**
 * The package a device driver and its relationship to the 
 * global account.
 * IE, its used to determine if a device has been downloaded.
 * @version 1.0
 */
public class DevicePackage{

// ============= Class variables ============== //
    /**
     * The device information represented by this class.
     */
  //  private DeviceState deviceState;
    /**
     * True if this device has been downloaded and available locally or false
     * otherwise.
     */
    private boolean isDownloaded;

    /**
     * The device associated with this package.
     *
     * @param deviceState
     */
     private Device device;
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

    // ============= Constructors ============== //
    public DevicePackage(Device device){
    this.device = device;
   // deviceState = device.getDeviceState();
	//this.deviceState = deviceState;
	isDownloaded = false;
    }
    // ============= Public Methods ============== //
    // public DeviceState getDeviceState(){	return deviceState; }

    public Device getDevice() { return device; }

   // public void setDeviceState(DeviceState deviceState) { this.deviceState = deviceState; }

    public boolean isIsDownloaded() {
	return isDownloaded;
    }

    public void setIsDownloaded(boolean isDownloaded) {
	this.isDownloaded = isDownloaded;
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
