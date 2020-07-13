package com.monkygames.kbmaster.cloud.thread;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.cloud.CloudAccount;
import com.monkygames.kbmaster.cloud.DropBoxAccount;
import com.monkygames.kbmaster.cloud.DropBoxApp;
import com.monkygames.kbmaster.controller.login.LoginUIController;
import com.monkygames.kbmaster.util.PopupManager;
import javafx.concurrent.Worker;
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
	private DropBoxAccount dropBoxAccount;
	private boolean checkRemember;

	public SyncEventHandler(DropBoxAccount dropBoxAccount, boolean checkRemember, LoginUIController loginController, Stage dropboxSyncStage){
		this.dropBoxAccount = dropBoxAccount;
		this.loginController = loginController;
		this.dropboxSyncStage = dropboxSyncStage;
		this.checkRemember = checkRemember;
	}

	@Override
	public void handle(WorkerStateEvent event) {
		dropboxSyncStage.hide();
		if (event.getSource().getMessage().equals("failed") || event.getEventType() == WorkerStateEvent.WORKER_STATE_FAILED) {
			loginController.resetLoginUI();
			loginController.showStage();
			KeyboardingMaster.getUserSettings().accessToken = null;
			DropBoxApp.ACCESS_TOKEN = null;
			PopupManager.getPopupManager().showError("Sync failed. Please log in again.");

		}
		else loginController.showDeviceMenuFromLogin(dropBoxAccount, checkRemember);
	}
}
