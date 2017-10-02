package main;

import java.awt.Color;

public class Player extends Tank
{
	private static final int TANK_SIZE = 120;
	
	private int health;						// player health
	private int x;							// player x coordinate
	private int y;							// player y coordinate
	private int velocity;					// player speed
	private boolean up;						// player orientation
	private boolean friendly;				// used to calculate friendly fire (avoid hitting yourself)
	private int[] location = new int[2];	// coordinates for unit(block) player is inside
	private Color tankColor;				// tank primary color 
	private int size;						// tank dimension (size x size) not including tank tread
	private int currency;					// player currency
	
	
	/**
	 * Default Constructor
	 */
	public Player()
	{
		health = 100;
		currency = 50;
		x = 700;
		y = 600;
		velocity = 25;
		up = true;
		location[0] = (x/100);
		location[1] = (y/100);
		tankColor = new Color(0);
		size = TANK_SIZE;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getVelocity()
	{
		return velocity;
	}
	
	public boolean isVertical()
	{
		return up;
	}
	
	public int[] getLocation()
	{
		return location;
	}
	
	public Color getColor()
	{
		return tankColor;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public boolean isFriendly() 
	{
		return friendly;
	}
	
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public void setLocation(int[] newLocation)
	{
		location = newLocation;
	}

	public void setColor(Color newColor)
	{
		tankColor = newColor;
	}

	public void setSize(int newSize) 
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

	public void setVelocity(int newVelocity) 
	{
		velocity = newVelocity;
	}
	
//========================================================
//					Methods Unique to Player
//========================================================
	/**
	 * Method to get the player currency
	 * @return currency
	 */
	public int getCurrency()
	{
		return currency;
	}
	
	/**
	 * Method to set the player currency
	 * @param newCurrency
	 */
	public void setCurrency(int newCurrency)
	{
		currency = newCurrency;
	}

}
