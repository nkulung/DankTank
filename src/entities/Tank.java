package entities;
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
import java.util.ArrayList;
import java.util.List;

public abstract class Tank 
{
	float health;			// represents the health of the tank
	float velocity;			// represents the velocity the tank moves at
	float x;				// represents the x coordinate of the tank on screen
	float y;				// represents the y coordinate of the tank on screen
	float[] location;		// represents the location of the tank in the world
	float size;				// represents the scaled size of the tank
	Color tankColor;		// represents the primary color of the tank
	boolean up;				// represents whether or not the tank is vertical(true) or horizontal(false)
	boolean friendly;		// represents the tanks allied status 
	float angle;			// represents the angle of the barrel
	Box collisionBox;
	
	ArrayList<Projectile> activeRounds = new ArrayList<Projectile>();	// represents list of active rounds from tank
	
	
	/**
	 * Method to return the tank's health
	 * @return health
	 */
	public float getHealth()
	{
		return health;
	}
	
	/**
	 * Method to return the tank's velocity
	 * @return velocity
	 */
	public float getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Method to return the tank's X coordinate in the window.
	 * This variable is separate from the X coordinate relative to the world.
	 * @return x
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Method to return the tank's Y coordinate in the window.
	 * This variable is separate from the Y coordinate relative to the world.
	 * @return y
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Method to return the tank's unit location in the world as an integer array.
	 * location[0] holds the X coordinate.
	 * location[1] holds the Y coordinate.
	 */
	public float[] getLocation()
	{
		return location;
	}
	
	/**
	 * Method to return the tank's size (scaled).
	 * @return size
	 */
	public float getSize()
	{
		return size;
	}
	
	/**
	 * Method to return the tank's primary color
	 * @return tankColor
	 */
	public Color getColor()
	{
		return tankColor;
	}
	
	/**
	 * Method to return the tanks collision box
	 * @return collisionBox
	 */
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	/**
	 * Method to set the collision box
	 */
	public void setCollisionBox(Box newBox)
	{
		collisionBox = newBox;
	}
	
	/**
	 * Method to return whether the tank is oriented vertically or not
	 * @return up
	 */
	public boolean isVertical()
	{
		return up;
	}
	
	/**
	 * Method to return tanks allied status
	 * @return friendly
	 */
	public boolean isFriendly()
	{
		return friendly;
	}
	
	public void setHealth(float newHealth)
	{
		health = newHealth;
	}
	
	public void setX(float newX)
	{
		x = newX;
	}
	
	public void setY(float newY)
	{
		y = newY;
	}
	
	public void setLocation(float[] newLocation)
	{
		location = newLocation;
	}

	public void setColor(Color newColor)
	{
		tankColor = newColor;
	}

	public void setSize(float newSize) 
	{
		size = newSize;
	}

	public void isVertical(boolean newValue) 
	{
		up = newValue;
	}

	public void isFriendly(boolean newValue) 
	{
		friendly = newValue;
	}

	public void setVelocity(float newVelocity) 
	{
		velocity = newVelocity;
	}
	
	public List<Projectile> getActiveRounds()
	{
		return activeRounds;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
	}

}
