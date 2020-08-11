/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.input;

/**
 * Contains the key event information that will be sent to the system for event 
 * processing.
 * @version 1.0
 */
public class OutputKey extends Output {
	public OutputKey(String name, int keycode, int modifier) {
		super(name, keycode, modifier);
	}
	
	@Override
	public Object clone() {
		OutputKey output = new OutputKey(this.name, this.keycode, this.modifier);
		output.setDescription(getDescription());
		return output;
	}
}
