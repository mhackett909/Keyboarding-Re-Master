/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver.devices.belkin.n52te;

// === java imports === //
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.driver.devices.razer.nostromo.Nostromo;

/**
 * Contains information about a specific device.
 */
public class N52TE extends Nostromo{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public N52TE(){
	super("Belkin","n52te","Belkin Belkin n52te", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/belkin/n52te/resources/Icon.png",
		// === Description === //
		"* Backlit keypad and scroll wheel for total control in dark conditions\n"
		+"* Enhanced tactile feedback and button responsiveness for rapid key presses\n"
		+"* 15 fully programmable keys built for complete customization and speed\n"
		+"* Adjustable soft-touch wrist pad for maximum comfort and endurance\n"
		+"* Programmable 8-way thumb pad with removable joystick\n"
		+"* Nonslip rubber pads grip in place for aggressive fragging\n"
		+"\nImages courtesy of Nathan T under the CC BY-SA 3.0\n",
		// === =========== === //
		"com.monkygames.kbmaster.driver.devices.belkin.n52te.N52TE",
		"/com/monkygames/kbmaster/driver/belkin/n52te/N52TE.fxml",
		"/com/monkygames/kbmaster/driver/belkin/n52te/resources/printable.png",
		"http://www.amazon.com/gp/product/B0010YL6ZS/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B0010YL6ZS&linkCode=as2&tag=monkygamescom-20",
		true);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
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
