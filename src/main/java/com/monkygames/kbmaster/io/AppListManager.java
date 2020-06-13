/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.io;

import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import java.util.ArrayList;

/**
 * Handles managing the lists of programs.
 * @version 1.0
 */
public class AppListManager{

// ============= Class variables ============== //
    /** 
     * A list of game names that can be used with profiles.
     */
    private ArrayList <App> games;
    /**
     * A list of application names that can be used with profiles.
     */
    private ArrayList <App> applications;
// ============= Constructors ============== //
    public AppListManager(){
	games = new ArrayList<>();
	applications = new ArrayList<>();
    }
// ============= Public Methods ============== //
    /**
     * Adds an app to the list specified by type.
     * @param app the app to be added.
     * @return if the app already exists, false is returned and true on success.
     */
    public boolean addApp(App app){
	ArrayList<App> apps;
	switch(app.getAppType()){
	    case APPLICATION:
		apps = applications;
		break;
	    case GAME:
	    default:
		apps = games;

	}
	if(apps.contains(app)){
	    return false;
	}
	apps.add(app);
	return true;
    }
    /**
     * Returns the available apps that can be used with profiles.
     * @param type the type of program names to get.
     */
    public ArrayList<App> getAvailableAppList(AppType type){
	if(type == AppType.APPLICATION){
	    return applications;
	}
	return games;
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
