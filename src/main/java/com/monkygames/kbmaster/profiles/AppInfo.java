package com.monkygames.kbmaster.profiles;
/**
 * Contains information about the application associated with this profile.
 * @author vapula87
 */
public class AppInfo {
    public String appName;
    public AppType appType;
    public AppInfo(App app) {
        appName = app.getName();
        appType = app.getAppType();
    }
    public String getName() { return appName; }
    public AppType getAppType() { return appType; }
}
