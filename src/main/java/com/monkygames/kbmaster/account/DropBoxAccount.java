/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadBuilder;
import com.dropbox.core.v2.files.UploadUploader;
import com.dropbox.core.v2.files.WriteMode;
import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.account.dropbox.MetaData;
import com.monkygames.kbmaster.account.dropbox.SyncMetaData;
import com.monkygames.kbmaster.controller.ProfileUIController;
import com.monkygames.kbmaster.io.XStreamManager;
import com.monkygames.kbmaster.profiles.RootManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the DropBox Synchronization.
 * @author spethm
 */
public class DropBoxAccount implements CloudAccount{

    private DbxAppInfo appInfo;
    private DbxWebAuth webAuth;
    private String accessToken;
    //private DbxClient client;
    private DbxClientV2 client;
    private DbxRequestConfig config;
	
    public DropBoxAccount(){
        // setup dropbox
        appInfo = new DbxAppInfo(DropBoxApp.APP_KEY, DropBoxApp.APP_SECRET);
        config = new DbxRequestConfig("kbmaster/"+KeyboardingMaster.VERSION);
        webAuth = new DbxWebAuth(config, appInfo);
    }

    public DropBoxAccount(String accessToken){
        this();
        this.accessToken = accessToken;
        setupClient();
    }

    @Override
    public String getAccessToken(){
        return accessToken;
    }

    /**
     * Returns the authorization URL.
     * @return The authorize URL.
     */
    public String getAuthorizeURL(){
        // Have the user sign in and authorize your app.
        //return webAuth.start();
        DbxWebAuth.Request authRequest = webAuth.newRequestBuilder()
	    .withNoRedirect()
	    .build();
	return webAuth.authorize(authRequest);
    }

