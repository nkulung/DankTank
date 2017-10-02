package main;
/**
 * The abstract Tank class acts as a base class for tanks of different characteristics.
 * All comments for methods in child classes are located in this class, with the exception of 
 * methods that operate differently or private methods.
 * CSC 305: Software Engineering Fall '17
 * @author Nate Larson
 * @author Gabe Le
 * @author Nick Kulungian
 * @author Ronil Soto
 * @author Kevin Andrade
 */
import java.awt.Color;

public abstract class Tank 
{
	private int health;			// represents the health of the tank
	private int velocity;		// represents the velocity the tank moves at
	private int x;				// represents the x coordinate of the tank on screen
	private int y;				// represents the y coordinate of the tank on screen
	private int[] location;		// represents the location of the tank in the world
	private int size;			// represents the scaled size of the tank
	private Color tankColor;	// represents the primary color of the tank
	private boolean up;			// represents whether or not the tank is vertical(true) or horizontal(false)
	private boolean friendly;	// represents the tanks allied status 
	
	
	/**
	 * Method to return the tank's health
	 * @return health
	 */
	public abstract int getHealth();
	
	/**
	 * Method to return the tank's velocity
	 * @return velocity
	 */
	public abstract int getVelocity();
	
	/**
	 * Method to return the tank's X coordinate in the window.
	 * This variable is separate from the X coordinate relative to the world.
	 * @return x
	 */
	public abstract int getX();
	
	/**
	 * Method to return the tank's Y coordinate in the window.
	 * This variable is separate from the Y coordinate relative to the world.
	 * @return y
	 */
	public abstract int getY();
	
	/**
	 * Method to return the tank's unit location in the world as an integer array.
	 * location[0] holds the X coordinate.
	 * location[1] holds the Y coordinate.
	 */
	public abstract int[] getLocation();
	
	/**
	 * Method to return the tank's size (scaled).
	 * @return size
	 */
	public abstract int getSize();
	
	/**
	 * Method to return the tank's primary color
	 * @return tankColor
	 */
	public abstract Color getColor();
	
	/**
	 * Method to return the 
	 * @return
	 */
	public abstract boolean isVertical();
	
	public abstract boolean isFriendly();
	
	public abstract void setHealth(int newHealth);
	
	public abstract void setVelocity(int newVelocity);
	
	public abstract void setX(int newX);
	
	public abstract void setY(int newY);
	
	public abstract void setLocation(int[] newLocation);
	
	public abstract void setSize(int newSize);
	
	public abstract void setColor(Color newColor);
	
	public abstract void isVertical(boolean newValue);
	
	public abstract void isFriendly(boolean newValue);

}
