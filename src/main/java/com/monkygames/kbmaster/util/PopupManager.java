/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.util;

import com.monkygames.kbmaster.controller.ErrorUIController;
import com.monkygames.kbmaster.controller.PopupController;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Singleton for managing popups.
 * @version 1.0
 */
public class PopupManager{

// ============= Class variables ============== //
    private static PopupManager popupManager;
    private ErrorUIController errorUIController;
// ============= Constructors ============== //
    private PopupManager(){
	try {
	    // pop open add new device
	    URL location = getClass().getResource("/com/monkygames/kbmaster/fxml/popup/ErrorUI.fxml");
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent root = (Parent)fxmlLoader.load(location.openStream());
	    errorUIController = (ErrorUIController) fxmlLoader.getController();
	    /*
	     * doesn't work - bug in javafx
	    Stage stage = WindowUtil.createStage(root.getLayoutBounds().getWidth(),
						 root.getLayoutBounds().getHeight(),
						 root);
	     */
	    Stage stage = WindowUtil.createStage(453, 190.8, root);
	    errorUIController.setStage(stage);
	} catch (IOException ex) {
	    Logger.getLogger(PopupManager.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
// ============= Public Methods ============== //
    /**
     * Shows the error in a popup.
     */
    public void showError(String errorMessage){
	errorUIController.showError(errorMessage);
    }
    /**
     * Opens a popup specified by the url.
     * @param fxmlURL the url of the fxml file to open.
     * @return the controller associated with the fxml file.
     */
    public PopupController openPopup(String fxmlURL){
	PopupController popupController = null;
	try {
	    // pop open add new device
	    URL location = getClass().getResource(fxmlURL);
	    FXMLLoader fxmlLoader = new FXMLLoader();
	    fxmlLoader.setLocation(location);
	    fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
	    Parent root = (Parent)fxmlLoader.load(location.openStream());
	    popupController = (PopupController)fxmlLoader.getController();
	    Stage stage = WindowUtil.createStage(root);
	    popupController.setStage(stage);
	} catch (IOException ex) {
	    Logger.getLogger(PopupManager.class.getName()).log(Level.SEVERE, null, ex);
	    return null;
	}
	return popupController;
    }
    
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //
    public static PopupManager getPopupManager(){
	if(popupManager == null){
	    popupManager = new PopupManager();
	}
	return popupManager;
    }
}
/*
 * Local variables:
 *  c-indent-level: 4
 *  c-basic-offset: 4
 * End:
 *
 * vim: ts=8 sts=4 sw=4 noexpandtab
 */