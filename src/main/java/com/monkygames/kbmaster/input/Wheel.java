/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

// === jinput imports === //
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier.Axis;

/**
 * A wheel event from the device.
 * 1 is scroll up, 2 is scroll down, and 3 is middle button.
 * @version 1.0
 */
public class Wheel extends Hardware{

// ============= Constructors ============== //
    public Wheel(int id) {
        super(id, "");
        switch (id) {
            case 1:
            case 2:
                inputString = Axis.Z.getName();
                break;
            default:
                inputString = net.java.games.input.Component.Identifier.Button.MIDDLE.getName();
        }
    }

// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new Wheel(id);
    }
    @Override
    public String toString(){
	return "Wheel["+id+","+inputString+"]";
    }
}
