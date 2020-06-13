/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util.thread;

import com.monkygames.kbmaster.account.CloudAccount;
import javafx.concurrent.Task;

/**
 * Manages the work to sync between local and dropbox.
 * @author spethm
 */
public class DropboxSyncTask extends Task{

	private CloudAccount cloudAccount;

	/**
	 * Uses the cloudAccount (which should already have the access token set.
	 * @param cloudAccount requires the access token to be set.
	 */
	public DropboxSyncTask(CloudAccount cloudAccount){
		this.cloudAccount = cloudAccount;
	}

	@Override
	protected Object call() throws Exception {
		// run sync

		if(cloudAccount.sync()){
			super.succeeded();
		}else {
			super.failed();
		}
		return null;
	}
}
