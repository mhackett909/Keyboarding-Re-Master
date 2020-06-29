/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.driver;

// === kbmaster imports === //
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.profiles.Profile;
import java.awt.Rectangle;
import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javafx.scene.input.KeyCode;
import net.java.games.input.Component.Identifier.Key;

/**
 * Contains device information and device driver implementation.
 * @version 1.0
 */
public abstract class Device implements Mapper{

// ============= Class variables ============== //
    /**
     * The device information.
     */
     @XStreamOmitField
    private DeviceInformation deviceInformation;
    private DeviceState deviceState;
    /**
     * Stores the inputs used to identify the input.
     */
	@XStreamOmitField
    protected HashMap<Integer,InputMap> inputMaps;
	@XStreamOmitField
    protected HashMap<String,String> codeToJFX;

// ============= Constructors ============== //
    /**
     * Creates a new device with the specified information.
     * @param deviceInformation contains the information of this device.
     */
    public Device(DeviceInformation deviceInformation){
	this.deviceInformation = deviceInformation;
	deviceState = new DeviceState(null,false,false,deviceInformation.getPackageName());
	inputMaps = new HashMap<>();
	initCodes();
    }
    /**
     * Creates a device and initializes the DeviceInformation
     * with the specified parameters.
     * @param make the manufacturer of the device.
     * @param model the model of the device.
     * @param jinputName the name of the device recognized by jinput.
     * @param deviceType the type of device (keyboard or mouse).
     * @param deviceIcon the icon for this device.
     * @param deviceDescription the description of the device.
     * @param packageName the name of the package for loading the driver.
     * @param uiFXMLURL the url to the package in order to load the driver.
     * @param imageBindingsTemplate used for exporting descriptions to an image.
     * @param amazonLink the amazon link to product page include associate code and null if none.
     * @param hasMouse True if this device has a mouse component and false otherwise.
     */
    public Device(String make, String model, String jinputName, 
		   DeviceType deviceType, String deviceIcon,
		   String deviceDescription, String packageName,
		   String uiFXMLURL, String imageBindingsTemplate,
		   String amazonLink, boolean hasMouse){
	this(new DeviceInformation(make,model,jinputName,
						  deviceType, deviceIcon,
						  deviceDescription,
						  packageName, uiFXMLURL,
						  imageBindingsTemplate,
						  amazonLink,
						  hasMouse));
    }
// ============= Public Methods ============== //
    public DeviceInformation getDeviceInformation(){
	return deviceInformation;
    }
    /**
     * Sets the default mapping for the passed in profile for this device.
     * @param profile the profile to set the keymaps.
     */
    public void setDefaultKeymaps(Profile profile){
	for(int i = 0; i < 8; i++){
	    profile.setKeymap(i,this.generateDefaultKeymap(i));
	}
    }
	public void setDefaultKeymap(Profile profile, int id){
    	profile.setKeymap(id,this.generateDefaultKeymap(id));
	}
    public Profile getProfile() {
	return deviceState.getProfile();
    }

    public void setProfile(Profile profile) {
	deviceState.setProfile(profile);
    }

    public boolean isConnected() {
	return deviceState.isIsConnected();
    }

    public void setIsConnected(boolean isConnected) {
	deviceState.setIsConnected(isConnected);
    }

	public DeviceState getDeviceState(){ return deviceState; }

    public boolean isEnabled() {
	return deviceState.isIsEnabled();
    }

    public void setIsEnabled(boolean isEnabled) {
	deviceState.setIsEnabled(isEnabled);
    }

    public void setDeviceState(DeviceState deviceState){
	this.deviceState = deviceState;
    }

    public void resetDevice() {
    	setIsEnabled(false);
    	setIsConnected(false);
    	setProfile(null);
    }

    /**
     * Add a new button mapping to the keymap.
     * @param keymap they keymap to add the button mapping.
     * @param inputMap the input map of the input to add.
     */
    protected void addButtonMapping(Keymap keymap, InputMap inputMap){
	keymap.addButtonMapping(inputMap.getName(), 
	    new ButtonMapping(new Button(inputMap.getId(), inputMap.getName()),
	    //new OutputKey(KeyEvent.getKeyText(inputMap.getKeyEvent()),inputMap.getKeyEvent(),0)));
	    new OutputKey(getJFXInputName(inputMap.getName()),inputMap.getKeyEvent(),0)));
    }

    @Override
    public String getId(int index){
	InputMap inputMap = inputMaps.get(index);
	if(inputMap == null){
	    return Key.TAB.getName();
	}
	return inputMap.getName();
    }

    /**
     * Returns the JFX name and the awt name if a jfx name cannot be found.
     * @param awtName the awt name to use to search for the jfx name.
     * @return the jfx name and the awt name if there is no jfx name found.
     */
    private String getJFXInputName(String awtName){
	String name = codeToJFX.get(awtName);
	if(name == null){
	    return awtName;
	}
	return name;
    }

