/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Holds the mapping between the device's event and the Robot event.
 * @version 1.0
 */
public class ButtonMapping extends Mapping{

    public ButtonMapping(Button inputButton, Output output){
	super(inputButton, output);
    }

    @Override
    public Object clone(){
	return new ButtonMapping((Button)inputHardware.clone(),(Output)output.clone());
    }
    @Override
    public String toString(){
	return "ButtonMapping["+inputHardware+","+output+"]";
    }

}
