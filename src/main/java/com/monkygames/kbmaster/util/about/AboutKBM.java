package com.monkygames.kbmaster.util.about;

import com.monkygames.kbmaster.KeyboardingMaster;

/* 
 * See COPYING in top-level directory.
 */

/**
 * Information about db4o.
 * @version 1.0
 */
public class AboutKBM extends AboutProgram{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public AboutKBM(){
	super("KeyboardingMaster.png",
		"Keyboarding Master",
		KeyboardingMaster.VERSION,
		"Monky Games",
		"https://sourceforge.net/projects/kbmaster/",
		"The keyboard master provides its users with the ability to custom configure their input devices for "
		+ "specific applications within a unified configuration environment. The main unique feature for input "
		+ "devices is the keymap concept. Each application can utilize 8 unique keymaps in which the user "
		+ "can toggle through via user assigned shortcuts. This enables a user to maintain a more ergonomic "
		+ "hand position when one hand is required for other duties such as mouse or tablet."
		);
    }

// ============= Public Methods ============== //
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
