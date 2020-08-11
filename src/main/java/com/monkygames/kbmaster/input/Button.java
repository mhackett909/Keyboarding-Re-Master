/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * A Button from the device.
 */
public class Button extends Hardware{

// ============= Constructors ============== //
    public Button(int id, String inputString){
	super(id,inputString);
    }

// ============= Extended Methods ============== //
    @Override
    public Object clone(){
	return new Button(this.id,this.inputString);
    }
    @Override
    public String toString(){
	return "Button["+id+","+inputString+"]";
    }


}
