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
    /**
     * Contains the events.
     */
    private ArrayList<Event> events;
	private float[] previousValues;

    public PollEventQueue(Component[] components) {
		this.components = components;
		previousValues = new float[components.length];
		// populate previous values
		for (int i = 0; i < components.length; i++)
			previousValues[i] = components[i].getPollData();
		events = new ArrayList<>();
	}

    /**
     * Runs through all components to check for updates to the poll value.
     */
    public ArrayList<Event> getEvents() {

		// clear the events list
		events.clear();
		float val;
		// check for new events
		for (int i = 0; i < components.length; i++) {
			val = components[i].getPollData();
			if (val != previousValues[i]) {

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
	/**
	 * Checks if an event exists. Used to ensure the grabHardware() method does not grab
	 * the mouse if a mouse button is being held down. It would otherwise result in a crash.
	 */
	public boolean eventExists() {
		for (int x = 0; x < components.length; x++) {
			float test = components[x].getPollData();
			if (test > 0) {
				if (!components[x].getName().equals("x") && !components[x].getName().equals("y"))
					return true;
			}
		}
		return false;
	}
	public void close() {
		for (Event event : events) event = null;
		events.clear();
		components = null;
	}
}
