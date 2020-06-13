/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

// === java imports === //
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
// === javafx imports === //
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Contains common methods useful for popups.
 * @version 1.0
 */
public class PopupController implements Initializable{


// ============= Class variables ============== //
    /**
     * Its protected because the popup may need access to the stage.
     */
    protected Stage stage;
    private ArrayList<PopupNotifyInterface> notifiers = new ArrayList<>();
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Sets the stage in order to hide this popup.
     * @param stage the stage that can be used to hide the windows.
     */
    public void setStage(Stage stage){
	this.stage = stage;
    }
    /**
     * Shows the window.
     */
    public void showStage(){
	stage.show();
    }
    public void addNotification(PopupNotifyInterface notify){
	notifiers.add(notify);
    }
// ============= Protected Methods ============== //
    /**
     * Hides this popup.
     */
    protected void hideStage(){
	stage.hide();
    }
    protected void notifyOK(String message){
	for(PopupNotifyInterface notify: notifiers){
	    notify.onOK(this,message);
	}
    }
    protected void notifyCancel(String message){
	for(PopupNotifyInterface notify: notifiers){
	    notify.onCancel(this,message);
	}
    }
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
    @Override
    public void initialize(URL url, ResourceBundle rb) { }
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */
