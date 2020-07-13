/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.cloud;

/**
 *
 * A ClientAccount manages saving and loading profiles.
 * Profiles can be uploaded to the cloud or saved locally.
 * @author spethm
 */
public interface CloudAccount {

	/**
	 * Syncs the devices and profiles with the cloud.
	 */
	public boolean sync();

	/**
	 * Returns the access token for the web account.
	 */
	public String getAccessToken();
}
