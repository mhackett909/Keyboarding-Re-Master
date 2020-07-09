/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.profiles;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The root of the data structure.
 * @version 1.0
 */
public class Root{

// ============= Class variables ============== //
    /**
     * The name of this root.
     */
    private String name;
    /**
     * The type of App.
     */
    private AppType appType;
    /**
     * A list of apps.
     */
    private ArrayList<App> apps;
	    
    
// ============= Constructors ============== //
    public Root(String name, AppType appType) {
        this.name = name;
        this.appType = appType;
        apps = new ArrayList<>();
    }
// ============= Public Methods ============== //

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public AppType getAppType() {
	return appType;
    }

    public ArrayList<App> getList() {
        // always sort
        Collections.sort(apps);
        return apps;
    }
    public void addApp(App app){
	apps.add(app);
    }
    public void removeApp(App app){
	apps.remove(app);
    }
    public void close() {
        for (App app : apps) {
            app.close();
            app = null;
        }
        apps.clear();
    }
    @Override
    public String toString(){
	return name;
    }

}
