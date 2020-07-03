package com.monkygames.kbmaster.profiles;
/**
 * Contains information about the application associated with this profile.
 * @author vapula87
 */
public class AppInfo {
    private String appName;
    private AppType appType;
    private String deviceName;
    public AppInfo(App app) {
        appName = app.getName();
        appType = app.getAppType();
        deviceName = app.getDeviceName();
    }
    public String getName() { return appName; }
    public String getDeviceName() { return deviceName; }
    public AppType getAppType() { return appType; }
}
