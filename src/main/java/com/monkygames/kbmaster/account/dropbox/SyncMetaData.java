package com.monkygames.kbmaster.account.dropbox;

/**
 * Contains methods for managing meta data.
 */
public interface SyncMetaData {
    /**
     * Returns the meta data.
     * @return the meta data and null if it doesn't exist.
     */
    public MetaData getMetaData();

    /**
     * Sets the meta data.
     * @param metaData the meta data to set.
     */
    public void setMetaData(MetaData metaData);
}
