package com.monkygames.kbmaster.io;

import com.monkygames.kbmaster.driver.DeviceList;
import com.monkygames.kbmaster.driver.DevicePackage;
import com.monkygames.kbmaster.cloud.UserSettings;
import com.monkygames.kbmaster.cloud.metadata.MetaData;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceInformation;
import com.monkygames.kbmaster.input.*;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.profiles.RootManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages files using XStream to read and write.
 */
public class XStreamManager {

    /**
     * Singleton.
     */
    private static XStreamManager xStreamManager;

    /**
     * For saving the user settings.
     */
    private XStream userSettingsStream;

    /**
     * For saving the Global Account and profiles.
     */
    private XStream globalStream;

    /**
     * For saving the root manager.
     */
     private XStream rootStream;
    /**
     * The user settings file.
     */
    private File settingsFile;

    /**
     * The global account file.
     */
    private File globalAccountFile;

    public static final String settingsFileName = "settings.xml";
    public static final String globalAccountFileName = "device_descriptors.xml";

    public XStreamManager(){
        // user settings
        userSettingsStream = new XStream(new DomDriver());
        userSettingsStream.alias("UserSettings", UserSettings.class);
        settingsFile = new File(settingsFileName);
        XStream.setupDefaultSecurity(userSettingsStream);
        userSettingsStream.allowTypesByWildcard(new String[] {"com.monkygames.kbmaster.**"});

        // root manager
        rootStream = new XStream(new DomDriver());
        rootStream.alias("RootManager",RootManager.class);
        rootStream.alias("Root",Root.class);
        rootStream.alias("App",App.class);
        rootStream.alias("Profile",Profile.class);
        rootStream.alias("Keymap",Keymap.class);
        rootStream.alias("ButtonMapping",ButtonMapping.class);
        rootStream.alias("Button",Button.class);
        rootStream.alias("OutputKey",OutputKey.class);
        rootStream.alias("OutputMouse",OutputMouse.class);
        rootStream.alias("OutputJoystick", OutputJoystick.class);
        rootStream.alias("Wheel",Wheel.class);
        XStream.setupDefaultSecurity(rootStream);
        rootStream.allowTypesByWildcard(new String[] {"com.monkygames.kbmaster.**"});

        // global account
        globalStream = new XStream(new DomDriver());
        globalStream.alias("Profile",Profile.class);
        globalStream.alias("App",App.class);
        globalStream.alias("Keymap",Keymap.class);
        globalStream.alias("ButtonMapping",ButtonMapping.class);
        globalStream.alias("DeviceList",DeviceList.class);
        globalStream.alias("DevicePackage",DevicePackage.class);
        globalStream.alias("AppType",AppType.class);
        globalStream.alias("ArrayList",ArrayList.class);
        globalStream.alias("HashMap",HashMap.class);
        globalStream.alias("Button",Button.class);
        globalStream.alias("Output",Output.class);
        globalStream.alias("WheelMapping",WheelMapping.class);
        globalStream.alias("Wheel",Wheel.class);
        globalStream.alias("Mapping",Mapping.class);
        globalStream.alias("Hardware",Hardware.class);
        globalStream.alias("OutputDisabled",OutputDisabled.class);
        globalStream.alias("OutputKey",OutputKey.class);
        globalStream.alias("OutputKeymapSwitch",OutputKeymapSwitch.class);
        globalStream.alias("OutputMouse",OutputMouse.class);
        globalStream.alias("DeviceInformation",DeviceInformation.class);
        globalStream.alias("MetaData",MetaData.class);
        globalStream.alias("Device",Device.class);
        XStream.setupDefaultSecurity(globalStream);
        globalStream.allowTypesByWildcard(new String[] {"com.monkygames.kbmaster.**"});
        globalAccountFile = new File(globalAccountFileName);

    }

    // === Public Methods === //

    /**
     * Writes the user settings out to the file.
     * @param userSettings the user settings to write.
     */
    public void writeUserSettings(UserSettings userSettings){
        write(userSettingsStream,settingsFile.getAbsolutePath(),userSettings);
    }

    /**
     * Reads the user settings from file.
     * If the file doesn't exist, than create a user setting and returns.
     * @return the user settings and null on error.
     */
    public UserSettings readUserSettings(){
        UserSettings userSettings = (UserSettings)read(userSettingsStream,settingsFile.getAbsolutePath());
        if(userSettings == null) return new UserSettings();
        return userSettings;
    }

    /**
     * Writes the specified root manager to file.
     * @return true on success and false otherwise.
     */
    public boolean writeRootManager(String filename, RootManager rootManager){
        return write(rootStream,filename,rootManager);
    }

    /**
     * Reads the root manager from file and returns.
     * If no file exist, it creats a new Root Manager.
     * @param filename the file to read from.
     * @return the root manager or null on error.
     */
    public RootManager readRootManager(String filename){
        RootManager rootManager = (RootManager)read(rootStream,filename);
        if(rootManager == null){
            return new RootManager();
        }
        return rootManager;
    }

    /**
     * Writes the profile to file.
     * @return true on success and false otherwise.
     */
    public boolean writeProfile(String filename, Profile profile){
        return write(globalStream,filename,profile);
    }

    /**
     * Reads the Profile from file.
     * If no file exists, return null.
     * @param filename the file to read from.
     * @return the Profile or null on error.
     */
    public Profile readProfile(String filename){
        return (Profile)read(globalStream,filename);
    }

    /**
     * Writes the specified root manager to file.
     * @param deviceList list of device packages.
     * @return true on success and false otherwise.
     */
    public boolean writeGlobalAccount(DeviceList deviceList){
        return write(globalStream,globalAccountFile.getAbsolutePath(),deviceList);
    }

    /**
     * Reads the root manager from file and returns.
     * If no file exist, it creates a new Root Manager.
     * @return the root manager or null on error.
     */
    public DeviceList readGlobalAccount(){
        DeviceList deviceList = (DeviceList)read(this.globalStream,globalAccountFile.getAbsolutePath());
        if(deviceList == null){
            return new DeviceList();
        }
        return deviceList;
    }

    // === Private Methods === //
    /**
     * Writes the specified object to the stream.
     * @return true on success and false otherwise.
     */
    private boolean write(XStream stream, String filename, Object obj){
        File file = new File(filename);
        String xml = stream.toXML(obj);
        try{
            Files.write(file.toPath(),xml.getBytes());
        } catch (IOException ex) {
            System.out.println("XStream write failure ("+file.getName()+"): "+ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Reads the object from file and returns the object.
     * If no file exist, null is returned.
     * @param filename the file to read from.
     * @return the unsearlized object and null if error.
     */
    private Object read(XStream stream, String filename){
        File file = new File(filename);
        if(!file.exists()) return null;
        try{
            String xml = new String(Files.readAllBytes(file.toPath()));
            return stream.fromXML(xml);
        } catch (IOException ex){
            System.out.println("XStream read error ("+file.getName()+"): "+ex.getMessage());
        } catch (StreamException e) {
            System.out.println("XStream read error ("+file.getName()+"): File corrupted.");
            file.delete();
        }
        return null;
    }

    // === Static Methods === //
    /**
     * Singleton for returning the manager.
     * @return A singleton that manages the XStreams.
     */
    public static synchronized XStreamManager getStreamManager(){
        if(xStreamManager == null){
            xStreamManager = new XStreamManager();
        }
        return xStreamManager;
    }
}