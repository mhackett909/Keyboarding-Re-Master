/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

// === java imports === //
import java.io.File;
// === kbmaster imports === //
import com.monkygames.kbmaster.controller.ProfileUIController;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.profiles.RootManager;
import com.monkygames.kbmaster.util.PopupManager;

/**
 * Manages saving and loading profiles.
 * @version 1.0
 */
public class ProfileManager{

// ============= Class variables ============== //
    private RootManager rootManager;
    private String databaseFilename;
    private ProfileUIController profileUIController;
// ============= Constructors ============== //
    /**
     * Create a new profile manager where the specified string is the location
     * of the profile database.
     * @param databaseFilename the location of the database db4o file.
     */
    public ProfileManager(String databaseFilename){
	this.databaseFilename = databaseFilename;
	loadRoots();
    }
// ============= Public Methods ============== //
    public void close(){
		saveProfile();
    }

    /**
     * Returns true if the profile already exists and false otherwise.
     * @param app
     * @param profileName the name of the profile.
     * @return true if profile exists and false if it does not exists.
     */
    public boolean doesProfileNameExists(App app, String profileName){
	for(Profile profile: app.getProfiles()){
	    if(profile.getAppInfo().getAppType() == app.getAppType() &&
	       profile.getAppInfo().getName().equals(app.getName()) &&
	       profile.getProfileName().equals(profileName)){
		return true;
	    }
	}
	return false;
    }
	/**
	 * Adds an app to the list if it doesn't already exist.
	 * @param app the app to add to the list.
	 * @return true on success and false if the name already exists.
	 */
	public boolean addApp(App app){ return rootManager.addApp(app); }
	/**
	 * Removes the app from the list.
	 * Note, if there are any profiles in this app, those profiles
	 * will also be removed.
	 */
	public void removeApp(App app){ rootManager.removeApp(app); }
    /**
     * Adds the profile to the app and stores to the database.
     * @param profile the profile to be added.
     */
    public boolean addProfile(Profile profile) { return rootManager.addProfile(profile); }
    /**
     * Saves the profile to the database.
     */
    public void saveProfile(){ XStreamManager.getStreamManager().writeRootManager(databaseFilename, rootManager); }

    /**
     * Removes the profile from the database and updates the list.
     * @param profile the profile to remove.
     */
    public void removeProfile(Profile profile){
    	App app = profileUIController.getAppByName(profile.getAppInfo().getAppType().toString(), profile.getAppInfo().getName());
    	AppType appType = profile.getAppInfo().getAppType();
    	int index = getRoot(appType).getList().indexOf(app);
    	getRoot(appType).getList().get(index).removeProfile(profile);
    }
	/**
	 * Sets the UI Controller
	 */
	 public void setUIController(ProfileUIController profileUIController) {
	 	this.profileUIController = profileUIController;
	 }

    /**
     * Export the profile to a unique file location.
     * @param file the file to write to.
     * @param profile the profile to export.
     * @return true on success and false otherwise.
     */
    public boolean exportProfile(File file, Profile profile){
    	String fileName = file.getAbsolutePath(), extension = "";
    	int lastIndex = fileName.lastIndexOf(".");
    	if (lastIndex > -1) extension = fileName.substring(lastIndex);
    	// export to an xml file only
		if (!extension.equals(".xml")) return false;
		if(file.exists()) file.delete();
		profileUIController.saveProfile();
		return XStreamManager.getStreamManager().writeProfile(file.getAbsolutePath(), profile);
    }

    /**
     * Imports the profile into the project.
     * @param file the file to import
     * @return false if error and true on success.
     */
    public boolean importProfile(File file){
		if(!file.exists()) return false;
		try {
			Profile profile = XStreamManager.getStreamManager().readProfile(file.getAbsolutePath());
			AppType appType = profile.getAppInfo().getAppType();
			String appName = profile.getAppInfo().getName();
			App app = profileUIController.getAppByName(appType.toString(), appName);
			if (app == null) {
				addApp(new App("", null, null, appName, appType));
				profileUIController.onOK(null,"AddApp`"+appType.toString()+"`"+appName);
			}
			if (!doesProfileNameExists(profileUIController.getAppByName(appType.toString(), appName), profile.getProfileName())) {
				addProfile(profile);
				profileUIController.onOK(null, "AddProfile`"+appType.toString()+"`"+appName+"`"+profile.getProfileName());
				return true;
			}
			else return false;
		}catch (Exception e) { return false; }
    }

    /**
     * Returns the root based on the app type.
     * @return the apps or games type, and defaults to games.
     */
    public Root getRoot(AppType type){
	if(type == AppType.APPLICATION){
	    return rootManager.getAppsRoot();
	}else if(type == AppType.GAME){
	    return rootManager.getGamesRoot();
	}
	return rootManager.getGamesRoot();
    }
	public Root getAppsRoot(){ return getRoot(AppType.APPLICATION);  }
	public Root getGamesRoot(){ return getRoot(AppType.GAME);  }
    /**
     * Prints the profiles out in a human readable way.
     */
    public void printProfilesFormatted(){
	System.out.println("=== Game Profiles ===");
	for(App app: rootManager.getGamesRoot().getList()){
	    for(Profile profile: app.getProfiles()){
		profile.printString();
		System.out.println("== End of "+profile.getProfileName()+" ==");
	    }
	}
	System.out.println("=== Appp Profiles ===");
	for(App app: rootManager.getAppsRoot().getList()){
	    for(Profile profile: app.getProfiles()){
		profile.printString();
		System.out.println("== End of "+profile.getProfileName()+" ==");
	    }
	}
	System.out.println("== ======== ==");
    }
    /**
     * Returns the path to the database file.
     * @return the database file name.
     */
    public String getDatabaseFilename(){
	return this.databaseFilename;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void loadRoots(){
	rootManager = XStreamManager.getStreamManager().readRootManager(databaseFilename);
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
