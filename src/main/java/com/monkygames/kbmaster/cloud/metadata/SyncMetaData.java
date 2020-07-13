package com.monkygames.kbmaster.cloud.metadata;

/**
 * Contains methods for managing meta data.
 */
public interface SyncMetaData {
    /**
     * Returns the meta data.
     * @return the meta data and null if it doesn't exist.
     */
    MetaData getMetaData();

    /**
     * Sets the meta data.
     * @param metaData the meta data to set.
     */
    void setMetaData(MetaData metaData);
}
