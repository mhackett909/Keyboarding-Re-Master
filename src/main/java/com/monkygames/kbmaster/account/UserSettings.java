/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.account;

/**
 * Contains information stored locally for the user.
 * @author spethm
 */
public class UserSettings {
	/**
	 * The user won't go to the login screen.
	 */
	public boolean isRemember = false;

	/**
	 * The login method.
	 **/
	public int loginMethod = 0;

	/**
	 * Used by web accounts to access online services.
	 */
	public String accessToken = "";
	
}
