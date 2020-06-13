/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Holds the mapping to switch to a keymap.
 * @version 1.0
 */
public class KeymapSwitchMapping{


// ============= Class variables ============== //
    /**
     * The input that is to be mapped.
     */
    private Button inputButton;
    /**
     * The keymap to switch to.
     */
    private Output output;
    /**
     * True if has mapping and false otherwise.
     */
    private boolean hasMapping = true;
// ============= Constructors ============== //
    public KeymapSwitchMapping(Button inputButton, Output output){
	this.inputButton = inputButton;
	this.output = output;
    }
// ============= Public Methods ============== //
    /**
     * Returns the input button.
     * @return the input button for this mapping.
     */
    public Button getInputButton(){
	return inputButton;
    }
    /**
     * Returns the output keycode for this mapping.
     */
    public Output getOutput(){
	return output;
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
	KeymapSwitchMapping mapping =  new KeymapSwitchMapping((Button)inputButton.clone(),(Output)output.clone());
	mapping.setMapping(hasMapping);
	return mapping;
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
