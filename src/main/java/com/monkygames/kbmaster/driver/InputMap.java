package com.monkygames.kbmaster.driver;

/**
 *
 * Used for setting the input mapping in order to detect input for the device.
 */
public class InputMap {

    /**
     * The id of the 
     */
    private final int id;
    private final String name;
    private final int keyEvent;
    public InputMap(int id, String name, int keyEvent){
        this.id = id;
        this.name = name;
        this.keyEvent = keyEvent;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getKeyEvent(){
        return keyEvent;
    }
}
