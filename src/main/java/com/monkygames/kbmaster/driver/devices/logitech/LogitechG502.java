package com.monkygames.kbmaster.driver.devices.logitech;
import java.awt.event.InputEvent;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;

import com.monkygames.kbmaster.input.*;
import net.java.games.input.Component;
// === java imports === //

// === kbmaster imports === //

import java.awt.Rectangle;

public class LogitechG502 extends Device {
    public LogitechG502() {
            super("Logitech","G502","Logitech Gaming Mouse G502", DeviceType.MOUSE,
                    "/com/monkygames/kbmaster/driver/logitech/g502/resources/icon.png",
                    // === Description === //
                    "Logitech updated its iconic G502 gaming mouse to deliver even "
                    +"higher performance and more precise functionality than ever. Logitech G502 Hero "
                    +"high performance gaming mouse features the next generation Hero 16K optical sensor, "
                    +"the highest performing and most efficient gaming sensor Logitech has ever made.\n\n"
                    +"An all-new lens and an updated tracking algorithm deliver ultra-precise tracking with "
                    +"no acceleration, smoothing, or filtering over the entire DPI range. Now, customize RGB "
                    +"mouse lighting to match your style and environment or sync to other Logitech G products.\n\n"
                    +"No matter your gaming style, it's easy to tweak G502 Hero to match your requirements with "
                    +"custom profiles for your games, adjustable sensitivity from 200 up to 16000 DPI, and a "
                    +"tunable weight system that allows for tuning and balancing of up to five 3.6G weights for "
                    +"just the right balance and feel.",
                    // === =========== === //
                    "com.monkygames.kbmaster.driver.devices.logitech.LogitechG502",
                    "/com/monkygames/kbmaster/driver/logitech/g502/LogitechG502.fxml",
                    "/com/monkygames/kbmaster/driver/logitech/g502/resources/printable.png",
                    "http://www.amazon.com/gp/product/B07GBZ4Q68/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=B07GBZ4Q68&linkCode=as2&tag=monkygamescom-20",
                    true);
    }
    @Override
    public Keymap generateDefaultKeymap(int id){
        Keymap keymap = new Keymap(id+1);
        Component.Identifier.Button jinputB;
        jinputB = Component.Identifier.Button.LEFT;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(1,jinputB.getName()),new OutputMouse(jinputB.getName(), InputEvent.BUTTON1_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = Component.Identifier.Button.RIGHT;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(2,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = Component.Identifier.Button.MIDDLE;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(3,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = Component.Identifier.Button.SIDE;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(6,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.EXTRA;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(7,jinputB.getName()),new OutputDisabled()));
        keymap.setzUpWheelMapping(new WheelMapping(new Wheel(4),new OutputMouse("Scroll Up",-1, OutputMouse.MouseType.MouseWheel)));
        keymap.setzDownWheelMapping(new WheelMapping(new Wheel(5),new OutputMouse("Scroll Down",1, OutputMouse.MouseType.MouseWheel)));
        return keymap;
    }
    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap){
        return keymap.getButtonMapping(getId(index));
    }
    
    @Override
    public JoystickMapping getJoystickMapping(int index, Keymap keymap) {
        return null;
    }
    
    @Override
    public Mapping getMapping(int index, Keymap keymap){
        switch(index){
            case 4:
                return keymap.getzUpWheelMapping();
            case 5:
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
            case 3:
                return net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
            case 6:
                return net.java.games.input.Component.Identifier.Button.SIDE.getName();
            case 7:
                return net.java.games.input.Component.Identifier.Button.EXTRA.getName();
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
            // zmid
            case 3:
                rect.x = 83;
                rect.y = 134;
                rect.width = 83;
                rect.height = 153;
                break;
            // zup
            case 4:
                rect.x = 83;
                rect.y = 76;
                rect.width = 83;
                rect.height = 95;
                break;
            // zdwn
            case 5:
                rect.x = 83;
                rect.y = 187;
                rect.width = 83;
                rect.height = 206;
                break;
            // side
            case 6:
                rect.x = 507;
                rect.y = 330;
                rect.width = 491;
                rect.height = 345;
                break;
            // extra
            case 7:
                rect.x = 460;
                rect.y = 374;
                rect.width = 445;
                rect.height = 389;
                break;
        }
        return rect;
    }
}
