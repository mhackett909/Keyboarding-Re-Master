package com.monkygames.kbmaster.profiles;

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
