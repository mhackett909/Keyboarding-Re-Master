/*
 * See COPYING in top-level directory.
 */
package com.monkygames.kbmaster.engine;

// === imports === //
import com.monkygames.kbmaster.controller.DeviceMenuUIController;
import com.monkygames.kbmaster.driver.Device;
import com.monkygames.kbmaster.profiles.Profile;
import java.util.HashMap;

/**
 * Manages the Hardware devices that are configured.
 * @version 1.0
 */
public class HardwareManager implements HardwareListener{

// ============= Class variables ============== //
	/**
	 * A list of engines that are configured.
	 */
	private HashMap<String,HardwareEngine> engines;
	/**
	 * Used to update if a device was connected/disconnected.
	 */
	private DeviceMenuUIController deviceMenuController;
	// ============= Constructors ============== //
	public HardwareManager(DeviceMenuUIController deviceMenuController){
		this.deviceMenuController = deviceMenuController;
		engines = new HashMap<>();
	}
// ============= Public Methods ============== //
	/**
	 * Adds a device that will be managed.
	 * @return true if the device is connected and false otherwise.
	 */
	public boolean addManagedDevice(Device device){
		HardwareEngine engine = new HardwareEngine(device);
		engine.addHardwareListener(this);
		engines.put(device.getDeviceInformation().getJinputName(),engine);
		boolean hardwareExist = engine.hardwareExist();
		return hardwareExist;
	}
	/**
	 * Checks if this device is already managed.
	 * @param device to be checked.
	 * @return true if already managed and false otherwise.
	 */
	public boolean isDeviceManaged(Device device){
		if(engines.get(device.getDeviceInformation().getJinputName()) != null){
			return true;
		}
		return false;
	}
	/**
	 * Updates the connection state for this device.
	 * @param device the connection state of the device to update.
	 */
	public void updateConnectionState(Device device){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine != null){
			device.setIsConnected(engine.hardwareExist());
		}
	}
	/**
	 * Disable the specified device but continue to poll to check
	 * for device status.
	 * @param device the device to disable.
	 */
	public void disableDevice(Device device){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine != null) engine.setEnabled(false);
	}
	/**
	 * Disables and removes the device from this list.
	 * @param device the device to be disabled and removed.
	 * @return true if disables and removes and false otherwise.
	 */
	public boolean removeDevice(Device device){
		disableDevice(device);
		HardwareEngine engine = engines.remove(device.getDeviceInformation().getJinputName());
		engine.stopPolling();
		if(engine != null){
			return true;
		}
		return false;
	}
	/**
	 * Starts polling the specified device.
	 * @param device the device to poll.
	 * @param profile the profile used by the Engine to remap the outputs.
	 * @return false if the device doesn't exist and true otherwise.
	 */
	public boolean startPollingDevice(Device device,Profile profile){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine == null || !device.isConnected() || !device.isEnabled()) return false;
		if(profile != null) {
			engine.setEnabled(true);
			engine.startPolling(profile);
			return true;
		}
		else return false;
	}
	/**
	 * Stops polling the specified device.
	 * @param device the device to stop polling.
	 * @return true if successfully stopped and false otherwise.
	 */
	public boolean stopPollingDevice(Device device){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine == null) return false;
		engine.stopPolling();
		return true;
	}
	/**
	 * Stops all devices from polling.
	 */
	public void stopPollingAllDevices(){
		for(HardwareEngine engine: engines.values()){
			engine.stopPolling();
			engine.setEnabled(false);
		}

	}
// ============= Protected Methods ============== //
// ============= Private Methods ============== //
// ============= Implemented Methods ============== //
// ============= Extended Methods ============== //
// ============= Internal Classes ============== //
// ============= Static Methods ============== //

	@Override
	public void hardwareStatusChange(boolean hasConnected, String deviceName) {
		// update device connection status
		HardwareEngine engine = engines.get(deviceName);
		engine.getDevice().setIsConnected(hasConnected);
		// propagate the changes
		deviceMenuController.updateDevices();
	}

	@Override
	public void eventIndexPerformed(int index) {
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