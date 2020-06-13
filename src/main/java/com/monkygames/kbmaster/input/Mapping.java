/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Holds the mapping between the device's event and the Robot event.
 * @version 1.0
 */
public class Mapping{

// ============= Class variables ============== //
    /**
     * The input that is to be mapped.
     */
    protected Hardware inputHardware;
    /**
     * The output which is to be sent through the Robot class.
     */
    protected Output output;
    /**
     * True if has mapping and false otherwise.
     */
    protected boolean hasMapping = true;
// ============= Constructors ============== //
    public Mapping(Hardware inputHardware, Output output){
	this.inputHardware = inputHardware;
	this.output = output;
    }
// ============= Public Methods ============== //
    /**
     * Returns the input button.
     * @return the input button for this mapping.
     */
    public Hardware getInputHardware(){
	return inputHardware;
    }
    /**
     * Returns the output for this mapping.
     */
    public Output getOutput(){
	return output;
    }
    public void setOutput(Output output){
	this.output = output;
    }
    /**
     * True if this has a mapping and false if the key should be disabled.
     */
    public void setMapping(boolean hasMapping){
	this.hasMapping = hasMapping;
    }
    /**
     * Returns true if this has a mapping.
     * @return true if this has a mapping and false if disabled.
     */
    public boolean hasMapping(){
	return hasMapping;
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	Mapping mapping =  new Mapping((Hardware)inputHardware.clone(),(Output)output.clone());
	mapping.setMapping(hasMapping);
	return mapping;
    }
    @Override
    public String toString(){
	return "Mapping["+inputHardware+","+output+"]";
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
