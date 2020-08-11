/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Holds the mapping between the device's mouse wheel and the Robot event.
 * @version 1.0
 */
public class WheelMapping extends Mapping{
    public WheelMapping(Wheel inputWheel, Output output){
	super(inputWheel,output);
    }
    @Override
    public Object clone(){
	return new WheelMapping((Wheel)inputHardware.clone(),(Output)output.clone());
    }
    @Override
    public String toString(){
	return "WheelMapping["+inputHardware+","+output+"]";
    }
}
