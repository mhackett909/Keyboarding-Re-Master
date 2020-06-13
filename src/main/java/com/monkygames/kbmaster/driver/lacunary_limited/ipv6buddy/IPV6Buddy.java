/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.lacunary_limited.ipv6buddy;

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
public class IPV6Buddy extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public IPV6Buddy(){
	super("Lacunary Limited","IPv6 Buddy","SONiX USB Keyboard", DeviceType.KEYBOARD,
		"/com/monkygames/kbmaster/driver/lacunary_limited/ipv6buddy/resources/icon.png",
		// === Description === //
		"* 0 to F right at your fingertips\n" +
	        "* Period key for IPv4 backwards compatibility and IPv4 mapped IPv6 addresses\n" +
	        "* A shift key-less colon key\n" +
		"* Tab key to change fields with ease\n" +
	        "* Forward slash for CIDR notation\n" +
	        "* Double action colon key for zero laden IPs",
		// === =========== === //
		"1.0",
		"com.monkygames.kbmaster.driver.lacunary_limited.ipv6buddy.IPV6Buddy",
		"/com/monkygames/kbmaster/driver/lacunary_limited/ipv6buddy/IPV6Buddy.fxml",
		"/com/monkygames/kbmaster/driver/lacunary_limited/ipv6buddy/resources/printable.png",
		null,
		false);
	int i = 1;
	inputMaps.put(i,new InputMap(i++,Key.D.getName(),         KeyEvent.VK_D));
	inputMaps.put(i,new InputMap(i++,Key.E.getName(),         KeyEvent.VK_E));
	inputMaps.put(i,new InputMap(i++,Key.F.getName(),         KeyEvent.VK_F));
	inputMaps.put(i,new InputMap(i++,Key.BACK.getName(),      KeyEvent.VK_BACK_SPACE));
	inputMaps.put(i,new InputMap(i++,Key.A.getName(),         KeyEvent.VK_A));
	inputMaps.put(i,new InputMap(i++,Key.B.getName(),         KeyEvent.VK_B));
	inputMaps.put(i,new InputMap(i++,Key.C.getName(),         KeyEvent.VK_C));
	inputMaps.put(i,new InputMap(i++,Key.TAB.getName(),       KeyEvent.VK_TAB));
	inputMaps.put(i,new InputMap(i++,Key._7.getName(),        KeyEvent.VK_7));
	inputMaps.put(i,new InputMap(i++,Key._8.getName(),        KeyEvent.VK_8));
	inputMaps.put(i,new InputMap(i++,Key._9.getName(),        KeyEvent.VK_9));
	inputMaps.put(i,new InputMap(i++,Key.SLASH.getName(),     KeyEvent.VK_SLASH));
	inputMaps.put(i,new InputMap(i++,Key._4.getName(),        KeyEvent.VK_4));
	inputMaps.put(i,new InputMap(i++,Key._5.getName(),        KeyEvent.VK_5));
	inputMaps.put(i,new InputMap(i++,Key._6.getName(),        KeyEvent.VK_6));
	inputMaps.put(i,new InputMap(i++,Key.LSHIFT.getName(),    KeyEvent.VK_SHIFT));
	inputMaps.put(i,new InputMap(i++,Key._1.getName(),        KeyEvent.VK_1));
	inputMaps.put(i,new InputMap(i++,Key._2.getName(),        KeyEvent.VK_2));
	inputMaps.put(i,new InputMap(i++,Key._3.getName(),        KeyEvent.VK_3));
	inputMaps.put(i,new InputMap(i++,Key.RETURN.getName(),    KeyEvent.VK_ENTER));
	inputMaps.put(i,new InputMap(i++,Key._0.getName(),        KeyEvent.VK_0));
	inputMaps.put(i,new InputMap(i++,Key.PERIOD.getName(),    KeyEvent.VK_PERIOD));
	inputMaps.put(i,new InputMap(i++,Key.COLON.getName(),     KeyEvent.VK_COLON));
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
	    pos[1] = row1_y;
	}else if(buttonID >= 5 && buttonID <= 8){
	    pos[0] = x+(buttonID-5)*(button_width+space);
	    pos[1] = row2_y;
	}else if(buttonID >= 9 && buttonID <= 12){
	    pos[0] = x+(buttonID-9)*(button_width+space);
	    pos[1] = row3_y;
	}else if(buttonID >= 13 && buttonID <= 16){
	    pos[0] = x+(buttonID-13)*(button_width+space);
	    pos[1] = row4_y;
	}else if(buttonID >= 17 && buttonID <= 20){
	    pos[0] = x+(buttonID-17)*(button_width+space);
	    pos[1] = row5_y;
	}else if(buttonID >= 21 && buttonID <= 23){
	    pos[0] = x+(buttonID-21)*(button_width+space);
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
