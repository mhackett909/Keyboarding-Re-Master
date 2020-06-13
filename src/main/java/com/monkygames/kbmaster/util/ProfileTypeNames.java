/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

// === kbmaster imports === //
import com.monkygames.kbmaster.profiles.AppType;

/**
 * [about]
 * @version 1.0
 */
public class ProfileTypeNames{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Returns the string human readable representation of the type.
     * @param type the type of profile.
     * @return the string representation of the type.
     */
    public static String getProfileTypeName(AppType type){
	switch(type){
	    case APPLICATION:
		return "Application";
	    case GAME:
	    default:
		return "Game";

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
