/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.profiles;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 * Contains information about an App.
 * App's are used for sorting profiles.
 * All profiles are children of an App.
 * @version 1.0
 */
public class App implements Comparable{

// ============= Class variables ============== //
    /**
     * The information about the app.
     */
    private String info;
    /**
     * The image of this application's logo.
     */
    private byte[] appLogoByteArray;
    /**
     * The image of the developer's logo of this app.
     */
    private byte[] devLogoByteArray;
    /**
     * The name of this app.
     */
    private String name;
    /**
     * The type of App.
     */
    private AppType appType;
    /**
     * A list of profiles.
     */
    private ArrayList<Profile> profiles;
	    
    
// ============= Constructors ============== //

    /**
     *
     * @param info
     * @param appLogoPath
     * @param devLogoPath
     * @param name
     * @param appType
     */
    public App(String info, String appLogoPath, String devLogoPath, String name, AppType appType){
	this.info = info;
	setAppLogoPath(appLogoPath);
	setDevLogoPath(devLogoPath);
	this.name = name;
	this.appType = appType;
	profiles = new ArrayList<>();
    }
// ============= Public Methods ============== //

    public String getInfo() {
	return info;
    }

    public void setInfo(String info) {
	this.info = info;
    }

    public void setAppLogoPath(String appLogoPath) {
	if(appLogoPath == null){
	    return;
	}
	try {
	    appLogoByteArray = Files.readAllBytes(new File(appLogoPath).toPath());
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    /**
     * Returns the app logo image and null if it doesn't exists.
     */
    public Image getAppLogo(){
	if(appLogoByteArray == null){
	    return null;
	}
	return new Image(new ByteArrayInputStream(appLogoByteArray));
    }

    public void setDevLogoPath(String devLogoPath) {
	if(devLogoPath == null){
	    return;
	}
	try {
	    devLogoByteArray = Files.readAllBytes(new File(devLogoPath).toPath());
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    /**
     * Returns the dev logo image and null if it doesn't exists.
     */
    public Image getDevLogo(){
	if(devLogoByteArray == null){
	    return null;
	}
	return new Image(new ByteArrayInputStream(devLogoByteArray));
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public AppType getAppType() {
	return appType;
    }

    public void setAppType(AppType appType) {
	this.appType = appType;
    }
    public void addProfile(Profile profile){
	profiles.add(profile);
    }
    public void removeProfile(Profile profile){
	profiles.remove(profile);
    }
    public ArrayList<Profile> getProfiles(){
	return profiles;
    }
    /**
     * True if the apps match and false otherwise.
     */
    @Override
    public boolean equals(Object app){
	if(app instanceof App){
	    App testApp = (App)app;
	    if(testApp.getName().equals(name) &&
	       testApp.getAppType() == appType){
		return true;
	    }
	}
	return false;
    }
    public void printString(){
	String ret = "App["+name+",";
	for(Profile profile: profiles){
	    ret += "\n"+profile;
	}
	ret += "]";
	System.out.println(ret);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public int compareTo(Object obj) {
	if(obj instanceof App){
	    App app = (App)obj;
	    return name.compareToIgnoreCase(app.getName());
	}
	return -1;
    }
// ============= Extended Methods ============== //
    @Override
    public String toString(){
	return name;
    }
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
