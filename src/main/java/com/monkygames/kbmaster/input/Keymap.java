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
     * Holds the mappings for the nostromo's buttons.
     */
    private HashMap<String,ButtonMapping> buttonMappings;
    /**
     * The z-axis up wheel mapping.
     */
    private WheelMapping zUpWheelMapping;
    /**
     * The z-axis down wheel mapping.
     */
    private WheelMapping zDownWheelMapping;
    /**
     * The middle mouse wheel mapping.
     */
    //private WheelMapping middleWheelMapping;
    /**
     * Describes this keymap.
     */
    private String description;
// ============= Constructors ============== //
    public Keymap(int id){
	this.id = id;
	buttonMappings = new HashMap<>();
	description = "";
    }
// ============= Public Methods ============== //
    public void addButtonMapping(String index, ButtonMapping buttonMapping){
	buttonMappings.put(index, buttonMapping);
    }
    public ButtonMapping getButtonMapping(String index){
   	   	return buttonMappings.get(index);
    }

    /*
    public WheelMapping getMiddleWheelMapping() {
	return middleWheelMapping;
    }
    */

    public String getDescription(){	return description;    }

    /*
    public void setMiddleWheelMapping(WheelMapping middleWheelMapping) {
	this.middleWheelMapping = middleWheelMapping;
    }
    */

    public WheelMapping getzDownWheelMapping() { return zDownWheelMapping;  }

    public void setzDownWheelMapping(WheelMapping zDownWheelMapping) {
	this.zDownWheelMapping = zDownWheelMapping;
    }

    public WheelMapping getzUpWheelMapping() {
	return zUpWheelMapping;
    }

    public void setzUpWheelMapping(WheelMapping zUpWheelMapping) {
	this.zUpWheelMapping = zUpWheelMapping;
    }

    public void setDescription(String description){
	this.description = description;
    }

    public int getID(){
	return id;
    }
    public void setID(int id){
	this.id = id;
    }
    /**
     * Returns a list of all the mappings.
     * @return a list of all the mappings.
	 * @param wheelsToo true if including wheel mappings.
     */
    public ArrayList<Mapping> getMappings(boolean wheelsToo) {
		ArrayList<Mapping> list = new ArrayList<Mapping>(buttonMappings.values());
		if (wheelsToo) {
			if (zUpWheelMapping != null) {
				list.add(zUpWheelMapping);
			}
			if (zDownWheelMapping != null) {
				list.add(zDownWheelMapping);
			}
		}
		return list;
	}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	Keymap keymap = new Keymap(this.id);
	keymap.setDescription(description);
	Iterator<String> iterator = buttonMappings.keySet().iterator();
	while(iterator.hasNext()){
	    String key = iterator.next();
	    ButtonMapping mapping = buttonMappings.get(key);
	    keymap.addButtonMapping(key, (ButtonMapping)mapping.clone());
	}
	
	// set setz
	if(zUpWheelMapping != null){
	    keymap.setzUpWheelMapping((WheelMapping)zUpWheelMapping.clone());
	}
	if(zDownWheelMapping != null){
	    keymap.setzDownWheelMapping((WheelMapping)zDownWheelMapping.clone());
	}
	/*
	if(middleWheelMapping != null){
	    keymap.setMiddleWheelMapping((WheelMapping)middleWheelMapping.clone());
	}
	*/

	return keymap;
    }
    public String toString(){
	String out = "";
	for(ButtonMapping buttonMapping: buttonMappings.values()){
	    out += buttonMapping+",";
	}
	out += zUpWheelMapping+",";
	//out += middleWheelMapping+",";
	out += zDownWheelMapping;

	return out;
    }
    public String toStringFormatted(){
	String out = "\tKeymap "+id+"[\n";
	for(ButtonMapping buttonMapping: buttonMappings.values()){
	    out += "\t\t"+buttonMapping+"\n";
	}
	out += "\t\t"+zUpWheelMapping+"\n";
	//out += "\t\t"+middleWheelMapping+"\n";
	out += "\t\t"+zDownWheelMapping;
	out += "\t]\n";

	return out;

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
