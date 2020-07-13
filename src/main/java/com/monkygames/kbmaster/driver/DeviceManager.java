/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver;

import com.monkygames.kbmaster.cloud.metadata.MetaData;
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.ProfileInfo;

import java.io.File;
import java.util.ArrayList;

/**
 * Contains a list of device drivers available for download and whats already
 * available locally.
 */
public class DeviceManager {

// ============= Class variables ============== //

    /**
     * Contains the local list of devices added by user. Used by XStream.
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
    public DeviceManager(DeviceMenuUIController deviceMenuUIController) {
		driverManager = new DriverManager();
		deviceList = XStreamManager.getStreamManager().readGlobalAccount();
		installedDevices = new ArrayList<>();
		for (DevicePackage devicePackage : deviceList.getList()) {
			Device installedDevice = driverManager.getDevice(devicePackage.getPackageName());
			deviceMenuUIController.getProfileManager().addManagedDevice(installedDevice);
			installedDevice.setEnabled(devicePackage.isEnabled());
			App app = null;
			if (devicePackage.getAppInfo() != null) {
				app = deviceMenuUIController.getProfileManager().getAppByName(
						installedDevice,
						devicePackage.getAppInfo().getAppType().toString(),
						devicePackage.getAppInfo().getName()
				);
			}
			if (app != null) {
				Profile profile = deviceMenuUIController.getProfileManager().getProfileByName(
						app,
						devicePackage.getProfileInfo().getProfileName()
				);
				profile.setDefaultKeymap(devicePackage.getProfileInfo().getDefaultMap());
				installedDevice.setProfile(profile);
			}
			installedDevices.add(installedDevice);
		}
	}

    // ============= Public Methods ============== //
    public DriverManager getDriverManager() { return driverManager;  }
	/**
	 * Returns a list of locally installed devices.
	 */
	public ArrayList<Device> getInstalledDevices() { return installedDevices; }

    /**
     * Writes the list out to file.
     */
    public void save() { XStreamManager.getStreamManager().writeGlobalAccount(deviceList); }
    
    /**
     * Downloads the device specified by the package name
     * @param devicePackageName the java package.
     * @return true on success and false otherwise (if the device is already 
     * downloaded than returns false).
     */
    public boolean downloadDevice(String devicePackageName) {
		// first check if the device is already added
		for (Device device : installedDevices)
			if (device.getDeviceInformation().getPackageName().equals(devicePackageName))
				return false;
		// Retrieve the device
		for (Device device : driverManager.getDevices()) {
			if (device.getDeviceInformation().getPackageName().equals(devicePackageName)) {
				DevicePackage devicePackage = new DevicePackage(device);
				deviceList.getList().add(devicePackage);
				installedDevices.add(device);
				break;
			}
		}
		return true;
	}
	/**
     * Removes the device from the local device list and stores to the database.
     * @param device the device to remove.
     * @device the device to remove.
     * @return true if the device was successfully removed and false otherwise.
     */
    public void removeDownloadedDevice(Device device) {
    	DevicePackage remPackage = null;
    	for (DevicePackage devicePackage : deviceList.getList()) {
			if (devicePackage.getPackageName().equals(device.getDeviceInformation().getPackageName())) {
				remPackage = devicePackage;
				break;
			}
		}
    	String fileName = device.getDeviceInformation().getName()+".xml";
		File deviceFile = new File(ProfileManager.PROFILE_DIR + File.separator + fileName);
		deviceFile.delete();
		device.resetDevice();
		installedDevices.remove(device);
		deviceList.getList().remove(remPackage);
	}

	/**
	 *  Updates the device descriptor.
	 * @param device The device to be updated.
	 */
	public void updateDescriptor(Device device) {
		for (DevicePackage devicePackage : deviceList.getList()) {
			Device tempDevice = driverManager.getDevice(devicePackage.getPackageName());
			if (tempDevice == device) {
				devicePackage.setEnabled(device.isEnabled());
				if (device.getProfile() != null) {
					devicePackage.setAppInfo(device.getProfile().getAppInfo());
					devicePackage.setProfileInfo(new ProfileInfo(device.getProfile()));
				}
				else {
					devicePackage.setAppInfo(null);
					devicePackage.setProfileInfo(null);
				}
			}
		}
	}
    public void close() {
    	installedDevices.clear();
    	driverManager.close();
	}
}
