/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.controller;

/**
 * Method to be called when a popup has closed.
 * @version 1.0
 */
public interface PopupNotifyInterface {

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Notifies that the OK button has been used and is complete.
     * @param src the popup that is notifying.
     * @param message a message to pass about the notification.
     */
    public void onOK(Object src, String message);
    /**
     * Notifies that the Cancel button has been used and is complete.
     * @parma src the popup that is notifying.
     * @param message a message to pass about the notification.
     */
    public void onCancel(Object src, String message);
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
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
