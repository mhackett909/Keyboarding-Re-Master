package com.monkygames.kbmaster.driver;

import java.util.ArrayList;

/**
 * Contains a list of Device Packages. Used by XStream.
 */
public class DeviceList {
    /**
     * The list of devices used locally.
     */
    private ArrayList<DevicePackage> devices;
    public DeviceList(){ devices = new ArrayList<>(); }
    public ArrayList<DevicePackage> getList(){ return devices; }
}
