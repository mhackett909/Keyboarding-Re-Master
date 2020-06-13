/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains the mouse event information that will be sent to the system for event 
 * processing.
 * @version 1.0
 */
public class OutputMouse extends Output{

// ============= Class variables ============== //
    /**
     * The type of mouse event.
     */
    private MouseType mouseType;
    public enum MouseType { MouseWheel, MouseClick, MouseDoubleClick};
// ============= Constructors ============== //
    public OutputMouse(String name, int keycode, MouseType mouseType){
	super(name,keycode,0);
	this.mouseType = mouseType;
    }
// ============= Public Methods ============== //
    public MouseType getMouseType() {
	return mouseType;
    }
    public void setMouseType(MouseType mouseType) {
	this.mouseType = mouseType;
    }
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	OutputMouse output = new OutputMouse(name,keycode,mouseType);
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
