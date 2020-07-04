/*
 * See LICENSE in top-level directory.
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
		HardwareEngine engine = new HardwareEngine(device, this);
		engines.put(device.getDeviceInformation().getJinputName(),engine);
		engine.setDeviceMenuUIController(deviceMenuController);
		engine.startScanning();
		boolean hardwareExist = engine.hardwareExist();
		return hardwareExist;
	}
	/**
	 * Checks if this device is already managed.
	 * @param device to be checked.
	 * @return true if already managed and false otherwise.
	 */
	public boolean isDeviceManaged(Device device){
		if(engines.get(device.getDeviceInformation().getJinputName()) != null)
			return true;
		return false;
	}
	/**
	 * Disable the specified device but continue to check
	 * for device status.
	 * @param device the device to disable.
	 */
	public void disableDevice(Device device){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine != null) engine.stopPolling();
	}
	/**
	 * Disables and removes the device from this list.
	 * @param device the device to be disabled and removed.
	 * @return true if disables and removes and false otherwise.
	 */
	public boolean removeDevice(Device device){
		HardwareEngine engine = engines.remove(device.getDeviceInformation().getJinputName());
		engine.stopPolling();
		engine.stopScanning();
		if(engine != null) return true;
		return false;
	}
	/**
	 * Starts polling the specified device.
	 * @param device the device to poll.
	 * @param profile the profile used by the Engine to remap the outputs.
	 * @return false if the device doesn't exist and true otherwise.
	 */
	public void startPollingDevice(Device device,Profile profile){
		HardwareEngine engine = engines.get(device.getDeviceInformation().getJinputName());
		if(engine == null || !device.isConnected() || !device.isEnabled()) return;
		engine.startPolling(profile);
	}
	/**
	 * Stops all polling threads.
	 */
	public void stopPollingAllDevices(){
		for(HardwareEngine engine: engines.values())
			engine.stopPolling();
	}
	/**
	 * Stops all hardware scanning threads.
	 */
	public void stopScanningAllDevices() {
		for (HardwareEngine engine: engines.values())
			engine.stopScanning();
	}
	/**
	 * Alerts the user interface of a change in device status.
	 *
	 * @param hasConnected true if the hardware has been connected or false for disconnected.
	 * @param deviceName the name of the device that was connected or disconnected.
	 */
	@Override
	public void hardwareStatusChange(boolean hasConnected, String deviceName) {
		// update device connection status
		HardwareEngine engine = engines.get(deviceName);
		engine.getDevice().setIsConnected(hasConnected);
		deviceMenuController.updateDevices();
	}
	@Override
	public void eventIndexPerformed(int index) {
	}
}
