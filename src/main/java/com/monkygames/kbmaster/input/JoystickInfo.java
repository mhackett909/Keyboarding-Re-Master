package com.monkygames.kbmaster.input;

import java.awt.*;

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
	public static final float PI = 3.14159f, RESET = 0.0f, MAX_SPEED = 1.0f;
	public static final long MIN_TIME = 300000;
	public static final int SENSITIVITY = 4;
	public enum LastPress { UP, DOWN, RIGHT, LEFT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, NONE }
	private float lastX, lastY, lastRX, lastRY, lastPOV, mouseSpeedXY, mouseSpeedRXRY, lastAngleXY, lastAngleRXRY;
	private float startingX, startingY, startingRX, startingRY;
	private long savedTimeXY, savedTimeRXRY;
	private int radiusXY, radiusRXRY;
	private enum Quadrant { I, II, III, IV };
	private LastPress lastPress;
	
	///---Constructor---\\\
	public JoystickInfo() {
		lastX = lastY = lastRX = lastRY = lastPOV = mouseSpeedXY = mouseSpeedRXRY = lastAngleXY = lastAngleRXRY = 0.0f;
		startingX = startingY = startingRX = startingRY = 0;
		savedTimeXY = savedTimeRXRY = 0;
		radiusXY = radiusRXRY = 1;
		lastPress = LastPress.NONE;
	}
	
	///---Setters---\\\
	public void setLastX(float lastX) { this.lastX = lastX; }
	public void setLastY(float lastY) { this.lastY = lastY; }
	public void setLastRX(float lastRX) { this.lastRX = lastRX; }
	public void setLastRY(float lastRY) { this.lastRY = lastRY; }
	public void setLastPOV(float lastPOV) { this.lastPOV = lastPOV; }
	public void setMouseSpeedXY(float mouseSpeedXY) { this.mouseSpeedXY = mouseSpeedXY; }
	public void setMouseSpeedRXRY(float mouseSpeedRXRY) { this.mouseSpeedRXRY = mouseSpeedRXRY; }
	public void setLastAngleXY(float lastAngleXY) {
		this.lastAngleXY = lastAngleXY;
		Point point = MouseInfo.getPointerInfo().getLocation();
		startingX = point.x;
		startingY = point.y;
		radiusXY = 1;
	}
	public void setLastAngleRXRY(float lastAngleRXRY) {
		this.lastAngleRXRY = lastAngleRXRY;
		Point point = MouseInfo.getPointerInfo().getLocation();
		startingRX = point.x;
		startingRY = point.y;
		radiusRXRY = 1;
	}
	public void setTimeXY(long savedTimeXY) { this.savedTimeXY = savedTimeXY; }
	public void setTimeRXRY(long savedTimeRXRY) { this.savedTimeRXRY = savedTimeRXRY; }
	public void setLastPress(LastPress lastPress) { this.lastPress = lastPress; }
	
	///---Getters---\\\
	public float getLastX() { return lastX; }
	public float getLastY() { return lastY; }
	public float getLastRX() { return lastRX; }
	public float getLastRY() { return lastRY; }
	public float getLastPOV() { return lastPOV; }
	public float getMouseSpeedXY() { return mouseSpeedXY; }
	public float getMouseSpeedRXRY() { return mouseSpeedRXRY; }
	public float getLastAngleXY() { return lastAngleXY; }
	public float getLastAngleRXRY() { return lastAngleRXRY; }
	public long getTimeXY() { return savedTimeXY; }
	public long getTimeRXRY() { return savedTimeRXRY; }
	public LastPress getLastPress() { return lastPress; }
	
	///---Public Methods---\\\
	/**
	 * The angle between the origin and the provided (x,y) coordinates.
	 * @param x The x coordinate of the joystick.
	 * @param y The y coordinate of the joystick.
	 */
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
	/**
	 * Uses the distance formula to obtain the mouse speed percentage.
	 * @param x The x coordinate of the joystick.
	 * @param y The y coordinate of the joystick.
	 * @return The percentage of normal mouse speed.
	 */
	public float getMouseSpeed(float x, float y) {
		float mouseSpeed = distance(0,0,x, y);
		mouseSpeed /= MAX_SPEED;
		return (mouseSpeed > 1 ? 1 : mouseSpeed);
	}
	/**
	 * Retrieves the next mouse coordinates.
	 * @param jType The joystick Axis type
	 * @return New coordinates for the robot.
	 */
	public int[] getNewCoords(String jType) {
		//Use the angle, radius, and the original x,y position to find a point on the circle
		float angle = (jType.equals("RXRY") ? lastAngleRXRY : lastAngleXY);
		int radius = (jType.equals("RXRY") ? radiusRXRY : radiusXY);
		radius*=radius;
		float x = (jType.equals("RXRY") ? startingRX : startingX);
		float y = (jType.equals("RXRY") ? startingRY : startingY);
		float[] floatCoords = findPoint(angle, radius, x, y);
		//Compare the point on the circle to the current mouse position.
		//Determine which direction is closest to the point and move that way.
		Point point = MouseInfo.getPointerInfo().getLocation();
		float[] distances = new float[5];
		distances[0] = distance(floatCoords[0], floatCoords[1], point.x, point.y+1); //Up
		distances[1] = distance(floatCoords[0], floatCoords[1], point.x, point.y-1); //Down
		distances[2] = distance(floatCoords[0], floatCoords[1], point.x-1, point.y); //Left
		distances[3] = distance(floatCoords[0], floatCoords[1], point.x+1, point.y); //Right
		distances[4] = distance(floatCoords[0], floatCoords[1], point.x, point.y); //Current position
		int position = 0;
		float shortestDistance = distances[position];
		for (int i = 1; i < distances.length; i++) {
			if (distances[i] < shortestDistance) {
				shortestDistance = distances[i];
				position = i;
			}
		}
		int[] intCoords;
		switch (position) {
			case 0:
				intCoords = new int[]{point.x,point.y+1}; //Up
				break;
			case 1:
				intCoords = new int[]{point.x,point.y-1}; //Down
				break;
			case 2:
				intCoords = new int[]{point.x-1,point.y}; //Left
				break;
			case 3:
				intCoords = new int[]{point.x+1,point.y}; //Right
				break;
			default:
				// Increase radius but do not move
				intCoords = new int[]{point.x, point.y};
				if (jType.equals("RXRY")) radiusRXRY++;
				else radiusXY++;
		}
		// If the new point is outside the radius, increase the radius
		float dist = distance(intCoords[0],intCoords[1], x, y);
		if (dist >= radius) {
			if (jType.equals("RXRY")) radiusRXRY++;
			else radiusXY++;
		}
		return intCoords;
	}
	
	///---Private  Methods---\\\
	/**
	 * Finds the quadrant in which the (x,y) value resides.
	 * Note: the y-axis is inverse for the joystick devices I have tested.
	 * @return null if the point is on an axis.
	 */
	private Quadrant findQuadrant(float x, float y) {
		if (x == 0 || y == 0) return null;
		else if (x > 0) return (y < 0 ? Quadrant.I : Quadrant.IV);
		else return (y < 0 ? Quadrant.II : Quadrant.III);
	}
	/**
	 * The distance formula.
	 */
	public float distance(float x1, float y1, float x2, float y2) {
		x2-=x1;
		x2*=x2;
		y2-=y1;
		y2*=y2;
		return (float) Math.sqrt(x2+y2);
	}
	/**
	 * Finds a point on the circle using the given angle, radius, and starting position.
	 */
	private float[] findPoint(float angle, int radius, float x, float y) {
		//Convert from degrees to radians
		angle *= PI;
		angle /= STRAIGHT_ANGLE;
		float newX = (float) (radius * Math.cos(angle));
		float newY = (float) (radius * Math.sin(angle));
		newY*=-1; //y-axis is inverse for the dualshock 4
		newX += x;
		newY += y;
		return new float[]{ newX, newY };
	}
}