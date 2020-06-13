/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.orbweaverchroma;

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
public class OrbweaverChroma extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public OrbweaverChroma(){
	super("Razer","Orbweaver Chroma","Razer Razer Orbweaver Chroma", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/orbweaverchroma/resources/icon.png",
		// === Description === //
	       "* Full mechanical keys with 50g actuation force\n"
	       + "* 30 fully programmable keys Programmable 8-way directional thumb - pad\n"
	       + "* Chroma backlighting with 16.8 million customizable color options\n"
	       + "* Adjustable hand, thumb, and palm - rests modules for maximum comfort\n"
	       + "* Braided fiber cable Backlit keypad for total control  even in dark conditions\n"
	       + "* Approximate Size Width: 154mm / 6.06\n"
	       + "* Height: 202mm / 7.95\n"
	       + "* Depth: 55mm / 2.17\n"
	       + "* Product weight: 395g / 0.87lbs",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.razer.orbweaverchroma.OrbweaverChroma",
		"/com/monkygames/kbmaster/driver/razer/orbweaverchroma/OrbweaverChroma.fxml",
		"/com/monkygames/kbmaster/driver/razer/orbweaverchroma/resources/printable.png",
		null,
		true);
	// setup input bindings
	int i = 1;
	//`~1234
	inputMaps.put(i,new InputMap(i++,Key.GRAVE.getName(),  KeyEvent.VK_QUOTE));
	inputMaps.put(i,new InputMap(i++,Key._1.getName(),     KeyEvent.VK_1));
	inputMaps.put(i,new InputMap(i++,Key._2.getName(),     KeyEvent.VK_2));
	inputMaps.put(i,new InputMap(i++,Key._3.getName(),     KeyEvent.VK_3));
	inputMaps.put(i,new InputMap(i++,Key._4.getName(),     KeyEvent.VK_4));
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
	// new keys?
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
	int mod = -40;
	int mod2 = 23;
	int mod2_y = 10;
	switch(buttonID){
	    case 1:
		pos[0] = 85+mod;
		pos[1] = 120;
		break;
	    case 2:
		pos[0] = 190+mod;
		pos[1] = 90;
		break;
	    case 3:
		pos[0] = 300+mod;
		pos[1] = 90;
		break;
	    case 4:
		pos[0] = 410+mod;
		pos[1] = 90;
		break;
	    case 5:
		pos[0] = 520+mod;
		pos[1] = 90;
		break;
	    case 6:
		pos[0] = 85+mod;
		pos[1] = 230;
		break;
	    case 7:
		pos[0] = 190+mod;
		pos[1] = 200;
		break;
	    case 8:
		pos[0] = 300+mod;
		pos[1] = 200;
		break;
	    case 9:
		pos[0] = 410+mod;
		pos[1] = 200;
		break;
	    case 10:
		pos[0] = 520+mod;
		pos[1] = 200;
		break;
	    case 11:
		pos[0] = 85+mod;
		pos[1] = 336;
		break;
	    case 12:
		pos[0] = 190+mod;
		pos[1] = 305;
		break;
	    case 13:
		pos[0] = 300+mod;
		pos[1] = 305;
		break;
	    case 14:
		pos[0] = 410+mod;
		pos[1] = 305;
		break;
	    case 15:
		pos[0] = 514+mod;
		pos[1] = 305;
		break;
	    case 16:
		pos[0] = 85+mod;
		pos[1] = 442;
		break;
	    case 17:
		pos[0] = 190+mod;
		pos[1] = 410;
		break;
	    case 18:
		pos[0] = 300+mod;
		pos[1] = 410;
		break;
	    case 19:
		pos[0] = 410+mod;
		pos[1] = 410;
		break;
	    case 20:
		pos[0] = 514+mod;
		pos[1] = 410;
		break;
	    case 21:
		pos[0] = 606;
		pos[1] = 475;
		break;
	    case 22:
		pos[0] = 567+mod2+2;
		pos[1] = 382+mod2_y;
		break;
	    case 23:
		pos[0] = 692+mod2;
		pos[1] = 335+mod2_y;
		break;
	    case 24:
		pos[0] = 730+mod2;
		pos[1] = 370+mod2_y;
		break;
	    case 25:
		pos[0] = 692+mod2;
		pos[1] = 397+mod2_y;
		break;
	    case 26:
		pos[0] = 660+mod2;
		pos[1] = 370+mod2_y;
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
