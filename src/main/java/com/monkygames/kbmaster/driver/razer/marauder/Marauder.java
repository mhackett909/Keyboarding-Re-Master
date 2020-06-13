/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.marauder;

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
public class Marauder extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Marauder(){
	super("Razer","Maurader","Razer Marauder Razer Marauder", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/razer/marauder/resources/icon.png",
		// === Description === //
		"Designed exclusively for StarCraft II: Wings of Liberty, the Razer Marauder StarCraft II gaming keyboard is a full-featured, tournament ready keyboard with an extremely compact design." +
		"* Full keyboard layout with integrated number pad keys\n" +
		"* APM-Lighting System\n" +
		"* Optimized key travel & spacing\n" +
		"* Ultrapolling™ (1000Hz Polling / 1ms Response)\n" +
		"* Up to 200 inches per second and 50g of acceleration\n" +
		"* Seven-foot lightweight, braided fiber cable",
		// === =========== === //
		"1.1",
		"com.monkygames.kbmaster.driver.razer.marauder.Marauder",
		"/com/monkygames/kbmaster/driver/razer/marauder/Marauder.fxml",
		"/com/monkygames/kbmaster/driver/razer/marauder/resources/printable.png",
		"http://www.amazon.com/gp/product/B004EYSH30/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B004EYSH30&linkCode=as2&tag=monkygamescom-20&linkId=GXXG2WKU6PBUPTRD",
		true);
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key.ESCAPE.getName(),	KeyEvent.VK_ESCAPE));
	inputMaps.put(i,new InputMap(i++,Key.F1.getName(),	KeyEvent.VK_F1));
	inputMaps.put(i,new InputMap(i++,Key.F2.getName(),	KeyEvent.VK_F2));
	inputMaps.put(i,new InputMap(i++,Key.F3.getName(),	KeyEvent.VK_F3));
	inputMaps.put(i,new InputMap(i++,Key.F4.getName(),	KeyEvent.VK_F4));
	inputMaps.put(i,new InputMap(i++,Key.F5.getName(),	KeyEvent.VK_F5));
	inputMaps.put(i,new InputMap(i++,Key.F6.getName(),	KeyEvent.VK_F6));
	inputMaps.put(i,new InputMap(i++,Key.F7.getName(),	KeyEvent.VK_F7));
	inputMaps.put(i,new InputMap(i++,Key.F8.getName(),	KeyEvent.VK_F8));
	inputMaps.put(i,new InputMap(i++,Key.F9.getName(),	KeyEvent.VK_F9));
	inputMaps.put(i,new InputMap(i++,Key.F10.getName(),	KeyEvent.VK_F10));
	inputMaps.put(i,new InputMap(i++,Key.F11.getName(),	KeyEvent.VK_F11));
	inputMaps.put(i,new InputMap(i++,Key.F12.getName(),	KeyEvent.VK_F12));
	inputMaps.put(i,new InputMap(i++,Key.NUMLOCK.getName(),	KeyEvent.VK_NUM_LOCK));
	inputMaps.put(i,new InputMap(i++,Key.SYSRQ.getName(),	KeyEvent.VK_PRINTSCREEN));
	inputMaps.put(i,new InputMap(i++,Key.SCROLL.getName(),	KeyEvent.VK_SCROLL_LOCK));
	inputMaps.put(i,new InputMap(i++,Key.PAUSE.getName(),	KeyEvent.VK_PAUSE));
	inputMaps.put(i,new InputMap(i++,Key.GRAVE.getName(),	KeyEvent.VK_BACK_QUOTE));
	inputMaps.put(i,new InputMap(i++,Key._1.getName(),	KeyEvent.VK_1));
	inputMaps.put(i,new InputMap(i++,Key._2.getName(),	KeyEvent.VK_2));
	inputMaps.put(i,new InputMap(i++,Key._3.getName(),	KeyEvent.VK_3));
	inputMaps.put(i,new InputMap(i++,Key._4.getName(),	KeyEvent.VK_4));
	inputMaps.put(i,new InputMap(i++,Key._5.getName(),	KeyEvent.VK_5));
	inputMaps.put(i,new InputMap(i++,Key._6.getName(),	KeyEvent.VK_6));
	inputMaps.put(i,new InputMap(i++,Key._7.getName(),	KeyEvent.VK_7));
	inputMaps.put(i,new InputMap(i++,Key._8.getName(),	KeyEvent.VK_8));
	inputMaps.put(i,new InputMap(i++,Key._9.getName(),	KeyEvent.VK_9));
	inputMaps.put(i,new InputMap(i++,Key._0.getName(),	KeyEvent.VK_0));
	inputMaps.put(i,new InputMap(i++,Key.MINUS.getName(),	KeyEvent.VK_MINUS));
	inputMaps.put(i,new InputMap(i++,Key.EQUALS.getName(),	KeyEvent.VK_EQUALS));
	inputMaps.put(i,new InputMap(i++,Key.BACK.getName(),	KeyEvent.VK_BACK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key.TAB.getName(),	KeyEvent.VK_TAB));
	inputMaps.put(i,new InputMap(i++,Key.Q.getName(),	KeyEvent.VK_Q));
	inputMaps.put(i,new InputMap(i++,Key.W.getName(),	KeyEvent.VK_W));
	inputMaps.put(i,new InputMap(i++,Key.E.getName(),	KeyEvent.VK_E));
	inputMaps.put(i,new InputMap(i++,Key.R.getName(),	KeyEvent.VK_R));
	inputMaps.put(i,new InputMap(i++,Key.T.getName(),	KeyEvent.VK_T));
	inputMaps.put(i,new InputMap(i++,Key.Y.getName(),	KeyEvent.VK_Y));
	inputMaps.put(i,new InputMap(i++,Key.U.getName(),	KeyEvent.VK_U));
	inputMaps.put(i,new InputMap(i++,Key.I.getName(),	KeyEvent.VK_I));
	inputMaps.put(i,new InputMap(i++,Key.O.getName(),	KeyEvent.VK_O));
	inputMaps.put(i,new InputMap(i++,Key.P.getName(),	KeyEvent.VK_P));
	inputMaps.put(i,new InputMap(i++,Key.LBRACKET.getName(),KeyEvent.VK_BRACELEFT));
	inputMaps.put(i,new InputMap(i++,Key.RBRACKET.getName(),KeyEvent.VK_BRACERIGHT));
	inputMaps.put(i,new InputMap(i++,Key.BACKSLASH.getName(),KeyEvent.VK_BACK_SLASH));
	inputMaps.put(i,new InputMap(i++,Key.CAPITAL.getName(),	KeyEvent.VK_CAPS_LOCK));
	inputMaps.put(i,new InputMap(i++,Key.A.getName(),	KeyEvent.VK_A));
	inputMaps.put(i,new InputMap(i++,Key.S.getName(),	KeyEvent.VK_S));
	inputMaps.put(i,new InputMap(i++,Key.D.getName(),	KeyEvent.VK_D));
	inputMaps.put(i,new InputMap(i++,Key.F.getName(),	KeyEvent.VK_F));
	inputMaps.put(i,new InputMap(i++,Key.G.getName(),	KeyEvent.VK_G));
	inputMaps.put(i,new InputMap(i++,Key.H.getName(),	KeyEvent.VK_H));
	inputMaps.put(i,new InputMap(i++,Key.J.getName(),	KeyEvent.VK_J));
	inputMaps.put(i,new InputMap(i++,Key.K.getName(),	KeyEvent.VK_K));
	inputMaps.put(i,new InputMap(i++,Key.L.getName(),	KeyEvent.VK_L));
	inputMaps.put(i,new InputMap(i++,Key.SEMICOLON.getName(),KeyEvent.VK_SEMICOLON));
	inputMaps.put(i,new InputMap(i++,Key.APOSTROPHE.getName(),KeyEvent.VK_QUOTE));
	inputMaps.put(i,new InputMap(i++,Key.RETURN.getName(),	KeyEvent.VK_ENTER));
	inputMaps.put(i,new InputMap(i++,Key.LSHIFT.getName(),	KeyEvent.VK_SHIFT));
	inputMaps.put(i,new InputMap(i++,Key.Z.getName(),	KeyEvent.VK_Z));
	inputMaps.put(i,new InputMap(i++,Key.X.getName(),	KeyEvent.VK_X));
	inputMaps.put(i,new InputMap(i++,Key.C.getName(),	KeyEvent.VK_C));
	inputMaps.put(i,new InputMap(i++,Key.V.getName(),	KeyEvent.VK_V));
	inputMaps.put(i,new InputMap(i++,Key.B.getName(),	KeyEvent.VK_B));
	inputMaps.put(i,new InputMap(i++,Key.N.getName(),	KeyEvent.VK_N));
	inputMaps.put(i,new InputMap(i++,Key.M.getName(),	KeyEvent.VK_M));
	inputMaps.put(i,new InputMap(i++,Key.COMMA.getName(),	KeyEvent.VK_COMMA));
	inputMaps.put(i,new InputMap(i++,Key.PERIOD.getName(),	KeyEvent.VK_PERIOD));
	inputMaps.put(i,new InputMap(i++,Key.SLASH.getName(),	KeyEvent.VK_SLASH));
	inputMaps.put(i,new InputMap(i++,Key.RSHIFT.getName(),	KeyEvent.VK_SHIFT));
	inputMaps.put(i,new InputMap(i++,Key.LCONTROL.getName(),KeyEvent.VK_CONTROL));
	inputMaps.put(i,new InputMap(i++,Key.LWIN.getName(),	KeyEvent.VK_WINDOWS));
	inputMaps.put(i,new InputMap(i++,Key.LALT.getName(),	KeyEvent.VK_ALT));
	inputMaps.put(i,new InputMap(i++,Key.SPACE.getName(),	KeyEvent.VK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key.RALT.getName(),	KeyEvent.VK_ALT));
	//inputMaps.put(i,new InputMap(i++,Key..getName())); Function -- doens't register
	i++;
	//inputMaps.put(i,new InputMap(i++,Key.APPS.getName())); -- doesn't register
	i++;
	inputMaps.put(i,new InputMap(i++,Key.RCONTROL.getName(),KeyEvent.VK_CONTROL));
	inputMaps.put(i,new InputMap(i++,Key.INSERT.getName(),	KeyEvent.VK_INSERT));

	// currently, numpad has some issues!
	inputMaps.put(i,new InputMap(i++,Key.HOME.getName(),	KeyEvent.VK_HOME));
	inputMaps.put(i,new InputMap(i++,Key.PAGEUP.getName(),	KeyEvent.VK_PAGE_UP));
	inputMaps.put(i,new InputMap(i++,Key.SUBTRACT.getName(),KeyEvent.VK_SUBTRACT));
	inputMaps.put(i,new InputMap(i++,Key.DELETE.getName(),	KeyEvent.VK_DELETE));
	inputMaps.put(i,new InputMap(i++,Key.END.getName(),	KeyEvent.VK_END));
	inputMaps.put(i,new InputMap(i++,Key.PAGEDOWN.getName(),KeyEvent.VK_PAGE_DOWN));

	inputMaps.put(i,new InputMap(i++,Key.NUMPAD4.getName(),	KeyEvent.VK_NUMPAD4));
	inputMaps.put(i,new InputMap(i++,Key.NUMPAD5.getName(), KeyEvent.VK_NUMPAD5));
	inputMaps.put(i,new InputMap(i++,Key.NUMPAD6.getName(), KeyEvent.VK_NUMPAD6));
	inputMaps.put(i,new InputMap(i++,Key.NUMPAD1.getName(), KeyEvent.VK_NUMPAD1));
	inputMaps.put(i,new InputMap(i++,Key.UP.getName(),	KeyEvent.VK_UP));
	inputMaps.put(i,new InputMap(i++,Key.NUMPAD3.getName(),	KeyEvent.VK_NUMPAD3));
	inputMaps.put(i,new InputMap(i++,Key.LEFT.getName(),	KeyEvent.VK_LEFT));
	inputMaps.put(i,new InputMap(i++,Key.DOWN.getName(),	KeyEvent.VK_DOWN));
	inputMaps.put(i,new InputMap(i++,Key.RIGHT.getName(),	KeyEvent.VK_RIGHT));
	inputMaps.put(i,new InputMap(i++,Key.ADD.getName(),	KeyEvent.VK_ADD));
	inputMaps.put(i,new InputMap(i++,Key.NUMPADENTER.getName(),KeyEvent.VK_ENTER));
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private int[] getBindingPosition(int buttonID){
	int button_width = 37;
	int x = 44;
	int row1_y = 161;
	int row2_y = 216;
	int row3_y = 253;
	int row4_y = 289;
	int row5_y = 326;
	int row6_y = 364;

	int pos[] = new int[2];
	if(buttonID == 1){
	    pos[0] = x-3;
	    pos[1] = row1_y;

	// F1 --- F4
	}else if(buttonID >= 2 && buttonID <= 5){
	    pos[0] = 115+(buttonID-2)*button_width;
	    pos[1] = row1_y;

	// F5 --- F8
	}else if(buttonID >= 6 && buttonID <= 9){
	    pos[0] = 282+(buttonID-6)*button_width;
	    pos[1] = row1_y;

	// F9 --- F12
	}else if(buttonID >= 10 && buttonID <= 13){
	    pos[0] = 449+(buttonID-10)*button_width;
	    pos[1] = row1_y;

	// Num Mode --- Pause
	}else if(buttonID >= 14 && buttonID <= 17){
	    pos[0] = 614+(buttonID-14)*button_width;
	    pos[1] = row1_y;

	// ` --- Backspace
	}else if(buttonID >= 18 && buttonID <= 31){
	    pos[0] = x+(buttonID-18)*button_width;
	    pos[1] = row2_y;

	// tab
	}else if(buttonID == 32){
	    pos[0] = x;
	    pos[1] = row3_y;

	// Q --- \
	}else if(buttonID >= 33 && buttonID <= 45){
	    pos[0] = x+53+(buttonID-33)*button_width;
	    pos[1] = row3_y;

	// caps lock
	}else if(buttonID == 46){
	    pos[0] = x;
	    pos[1] = row4_y;

	// A --- Enter
	}else if(buttonID >= 47 && buttonID <= 58){
	    pos[0] = x+63+(buttonID-47)*button_width;
	    pos[1] = row4_y;

	// shift
	}else if(buttonID == 59){
	    pos[0] = x;
	    pos[1] = row5_y;

	// Z --- Shift
	}else if(buttonID >= 60 && buttonID <= 70){
	    pos[0] = x+79+(buttonID-60)*button_width;
	    pos[1] = row5_y;

	// lctrl
	}else if(buttonID == 71){
	    pos[0] = x;
	    pos[1] = row6_y;

	// win
	}else if(buttonID == 72){
	    pos[0] = x+51;
	    pos[1] = row6_y;

	// lalt
	}else if(buttonID == 73){
	    pos[0] = x+98;
	    pos[1] = row6_y;

	// space
	}else if(buttonID == 74){
	    pos[0] = x+144;
	    pos[1] = row6_y;

	// ralt
	}else if(buttonID == 75){
	    pos[0] = x+361;
	    pos[1] = row6_y;

	// function
	}else if(buttonID == 76){
	    pos[0] = x+361+43;
	    pos[1] = row6_y;

	// menu?
	}else if(buttonID == 77){
	    pos[0] = x+361+44*2;
	    pos[1] = row6_y;

	// rctrl
	}else if(buttonID == 78){
	    pos[0] = x+361+44*3;
	    pos[1] = row6_y;

	// Insert --- "-"
	}else if(buttonID >= 79 && buttonID <= 82){
	    pos[0] = x+571+(buttonID-79)*button_width;
	    pos[1] = row2_y;

	// Del --- PgDn
	}else if(buttonID >= 83 && buttonID <= 85){
	    pos[0] = x+571+(buttonID-83)*button_width;
	    pos[1] = row3_y;

	// num4 --- num6
	}else if(buttonID >= 86 && buttonID <= 88){
	    pos[0] = x+571+(buttonID-86)*button_width;
	    pos[1] = row4_y;

	// num1 --- num3
	}else if(buttonID >= 89 && buttonID <= 91){
	    pos[0] = x+571+(buttonID-89)*button_width;
	    pos[1] = row5_y;

	// left --- right
	}else if(buttonID >= 92 && buttonID <= 94){
	    pos[0] = x+571+(buttonID-92)*button_width;
	    pos[1] = row6_y;

	// num+
	}else if(buttonID == 95){
	    pos[0] = x+571+(3)*button_width;
	    pos[1] = row3_y;

	// num enter
	}else if(buttonID == 96){
	    pos[0] = x+571+(3)*button_width;
	    pos[1] = row5_y;
	}
	return pos;
    }
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id+1);
	for(InputMap inputMap: inputMaps.values()){
	    addButtonMapping(keymap,inputMap);
	}
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
