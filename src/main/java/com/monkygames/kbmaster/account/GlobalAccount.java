/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

// === java imports === //
import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceState;
import com.monkygames.kbmaster.driver.DriverManager;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.util.DeviceEntry;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Contains a list of device drivers available for download and whats already
 * available locally.
 * @version 1.0
 */
public class GlobalAccount{

// ============= Class variables ============== //

    /**
     * Contains the local list of devices added by user.
     */
    private DeviceList deviceList;

    /**
     * The installed devices.
     */
    private ArrayList<Device> installedDevices;

    /**
     * Contains all drivers available.
     */
    private DriverManager driverManager;

// ============= Constructors ============== //
    public GlobalAccount(){
	driverManager = new DriverManager();

	// populate supported devices
	deviceList = XStreamManager.getStreamManager().readGlobalAccount();

	installedDevices = new ArrayList<>();
	
	// instantiate devices
	for(DevicePackage devicePackage: deviceList.getList()){
	    Device device = driverManager.getDeviceByPackageName(devicePackage.getDeviceState().getPackageName());
	    if(device != null){
		device.setDeviceState(devicePackage.getDeviceState());
		installedDevices.add(device);
	    }
	}
    }

    // ============= Public Methods ============== //
    public DriverManager getDriverManager() {
	return driverManager;
    }

    /**
     * Writes the list out to file.
     */
    public boolean save(){
        MetaData metaData = deviceList.getMetaData();
        if(metaData != null){
            metaData.rev = "update";
        }
		return XStreamManager.getStreamManager().writeGlobalAccount(deviceList);
    }
    
    /**
     * Downloads the device specified by the package name
     * Note, right now this method expects the driver to be already
     * packaged in the jar; however, the future development of this
     * method should get the driver from outside the jar.
     * @param devicePackageName the java package.
     * @return true on success and false otherwise (if the device is already 
     * downloaded than returns false).
     */
    public boolean downloadDevice(String devicePackageName){
	// first check if the device is already added 

	for(DevicePackage devicePackage: deviceList.getList()){
	    if(devicePackage.getDeviceState().getPackageName().equals(devicePackageName)){
		// already added
		return false;
	    }
	}

	// get all devices 
	for(Device device: driverManager.getDevices()){
	    if(device.getDeviceInformation().getPackageName().equals(devicePackageName)){
		Device newDevice = instantiate(device.getDeviceInformation().getPackageName(),Device.class);
		if(device == null){
		    return false;
		}

		DeviceState deviceState = new DeviceState(device.getProfile(),
		    device.isConnected(),
		    device.isEnabled(),
		    device.getDeviceInformation().getPackageName());

		DevicePackage devicePackage = new DevicePackage(deviceState);
		devicePackage.setIsDownloaded(true);
		deviceList.getList().add(devicePackage);
	    }
	}
	return save();
    }
	/**
	 * Saves the current state of each device to its respective DevicePackage.
	 */
	public void updateDeviceState(DeviceState currentState) {
		for (DevicePackage devicePackage : deviceList.getList()) {
			if (devicePackage.getDeviceState().getPackageName().equals(currentState.getPackageName()))
				devicePackage.setDeviceState(currentState);
		}
	}
	/**
     * Removes the device from the local device list and stores to the database.
     * @param device the device to remove.
     * @device the device to remove.
     * @return true if the device was successfully removed and false otherwise.
     */
    public boolean removeDownloadedDevice(Device device){
	String devicePackageName = device.getDeviceInformation().getPackageName();
	for(DevicePackage devicePackage: deviceList.getList()){
	    if(devicePackage.getDeviceState().getPackageName().equals(devicePackageName)){
		deviceList.getList().remove(devicePackage);
		// save
		return save();
	    }
	}
	return false;
    }
    /**
     * Returns a list of locally installed devices.
     * @return a list of locally installed devices.
     */
    public ArrayList<Device> getInstalledDevices(){
	ArrayList<Device> devices = new ArrayList<>();

	for(DevicePackage devicePackage: deviceList.getList()){

	    if(devicePackage.isIsDownloaded()){
		devices.add(driverManager.getDeviceByPackageName(devicePackage.getDeviceState().getPackageName()));
	    }
	}
	return devices;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private <T> T instantiate(final String className, final Class<T> type){
	try{
	    return type.cast(Class.forName(className).newInstance());
	} catch(final InstantiationException | IllegalAccessException | ClassNotFoundException e){
	    throw new IllegalStateException(e);
	}
    }
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