    /**
     * Initializes the codes to convert from java awt to java fx.
     */
    private void initCodes(){
	codeToJFX = new HashMap<>();
	codeToJFX.put(Key.ESCAPE.getName(),	KeyCode.ESCAPE.getName());
	codeToJFX.put(Key.F1.getName(),		KeyCode.F1.getName());
	codeToJFX.put(Key.F2.getName(),		KeyCode.F2.getName());
	codeToJFX.put(Key.F3.getName(),		KeyCode.F3.getName());
	codeToJFX.put(Key.F4.getName(),		KeyCode.F4.getName());
	codeToJFX.put(Key.F5.getName(),		KeyCode.F5.getName());
	codeToJFX.put(Key.F6.getName(),		KeyCode.F6.getName());	
	codeToJFX.put(Key.F7.getName(),		KeyCode.F7.getName());	
	codeToJFX.put(Key.F8.getName(),		KeyCode.F8.getName());
	codeToJFX.put(Key.F9.getName(),		KeyCode.F9.getName());
	codeToJFX.put(Key.F10.getName(),	KeyCode.F10.getName());
	codeToJFX.put(Key.F11.getName(),		KeyCode.F11.getName());
	codeToJFX.put(Key.F12.getName(),	KeyCode.F12.getName());
	codeToJFX.put(Key.NUMLOCK.getName(),	KeyCode.NUM_LOCK.getName());
	codeToJFX.put(Key.SYSRQ.getName(),	KeyCode.PRINTSCREEN.getName());
	codeToJFX.put(Key.SCROLL.getName(),	KeyCode.PAUSE.getName());
	codeToJFX.put(Key.PAUSE.getName(),	KeyCode.PAUSE.getName());
	codeToJFX.put(Key.GRAVE.getName(),	KeyCode.BACK_QUOTE.getName());
	codeToJFX.put(Key._1.getName(),		KeyCode.DIGIT1.getName());
	codeToJFX.put(Key._2.getName(),		KeyCode.DIGIT2.getName());
	codeToJFX.put(Key._3.getName(),		KeyCode.DIGIT3.getName());
	codeToJFX.put(Key._4.getName(),		KeyCode.DIGIT4.getName());
	codeToJFX.put(Key._5.getName(),		KeyCode.DIGIT5.getName());
	codeToJFX.put(Key._6.getName(),		KeyCode.DIGIT6.getName());
	codeToJFX.put(Key._7.getName(),		KeyCode.DIGIT7.getName());
	codeToJFX.put(Key._8.getName(),		KeyCode.DIGIT8.getName());
	codeToJFX.put(Key._9.getName(),		KeyCode.DIGIT9.getName());
	codeToJFX.put(Key._0.getName(),		KeyCode.DIGIT0.getName());
	codeToJFX.put(Key.MINUS.getName(),	KeyCode.MINUS.getName());
	codeToJFX.put(Key.EQUALS.getName(),	KeyCode.EQUALS.getName());
	codeToJFX.put(Key.BACK.getName(),	KeyCode.BACK_SPACE.getName());
	codeToJFX.put(Key.TAB.getName(),	KeyCode.TAB.getName());
	codeToJFX.put(Key.Q.getName(),		KeyCode.Q.getName());
	codeToJFX.put(Key.W.getName(),		KeyCode.W.getName());
	codeToJFX.put(Key.E.getName(),		KeyCode.E.getName());
	codeToJFX.put(Key.R.getName(),		KeyCode.R.getName());
	codeToJFX.put(Key.T.getName(),		KeyCode.T.getName());
	codeToJFX.put(Key.Y.getName(),		KeyCode.Y.getName());
	codeToJFX.put(Key.U.getName(),		KeyCode.U.getName());
	codeToJFX.put(Key.I.getName(),		KeyCode.I.getName());
	codeToJFX.put(Key.O.getName(),		KeyCode.O.getName());
	codeToJFX.put(Key.P.getName(),		KeyCode.P.getName());
	codeToJFX.put(Key.LBRACKET.getName(),	KeyCode.BRACELEFT.getName());
	codeToJFX.put(Key.RBRACKET.getName(),	KeyCode.BRACERIGHT.getName());
	codeToJFX.put(Key.BACKSLASH.getName(),	KeyCode.BACK_SLASH.getName());
	codeToJFX.put(Key.CAPITAL.getName(),	KeyCode.CAPS.getName());
	codeToJFX.put(Key.A.getName(),		KeyCode.A.getName());
	codeToJFX.put(Key.S.getName(),		KeyCode.S.getName());
	codeToJFX.put(Key.D.getName(),		KeyCode.D.getName());
	codeToJFX.put(Key.F.getName(),		KeyCode.F.getName());
	codeToJFX.put(Key.G.getName(),		KeyCode.G.getName());
	codeToJFX.put(Key.H.getName(),		KeyCode.H.getName());
	codeToJFX.put(Key.J.getName(),		KeyCode.J.getName());
	codeToJFX.put(Key.K.getName(),		KeyCode.K.getName());
	codeToJFX.put(Key.L.getName(),		KeyCode.L.getName());
	codeToJFX.put(Key.SEMICOLON.getName(),	KeyCode.SEMICOLON.getName());
	codeToJFX.put(Key.APOSTROPHE.getName(),	KeyCode.QUOTE.getName());
	codeToJFX.put(Key.RETURN.getName(),	KeyCode.ENTER.getName());
	codeToJFX.put(Key.LSHIFT.getName(),	KeyCode.SHIFT.getName());
	codeToJFX.put(Key.Z.getName(),		KeyCode.Z.getName());
	codeToJFX.put(Key.X.getName(),		KeyCode.X.getName());
	codeToJFX.put(Key.C.getName(),		KeyCode.C.getName());
	codeToJFX.put(Key.V.getName(),		KeyCode.V.getName());
	codeToJFX.put(Key.B.getName(),		KeyCode.B.getName());
	codeToJFX.put(Key.N.getName(),		KeyCode.N.getName());
	codeToJFX.put(Key.M.getName(),		KeyCode.M.getName());
	codeToJFX.put(Key.COMMA.getName(),	KeyCode.COMMA.getName());
	codeToJFX.put(Key.PERIOD.getName(),	KeyCode.PERIOD.getName());
	codeToJFX.put(Key.SLASH.getName(),	KeyCode.SLASH.getName());
	codeToJFX.put(Key.RSHIFT.getName(),	KeyCode.SHIFT.getName());
	codeToJFX.put(Key.LCONTROL.getName(),	KeyCode.CONTROL.getName());
	codeToJFX.put(Key.LWIN.getName(),	KeyCode.WINDOWS.getName());
	codeToJFX.put(Key.LALT.getName(),	KeyCode.ALT.getName());
	codeToJFX.put(Key.SPACE.getName(),	KeyCode.SPACE.getName());
	codeToJFX.put(Key.RALT.getName(),	KeyCode.ALT.getName());
	codeToJFX.put(Key.RCONTROL.getName(),	KeyCode.CONTROL.getName());
	codeToJFX.put(Key.INSERT.getName(),	KeyCode.INSERT.getName());
	codeToJFX.put(Key.HOME.getName(),	KeyCode.HOME.getName());
	codeToJFX.put(Key.PAGEUP.getName(),	KeyCode.PAGE_UP.getName());
	codeToJFX.put(Key.SUBTRACT.getName(),	KeyCode.SUBTRACT.getName());
	codeToJFX.put(Key.DELETE.getName(),	KeyCode.DELETE.getName());
	codeToJFX.put(Key.END.getName(),	KeyCode.END.getName());
	codeToJFX.put(Key.PAGEDOWN.getName(),	KeyCode.PAGE_DOWN.getName());
	codeToJFX.put(Key.NUMPAD1.getName(),	KeyCode.NUMPAD1.getName());
	codeToJFX.put(Key.NUMPAD2.getName(),	KeyCode.NUMPAD2.getName());
	codeToJFX.put(Key.NUMPAD3.getName(),	KeyCode.NUMPAD3.getName());
	codeToJFX.put(Key.NUMPAD4.getName(),	KeyCode.NUMPAD4.getName());
	codeToJFX.put(Key.NUMPAD5.getName(),	KeyCode.NUMPAD5.getName());
	codeToJFX.put(Key.NUMPAD6.getName(),	KeyCode.NUMPAD6.getName());
	codeToJFX.put(Key.NUMPAD7.getName(),	KeyCode.NUMPAD7.getName());
	codeToJFX.put(Key.NUMPAD8.getName(),	KeyCode.NUMPAD8.getName());
	codeToJFX.put(Key.NUMPAD9.getName(),	KeyCode.NUMPAD9.getName());
	codeToJFX.put(Key.NUMPAD0.getName(),	KeyCode.NUMPAD0.getName());
	codeToJFX.put(Key.UP.getName(),		KeyCode.UP.getName());
	codeToJFX.put(Key.LEFT.getName(),	KeyCode.LEFT.getName());
	codeToJFX.put(Key.DOWN.getName(),	KeyCode.DOWN.getName());
	codeToJFX.put(Key.RIGHT.getName(),	KeyCode.RIGHT.getName());
	codeToJFX.put(Key.ADD.getName(),	KeyCode.ADD.getName());
	codeToJFX.put(Key.NUMPADENTER.getName(),KeyCode.ENTER.getName());
    }

    /**
     * Returns the x,y pixel location to write the output and
     * the x,y pixel location to write the description for the 
     * specified binding.
     * @param mapping gets the pixel location for this mapping.
     * @return Rectangle.x and Rectangle.y are the x,y pixel coordinates to
     * write the output and Rectangle.width and Rectangle.height contain
     * the x,y pixel coordinates to write the description.
     */
    public abstract Rectangle getBindingOutputAndDescriptionLocation(Mapping mapping);
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
