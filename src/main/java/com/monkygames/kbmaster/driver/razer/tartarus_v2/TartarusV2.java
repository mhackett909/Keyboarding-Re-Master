/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.tartarus_v2;

// === java imports === //

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;
import com.monkygames.kbmaster.driver.InputMap;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.input.Button;
import net.java.games.input.Component.Identifier.Key;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

// === jinput imports === //
// === kbmaster imports === //

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class TartarusV2 extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public TartarusV2(){

	super("Razer","Tartarus V2","Razer Razer Tartarus V2", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/tartarus_v2/resources/icon.png",
		// === Description === //
		"* 32 Fully programmable keys including an 8-way thumb-pad and 3-way scroll wheel\n"
		+ "* Instantaneous switching between 8 key maps\n"
		+ "* Improved ergonomic form factor\n"
		+ "* Adjustable palm rest with two positions\n"
		+ "* Full anti - ghosting\n"
		+ "* Backlit keys with 16.8 million color options\n"
		+ "* Braided fiber cable\n"
		+ "* Approximate Size Width: 150mm / 5.90\n"
		+ "* Height: 203mm / 7.99\n"
		+ "* Depth: 59.6mm / 2.34\n"
		+ "* Product weight: 340g / 0.749bs",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.razer.tartarus_v2.TartarusV2",
		"/com/monkygames/kbmaster/driver/razer/tartarus_v2/TartarusV2.fxml",
		"/com/monkygames/kbmaster/driver/razer/tartarus_v2/resources/printable.png",
		"https://www.amazon.com/gp/product/B07754PYFK/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B07754PYFK&linkCode=as2&tag=monkygamescom-20&linkId=BAIC34E3NQE62O44",
		true);

	// setup input bindings
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key._1.getName(),     KeyEvent.VK_1));
	inputMaps.put(i,new InputMap(i++,Key._2.getName(),     KeyEvent.VK_2));
	inputMaps.put(i,new InputMap(i++,Key._3.getName(),     KeyEvent.VK_3));
	inputMaps.put(i,new InputMap(i++,Key._4.getName(),     KeyEvent.VK_4));
	inputMaps.put(i,new InputMap(i++,Key._5.getName(),     KeyEvent.VK_5));
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
	inputMaps.put(i,new InputMap(i++,Key.SPACE.getName(),  KeyEvent.VK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key.UP.getName(),     KeyEvent.VK_UP));
	inputMaps.put(i,new InputMap(i++,Key.RIGHT.getName(),  KeyEvent.VK_RIGHT));
	inputMaps.put(i,new InputMap(i++,Key.DOWN.getName(),   KeyEvent.VK_DOWN));
	inputMaps.put(i,new InputMap(i++,Key.LEFT.getName(),   KeyEvent.VK_LEFT));
	inputMaps.put(i,new InputMap(i++,Key.LALT.getName(),   KeyEvent.VK_ALT));
	inputMaps.put(28,new InputMap(28,
			net.java.games.input.Component.Identifier.Button.MIDDLE.getName(),
			InputEvent.BUTTON2_MASK));

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
		pos[0] = 85;
		pos[1] = 446;
		break;
	    case 17:
		pos[0] = 190;
		pos[1] = 420;
		break;
	    case 18:
		pos[0] = 300;
		pos[1] = 420;
		break;
	    case 19:
		pos[0] = 410;
		pos[1] = 420;
		break;
	    case 20:
		pos[0] = 620;
		pos[1] = 480;
		break;
	    case 21:
		pos[0] = 688;
		pos[1] = 315;
		break;
		case 22:
		pos[0] = 725;
		pos[1] = 355;
		break;
		case 23:
		pos[0] = 688;
		pos[1] = 395;
		break;
		case 24:
		pos[0] = 650;
		pos[1] = 355;
		break;
		case 25:
		pos[0] = 730;
		pos[1] = 200;
		break;
		case 26:
		pos[0] = 632;
		pos[1] = 145;
		break;
		case 27:
		pos[0] = 632;
		pos[1] = 260;
		break;
		case 28:
		pos[0] = 632;
		pos[1] = 200;
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

	// wheel
	net.java.games.input.Component.Identifier.Button jinputB;
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(28,jinputB.getName()),new OutputMouse(jinputB.getName(), InputEvent.BUTTON2_MASK, OutputMouse.MouseType.MouseClick)));
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(26),new OutputMouse("Scroll Up",-1, OutputMouse.MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(27),new OutputMouse("Scroll Down",1, OutputMouse.MouseType.MouseWheel)));

	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
		switch(index){
			case 26:
				return keymap.getzUpWheelMapping();
			case 27:
				return keymap.getzDownWheelMapping();
			//case 28:
			//return keymap.getMiddleWheelMapping();
			default:
				return keymap.getButtonMapping(getId(index));
		}
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
