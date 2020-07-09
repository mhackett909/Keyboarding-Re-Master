/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.profiles;

// === java imports === //
import java.io.File;
import java.util.HashMap;

// === kbmaster imports === //
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.io.XStreamManager;
/**
 * Manages saving and loading profiles.
 */
public class ProfileManager {

	// ============= Class variables ============== //
	public static final String PROFILE_DIR = "profiles";
	private HashMap<Device, RootManager> deviceRoots;
	private DeviceMenuUIController deviceMenuUIController;

	// ============= Constructors ============== //

	/**
	 * Create a new profile manager.
	 */
	public ProfileManager(DeviceMenuUIController deviceMenuUIController) {
		this.deviceMenuUIController = deviceMenuUIController;
		deviceRoots = new HashMap<>();
		File profileDir = new File(PROFILE_DIR);
		if (!profileDir.exists()) profileDir.mkdir();
	}

	// ============= Public Methods ============== //
	/**
	 * Adds an app to the list if it doesn't already exist.
	 * @param app the app to add to the list.
	 * @return true on success and false if the name already exists.
	 */
	public boolean addApp(Device device, App app) {
		return getRootManager(device).addApp(app);
	}

	/**
	 * Removes the app from the list.
	 * Note, if there are any profiles in this app, those profiles
	 * will also be removed.
	 */
	public void removeApp(Device device, App app) {
		getRootManager(device).removeApp(app);
	}

	/**
	 * Adds the profile to the app and stores to the database.
	 *
	 * @param profile the profile to be added.
	 */
	public void addProfile(Device device, App app, Profile profile) {
		getRootManager(device).addProfile(app, profile);
	}

	/**
	 * Saves the profile to the database.
	 */
	public void saveProfile(Device device) {
		String fileName = device.getDeviceInformation().getName()+".xml";
		XStreamManager.getStreamManager().writeRootManager(PROFILE_DIR+File.separator+fileName, getRootManager(device));
		deviceMenuUIController.getDeviceManager().updateDescriptor(device);
		deviceMenuUIController.getDeviceManager().save();
	}

	/**
	 * Sets the active profile
	 */
	public void setActiveProfile(Device device, Profile profile) {
		deviceMenuUIController.setActiveProfile(device, profile);
	}

	/**
	 * Removes the profile from the database and updates the list.
	 *
	 * @param profile the profile to remove.
	 */
	public void removeProfile(Device device, App app, Profile profile) {
		int index = getRoot(device, app.getAppType()).getList().indexOf(app);
		getRoot(device, app.getAppType()).getList().get(index).removeProfile(profile);
	}

	/**
	 * Returns the root based on the app type.
	 *
	 * @return the apps or games type, and defaults to games.
	 */
	public Root getRoot(Device device, AppType type) {
		if (type == AppType.APPLICATION)
			return getRootManager(device).getAppsRoot();
		else return getRootManager(device).getGamesRoot();
	}

	public Root getAppsRoot(Device device) {
		return getRoot(device, AppType.APPLICATION);
	}

	public Root getGamesRoot(Device device) {
		return getRoot(device, AppType.GAME);
	}
	public RootManager getRootManager(Device device) {
		return deviceRoots.get(device);
	}
	/**
	 * Adds a device to the profile manager. Allows for retrieval of profiles.
	 */
	public void addManagedDevice(Device device) {
		String fileName = device.getDeviceInformation().getName()+".xml";
		deviceRoots.put(device, XStreamManager.getStreamManager().readRootManager(PROFILE_DIR + File.separator + fileName));
	}
	public void removeDevice(Device device) {
		RootManager rootManager = getRootManager(device);
		rootManager.close();
		rootManager = null;
		deviceRoots.remove(device);
	}
	/**
	 * Finds and returns an application. Null if no matching application found.
	 */
	public App getAppByName(Device device, String appTypeString, String appName) {
		AppType appType;
		switch (appTypeString.toLowerCase()) {
			case "game":
				appType = AppType.GAME;
				break;
			default:
				appType = AppType.APPLICATION;
		}
		for (App app : getRoot(device, appType).getList())
			if (app.toString().equals(appName)) return app;
		return null;
	}
	/**
	 * Finds and returns a profile. Null if no matching profile found.
	 */
	public Profile getProfileByName(App app, String profileName) {
		for (Profile profile : app.getProfiles()) {
			if (profile.getProfileName().equals(profileName)) return profile;
		}
		return null;
	}
	public void close() {
		for (RootManager rootManager : deviceRoots.values()) {
			rootManager.close();
			rootManager = null;
		}
		deviceRoots.clear();
	}
}