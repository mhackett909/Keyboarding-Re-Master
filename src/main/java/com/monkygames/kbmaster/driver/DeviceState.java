package com.monkygames.kbmaster.driver;

import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.Profile;

/**
 * Contains the state of the device.
 */
public class DeviceState {
    /**
     * The default profile.
     */
    private Profile profile;
    /**
     * The current status of the device.
     */
    private boolean isConnected;
    /**
     * The state in which this driver is being used or not.
     */
    private boolean isEnabled;
    /**
     * The name of the package this state belongs to.
     */
    private String packageName;

    public DeviceState(Profile profile, boolean isConnected,
        boolean isEnabled, String packageName){

        this.profile = profile;
        this.isConnected = isConnected;
        this.isEnabled = isEnabled;
        this.packageName = packageName;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) { this.profile = profile; }

    public boolean isIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
