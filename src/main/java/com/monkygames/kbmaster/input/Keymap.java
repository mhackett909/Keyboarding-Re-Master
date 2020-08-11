/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

// === java imports === //
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The key mappings.
 * @version 1.0
 */
public class Keymap{

// ============= Class variables ============== //
    /**
     * The id of this keymap - valid from 1 to 8.
     */
    private int id;
    /**
     * Button mappings.
     */
    private HashMap<String,ButtonMapping> buttonMappings;
	/**
	 * Joystick mappings.
	 */
	private HashMap<String,JoystickMapping> joystickMappings;
    /**
     * The z-axis up wheel mapping.
     */
    private WheelMapping zUpWheelMapping;
    /**
     * The z-axis down wheel mapping.
     */
    private WheelMapping zDownWheelMapping;
	/**
     * Describes this keymap.
     */
    private String description;
// ============= Constructors ============== //
    public Keymap(int id) {
		this.id = id;
		buttonMappings = new HashMap<>();
		joystickMappings = new HashMap<>();
		description = "";
	}
// ============= Public Methods ============== //
    public void addButtonMapping(String index, ButtonMapping buttonMapping){
		buttonMappings.put(index, buttonMapping);
    }
    public ButtonMapping getButtonMapping(String index){
   	   	return buttonMappings.get(index);
    }
	public void addJoystickMapping(String index, JoystickMapping joystickMapping) {
		joystickMappings.put(index, joystickMapping);
	}
	public JoystickMapping getJoystickMapping(String index){ return joystickMappings.get(index); }
    public void setzDownWheelMapping(WheelMapping zDownWheelMapping) {
	this.zDownWheelMapping = zDownWheelMapping;
    }
	public WheelMapping getzDownWheelMapping() { return zDownWheelMapping;  }
	public void setzUpWheelMapping(WheelMapping zUpWheelMapping) {
		this.zUpWheelMapping = zUpWheelMapping;
	}
    public WheelMapping getzUpWheelMapping() {
	return zUpWheelMapping;
    }
    public void setDescription(String description){
	this.description = description;
    }
	public String getDescription(){	return description; }
    public int getID(){
	return id;
    }

    /**
     * Returns a list of all the mappings.
     * @return a list of all the mappings.
	 * @param wheelsToo true if including wheel and joystick mappings.
     */
    public ArrayList<Mapping> getMappings(boolean wheelsToo) {
		ArrayList<Mapping> list = new ArrayList<Mapping>(buttonMappings.values());
		if (wheelsToo) {
			if (zUpWheelMapping != null) list.add(zUpWheelMapping);
			if (zDownWheelMapping != null) list.add(zDownWheelMapping);
			for (JoystickMapping joystickMapping : joystickMappings.values()) list.add(joystickMapping);
		}
		return list;
	}
	public void close() {
    	try {
    	    buttonMappings.clear();
		    joystickMappings.clear();
	    }catch (NullPointerException e) { }
    	zDownWheelMapping = null;
    	zUpWheelMapping = null;
    }
// ============= Extended Methods ============== //
    @Override
    public Object clone() {
		Keymap keymap = new Keymap(this.id);
		keymap.setDescription(description);
		for (String key : buttonMappings.keySet()) {
			ButtonMapping mapping = buttonMappings.get(key);
			keymap.addButtonMapping(key, (ButtonMapping) mapping.clone());
		}
	    for (String key : joystickMappings.keySet()) {
			JoystickMapping mapping = joystickMappings.get(key);
		    keymap.addJoystickMapping(key, (JoystickMapping) mapping.clone());
	    }
		if (zUpWheelMapping != null)
			keymap.setzUpWheelMapping((WheelMapping) zUpWheelMapping.clone());
		if (zDownWheelMapping != null)
			keymap.setzDownWheelMapping((WheelMapping) zDownWheelMapping.clone());
		return keymap;
	}
    public String toString() {
		String out = "";
		for (ButtonMapping buttonMapping : buttonMappings.values())
			out += buttonMapping + ",";
		for (JoystickMapping joystickMapping : joystickMappings.values())
			out += joystickMapping + ",";
		out += zUpWheelMapping + ",";
		out += zDownWheelMapping + ",";
		return out;
	}
}
