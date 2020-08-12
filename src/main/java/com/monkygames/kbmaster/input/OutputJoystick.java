package com.monkygames.kbmaster.input;
import javafx.scene.input.KeyCode;

import java.security.Key;
import java.util.HashMap;

/**
 * Contains the joystick event information that will be sent to the system for event
 * processing.
 */
public class OutputJoystick extends Output {
    private JoystickType joystickType;
    private boolean mousePress;
    private boolean wasd;
    private HashMap<String, KeyCode> keyCodes;
    public enum JoystickType { DPAD, MOUSE };
    public OutputJoystick(String name, int keycode, JoystickType joystickType) {
        super (name, keycode, 0);
        this.joystickType = joystickType;
        keyCodes = new HashMap<>();
        keyCodes.put("Up",KeyCode.UP);
        keyCodes.put("Down",KeyCode.DOWN);
        keyCodes.put("Left",KeyCode.LEFT);
        keyCodes.put("Right",KeyCode.RIGHT);
        keyCodes.put("W",KeyCode.W);
        keyCodes.put("A",KeyCode.A);
        keyCodes.put("S",KeyCode.S);
        keyCodes.put("D",KeyCode.D);
    }
    public JoystickType getJoystickType() { return joystickType; }
    public void setJoystickType(JoystickType joystickType) { this.joystickType = joystickType; }
    public boolean doPress() { return mousePress; }
    public void setPress(boolean mousePress) { this.mousePress = mousePress; }
    public boolean useWASD() { return wasd; }
    public void setWASD(boolean wasd) { this.wasd = wasd; }
    public int getKeycode(String dir, int invert) {
        //TODO finish code for Q and E (if I decide to allow it)
        if (dir.equals("UP")) {
            if (wasd) {
                if (invert == -1) return keyCodes.get("S").getCode();
                else return keyCodes.get("W").getCode();
            }
            else {
                if (invert == -1) return keyCodes.get("Down").getCode();
                else return keyCodes.get("Up").getCode();
            }
        }
        else if (dir.equals("DOWN")) {
            if (wasd) {
                if (invert == -1) return keyCodes.get("W").getCode();
                else return keyCodes.get("S").getCode();
            }
            else {
                if (invert == -1) return keyCodes.get("Up").getCode();
                else return keyCodes.get("Down").getCode();
            }
        }
        else if (dir.equals("LEFT")) {
            if (wasd) {
                if (invert == -1) return keyCodes.get("D").getCode();
                else return keyCodes.get("A").getCode();
            }
            else {
                if (invert == -1) return keyCodes.get("Right").getCode();
                else return keyCodes.get("Left").getCode();
            }
        }
        else if (dir.equals("RIGHT")) {
            if (wasd) {
                if (invert == -1) return keyCodes.get("A").getCode();
                else return keyCodes.get("D").getCode();
            }
            else {
                if (invert == -1) return keyCodes.get("Left").getCode();
                else return keyCodes.get("Right").getCode();
            }
        }
        else return 0;
    }
    @Override
    public Object clone() {
        OutputJoystick output = new OutputJoystick(name, keycode, joystickType);
        output.setDescription(getDescription());
        return output;
    }
}
