/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * A hardware component from the device.
 * @version 1.0
 */
public class Hardware{

// ============= Class variables ============== //
    /**
     * The id of this hardware.
     */
    protected int id;
    /**
     * The default mapping.
     */
    protected String inputString;
// ============= Constructors ============== //
    public Hardware(int id, String inputString){
	this.id = id;
	this.inputString = inputString;
    }
// ============= Public Methods ============== //
    /**
     * Returns the id of this button.
     */
    public int getID(){
	return id;
    }

    /**
     * Returns true if this hardware is this key.
     * @param identifier the jinput key to check.
     * @return true if this hardware matches the identifier and false otherwise.
     */
    public boolean isHardware(String identifier){
	return inputString.equals(identifier);
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new Hardware(this.id,this.inputString);
    }
    @Override
    public String toString(){
	return "Hardware["+id+","+inputString+"]";
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
