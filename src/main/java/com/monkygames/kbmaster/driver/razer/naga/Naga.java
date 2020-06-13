/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.naga;

// === java imports === //
import java.awt.event.InputEvent;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import net.java.games.input.Component.Identifier.Key;

/**
 * Contains information about a specific device.
 * @version 1.0
 */
public class Naga extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Naga(){
	super("Razer","Naga","Razer Razer Naga", DeviceType.MOUSE,
		"/com/monkygames/kbmaster/driver/razer/naga/resources/icon.png",
		// === description === //
		"* 4G Dual Sensor System - 8200dpi\n"
		+" ",
		// ===  
		"1.0",
		"com.monkygames.kbmaster.driver.razer.naga.Naga",
		"/com/monkygames/kbmaster/driver/razer/naga/Naga.fxml",
		"/com/monkygames/kbmaster/driver/razer/naga/resources/printable.png",
		"http://www.amazon.com/gp/product/B00E8CF268/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B00E8CF268&linkCode=as2&tag=monkygamescom-20&linkId=3UAZCMWKNC4LYCGG",
		true);
    }
// ============= Public Methods ============== //
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //

    @Override
    public Keymap generateDefaultKeymap(int id){
	Keymap keymap = new Keymap(id+1);
	net.java.games.input.Component.Identifier.Button jinputB;
	keymap.addButtonMapping(Key._1.getName(), new ButtonMapping(new Button(1,Key._1.getName()),new OutputKey("1",KeyEvent.VK_1,0)));
	keymap.addButtonMapping(Key._2.getName(), new ButtonMapping(new Button(2,Key._2.getName()),new OutputKey("2",KeyEvent.VK_2,0)));
	keymap.addButtonMapping(Key._3.getName(), new ButtonMapping(new Button(3,Key._3.getName()),new OutputKey("3",KeyEvent.VK_3,0)));
	keymap.addButtonMapping(Key._4.getName(), new ButtonMapping(new Button(4,Key._4.getName()),new OutputKey("4",KeyEvent.VK_4,0)));
	keymap.addButtonMapping(Key._5.getName(), new ButtonMapping(new Button(5,Key._5.getName()),new OutputKey("5",KeyEvent.VK_5,0)));
	keymap.addButtonMapping(Key._6.getName(), new ButtonMapping(new Button(6,Key._6.getName()),new OutputKey("6",KeyEvent.VK_6,0)));
	keymap.addButtonMapping(Key._7.getName(), new ButtonMapping(new Button(7,Key._7.getName()),new OutputKey("7",KeyEvent.VK_7,0)));
	keymap.addButtonMapping(Key._8.getName(), new ButtonMapping(new Button(8,Key._8.getName()),new OutputKey("8",KeyEvent.VK_8,0)));
	keymap.addButtonMapping(Key._9.getName(), new ButtonMapping(new Button(9,Key._9.getName()),new OutputKey("9",KeyEvent.VK_9,0)));
	keymap.addButtonMapping(Key._0.getName(), new ButtonMapping(new Button(10,Key._0.getName()),new OutputKey("0",KeyEvent.VK_0,0)));
	keymap.addButtonMapping(Key.MINUS.getName(), new ButtonMapping(new Button(11,Key.MINUS.getName()),new OutputKey("Minus",KeyEvent.VK_MINUS,0)));
	keymap.addButtonMapping(Key.EQUALS.getName(), new ButtonMapping(new Button(12,Key.EQUALS.getName()),new OutputKey("Equals",KeyEvent.VK_EQUALS,0)));
	jinputB = net.java.games.input.Component.Identifier.Button.LEFT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(13,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON1_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.RIGHT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(14,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(17,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_MASK,MouseType.MouseClick)));

	// wheel 
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(15),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(16),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	switch(index){
	    case 15:
		return keymap.getzUpWheelMapping();
	    case 16:
		return keymap.getzDownWheelMapping();
	    default:
		return keymap.getButtonMapping(getId(index));
	}

    }
    @Override
    public String getId(int index){
	switch(index){
	    case 1:
		return Key._1.getName();
	    case 2:
		return Key._2.getName();
	    case 3:
		return Key._3.getName();
	    case 4:
		return Key._4.getName();
	    case 5:
		return Key._5.getName();
	    case 6:
		return Key._6.getName();
	    case 7:
		return Key._7.getName();
	    case 8:
		return Key._8.getName();
	    case 9:
		return Key._9.getName();
	    case 10:
		return Key._0.getName();
	    case 11:
		return Key.MINUS.getName();
	    case 12:
		return Key.EQUALS.getName();
	    case 13:
		return net.java.games.input.Component.Identifier.Button.LEFT.getName();
	    case 14:
		return net.java.games.input.Component.Identifier.Button.RIGHT.getName();
	    case 17:
		return net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
	}
	return net.java.games.input.Component.Identifier.Button.LEFT.getName();
    }
    private int[] getButtonLocation(int id){
	int ret[] = new int[2];
	// calculate x coordinate
	ret[0] = 495 + 43 * ((id-1)%3);
	ret[1] = 225 + 43 * ((id-1)/3);
	return ret;
    }
    private int[] getDescriptionLocation(int id){
	int ret[] = getButtonLocation(id);
	ret[1] += 15;
	return ret;
    }
// ============= Extended Methods ============== //
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
	Rectangle rect = new Rectangle();
	int id = mapping.getInputHardware().getID();
	if(id >= 1 && id <13){
	    int loc[] = this.getButtonLocation(id);
	    int des[] = this.getDescriptionLocation(id);
	    rect.x = loc[0];
	    rect.y = loc[1];
	    rect.width = des[0];
	    rect.height = des[1];
	}else{
	    switch(mapping.getInputHardware().getID()){
		// buttons 1 - 12
		// left
		case 13:
		    rect.x = 188;
		    rect.y = 119;
		    rect.width = 174;
		    rect.height = 134;
		    break;
		// right
		case 14:
		    rect.x = 278;
		    rect.y = 119;
		    rect.width = 290;
		    rect.height = 134;
		    break;
		// zup
		case 15:
		    rect.x = 95;
		    rect.y = 88;
		    rect.width = 95;
		    rect.height = 103;
		    break;
		// zdwn
		case 16:
		    rect.x = 95;
		    rect.y = 200;
		    rect.width = 95;
		    rect.height = 217;
		    break;
		// zmid
		case 17:
		    rect.x = 95;
		    rect.y = 148;
		    rect.width = 95;
		    rect.height = 163;
		    break;
	    }
	}
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
