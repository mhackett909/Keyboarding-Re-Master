/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains the key event information that will be sent to the system for event 
 * processing.
 * @version 1.0
 */
public class OutputDisabled extends Output{
    public OutputDisabled(){
	super("Disabled",0,0);
    }
    @Override
    public Object clone(){
	OutputDisabled output =  new OutputDisabled();
	output.setDescription(getDescription());
	return output;
    }
}
