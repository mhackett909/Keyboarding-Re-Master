/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.engine;

import com.monkygames.kbmaster.driver.Device;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.java.games.input.Controller;
import net.java.games.input.LinuxEnvironmentPlugin;

/**
 * Checks for hardware state changes.
 * @version 1.0
 */
public class HardwareDetector implements Runnable{

// ============= Class variables ============== //
    /**
     * Contains the en
     */
    private List<Device> deviceList;
    /**
     * True if detecting and false otherwise.
     */
    private boolean detecting;
    /**
     * The amount of time to check for new devices.
     */
    private static final long sleepPassive = 1000;
// ============= Constructors ============== //
    public HardwareDetector(){
	deviceList = Collections.synchronizedList(new ArrayList<Device>());
	detecting = true;
	Thread thread = new Thread(this);
	thread.start();
    }
// ============= Public Methods ============== //
    public void addDevice(Device device){
	deviceList.add(device);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void checkDevices(){
	Controller[] controllers = LinuxEnvironmentPlugin.getDefaultEnvironment().rescanControllers();
	boolean exists = false;
	synchronized (deviceList) {
	    for(Device device: deviceList){
		exists = false;
		for(Controller controller: controllers){
		    if(controller.getName().equals(device.getDeviceInformation().getJinputName())){
			exists = true;	
			break;
		    }
		}
		if(exists){
		    
		}
	    }
	}
    }
// ============= Implemented Methods ============== //
    public void run(){
	while(detecting){
	    try{
		Thread.sleep(sleepPassive);
	    }catch(Exception e){}
	}
    }
// ============= Extended Methods ============== //
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
