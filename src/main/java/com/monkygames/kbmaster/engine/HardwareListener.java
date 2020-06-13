/* 
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.engine;

/**
 * Informs of hardware status change.
 * @version 1.0
 */
public interface HardwareListener{

// ============= Class variables ============== //
// ============= Constructors ============== //
// ============= Public Methods ============== //
    /**
     * Signals if the hardware has connected or has been disconnected.
     * @param hasConnected true if the hardware has been connected or false for disconnected.
     * @param deviceName the name of the device that was connected or disconnected.
     */
    public void hardwareStatusChange(boolean hasConnected, String deviceName);
    /**
     * Informs of an event with the specified index.
     * Note, this is mainly used when in edit mode.
     * @param index the index of the key pressed.
     */
    public void eventIndexPerformed(int index);
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
