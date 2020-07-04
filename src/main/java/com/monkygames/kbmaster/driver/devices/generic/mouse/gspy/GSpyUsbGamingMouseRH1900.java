/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver.devices.generic.mouse.gspy;

// === java imports === //
import java.awt.event.InputEvent;
// === kbmaster imports === //
import com.monkygames.kbmaster.driver.*;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.OutputMouse.MouseType;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import java.awt.Rectangle;

/**
 * Contributed by David Ferreira (FZ)
 */
public class GSpyUsbGamingMouseRH1900 extends Device{

// ============= Class variables ============== //
// ============= Constructors ============== //
    public GSpyUsbGamingMouseRH1900(){
    	super("G-SPY","RH1900","G-SPY USB Gaming Mouse", DeviceType.MOUSE,
    			"/com/monkygames/kbmaster/driver/generic/mouse/gspy/rh1900/resources/gspy_rh1900_icon.png",
    			// === description === //
    			"* RH1900 Wired USB 2.0 800/1600/2400/3200dpi Laser Engine Game Mouse - Black\n"
    			+ "* 7 programmable Hyperesponse buttons\n"
    			+ "* 1000Hz Ultrapolling / 1ms response time\n",
    			// ===
    			"com.monkygames.kbmaster.driver.devices.generic.mouse.gspy.GSpyUsbGamingMouseRH1900",
    			"/com/monkygames/kbmaster/driver/generic/mouse/gspy/rh1900/Gspy_RH1900.fxml",
    			"/com/monkygames/kbmaster/driver/generic/mouse/gspy/rh1900/resources/gspy_rh1900_printable.png",
    			null,
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
	jinputB = net.java.games.input.Component.Identifier.Button.LEFT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(1,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON1_DOWN_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.RIGHT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(2,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_DOWN_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.EXTRA;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(3,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.SIDE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(4,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(7,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_DOWN_MASK,MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button._8;	
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(8,jinputB.getName()),new OutputDisabled()));
	
	// wheel
	keymap.setzUpWheelMapping(new WheelMapping(new Wheel(5),new OutputMouse("Scroll Up",-1,MouseType.MouseWheel)));
	keymap.setzDownWheelMapping(new WheelMapping(new Wheel(6),new OutputMouse("Scroll Down",1,MouseType.MouseWheel)));
	//keymap.setMiddleWheelMapping(new WheelMapping(new Wheel(7),new OutputMouse("Middle-Click",InputEvent.BUTTON2_MASK,MouseType.MouseClick)));
	return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
	return keymap.getButtonMapping(getId(index));
    }
    @Override
    public Mapping getMapping(int index, Keymap keymap){
	switch(index){
	    case 5:
		return keymap.getzUpWheelMapping();
	    case 6:
		return keymap.getzDownWheelMapping();
	    default:
		return keymap.getButtonMapping(getId(index));
	}

    }
    @Override
    public String getId(int index){
	switch(index){
	    case 1:
		return net.java.games.input.Component.Identifier.Button.LEFT.getName();
	    case 2:
		return net.java.games.input.Component.Identifier.Button.RIGHT.getName();
	    case 7:
		return net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
	    case 3:
		return net.java.games.input.Component.Identifier.Button.EXTRA.getName();
	    case 4:
		return net.java.games.input.Component.Identifier.Button.SIDE.getName();
	    case 8:
		return net.java.games.input.Component.Identifier.Button._8.getName();
		
	}
	return net.java.games.input.Component.Identifier.Button.LEFT.getName();
    }
// ============= Extended Methods ============== //
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
	Rectangle rect = new Rectangle();
	switch(mapping.getInputHardware().getID()){
	    // left
	    case 1:
		rect.x = 188;
		rect.y = 119;
		rect.width = 174;
		rect.height = 134;
		break;
	    // right
	    case 2:
		rect.x = 278;
		rect.y = 119;
		rect.width = 290;
		rect.height = 134;
		break;
	    // extra
	    case 3:
		rect.x = 460;
		rect.y = 374;
		rect.width = 445;
		rect.height = 389;
		break;
	    // side
	    case 4:
		rect.x = 507;
		rect.y = 330;
		rect.width = 491;
		rect.height = 345;
		break;
	    // zup
	    case 5:
		rect.x = 83;
		rect.y = 76;
		rect.width = 83;
		rect.height = 95;
		break;
	    // zdwn
	    case 6:
		rect.x = 83;
		rect.y = 187;
		rect.width = 83;
		rect.height = 206;
		break;
	    // zmid
	    case 7:
		rect.x = 83;
		rect.y = 134;
		rect.width = 83;
		rect.height = 153;
		break;
	    case 8:
		rect.x = 83;
		rect.y = 100;
		rect.width = 83;
		rect.height = 120;
		break;			
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
