package com.monkygames.kbmaster.account.dropbox;

/**
 * The revision number for dropbox files.
 * @author spethm
 */
public class MetaData {

	/**
	 * The last modified time of the file.
	 */
	public long lastModified;

	/**
	 * The revision number for the file.
	 */
	public String rev;

	public MetaData(String rev, long lastModified){
		this.rev = rev;
		this.lastModified = lastModified;
	}

	/**
	 * Updates this meta data with the passed in meta data.
	 * @param metaData used to update this.
	 */
	public void update(MetaData metaData){
		this.rev = metaData.rev;
		this.lastModified = metaData.lastModified;
	}

	@Override
	public String toString(){
		return "MetaData["+rev+","+lastModified+"]";
	}
}