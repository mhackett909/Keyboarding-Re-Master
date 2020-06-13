/**
 * Replaces the JInput Event queue, and manages events based on the poll
 * not using the system event queue (not as efficiant but event queue
 * doesn't work in Ubuntu 13.10.
 */

package com.monkygames.kbmaster.engine;

import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Event;

/**
 *
 * @author mspeth
 */
public class PollEventQueue {
    /**
     * The components for this event to monitor.
     */
    private Component[] components; 
    private float[] previousValues;
    /**
     * Contains the events.
     */
    private ArrayList<Event> events;
    private int i;
    private float val;

    public PollEventQueue(Component[] components){
 	this.components = components;
	previousValues = new float[components.length];
	// populate previous values
	for(i = 0; i < components.length; i++){
	    previousValues[i] = components[i].getPollData();
	}
	events = new ArrayList<Event>();
    }

    /**
     * Runs through all components to check for updates to the poll value.
     */
    public ArrayList<Event> getEvents(){

	// clear the events list
	events.clear();

	// check for new events
	for(i = 0; i < components.length; i++){
	    val = components[i].getPollData();
	    if(val != previousValues[i]){

		// create a new Event
		Event event = new Event();
		event.set(components[i], val, 0);

		// add to queue
		events.add(event);

		// set previous values
		previousValues[i] = val;
	    }
	}

	return events;
    }
}
