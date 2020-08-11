package com.monkygames.kbmaster.input;
/**
 * Contains the joystick event information that will be sent to the system for event
 * processing.
 */
public class OutputJoystick extends Output {
    private JoystickType joystickType;
    private boolean mousePress;
    private boolean wasd;
    public enum JoystickType { DPAD, MOUSE };
    public OutputJoystick(String name, int keycode, JoystickType joystickType) {
        super (name, keycode, 0);
        this.joystickType = joystickType;
    }
    public JoystickType getJoystickType() { return joystickType; }
    public void setJoystickType(JoystickType joystickType) { this.joystickType = joystickType; }
    public boolean doPress() { return mousePress; }
    public void setPress(boolean mousePress) { this.mousePress = mousePress; }
    public boolean useWASD() { return wasd; }
    public void setWASD(boolean wasd) { this.wasd = wasd; }
    @Override
    public Object clone() {
        OutputJoystick output = new OutputJoystick(name, keycode, joystickType);
        output.setDescription(getDescription());
        return output;
    }
}
