/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.profiles;

// === jnostromo imports === //

import com.monkygames.kbmaster.input.Keymap;


/**
 * Contains the configuration for all the keymaps.
 * @version 1.0
 */
public class Profile{

    // ============= Class variables ============== //
    /**
     * The app this profile is classified under.
     */
    private AppInfo appInfo;
    /**
     * The name of the profile.
     */
    private String profileName;
    /**
     * The keymaps for this profile.
     */
    private Keymap[] keymaps;
    /**
     * The author of this profile.
     */
    private String author;
    /**
     * The time in milliseconds
     */
    private long lastUpdatedDate;
    /**
     * The information about this profile.
     */
    private String info;
    /**
     *  The default map.
     */
    private int defaultMap;

    // ============= Constructors ============== //
    public Profile(){
	this(new App("",null,null,"Generic", "Generic", AppType.APPLICATION),"Default");
    }
    public Profile(App app, String profileName){
	this(app,profileName,"","",0,0);
    }
    public Profile(App app, String profileName, String author, String info, long lastUpdatedDate, int defaultMap){
	    this.profileName = profileName;
	    this.author = author;
	    this.info = info;
	    this.lastUpdatedDate = lastUpdatedDate;
	    keymaps = new Keymap[8];
	    this.defaultMap = defaultMap;
	    setAppInfo(app);
    }

    // ============= Setters and Getters ============== //
    public AppInfo getAppInfo(){
        return appInfo;
    }
    public String getProfileName() {
        return profileName;
    }
    public long getLastUpdatedDate() { return lastUpdatedDate; }
    public int getDefaultKeymap() { return defaultMap; }
    public String getInfo() { return info; }
    public String getAuthor() { return author; }
    /**
     * Returns the keymap at the specified index.
     * Note, valid index is 0 - 7 inclusive.
     */
    public Keymap getKeymap(int index){
        return keymaps[index];
    }
    /**
     * Returns a collection of all keymaps for this profile.
     */
    public Keymap[] getKeymaps(){ return keymaps; }
    public void setKeymap(int index, Keymap keymap){ keymaps[index] = keymap; }
    public void setAppInfo(App app){
        this.appInfo = new AppInfo(app);
    }
    public void setAuthor(String author) { this.author = author; }
    public void setInfo(String info) { this.info = info; }
    public void setDefaultKeymap(int defaultMap) { this.defaultMap = defaultMap; }
    public void setLastUpdatedDate(long lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }

    // ============= Public Methods ============== //
    /**
     * Clones this profile.
     * Note, this is a deep clone.
     * @param profile the clone target.
     * @return the cloned profile.
     */
    public Profile cloneProfile(Profile profile, App app){
        profile.setAppInfo(app);
	    for(int i = 0; i < 8; i++)
	        profile.setKeymap(i, (Keymap)keymaps[i].clone());
	    profile.setDefaultKeymap(this.getDefaultKeymap());
	    profile.setAuthor(this.author);
	    profile.setLastUpdatedDate(this.lastUpdatedDate);
	    profile.setInfo(this.info);
	    return profile;
    }

    // ============= Extended Methods ============== //
    @Override
    public String toString(){
	return profileName;
    }
}
