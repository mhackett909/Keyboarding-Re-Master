package com.monkygames.kbmaster.cloud.thread;

import com.monkygames.kbmaster.KeyboardingMaster;
import com.monkygames.kbmaster.util.PopupManager;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Handles the response from the SyncTask for syncing on exit.
 * @author spethm
 */
public class SyncEventOnExitHandler implements EventHandler<WorkerStateEvent>{
	private Stage dropboxSyncStage;
	private boolean logout;

	public SyncEventOnExitHandler(Stage dropboxSyncStage, boolean logout){
		this.dropboxSyncStage = dropboxSyncStage;
		this.logout = logout;
	}

	@Override
	public void handle(WorkerStateEvent event) {
		dropboxSyncStage.hide();
		if (event.getSource().getMessage().equals("failed") || event.getEventType() == WorkerStateEvent.WORKER_STATE_FAILED) {
			System.out.println("Sync failed");
			//TODO Make this a popup (must interrupt function)
		}
		if (logout) KeyboardingMaster.getInstance().logout();
 		else KeyboardingMaster.getInstance().exit();
	}
}
