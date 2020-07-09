package com.monkygames.kbmaster.profiles;

/**
 * Contains information about this profile. Used by the device descriptor.
 * @author vapula87
 */
public class ProfileInfo {
    private String profileName;
    private int defaultMap;
    public ProfileInfo(Profile profile) {
        profileName = profile.getProfileName();
        defaultMap = profile.getDefaultKeymap();
    }
    public String getProfileName() { return profileName; }
    public int getDefaultMap() { return defaultMap; }
}
