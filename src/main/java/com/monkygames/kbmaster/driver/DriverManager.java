/* 
 * See LICENSE in top-level directory.
 */
package com.monkygames.kbmaster.driver;

import java.util.ArrayList;

/**
 * Manages drivers by creating the necessary information for all device drivers.
 * For now, the makes of the devices are stored in two lists; however,
 * later on when there becomes thousands of devices hopefully, a tree like
 * data structure will be more efficient.  For now, I went with a simpler option.
 * @version 1.0
 */
public class DriverManager {

	// ============= Class variables ============== //
	ArrayList<Device> devices;
	/**
	 * The list of keyboard device makes.
	 */
	ArrayList<String> keyboardMakes;
	/**
	 * The list of mouse device makes.
	 */
	ArrayList<String> mouseMakes;

	// ============= Constructors ============== //
	public DriverManager() {
		devices = new ArrayList<>();
		keyboardMakes = new ArrayList<>();
		mouseMakes = new ArrayList<>();
		// initialize all drivers by make
		createRazerDrivers();
		createBelkinDrivers();
		createOtherDrivers();
	}

	// ============= Public Methods ============== //

	/**
	 * Returns all installed devices.
	 */
	public ArrayList<Device> getDevices() { return devices; }
	/**
	 * Returns a list of device makes.
	 */
	public ArrayList<String> getKeyboardMakes() { return keyboardMakes; }
	/**
	 * Returns a list of device makes.
	 */
	public ArrayList<String> getMouseMakes() { return mouseMakes; }
	/**
	 * Returns the list of device model names specified by the make and the type.
	 *
	 * @param type the type of device.
	 * @param make the string of the make (ie Razer).
	 * @return returns a list of model names.
	 */
	public ArrayList<String> getDevicesByMake(DeviceType type, String make) {
		//Investigate
		ArrayList<String> list = new ArrayList<>();
		for (Device device : devices) {
			if (device.getDeviceInformation().getDeviceType() == type &&
					device.getDeviceInformation().getMake().equals(make)) {
				list.add(device.getDeviceInformation().getModel());
			}
		}
		return list;
	}
	/**
	 * Returns the device based on the specified information.
	 * @param packageName the device package name.
	 * @return the device if found and null if not found.
	 */
	public Device getDevice(String packageName) {
		for (Device device : devices) {
			if (device.getDeviceInformation().getPackageName().equals(packageName))
				return device;
		}
		return null;
	}
	/**
	 * Returns the device based on the specified information.
	 * @return the device if found and null if not found.
	 */
	public Device getDeviceByType(DeviceType deviceType, String make, String model) {
		for (Device device : devices) {
			if (device.getDeviceInformation().getDeviceType() == deviceType
					&& device.getDeviceInformation().getMake().equals(make)
					&& device.getDeviceInformation().getModel().equals(model))
				return device;
		}
		return null;
	}

	/**
	 * Cleans up the devices. Invoked on program logout/exit.
	 */
	public void close() {
		for (Device device : devices) device = null;
		for (String keyboardMake : keyboardMakes) keyboardMake = null;
		for (String mouseMake : mouseMakes) mouseMake = null;
		devices.clear();
		keyboardMakes.clear();
		mouseMakes.clear();
	}
// ============= Private Methods ============== //

	/**
	 * Adds a device and sets up the appropriate make information.
	 *
	 * @param device the device to add.
	 */
	private void addDevice(Device device) {
		devices.add(device);
		if (device.getDeviceInformation().getDeviceType() == DeviceType.KEYBOARD) {
			if (!keyboardMakes.contains(device.getDeviceInformation().getMake())) {
				keyboardMakes.add(device.getDeviceInformation().getMake());
			}
		} else if (device.getDeviceInformation().getDeviceType() == DeviceType.MOUSE) {
			if (!mouseMakes.contains(device.getDeviceInformation().getMake())) {
				mouseMakes.add(device.getDeviceInformation().getMake());
			}
		}
	}

	/**
	 * Handles creating all Razer drivers.
	 * Includes mice and keyboards.
	 */
	private void createRazerDrivers() {
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.nostromo.Nostromo());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.tartarus.Tartarus());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.tartarus_v2.TartarusV2());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.tartaruschroma.TartarusChroma());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.orbweaver.Orbweaver());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.orbweaverchroma.OrbweaverChroma());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.naga.Naga());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.taipan.Taipan());
		addDevice(new com.monkygames.kbmaster.driver.devices.razer.marauder.Marauder());
	}

	/**
	 * Creates all Belkin devices.
	 */
	private void createBelkinDrivers() {
		addDevice(new com.monkygames.kbmaster.driver.devices.belkin.n52.N52());
		addDevice(new com.monkygames.kbmaster.driver.devices.belkin.n52te.N52TE());
	}

	private void createOtherDrivers() {
		addDevice(new com.monkygames.kbmaster.driver.devices.lacunary_limited.ipv6buddy.IPV6Buddy());
		addDevice(new com.monkygames.kbmaster.driver.devices.digitus.numpad.Numpad());
		addDevice(new com.monkygames.kbmaster.driver.devices.generic.mouse.gspy.GSpyUsbGamingMouseRH1900());
		addDevice(new com.monkygames.kbmaster.driver.devices.logitech.LogitechG502());
	}
}
