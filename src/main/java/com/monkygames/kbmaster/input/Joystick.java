package com.monkygames.kbmaster.input;
/**
 * A Joystick from the device.
 */
public class Joystick extends Hardware {
    public Joystick(int id, String inputString) { super(id, inputString); }
    @Override
    public Object clone(){
        return new Joystick(this.id,this.inputString);
    }
    @Override
    public String toString(){
        return "Joystick["+id+","+inputString+"]";
    }
}
