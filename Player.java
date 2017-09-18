package main;

public class Player 
{
	// Constants
	
	private int health;		// player health
	private int currency;	// player currency
	private int x;			// player x coordinate
	private int y;			// player y coordinate
	private int velocity;	// player speed
	private boolean up;		// player orientation
	private int[] location = new int[2];	// coordinates for block player is inside
	
	/**
	 * Default constructor given no parameters
	 */
	public Player()
	{
		health = 100;
		currency = 0;
		x = 700;
		y = 600;
		velocity = 15;
		up = true;
		location[0] = (x/100);
		location[1] = (y/100);
	}
	
	/**
	 * Constructor given x and y coordinates
	 * @param someX
	 * @param someY
	 */
	public Player(int someX, int someY)
	{
		health = 100;
		currency = 0;
		x = someX;
		y = someY;
		velocity = 5;
		up = true;
		location[0] = (x/100);
		location[1] = (y/100);
	}
	
	/**
	 * Method to get the player health
	 * @return health
	 */
	public int getHealth()
	{
		return health;
	}
	
	/**
	 * Method to get the player currency
	 * @return currency
	 */
	public int getCurrency()
	{
		return currency;
	}
	
	/**
	 * Method to get the players x coordinate
	 * @return x
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * Method to get the players y coordinate
	 * @return y
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * Method to get the players velocity
	 * @return velocity
	 */
	public int getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Method to get the player orientation
	 * @return up
	 */
	public boolean getOrientation()
	{
		return up;
	}
	
	/**
	 * Method to get the player coordinate location
	 * @return location
	 */
	public int[] getLocation()
	{
		return location;
	}
	
	/**
	 * Method to set the player health
	 * @param newHealth
	 */
	public void setHealth(int newHealth)
	{
		health = newHealth;
	}
	
	/**
	 * Method to set the player currency
	 * @param newCurrency
	 */
	public void setCurrency(int newCurrency)
	{
		currency = newCurrency;
	}
	
	/**
	 * Method to set the player x coordinate
	 * @param newX
	 */
	public void setX(int newX)
	{
		x = newX;
		location[0] = x;
	}
	
	/**
	 * Method to set the player y coordinate
	 * @param newY
	 */
	public void setY(int newY)
	{
		y = newY;
		location[1] = y;
	}
	
	/**
	 * Method to set the player orientation
	 * @param newUp
	 */
	public void setOrientation(boolean newUp)
	{
		up = newUp;
	}
	
	/**
	 * Method to set the player location
	 * @param newLocation
	 */
	public void setLocation(int[] newLocation)
	{
		location = newLocation;
	}

}
