package com.monkygames.kbmaster.profiles;

import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.account.dropbox.SyncMetaData;

/**
 * Contains all the roots.
 */
public class RootManager implements SyncMetaData{

    /**
     * The Root that contains the applications profiles.
     */
    private Root appsRoot;

    /**
     * The root that contains the game profiles.
     */
    private Root gamesRoot;

    /**
     * The meta data used for dropbox sync.
     */
    private MetaData metaData;


    public RootManager(){
        appsRoot = new Root("Application",AppType.APPLICATION);
        gamesRoot = new Root("Game",AppType.GAME);
        metaData = null;
    }

    public RootManager(Root appsRoot, Root gamesRoot){
        this.appsRoot = appsRoot;
        this.gamesRoot = gamesRoot;
    }

    /**
     * Returns the apps root.
     */
    public Root getAppsRoot(){
        return appsRoot;
    }

    /**
     * Returns the games root.
     */
    public Root getGamesRoot(){
        return gamesRoot;
    }

    @Override
    public MetaData getMetaData() {
        return metaData;
    }

    @Override
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     * Adds the profile to the manager.
     * @param profile
     * @return 
     */
    public boolean addProfile(Profile profile){
        Root root;
        if(profile.getApp().getAppType() == AppType.APPLICATION){
            root = appsRoot;
        }else{
            root = gamesRoot;
        }
        // find the app
        App app = null;
        for(App tmpApp: root.getList()){
            if(tmpApp.getName().equals(profile.getApp().getName())){
                app = tmpApp;
                break;
            }
        }

        if(app == null){
            // add app to root
            // at this point, done since app has a reference to profile
            root.addApp(app);
        }else{
            // add profile to app
            app.addProfile(profile);
        }
        return true;
    }

    /**
     * Removes the profile from the app.
     * @param profile the profile to remove (note it has a reference to app).
     */
    public void removeProfile(Profile profile){
	profile.getApp().removeProfile(profile);
	profile.unlink();
    }

    /**
     * Remove the app from the root.
     * @param app the app to remove.
     */
    public boolean removeApp(App app){
	Root root;
	if(app.getAppType() == gamesRoot.getAppType()){
	    root = gamesRoot;
	}else if(app.getAppType() == appsRoot.getAppType()){
	    root = appsRoot;
	}else{
	    return false;
	}
	root.removeApp(app);
        return true;
    }

    /**
     * Adds the app if it doesn't already exists.
     * @param app the app to add.
     * @return true if able to add and false otherwise.
     */
    public boolean addApp(App app){
	Root root = null;
	if(app.getAppType() == gamesRoot.getAppType()){
	    root = gamesRoot;
	}else if(app.getAppType() == appsRoot.getAppType()){
	    root = appsRoot;
	}else{
	    return false;
	}
	// check if there already exists an app!
	for(App testApp: root.getList()){
	    if(testApp.getName().equals(app.getName())){
		return false;
	    }
	}
	root.addApp(app);
        return true;
    }
}
