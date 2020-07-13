/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.cloud.thread;

import com.monkygames.kbmaster.cloud.DropBoxAccount;
import javafx.concurrent.Task;

/**
 * Manages the work to sync between local and dropbox.
 * @author spethm
 */
public class DropboxSyncTask extends Task{

	private DropBoxAccount dropBoxAccount;

	/**
	 * Uses the cloudAccount (which should already have the access token set.
	 * @param dropBoxAccount requires the access token to be set.
	 */
	public DropboxSyncTask(DropBoxAccount dropBoxAccount){
		this.dropBoxAccount = dropBoxAccount;
	}

	@Override
	protected Object call() {
		if (!dropBoxAccount.sync()) super.updateMessage("failed");
		else super.updateMessage("");
		return null;
	}
}
