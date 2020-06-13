/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains the key event information that will be sent to the system for event 
 * processing.
 * @version 1.0
 */
public class OutputKey extends Output{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public OutputKey(String name, int keycode, int modifier){
	super(name,keycode,modifier);
    }
// ============= Public Methods ============== //
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	OutputKey output = new OutputKey(this.name,this.keycode,this.modifier);
	output.setDescription(getDescription());
	return output;
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
