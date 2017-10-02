package main;

import java.awt.Color;

/**
 * This class represents the world in the DankTank game
 * @author 
 */

public class World 
{
	private static final int DIMENSION = 50;
	private static final int UNIT_SIZE = 100;
	
	Unit[][] map;
	private int[] playerLoc;
	
	/**
	 * Constructor for world class
	 */
	public World(int[] playerCoordinate)
	{
		map = new Unit[DIMENSION][DIMENSION];
		playerLoc = playerCoordinate;
		initialize();
	}
	
	/**
	 * Method to initialize the world map
	 */
	private void initialize()
	{
		for(int i = 0; i < DIMENSION; i++)
		{
			for(int j = 0; j < DIMENSION; j++)
			{
				map[i][j] = new Unit(i*UNIT_SIZE, j*UNIT_SIZE);
			}
		}
	}
	
	/**
	 * Method to get the map 2D array
	 * @return map
	 */
	public Unit[][] getMap()
	{
		return map;
	}
	
	/**
	 * Method to get the map dimension
	 * @return DIMENSION
	 */
	public int getDimension()
	{
		return DIMENSION;
	}
	
	/**
	 * Method to print world map to console
	 */
	public void printMap()
	{
		for(int i = 0; i < DIMENSION; i++)
		{
			for(int j = 0; j < DIMENSION; j++)
			{
				System.out.print("[" + map[i][j].getX() + "," + map[i][j].getY() + "], ");
			}
			System.out.print("\n");
	
		}
	}
	
	/**
	 * Method to get the player location as an integer array
	 * @return playerLoc
	 */
	public int[] getPlayerLocation()
	{
		return playerLoc;
	}
	
	/**
	 * Method to set the player location on the world map
	 */
	public void setPlayerLocation(int[] newLocation)
	{
		playerLoc = newLocation;
	}
	
	
//=================================================================================
//									Unit Class
//=================================================================================
	/**
	 * This class represents a single unit on the map
	 */
	public class Unit
	{
		private int x;				// unit x coordinate (top left)
		private int y;				// unit y coordinate (top left)
		private Color unitColor;	// unit color
		
		/**
		 * Default constructor for unit class
		 */
		public Unit()
		{
			x = 0;
			y = 0;
		}
		
		/**
		 * Unit class constructor takes the x and y coordinates as parameters
		 * @param newX
		 * @param newY
		 */
		public Unit(int newX, int newY)
		{
			x = newX;
			y = newY;
		}
		
		/**
		 * Method to return the x coordinate
		 * @return x
		 */
		public int getX()
		{
			return x;
		}
		
		/**
		 * Method to return the y coordinate
		 * @return y
		 */
		public int getY()
		{
			return y;
		}
		
		/**
		 * Method to set the x coordinate to the input
		 * @param newX
		 */
		public void setX(int newX)
		{
			x = newX;
		}
		
		/**
		 * Method to set the y coordinate to the input
		 * @param newY
		 */
		public void setY(int newY)
		{
			y = newY;
		}
		
	}

}
