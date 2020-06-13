/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.belkin.n52;

// === java imports === //
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.driver.razer.nostromo.Nostromo;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class N52 extends Nostromo{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public N52(){
	super("Belkin","n52","Honey Bee  Nostromo SpeedPad2 ", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/belkin/n52/resources/icon.png",
		// === Description === //
		"* Enhanced tactile feedback and button responsiveness for rapid key presses\n"
		+"* 15 fully programmable keys built for complete customization and speed\n"
		+"* Adjustable soft-touch wrist pad for maximum comfort and endurance\n"
		+"* Programmable 8-way thumb pad with removable joystick\n"
		+"* Nonslip rubber pads grip in place for aggressive fragging\n"
		+"\nImages courtesy of Steve W\n",
		// === =========== === //
		"2.0",
		"com.monkygames.kbmaster.driver.belkin.n52.N52",
		"/com/monkygames/kbmaster/driver/belkin/n52/N52.fxml",
		"/com/monkygames/kbmaster/driver/belkin/n52/resources/printable.png",
		"http://www.amazon.com/gp/product/B0000DC643/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B0000DC643&linkCode=as2&tag=monkygamescom-20&linkId=EBBITLCCOAU4QXVX",
		true);
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