    /**
     * Set the authorize code to get access token.
     * @param code the authorize code to get the access token.
     */
    public void setAuthorizeCode(String code){
        DbxAuthFinish authFinish;
        try {
	    authFinish = webAuth.finishFromCode(code);
	    accessToken = authFinish.getAccessToken();
	    setupClient();
        } catch (DbxException ex) {
	    Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
	/*
        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finish(code);
            accessToken = authFinish.accessToken;
            setupClient();
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
	*/
    }

    @Override
    public boolean sync() {
        // sync globalaccount
        if(syncFile(XStreamManager.globalAccountFileName)){
            //System.out.println("[DropBoxAccount:sync] "+XStreamManager.globalAccountFileName+" sync success");
        }else{
            //System.out.println("[DropBoxAcount:sync] "+XStreamManager.globalAccountFileName+" sync failure");
            return false;
        }

        // make sure local profiles dir exists
        String profileDir = ProfileUIController.profileDirS;
        File localProfileDir = new File(profileDir);
        if(!localProfileDir.exists()){
            localProfileDir.mkdir();
        }

        try {
            // sync profiles
	    DbxUserFilesRequests request = client.files();
	    ListFolderResult result = client.files().listFolder("/"+profileDir);
            if(result == null){
                FolderMetadata folderMetadata = client.files().createFolder("/"+profileDir);
                // upload only xml files from profiles directory
                for(File file: localProfileDir.listFiles()){
                    if(isValidFile(file.getName())){
                        if(!syncFile(profileDir+"/"+file.getName())){
                            return false;
                        }
                    }
                }
            }else{
                List<Metadata> list = result.getEntries();
                if(list.isEmpty()){
                    FolderMetadata folderMetadata = client.files().createFolder("/"+profileDir);
                    // upload only xml files from profiles directory
                    for(File file: localProfileDir.listFiles()){
                        if(isValidFile(file.getName())){
                            if(!syncFile(profileDir+"/"+file.getName())){
                                return false;
                            }
                        }
                    }
                }else{
                    // need to iterate through all and compare individual files
                    while(result.getHasMore()){

                        for (Metadata metadata : result.getEntries()) {
                            //System.out.println(metadata.getPathLower());
	                }
                        for(File file: localProfileDir.listFiles()){
                            if(isValidFile(file.getName())){
                                if(!syncFile(profileDir+"/"+file.getName())){
                                    return false;
                                }
                            }
                        }
                    }
                    // now iterate through all children
                    for(File file: localProfileDir.listFiles()){
                        if(isValidFile(file.getName())){
                            if(!syncFile(profileDir+"/"+file.getName())){
                                return false;
                            }
                        }
                    }
                }
            }


            /*
            DbxEntry.WithChildren listing = client.getMetadataWithChildren("/"+profileDir);
            if(listing == null){
                // need to create the directory
                DbxEntry entry =  client.createFolder("/"+profileDir);
                if(entry == null){
                    return false;
                }
                // upload only xml files from profiles directory
                for(File file: localProfileDir.listFiles()){
                    if(isValidFile(file.getName())){
                        if(!syncFile(profileDir+"/"+file.getName())){
                            return false;
                        }
                    }
                }
            }else {
                // need to iterate through all and compare individual files
                for (DbxEntry child : listing.children) {
                    if(isValidFile(child.name)){
                        // first investigate files stored on the cloud
                        if(!syncFile(profileDir+"/"+child.name)){
                            return false;
                        }
                    }
                }
                // now iterate through all children
                for(File file: localProfileDir.listFiles()){
                    if(isValidFile(file.getName())){
                        if(!syncFile(profileDir+"/"+file.getName())){
                            return false;
                        }
                    }
                }
            }

            */
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    private void setupClient(){
        //client = new DbxClient(config, accessToken);
        client = new DbxClientV2(config, accessToken);
    }

    private boolean syncFile(String filename){
        MetaData localMetaData = getLocalDropboxMetaData(filename);
        MetaData cloudMetaData = getCloudDropboxMetaData(filename);
        //System.out.println("[DropBox] filename = "+filename);
        //System.out.println("local = "+localMetaData);
        //System.out.println("cloud = "+cloudMetaData);

        // note, the conditionals below could be reduced;
        // however, for code readabilty, i have elected to
        // break it out

        // both local and cloud doesn't exist
        // OR local only exists
        if( (localMetaData == null && cloudMetaData == null) ||
            (localMetaData != null && cloudMetaData == null) ){
            //System.out.println("[DropBox] uploading b/c local/cloud doesn't exist");
            return uploadAndUpdateLocalFile(filename);
        }

        // cloud only exists
        if(localMetaData == null && cloudMetaData != null){
            //System.out.println("[DropBox] Only cloud exists so download");
            return downloadAndUpdateLocalFile(filename);
        }

        // test if the file has been updated 
        if(localMetaData.rev.equals("update")){
            //System.out.println("[DropBox] metadata says to upload");
            return uploadAndUpdateLocalFile(filename);
        }else if(localMetaData.rev.equals(cloudMetaData.rev)){
            //System.out.println("[DropBox] metadata are equal");
            return true;
        }else{
            // different, select which one
            if(localMetaData.lastModified > cloudMetaData.lastModified){
                //System.out.println("[DropBox] local data is newer");
                // local data is newer
                return uploadAndUpdateLocalFile(filename);
            }else{
                // the cloud is newer
            //System.out.println("[DropBox] cloud data is newer");
                return downloadAndUpdateLocalFile(filename);
            }
        }
    }

    /**
     * Return the Drobox metadata from the cloud.
     * @param filename the name of the file to get the rev number
     * @return the metadata and -1 if it doesn't exist
     */
    private MetaData getCloudDropboxMetaData(String filename){
        try {
            //System.out.println("[DropBoxAccount:getCloudDropboxMetaData] "+filename);
            Metadata entry = client.files().getMetadata("/"+filename);
            if(entry != null){
                if(entry instanceof FileMetadata){
                    FileMetadata fileMetadata = (FileMetadata)entry;
                    MetaData metaData = new MetaData(fileMetadata.getRev(), 
                            fileMetadata.getServerModified().getTime());
                    return metaData;
                }
            }
	    return null;
        } catch (DbxException ex) {
            Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Return the dropbox meta data
     * @param the filename on the local filesystem.
     * @return the meta data and null if it doesn't exist.
     */
    private MetaData getLocalDropboxMetaData(String filename){
        Object obj = XStreamManager.getStreamManager().readFile(filename);
        if(obj == null){
            return null;
        }
        if (obj instanceof DeviceList){
            return ((DeviceList)obj).getMetaData();
        }else if(obj instanceof RootManager){
            return ((RootManager)obj).getMetaData();
        }
        return null;
    }

    /**
     * Uploads a file to the cloud.
     * @param filename the file to upload to the cloud.
     * @return the metadata for the uploaded file (on success) 
     * and null on failure.
     */ 
    private MetaData uploadFile(String filename){
        //System.out.println("[DropBoxAccount:uploadFile] "+filename);
        FileInputStream inputStream = null;
	UploadUploader uploader     = null;
	UploadBuilder builder       = null;
	File inputFile;
	FileMetadata fileMetadata;
	MetaData metaData;
        try {
            inputFile    = new File(filename);
            inputStream  = new FileInputStream(inputFile);
            //uploader     = client.files().upload("/"+filename);
            builder      = client.files().uploadBuilder("/"+filename);
	    builder.withMode(WriteMode.OVERWRITE);
	    uploader     = builder.start();
            fileMetadata = uploader.uploadAndFinish(inputStream);
            metaData     = new MetaData(fileMetadata.getRev(),fileMetadata.getServerModified().getTime());
            return metaData;
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(DropBoxAccount.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	    if(uploader != null){
	        uploader.close();
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
            DbxDownloader<FileMetadata> downloader = client.files().download("/"+filename);
            FileMetadata result = downloader.getResult();
            MetaData metaData = new MetaData(result.getRev(), result.getServerModified().getTime());
            downloader.download(outputStream);
            return metaData;
        }catch (Exception e){
        } finally {
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

    private boolean updateLocalFileMetaData(String filename, MetaData metaData){
        try{

            Object obj = XStreamManager.getStreamManager().readFile(filename);
            if(obj == null){
                // problems
                //System.out.println("Error in updating metadata: "+filename);
                return false;
            }

            SyncMetaData syncMD = null;
             
            if (obj instanceof DeviceList){
                syncMD = (DeviceList)obj;
            }else if(obj instanceof RootManager){
                syncMD =  (RootManager)obj;
            }else {
                // failed
                return false;
            }

            syncMD.setMetaData(metaData);
            // write file
            return XStreamManager.getStreamManager().writeFile(filename, syncMD);
        }catch (Exception e){
            e.printStackTrace();
            //System.out.println("Error in updating metadata: "+filename);
            return false;
        }
    }

    /**
     * Uploads the file to the cloud and updates the local file's meta data.
     * @param filename the filename to upload and update.
     * @return true on success and false otherwise.
     */
    private boolean uploadAndUpdateLocalFile(String filename){
        MetaData metaData = uploadFile(filename);
        if(metaData != null){
            // update local file's metadata
            return updateLocalFileMetaData(filename, metaData);
        }
        return false;
    }

    /**
     * Downloads the file to the cloud and updates the local file's meta data.
     * @param filename the filename to download and update.
     * @return true on success and false otherwise.
     */
    private boolean downloadAndUpdateLocalFile(String filename){
        // download file
        MetaData metaData = downloadFile(filename);
        if(metaData != null){
            // update local file's metadata
            return updateLocalFileMetaData(filename, metaData);
        }
        return false;
    }

    /**
     * Returns true if this is a valid file to upload to dropbox.
     * Only xml files are valid.
     * @param filename the file to check.
     */
    private boolean isValidFile(String filename){
        String ext = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
           ext = filename.substring(i + 1);
        }
        if(ext == null){
            return false;
        }
        if(ext.equals("xml")){
            return true;
        }
        return false;
    }
}