package com.monkygames.kbmaster.input;
/**
 * Contains info about the joystick device.
 * Used by the engine.
 * @author vapula87
 */
public class JoystickInfo {
	///---Members---\\\
	public static final float ACUTE_ANGLE = 22.5f, RIGHT_ANGLE = 90.0f, STRAIGHT_ANGLE = 180.0f, REFLEX_ANGLE = 270.0f, COMPLETE_ANGLE = 360.0f;
	public static final float DPAD_UP = 0.25f, DPAD_DOWN = 0.75f, DPAD_LEFT = 1.0f, DPAD_RIGHT = 0.5f;
	public static final float DPAD_UP_RIGHT = 0.375f, DPAD_UP_LEFT = 0.125f, DPAD_DOWN_RIGHT = 0.625f, DPAD_DOWN_LEFT = 0.875f;
	public static final float PI = 3.14159f, RESET = 0.0f;
	public enum LastPress { UP, DOWN, RIGHT, LEFT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, NONE }
	private float lastX, lastY, lastRX, lastRY, lastPOV;
	private enum Quadrant { I, II, III, IV };
	private LastPress lastPress;
	
	///---Constructor---\\\
	public JoystickInfo() {
		lastX = lastY = lastRX = lastRY = lastPOV = 0.0f;
		lastPress = LastPress.NONE;
	}
	
	///---Setters---\\\
	public void setLastX(float lastX) { this.lastX = lastX; }
	public void setLastY(float lastY) { this.lastY = lastY; }
	public void setLastRX(float lastRX) { this.lastRX = lastRX; }
	public void setLastRY(float lastRY) { this.lastRY = lastRY; }
	public void setLastPOV(float lastPOV) { this.lastPOV = lastPOV; }
	public void setLastPress(LastPress lastPress) { this.lastPress = lastPress; }
	
	///---Getters---\\\
	public float getLastX() { return lastX; }
	public float getLastY() { return lastY; }
	public float getLastRX() { return lastRX; }
	public float getLastRY() { return lastRY; }
	public float getLastPOV() { return lastPOV; }
	public LastPress getLastPress() { return lastPress; }
	
	///---Public Methods---\\\
	public float findAngle(float x, float y) {
		float angle = 0;
		if (x == 0 && y == 0) return angle;
		else if (x == 0) {
			if (y > 0) return REFLEX_ANGLE;
			else return RIGHT_ANGLE;
		}
		else if (y == 0) {
			if (x > 0) return COMPLETE_ANGLE;
			else return STRAIGHT_ANGLE;
		}
		else {
			//Convert from radians to degrees
			angle = (float) Math.atan(Math.abs(y)/Math.abs(x));
			angle *= STRAIGHT_ANGLE;
			angle /= PI;
			switch (findQuadrant(x,y)) {
				case II:
					angle = STRAIGHT_ANGLE-angle;
					break;
				case III:
					angle = STRAIGHT_ANGLE+angle;
					break;
				case IV:
					angle = COMPLETE_ANGLE-angle;
			}
		}
		return angle;
	}
	
	///---Private  Methods---\\\
	private Quadrant findQuadrant(float x, float y) {
		if (x > 0 && y < 0) return Quadrant.I;
		else if (x < 0 && y < 0) return Quadrant.II;
		else if (x < 0 && y > 0) return Quadrant.III;
		else return Quadrant.IV;
	}
}