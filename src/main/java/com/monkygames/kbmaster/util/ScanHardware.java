/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.engine.PollEventQueue;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.LinuxEnvironmentPlugin;

/**
 * Scans the hardware and prints out the information.
 * @version 1.0
 */
public class ScanHardware implements Runnable{

// ============= Class variables ============== //
    private Controller[] pollControllers;
// ============= Constructors ============== //
    public ScanHardware(String deviceName, boolean doPoll){
	pollControllers = scanHardware(deviceName);
	if(doPoll){
	    if(pollControllers == null){
		System.out.println("Device: "+deviceName+" not found");
		return;
	    }
	    System.out.println("====== Polling ====== ");
	    Thread thread = new Thread(this);
	    thread.start();
	}
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    /**
     * Scans all hardware and writes out the components to standard out.
     * @param deviceName the name of the device.
     * @return the controllers specified by the devicename.
     */
    private Controller[] scanHardware(String deviceName){
	ArrayList<Controller> returnControllers = new ArrayList<>();
	Controller[] controllers = LinuxEnvironmentPlugin.getDefaultEnvironment().getControllers();
	System.out.println("Device Name == "+deviceName);
	for(Controller controller: controllers){
	    // Check if this controller should be returned for polling.
	    System.out.println("Controller.getName == "+controller.getName());
	    if(deviceName != null && controller.getName().equals(deviceName)){
		System.out.println("adding controller == "+controller.getName());
		returnControllers.add(controller);
	    }
	    // if no device was specified, than print the controller.
	    if(deviceName == null){
		printControllerInformation(controller);
	    }else if(controller.getName().equals(deviceName)){
		// if its the controller, print it to the screen.
		printControllerInformation(controller);
	    }
	}
	if(deviceName == null || returnControllers.size()== 0){
	    return null;
	}
	Controller[] returnControllersArray = new Controller[returnControllers.size()];
	for(int i = 0; i < returnControllers.size(); i++){
	    returnControllersArray[i] = returnControllers.get(i);
	}
	return returnControllersArray;
    }
    /**
     * Gets the component details as a string.
     * @param component gets the details from this object.
     * @return the details of the component.
     */
    private String getComponentDetails(Component component){
		//String name = component.getIdentifier().getName();
		//ButtonMapping mapping = keymap.getButtonMapping(name);
		//processOutput(name, mapping.getOutput(), event.getValue());
	String data = "Name["+component.getName()+
		      "] Identifier["+component.getIdentifier().getName();
	if(component.isAnalog()){
	    data += "] Analog";
	}else{
	    data += "] Digital";
	}
	if(component.isRelative()){
	    data += " Relative";
	}else{
	    data += " Absolute";
	}
	String className = component.getIdentifier().getClass().getName();
	data += " Class["+className+"]";
	return data;
    }
    /**
     * Prints the controller information to the screen.
     * @param controller the controller to print.
     */
    private void printControllerInformation(Controller controller){
	System.out.println("Name: "+controller.getName()+" Type: "+controller.getType());
	Component[] components = controller.getComponents();
	System.out.println("=== Inputs ===");
	System.out.println("Number of Inputs: "+components.length);
	for(Component component: components){
	    if(component.getName().equals("Unknown")){
		continue;
	    }
	    String data = getComponentDetails(component);
	    System.out.println(data);
	}
	System.out.println("===============");
    }
// ============= Implemented Methods ============== //
    @Override
    public void run(){
	System.out.println("Number of controllers to poll = "+pollControllers.length);
	ArrayList <EventMapping> eventQueues = new ArrayList<>();

	for(Controller controller: pollControllers){
	    eventQueues.add(new EventMapping(new PollEventQueue(controller.getComponents()),
	    "["+controller.getName()+":"+controller.getType()+"] ",
	    controller));
	}

	while(true){
	    //for(Event event: keyboardEventQueue.getEvents()){
	    for(EventMapping eventMapping: eventQueues){
		eventMapping.controller.poll();
		String out = eventMapping.deviceInfo;

		for(Event event: eventMapping.eventQueue.getEvents()){
		    Component component = event.getComponent();
		    //String name = component.getIdentifier().getName();
		    if(component != null){
			System.out.println(out + getComponentDetails(component));
		    }
		}
		/*
		for(Component component: controller.getComponents()){
		    if(component.getIdentifier().getName().equals("A")){
			System.out.println(component.getIdentifier()+" data = "+component.getPollData());
		    }
		}
		*/
	    }
	    try {
		// print out every 0.1 seconds
		Thread.sleep(100);
	    } catch (InterruptedException ex) {
		Logger.getLogger(ScanHardware.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
    class EventMapping {
	public PollEventQueue eventQueue;
	public String deviceInfo;
	public Controller controller;
	public EventMapping(PollEventQueue eventQueue, String deviceInfo, Controller controller){
	    this.eventQueue = eventQueue;
	    this.deviceInfo = deviceInfo;
	    this.controller = controller;
	}
    }
// ============= Static Methods ============== //
    public static void main(String[] args){
	boolean doPoll = true;
	String deviceName = null;
	if(args.length == 0){
	    doPoll = false;
	}else{
	    deviceName = args[0];
	}
	ScanHardware scanHardware = new ScanHardware(deviceName,doPoll);
    }
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
