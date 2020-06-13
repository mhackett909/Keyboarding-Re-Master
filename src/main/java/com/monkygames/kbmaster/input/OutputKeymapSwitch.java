/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains the keymap switch event information that will be
 * used internally to detect keymap switch events.
 * processing.
 * @version 1.0
 */
public class OutputKeymapSwitch extends Output{

// ============= Class variables ============== //
    /**
     * Switches back to the previous keymap on release.
     */
    private boolean isSwitchOnRelease;
    /**
     * Used for specifying if its toggled or not.
     */
    private String originalName;

// ============= Constructors ============== //
    public OutputKeymapSwitch(String name, int keycode, boolean isSwitchOnRelease){
	super(name,keycode,0);
	this.isSwitchOnRelease = isSwitchOnRelease;
	this.originalName = new String(name);
	updateName();
    }
// ============= Public Methods ============== //
    public boolean isIsSwitchOnRelease() {
	return isSwitchOnRelease;
    }

    public void setIsSwitchOnRelease(boolean isSwitchOnRelease) {
	this.isSwitchOnRelease = isSwitchOnRelease;
	updateName();
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private void updateName(){
	if(isSwitchOnRelease){
	    name = originalName+" (while held)";
	}else{
	    name = originalName;
	}
    }
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	OutputKeymapSwitch output = new OutputKeymapSwitch(name,keycode,isSwitchOnRelease);
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
