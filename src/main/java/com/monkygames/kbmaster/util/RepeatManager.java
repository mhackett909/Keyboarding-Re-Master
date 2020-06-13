/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * In linux, the OS automatically repeats key presses.  This is a problem
 * for most games especially FPS's.  This class allows the repeat to be disabled 
 * and re-enabled.
 * @version 1.0
 */
public class RepeatManager{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Sets the repeat for linux keypresses.
     * Note, only works in linux
     * @param isRepeat true to set repeat on and false for off.
     */
    public static void setRepeat(boolean isRepeat){
	try {
	    String state = "";
	    if(isRepeat){
		state = "on";
	    }else{
		state = "off";
	    }
	    Runtime rt = Runtime.getRuntime();
	    Process exec = rt.exec("xset r "+state);
	} catch (IOException ex) {
	    Logger.getLogger(RepeatManager.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
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
