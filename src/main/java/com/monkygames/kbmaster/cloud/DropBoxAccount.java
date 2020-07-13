/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.cloud;

import com.dropbox.core.*;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.cloud.metadata.MetaData;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.ProfileManager;
import com.monkygames.kbmaster.profiles.RootManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the DropBox Synchronization.
 * @author spethm (modified by vapula87)
 */
public class DropBoxAccount implements CloudAccount{
    private DbxAppInfo appInfo;
    private DbxPKCEWebAuth webAuth;
    private String dropBoxAccessToken;
    private DbxClientV2 dropBoxClient;
    private DbxRequestConfig dropBoxConfig;

	///---Constructors---\\\
    public DropBoxAccount(){
        dropBoxConfig = DbxRequestConfig.newBuilder(
                "Keyboarding(Re)Master/"+ KeyboardingMaster.VERSION).build();
        appInfo = new DbxAppInfo(DropBoxApp.APP_KEY);
        webAuth = new DbxPKCEWebAuth(dropBoxConfig, appInfo);
    }
    public DropBoxAccount(String dropBoxAccessToken){
        this();
        this.dropBoxAccessToken = dropBoxAccessToken;
        setupClient();
    }

    ///---Getters and Setters---\\\
    @Override
    public String getAccessToken(){
        return dropBoxAccessToken;
    }
    /**
     * Returns the authorization URL.
     * @return The authorize URL.
     */
    public String getAuthorizeURL() {
        // Have the user sign in and authorize your app.
        return webAuth.authorize(DbxWebAuth.newRequestBuilder()
                .withNoRedirect()
                .build());
    }
    /**
     * Set the authorize code to get access token.
     * @param code the authorize code to get the access token.
     */
    public boolean setAuthorizeCode(String code) {
        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finishFromCode(code);
            dropBoxAccessToken = authFinish.getAccessToken();
            setupClient();
            return true;
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    ///---Public Methods---\\\
    @Override
    public boolean sync() {
        String profileDir = ProfileManager.PROFILE_DIR;
        File localProfileDir = new File(profileDir);
        if (!localProfileDir.exists()) localProfileDir.mkdir();
        ListFolderResult result = null;
        try {
            result = dropBoxClient.files().listFolder("/" + profileDir);
        }catch (DbxException ex) { }
        if(result == null) {
            try {
                dropBoxClient.files().createFolderV2("/" + profileDir);
                result = dropBoxClient.files().listFolder("/" + profileDir);
            } catch (DbxException ex) { return false; }
        }
        for(File file: localProfileDir.listFiles()){
           if (isValidFile(file.getName())) {
               if(!syncFile(profileDir+"/"+file.getName()))
                   return false;
           }
        }
        for (Metadata metadata : result.getEntries()) {
            if (isValidFile(metadata.getName())) {
                if (!syncFile(profileDir+"/"+metadata.getName()))
                    return false;
            }
        }
        return true;
    }

    ///---Private Methods---\\\
    private void setupClient(){
        dropBoxClient = new DbxClientV2(dropBoxConfig, dropBoxAccessToken);
    }

    /**
     * Attemps to sync the file using local and cloud meta data.
     * @param filename The file to be synced.
     * @return true if successful.
     */
    private boolean syncFile(String filename) {
        MetaData localMetaData = getLocalDropboxMetaData(filename);
        MetaData cloudMetaData = getCloudDropboxMetaData(filename);
        MetaData metaData;
        if (cloudMetaData == null) metaData = uploadFile(filename);
        else if (localMetaData == null) metaData = downloadFile(filename);
        else if (localMetaData.lastSync > cloudMetaData.lastSync) metaData = uploadFile(filename);
        else metaData = downloadFile(filename);
        if (metaData == null) return false;
        return updateLocalFileMetaData(filename, metaData);
    }
    /**
     * Uploads a file to the cloud.
     * @param filename the file to upload to the cloud.
     * @return the metadata for the uploaded file (on success) 
     * and null on failure.
     */ 
    private MetaData uploadFile(String filename) {
        FileInputStream inputStream = null;
        try {
            File inputFile = new File(filename);
            inputStream = new FileInputStream(inputFile);
            UploadBuilder builder = dropBoxClient.files().uploadBuilder("/" + filename);
            builder.withMode(WriteMode.OVERWRITE);
            FileMetadata fileMetadata = builder.uploadAndFinish(inputStream);
            MetaData metaData = new MetaData(fileMetadata.getServerModified().getTime());
            return metaData;
        } catch (Exception e) { }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    /**
     * Download the file from the cloud.
     * @return the meta data for the downloaded file (on success) 
     * and null on failure.
     */
    private MetaData downloadFile(String filename){
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
            DbxDownloader<FileMetadata> downloader = dropBoxClient.files().download("/"+filename);
            MetaData metaData = new MetaData(downloader.getResult().getServerModified().getTime());
            downloader.download(outputStream);
            return metaData;
        }catch (Exception e){ }
         finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    /**
     * Return the DropBox metadata from the cloud.
     * @param filename the name of the file to get the rev number
     * @return the metadata and -1 if it doesn't exist
     */
    private MetaData getCloudDropboxMetaData(String filename){
        try {
            Metadata entry = dropBoxClient.files().getMetadata("/"+filename);
            if(entry != null){
                if(entry instanceof FileMetadata){
                    FileMetadata fileMetadata = (FileMetadata)entry;
                    MetaData metaData = new MetaData(fileMetadata.getServerModified().getTime());
                    return metaData;
                }
            }
        } catch (DbxException ex) { }
        return null;
    }
    /**
     * Return the dropbox meta data
     * @param filename on the local filesystem.
     * @return the meta data and null if it doesn't exist.
     */
    private MetaData getLocalDropboxMetaData(String filename){
        RootManager rootManager = XStreamManager.getStreamManager().readRootManager(filename);
        if (rootManager == null) return null;
        return rootManager.getMetaData();
    }
    /**
     * Updates the local metadata.
     * @param filename The file to be updated.
     * @param metaData The meta data to write.
     * @return true if successful.
     */
    private boolean updateLocalFileMetaData(String filename, MetaData metaData){
        try{
            RootManager rootManager = XStreamManager.getStreamManager().readRootManager(filename);
            if(rootManager == null) return false;
            rootManager.setMetaData(metaData);
            return XStreamManager.getStreamManager().writeRootManager(filename, rootManager);
        }catch (Exception e){ return false; }
    }
    /**
     * Returns true if this is a valid file to upload to dropbox.
     * Only xml files are valid.
     * @param filename the file to check.
     */
    private boolean isValidFile(String filename){
        int i = filename.lastIndexOf('.');
        String ext = filename.substring(i);
        if(ext == null) return false;
        return (ext.equals(".xml"));
    }
}