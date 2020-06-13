/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * The base class for handling output information.
 * processing.
 * @version 1.0
 */
public class Output{

// ============= Class variables ============== //
    /**
     * The name of this output key.
     */
    protected String name;
    /**
     * The key code to be sent to the system for an event generation.
     */
    protected int keycode;
    /**
     * The modifier for the key to be sent to the system for an event generation.
     */
    protected int modifier;
    /**
     * The description for the functionality of this output.
     */
    protected String description;
// ============= Constructors ============== //
    public Output(){}
    public Output(String name, int keycode, int modifier){
	this.name = name;
	this.keycode = keycode;
	this.modifier = modifier;
	this.description = "";
    }
// ============= Public Methods ============== //
    public int getKeycode() {
	return keycode;
    }

    public int getModifier() {
	return modifier;
    }

    public String getName() {
	return name;
    }

    public String getDescription() {
	return description;
    }

    public void setKeycode(int keycode) {
	this.keycode = keycode;
    }

    public void setModifier(int modifier) {
	this.modifier = modifier;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setDescription(String description){
	this.description = description;
    }
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	Output output = new Output(name,keycode,modifier);
	output.setDescription(description);
	return output;
    }
    @Override
    public String toString(){
	return name;
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
