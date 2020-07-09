/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver;

import com.monkygames.kbmaster.profiles.AppInfo;
import com.monkygames.kbmaster.profiles.ProfileInfo;
/**
 * A device package used by XStream for saving and loading.
 * @author vapula87
 */
public class DevicePackage{
    ///===Private member variables===\\\
    private String packageName;
    private Boolean isEnabled;
    private AppInfo appInfo;
    private ProfileInfo profileInfo;

    ///===Constructor===\\\
    public DevicePackage(Device device) {
        this.packageName = device.getDeviceInformation().getPackageName();
        isEnabled = false;
        profileInfo = null;
        appInfo = null;
    }
    ///===Setters===\\\
    public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
    public void setProfileInfo(ProfileInfo profileInfo) { this.profileInfo = profileInfo; }
    public void setAppInfo(AppInfo appInfo) { this.appInfo = appInfo; }

    ///===Getters===\\\
    public boolean isEnabled() { return isEnabled; }
    public String getPackageName() { return packageName; }
    public ProfileInfo getProfileInfo() { return profileInfo; }
    public AppInfo getAppInfo() { return appInfo; }
}
