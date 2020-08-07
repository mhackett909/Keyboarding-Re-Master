package com.monkygames.kbmaster.profiles;

import com.monkygames.kbmaster.cloud.metadata.MetaData;
import com.monkygames.kbmaster.cloud.metadata.SyncMetaData;
import com.monkygames.kbmaster.driver.Device;

import java.util.Calendar;

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
        metaData = new MetaData(0);
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
    public void addProfile(App app, Profile profile){
        app.addProfile(profile);
        setMetaData(new MetaData(Calendar.getInstance().getTimeInMillis()));
    }
    /**
     * Removes the profile from the database and updates the list.
     *
     * @param profile the profile to remove.
     */
    public void removeProfile(App app, Profile profile) {
        app.removeProfile(profile);
        setMetaData(new MetaData(Calendar.getInstance().getTimeInMillis()));
    }

    /**
     * Remove the app from the root.
     * @param app the app to remove.
     */
    public boolean removeApp(App app) {
        Root root;
        if (app.getAppType() == gamesRoot.getAppType()) {
            root = gamesRoot;
        } else if (app.getAppType() == appsRoot.getAppType()) {
            root = appsRoot;
        } else {
            return false;
        }
        root.removeApp(app);
        setMetaData(new MetaData(Calendar.getInstance().getTimeInMillis()));
        return true;
    }

    /**
     * Adds the app if it doesn't already exists.
     * @param app the app to add.
     * @return true if able to add and false otherwise.
     */
    public boolean addApp(App app) {
        Root root;
        if (app.getAppType() == gamesRoot.getAppType()) root = gamesRoot;
        else if (app.getAppType() == appsRoot.getAppType()) root = appsRoot;
        else return false;
        // check if there already exists an app!
        for (App testApp : root.getList()) {
            if (testApp.getName().equals(app.getName())) return false;
        }
        root.addApp(app);
        setMetaData(new MetaData(Calendar.getInstance().getTimeInMillis()));
        return true;
    }
    public void close() {
        appsRoot.close();
        gamesRoot.close();
    }
}
