package com.monkygames.kbmaster.account;

import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.account.dropbox.SyncMetaData;
import java.util.ArrayList;


/**
 * Contains a list of DevicesPackages.
 */
public class DeviceList implements SyncMetaData{

    /**
     * The list of devices used locally.
     */
    private ArrayList<DevicePackage> devices;

    /**
     * Metadata used for syncing with dropbox.
     */
    private MetaData metaData;

    public DeviceList(){
        devices = new ArrayList<>();
        metaData = null;
    }

    public ArrayList<DevicePackage> getList(){
        return devices;
    }

    @Override
    public MetaData getMetaData(){
        return metaData;
    }

    @Override
    public void setMetaData(MetaData metaData){
        this.metaData = metaData;
    }
}
