package com.monkygames.kbmaster.driver.devices.sony;

import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceType;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.input.Button;
import net.java.games.input.Component;

import java.awt.*;
import java.awt.event.KeyEvent;

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
                false);
    }
    @Override
    public Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping) {
       return null;
    }

    @Override
    public Keymap generateDefaultKeymap(int id) {
        Keymap keymap = new Keymap(id+1);
    
        //Axis
        Component.Identifier.Axis jinputA;
        jinputA = Component.Identifier.Axis.POV;
        String name = jinputA.getName();
        keymap.addButtonMapping(name+"UP", new ButtonMapping(new Button(5,name+"UP"),new OutputKey("Up", KeyEvent.VK_UP,0)));
        keymap.addButtonMapping(name+"DOWN", new ButtonMapping(new Button(6,name+"DOWN"),new OutputKey("Down", KeyEvent.VK_DOWN,0)));
        keymap.addButtonMapping(name+"LEFT", new ButtonMapping(new Button(7,name+"LEFT"),new OutputKey("Left", KeyEvent.VK_LEFT,0)));
        keymap.addButtonMapping(name+"RIGHT", new ButtonMapping(new Button(8,name+"RIGHT"),new OutputKey("Right", KeyEvent.VK_RIGHT,0)));
        
        Joystick joystick = new Joystick(9, "Left");
        jinputA = Component.Identifier.Axis.X;
        keymap.addJoystickMapping(jinputA.getName(),new JoystickMapping(joystick,new OutputJoystick("X",1, OutputJoystick.JoystickType.DPAD)));
        jinputA = Component.Identifier.Axis.Y;
        keymap.addJoystickMapping(jinputA.getName(),new JoystickMapping(joystick,new OutputJoystick("Y",1, OutputJoystick.JoystickType.DPAD)));
        joystick = new Joystick(10, "Right");
        jinputA = Component.Identifier.Axis.RX;
        keymap.addJoystickMapping(jinputA.getName(),new JoystickMapping(joystick,new OutputJoystick("RX",1, OutputJoystick.JoystickType.DPAD)));
        jinputA = Component.Identifier.Axis.RY;
        keymap.addJoystickMapping(jinputA.getName(),new JoystickMapping(joystick,new OutputJoystick("RY",1, OutputJoystick.JoystickType.DPAD)));
        
        //Buttons
        Component.Identifier.Button jinputB;
        jinputB = Component.Identifier.Button.X;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(1,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.Y;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(2,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.A;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(3,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.B;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(4,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.LEFT_THUMB;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(11,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.RIGHT_THUMB;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(12,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.LEFT_THUMB2;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(13,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.RIGHT_THUMB2;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(14,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.LEFT_THUMB3;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(15,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.RIGHT_THUMB3;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(16,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.UNKNOWN;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(17,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.MODE;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(18,jinputB.getName()),new OutputDisabled()));
        jinputB = Component.Identifier.Button.SELECT;
        keymap.addButtonMapping(jinputB.getName(), new ButtonMapping(new Button(19,jinputB.getName()),new OutputDisabled()));
        
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

    @Override
    public String getId(int index){
        switch(index){
            case 1:
                return Component.Identifier.Button.X.getName();
            case 2:
                return Component.Identifier.Button.Y.getName();
            case 3:
                return Component.Identifier.Button.A.getName();
            case 4:
                return Component.Identifier.Button.B.getName();
            case 5:
                return Component.Identifier.Axis.POV.getName()+"UP";
            case 6:
                return Component.Identifier.Axis.POV.getName()+"DOWN";
            case 7:
                return Component.Identifier.Axis.POV.getName()+"LEFT";
            case 8:
                return Component.Identifier.Axis.POV.getName()+"RIGHT";
            case 11:
                return Component.Identifier.Button.LEFT_THUMB.getName();
            case 12:
                return Component.Identifier.Button.RIGHT_THUMB.getName();
            case 13:
                return Component.Identifier.Button.LEFT_THUMB2.getName();
            case 14:
                return Component.Identifier.Button.RIGHT_THUMB2.getName();
            case 15:
                return Component.Identifier.Button.LEFT_THUMB3.getName();
            case 16:
                return Component.Identifier.Button.RIGHT_THUMB3.getName();
            case 17:
                return Component.Identifier.Button.UNKNOWN.getName();
            case 18:
                return Component.Identifier.Button.MODE.getName();
            case 19:
                return Component.Identifier.Button.SELECT.getName();
        }
        return null;
    }
}