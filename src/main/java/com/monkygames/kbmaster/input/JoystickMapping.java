package com.monkygames.kbmaster.input;
/**
 * Holds the mapping between the device's event and the Robot event.
 */
public class JoystickMapping extends Mapping {
    public JoystickMapping(Hardware inputHardware, Output output) { super(inputHardware, output); }
    @Override
    public Object clone(){
        return new JoystickMapping((Joystick)inputHardware.clone(),(Output)output.clone());
    }
    @Override
    public String toString(){
        return "JoystickMapping["+inputHardware+","+output+"]";
    }

}
