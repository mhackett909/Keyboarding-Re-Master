/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.tartarus;

// === java imports === //
import java.awt.event.KeyEvent;
// === jinput imports === //
import net.java.games.input.Component.Identifier.Key;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import java.awt.Rectangle;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class Tartarus extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Tartarus(){
	super("Razer","Tartarus","Razer Razer Tartarus", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/tartarus/resources/icon.png",
		// === Description === //
		"* 25 Fully programmable keys including an 8-way thumb-pad\n"
		+ "* Instantaneous switching between 8 key maps\n"
		+ "* Improved ergonomic form factor\n"
		+ "* Adjustable soft-touch wrist pad for remarkable comfort\n"
		+ "* Full anti - ghosting\n"
		+ "* Backlit keys for total control  even under dark conditions\n"
		+ "* Braided fibre cable\n"
		+ "* Approximate Size Width: 153mm / 6.03\n"
		+ "* Height: 186mm / 7.32\n"
		+ "* Depth: 54.8mm / 2.16\n"
		+ "* Product weight: 370g / 0.66lbs",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.razer.tartarus.Tartarus",
		"/com/monkygames/kbmaster/driver/razer/tartarus/Tartarus.fxml",
		"/com/monkygames/kbmaster/driver/razer/tartarus/resources/printable.png",
		"http://www.amazon.com/gp/product/B00EHBKUTE/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B00EHBKUTE&linkCode=as2&tag=monkygamescom-20&linkId=BAIC34E3NQE62O44");
	// setup input bindings
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key.TAB.getName(),    KeyEvent.VK_TAB));
	inputMaps.put(i,new InputMap(i++,Key.Q.getName(),      KeyEvent.VK_Q));
	inputMaps.put(i,new InputMap(i++,Key.W.getName(),      KeyEvent.VK_W));
	inputMaps.put(i,new InputMap(i++,Key.E.getName(),      KeyEvent.VK_E));
	inputMaps.put(i,new InputMap(i++,Key.R.getName(),      KeyEvent.VK_R));
	inputMaps.put(i,new InputMap(i++,Key.CAPITAL.getName(),KeyEvent.VK_CAPS_LOCK));
	inputMaps.put(i,new InputMap(i++,Key.A.getName(),      KeyEvent.VK_A));
	inputMaps.put(i,new InputMap(i++,Key.S.getName(),      KeyEvent.VK_S));
	inputMaps.put(i,new InputMap(i++,Key.D.getName(),      KeyEvent.VK_D));
	inputMaps.put(i,new InputMap(i++,Key.F.getName(),      KeyEvent.VK_F));
	inputMaps.put(i,new InputMap(i++,Key.LSHIFT.getName(), KeyEvent.VK_SHIFT));
	inputMaps.put(i,new InputMap(i++,Key.Z.getName(),      KeyEvent.VK_Z));
	inputMaps.put(i,new InputMap(i++,Key.X.getName(),      KeyEvent.VK_X));
	inputMaps.put(i,new InputMap(i++,Key.C.getName(),      KeyEvent.VK_C));
	inputMaps.put(i,new InputMap(i++,Key.V.getName(),      KeyEvent.VK_V));
	inputMaps.put(i,new InputMap(i++,Key.SPACE.getName(),  KeyEvent.VK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key.LALT.getName(),   KeyEvent.VK_ALT));
	inputMaps.put(i,new InputMap(i++,Key.UP.getName(),     KeyEvent.VK_UP));
	inputMaps.put(i,new InputMap(i++,Key.RIGHT.getName(),  KeyEvent.VK_RIGHT));
	inputMaps.put(i,new InputMap(i++,Key.DOWN.getName(),   KeyEvent.VK_DOWN));
	inputMaps.put(i,new InputMap(i++,Key.LEFT.getName(),   KeyEvent.VK_LEFT));

    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private int[] getBindingPosition(int buttonID){
	int pos[] = new int[2];
	switch(buttonID){
	    case 1:
		pos[0] = 85;
		pos[1] = 120;
		break;
	    case 2:
		pos[0] = 190;
		pos[1] = 90;
		break;
	    case 3:
		pos[0] = 300;
		pos[1] = 90;
		break;
	    case 4:
		pos[0] = 410;
		pos[1] = 90;
		break;
	    case 5:
		pos[0] = 520;
		pos[1] = 90;
		break;
	    case 6:
		pos[0] = 85;
		pos[1] = 230;
		break;
	    case 7:
		pos[0] = 190;
		pos[1] = 200;
		break;
	    case 8:
		pos[0] = 300;
		pos[1] = 200;
		break;
	    case 9:
		pos[0] = 410;
		pos[1] = 200;
		break;
	    case 10:
		pos[0] = 520;
		pos[1] = 200;
		break;
	    case 11:
		pos[0] = 85;
		pos[1] = 336;
		break;
	    case 12:
		pos[0] = 190;
		pos[1] = 305;
		break;
	    case 13:
		pos[0] = 300;
		pos[1] = 305;
		break;
	    case 14:
		pos[0] = 410;
		pos[1] = 305;
		break;
	    case 15:
		pos[0] = 514;
		pos[1] = 305;
		break;
	    case 16:
		pos[0] = 606;
		pos[1] = 475;
		break;
	    case 17:
		pos[0] = 567;
		pos[1] = 382;
		break;
	    case 18:
		pos[0] = 692;
		pos[1] = 335;
		break;
	    case 19:
		pos[0] = 730;
		pos[1] = 370;
		break;
	    case 20:
		pos[0] = 692;
		pos[1] = 397;
		break;
	    case 21:
		pos[0] = 660;
		pos[1] = 370;
		break;
	}
	return pos;
    }
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id+1);
	inputMaps.values().stream().forEach((inputMap) -> {
	    addButtonMapping(keymap, inputMap);
	});
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
// ============= Extended Methods ============== //
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
	Rectangle rect = new Rectangle();
	int[] pos = getBindingPosition(mapping.getInputHardware().getID());
	rect.x = pos[0];
	rect.y = pos[1];
	rect.width = pos[0] - 8;
	rect.height = pos[1] + 15;

	return rect;
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
