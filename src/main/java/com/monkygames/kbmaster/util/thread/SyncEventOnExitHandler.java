package com.monkygames.kbmaster.util.thread;

import com.monkygames.kbmaster.KeyboardingMaster;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Handles the response from the SyncTask for syncing on exit.
 * @author spethm
 */
public class SyncEventOnExitHandler implements EventHandler<WorkerStateEvent>{
	private Stage dropboxSyncStage;

	public SyncEventOnExitHandler(Stage dropboxSyncStage){
		this.dropboxSyncStage = dropboxSyncStage;
	}

	@Override
	public void handle(WorkerStateEvent event) {
		// hide sync ui
		dropboxSyncStage.hide();

		if(event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED){
			// use login to show device menu

		}else if(event.getEventType() == WorkerStateEvent.WORKER_STATE_FAILED){
			// pop errro

		}
		KeyboardingMaster.getInstance().exit();
	}
	
}
