/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * A Button from the device.
 * Buttons 01 - 14 are the keypad.
 * Button 15 is labeled and is the thumb button.
 * Button 16 is the button just above the d-pad.
 * The d-pad is 17 - 20.
 * @version 1.0
 */
public class Button extends Hardware{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public Button(int id, String inputString){
	super(id,inputString);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new Button(this.id,this.inputString);
    }
    @Override
    public String toString(){
	return "Button["+id+","+inputString+"]";
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
