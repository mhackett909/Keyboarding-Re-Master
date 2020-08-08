package com.monkygames.kbmaster.driver.devices.sony;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.input.Button;
import net.java.games.input.Component;

import java.awt.*;
import java.awt.event.InputEvent;

public class SonyDualShock4 extends Device {

    public SonyDualShock4() {
        super("Sony","DualShock4","Sony Computer Entertainment Wireless Controller", DeviceType.GAMEPAD,
                "/com/monkygames/kbmaster/driver/sony/dualshock/resources/icon.png",
                // === Description === //
                "Coming soon...",
                // === =========== === //
                "com.monkygames.kbmaster.driver.devices.sony.SonyDualShock4",
                "/com/monkygames/kbmaster/driver/sony/dualshock/SonyDualShock4.fxml",
                "/com/monkygames/kbmaster/driver/sony/dualshock/resources/printable.png",
                null,
                true);
    }
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
        return null;
    }

    @Override
    public Keymap generateDefaultKeymap(int id) {
        Keymap keymap = new Keymap(id+1);
        net.java.games.input.Component.Identifier.Button jinputB;
        jinputB = net.java.games.input.Component.Identifier.Button.LEFT;
        //Component.Identifier.Axis.POV;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new com.monkygames.kbmaster.input.Button(1,jinputB.getName()),new OutputMouse(jinputB.getName(), InputEvent.BUTTON1_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = net.java.games.input.Component.Identifier.Button.RIGHT;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new com.monkygames.kbmaster.input.Button(2,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON3_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = net.java.games.input.Component.Identifier.Button.MIDDLE;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new com.monkygames.kbmaster.input.Button(3,jinputB.getName()),new OutputMouse(jinputB.getName(),InputEvent.BUTTON2_DOWN_MASK, OutputMouse.MouseType.MouseClick)));
        jinputB = net.java.games.input.Component.Identifier.Button.SIDE;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new com.monkygames.kbmaster.input.Button(6,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.EXTRA;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(7,jinputB.getName()),new OutputDisabled()));
        keymap.setzUpWheelMapping(new WheelMapping(new Wheel(4),new OutputMouse("Scroll Up",-1, OutputMouse.MouseType.MouseWheel)));
        keymap.setzDownWheelMapping(new WheelMapping(new Wheel(5),new OutputMouse("Scroll Down",1, OutputMouse.MouseType.MouseWheel)));
        return keymap;
    }

    @Override
    public ButtonMapping getButtonMapping(int index, Keymap keymap) {

        return keymap.getButtonMapping(getId(index));
    }

    @Override
    public Mapping getMapping(int index, Keymap keymap) {
        return keymap.getButtonMapping(getId(index));
    }
}
