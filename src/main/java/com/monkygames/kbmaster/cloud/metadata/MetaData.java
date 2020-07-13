package com.monkygames.kbmaster.cloud.metadata;

/**
 * Used for DropBox syncing.
 */
public class MetaData {
	/**
	 * The last sync time of the file.
	 */
	public long lastSync;
	public MetaData(long lastSync) { this.lastSync = lastSync; }
	@Override
	public String toString(){ return "MetaData["+lastSync+"]"; }
}