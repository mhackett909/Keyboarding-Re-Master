/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver.razer.taipan;

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
 * Contains information about a specific device.
 * @version 1.0
 */
public class Taipan extends Device{

// ============= Class variables ============== //

// ============= Constructors ============== //
    public Taipan(){
	super("Razer","Taipan","Razer Razer Taipan", DeviceType.MOUSE,
		"/com/monkygames/kbmaster/driver/razer/taipan/resources/TaipanIcon.png",
		// === description === //
		"* 4G Dual Sensor System - 8200dpi\n"
		+ "* Ambidextrous form factor\n"
		+ "* Razer Synapse 2.0 enabled\n"
		+ "* 9 programmable Hyperesponse buttons\n"
		+ "* 1000Hz Ultrapolling / 1ms response time\n"
		+ "* Up to 200 inches per second / 50g acceleration\n"
		+ "* Approximate size: 124 mm (Length) x 63 mm (Width) x 36 mm (Height)\n"
		+ "* Approximate Weight: 95g without cable, 132g with cable\n",
		// ===  
		"1.0",
		"com.monkygames.kbmaster.driver.razer.taipan.Taipan",
		"/com/monkygames/kbmaster/driver/razer/taipan/Taipan.fxml",
		"/com/monkygames/kbmaster/driver/razer/taipan/resources/taipan_printable.png",
		"http://www.amazon.com/gp/product/B008BGXYBM/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B008BGXYBM&linkCode=as2&tag=monkygamescom-20&linkId=TNHGSNN5OYZP3VYX",
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
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(1,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON1_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.RIGHT;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(2,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_MASK, MouseType.MouseClick)));
	jinputB = net.java.games.input.Component.Identifier.Button.EXTRA;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(3,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.SIDE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(4,jinputB.getName()),new OutputDisabled()));
	jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
	keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(7,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_MASK,MouseType.MouseClick)));

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
