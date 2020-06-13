package com.monkygames.kbmaster.io;

import com.monkygames.kbmaster.account.DeviceList;
import com.monkygames.kbmaster.account.DevicePackage;
import com.monkygames.kbmaster.account.UserSettings;
import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.driver.DeviceInformation;
import com.monkygames.kbmaster.driver.DeviceState;
import com.monkygames.kbmaster.input.Button;
import com.monkygames.kbmaster.input.ButtonMapping;
import com.monkygames.kbmaster.input.Hardware;
import com.monkygames.kbmaster.input.Keymap;
import com.monkygames.kbmaster.input.Mapping;
import com.monkygames.kbmaster.input.Output;
import com.monkygames.kbmaster.input.OutputDisabled;
import com.monkygames.kbmaster.input.OutputKey;
import com.monkygames.kbmaster.input.OutputKeymapSwitch;
import com.monkygames.kbmaster.input.OutputMouse;
import com.monkygames.kbmaster.input.Wheel;
import com.monkygames.kbmaster.input.WheelMapping;
import com.monkygames.kbmaster.profiles.App;
import com.monkygames.kbmaster.profiles.AppType;
import com.monkygames.kbmaster.profiles.Profile;
import com.monkygames.kbmaster.profiles.Root;
import com.monkygames.kbmaster.profiles.RootManager;
import com.thoughtworks.xstream.XStream;
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
     * The user settings file.
     */
    private File settingsFile;

    /**
     * The global account file.
     */
    private File globalAccountFile;

    public static final String settingsFileName = "settings.xml";
    public static final String globalAccountFileName = "global_account.xml";

    public XStreamManager(){
        // user settings
        userSettingsStream = new XStream(new DomDriver());
        userSettingsStream.alias("UserSettings", UserSettings.class);
        settingsFile = new File(settingsFileName);

        // global account
        globalStream = new XStream(new DomDriver());
        globalStream.alias("RootManager",RootManager.class);
        globalStream.alias("Root",Root.class);
        globalStream.alias("AppType",AppType.class);
        globalStream.alias("ArrayList",ArrayList.class);
        globalStream.alias("Profile",Profile.class);
        globalStream.alias("App",App.class);
        globalStream.alias("Keymap",Keymap.class);
        globalStream.alias("HashMap",HashMap.class);
        globalStream.alias("ButtonMapping",ButtonMapping.class);
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
        globalStream.alias("DeviceList",DeviceList.class);
        globalStream.alias("DevicePackage",DevicePackage.class);
        globalStream.alias("Device",Device.class);
        globalStream.alias("DeviceInformation",DeviceInformation.class);
        globalStream.alias("MetaData",MetaData.class);
        globalStream.alias("DeviceState",DeviceState.class);
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
        if(userSettings == null){
            return new UserSettings();
        }
        return userSettings;
    }

    /**
     * Writes the specified root manager to file.
     * @return true on success and false otherwise.
     */
    public boolean writeRootManager(String filename, RootManager rootManager){
        MetaData metaData = rootManager.getMetaData();
        if(metaData != null){
            metaData.rev = "update";
        }
        // metadata
        return write(globalStream,filename,rootManager);
    }

    /**
     * Reads the root manager from file and returns.
     * If no file exist, it creats a new Root Manager.
     * @param filename the file to read from.
     * @return the root manager or null on error.
     */
    public RootManager readRootManager(String filename){
        RootManager rootManager = (RootManager)read(globalStream,filename);
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

    /**
     * Reads a file using the global stream.
     * @param filename the path to the file to read.
     * @return the object read from file and null otherwise.
     */
    public Object readFile(String filename){
        return read(globalStream,filename);
    }

    /**
     * Writes using the global stream.
     * @param filename
     * @param obj
     * @return 
     */
    public boolean writeFile(String filename, Object obj){
        return write(globalStream, filename, obj);
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
            ex.printStackTrace();
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
        if(!file.exists()){
            return null;
        }
        try{
            String xml = new String(Files.readAllBytes(file.toPath()));
            return stream.fromXML(xml);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    // === Static Methods === //
    /**
     * Singleton for returning the manager.
     * @return A singleton that manages the XStreams.
     */
    public static XStreamManager getStreamManager(){
        if(xStreamManager == null){
            xStreamManager = new XStreamManager();
        }
        return xStreamManager;
    }
}