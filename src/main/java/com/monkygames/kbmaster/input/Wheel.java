/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.input;

// === jinput imports === //
import net.java.games.input.Component.Identifier.Axis;

/**
 * A wheel event from the device.
 * 1 is scroll up, 2 is scroll down, and 3 is middle button.
 * @version 1.0
 */
public class Wheel extends Hardware{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public Wheel(int id){
	super(id,"");
	if(id == 1 || id == 2){
	    inputString = Axis.Z.getName();
	}else{
	    inputString = net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
	}

    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new Wheel(id);
    }
    @Override
    public String toString(){
	return "Wheel["+id+","+inputString+"]";
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
