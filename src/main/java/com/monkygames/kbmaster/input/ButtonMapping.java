/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Holds the mapping between the device's event and the Robot event.
 * @version 1.0
 */
public class ButtonMapping extends Mapping{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public ButtonMapping(Button inputButton, Output output){
	super(inputButton, output);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new ButtonMapping((Button)inputHardware.clone(),(Output)output.clone());
    }
    @Override
    public String toString(){
	return "ButtonMapping["+inputHardware+","+output+"]";
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
