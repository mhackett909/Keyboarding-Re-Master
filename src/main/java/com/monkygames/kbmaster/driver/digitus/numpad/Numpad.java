/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.digitus.numpad;

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
public class Numpad extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Numpad(){
	super("DIGITUS","Numpad","USB Keyboard", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/digitus/numpad/resources/icon.png",
		// === Description === //
		"* Compliant with USB standards\n" +
		"* Plug and Play compatible\n" +
	        "* Self powered by USB\n" +
		"* Retractable cable\n" +
	        "* Dimension: 13.7 x 9.4 x 2.4 cm",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.digitus.numpad.Numpad",
		"/com/monkygames/kbmaster/driver/digitus/numpad/Numpad.fxml",
		"/com/monkygames/kbmaster/driver/digitus/numpad/resources/printable.png",
		null,
		false);
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key.NUMLOCK.getName(),   KeyEvent.VK_NUM_LOCK));
	inputMaps.put(i,new InputMap(i++,Key.SLASH.getName(),     KeyEvent.VK_SLASH));
	inputMaps.put(i,new InputMap(i++,Key.MULTIPLY.getName(),  KeyEvent.VK_MULTIPLY));
	inputMaps.put(i,new InputMap(i++,Key.MINUS.getName(),     KeyEvent.VK_MINUS));
	inputMaps.put(i,new InputMap(i++,Key._7.getName(),        KeyEvent.VK_7));
	inputMaps.put(i,new InputMap(i++,Key._8.getName(),        KeyEvent.VK_8));
	inputMaps.put(i,new InputMap(i++,Key._9.getName(),        KeyEvent.VK_9));
	inputMaps.put(i,new InputMap(i++,Key.BACK.getName(),      KeyEvent.VK_BACK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key._4.getName(),        KeyEvent.VK_4));
	inputMaps.put(i,new InputMap(i++,Key._5.getName(),        KeyEvent.VK_5));
	inputMaps.put(i,new InputMap(i++,Key._6.getName(),        KeyEvent.VK_6));
	inputMaps.put(i,new InputMap(i++,Key.ADD.getName(),       KeyEvent.VK_ADD));
	inputMaps.put(i,new InputMap(i++,Key._1.getName(),        KeyEvent.VK_1));
	inputMaps.put(i,new InputMap(i++,Key._2.getName(),        KeyEvent.VK_2));
	inputMaps.put(i,new InputMap(i++,Key._3.getName(),        KeyEvent.VK_3));
	inputMaps.put(i,new InputMap(i++,Key._0.getName(),        KeyEvent.VK_0));
	inputMaps.put(i,new InputMap(i++,Key.PERIOD.getName(),    KeyEvent.VK_PERIOD));
	inputMaps.put(i,new InputMap(i++,Key.RETURN.getName(),    KeyEvent.VK_ENTER));
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
    private int[] getBindingPosition(int buttonID){
	// 49 is halfway  98 is full
	// top is 19
	// space between = 4
	int button_width = 79;
	int button_height = 79;
	int space = 4;
	int x = 236;
	int row1_y = 19+button_height/2;
	int row2_y = row1_y+button_height+space;
	int row3_y = row2_y+button_height+space;
	int row4_y = row3_y+button_height+space;
	int row5_y = row4_y+button_height+space;
	int row6_y = row5_y+button_height+space;

	int pos[] = new int[2];
	if(buttonID >= 1 && buttonID <= 4){
	    pos[0] = x+(buttonID-1)*(button_width+space);
	    pos[1] = row2_y;
	}else if(buttonID >= 5 && buttonID <= 8){
	    pos[0] = x+(buttonID-5)*(button_width+space);
	    pos[1] = row3_y;
	}else if(buttonID >= 9 && buttonID <= 12){
	    pos[0] = x+(buttonID-9)*(button_width+space);
	    pos[1] = row4_y;
	}else if(buttonID >= 13 && buttonID <= 16){
	    pos[0] = x+(buttonID-13)*(button_width+space);
	    pos[1] = row5_y;
	}else if(buttonID == 17){
	    pos[0] = x;
	    pos[1] = row6_y;
	}else if(buttonID == 18){
	    pos[0] = x+(2)*(button_width+space);
	    pos[1] = row6_y;
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
