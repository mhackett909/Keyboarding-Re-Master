package com.monkygames.kbmaster.util.thread;

import com.monkygames.kbmaster.account.CloudAccount;
import com.monkygames.kbmaster.account.DropBoxAccount;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Handles the response from the SyncTask
 * @author spethm
 */
public class SyncEventHandler implements EventHandler<WorkerStateEvent>{
	private LoginUIController loginController;
	private Stage dropboxSyncStage;
	private CloudAccount cloudAccount;
	private boolean checkRemember;

	public SyncEventHandler(CloudAccount cloudAccount, boolean checkRemember, LoginUIController loginController, Stage dropboxSyncStage){
		this.cloudAccount = cloudAccount;
		this.loginController = loginController;
		this.dropboxSyncStage = dropboxSyncStage;
		this.checkRemember = checkRemember;
	}

	@Override
	public void handle(WorkerStateEvent event) {
		// hide sync ui
		dropboxSyncStage.hide();

		if(event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED){
			// use login to show device menu
			loginController.showDeviceMenuFromLogin(cloudAccount, checkRemember);

		}else if(event.getEventType() == WorkerStateEvent.WORKER_STATE_FAILED){
			// TODO pop errro

			// backout to login page
			loginController.showStage();
		}
	}
	
}
