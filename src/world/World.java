package world;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;

import entities.Box;
import main.ApplicationConstants;
import main.Mode;
import processing.core.PApplet;

/**
 * This class represents the world in the DankTank game
 * @author 
 */

public class World implements ApplicationConstants
{
	PApplet app;
	
	float WIN_WIDTH;
	float WIN_HEIGHT;
	float RENDER_ORIGIN_X;
	float RENDER_ORIGIN_Y;
	float RENDER_WIDTH;
	float RENDER_HEIGHT;
	float UI_WIDTH;
	
	Tile[][] tileCollection;
	
	private int playerX;
	private int playerY;
	
	Box renderBox;
	
	/**
	 * Constructor for world class
	 */
	public World(PApplet theApp, int playerX, int playerY)
	{
		app = theApp;
		
		WIN_WIDTH = app.width;
		WIN_HEIGHT = app.height;
		
		UI_WIDTH = WIN_WIDTH/4;
		RENDER_ORIGIN_X = (WIN_WIDTH - UI_WIDTH)/2;
		RENDER_ORIGIN_Y = WIN_HEIGHT/2;
		RENDER_WIDTH = WIN_WIDTH - UI_WIDTH;
		RENDER_HEIGHT = WIN_HEIGHT;
		
		tileCollection = new Tile[WORLD_DIMENSION][WORLD_DIMENSION];
		renderBox = new Box(theApp, RENDER_ORIGIN_X, RENDER_ORIGIN_Y, RENDER_WIDTH, RENDER_HEIGHT, new Color(0, 255, 0, 102));
		this.playerX = playerX;
		this.playerY = playerY;
		initialize();
	}
	
	/**
	 * Method to initialize the world map
	 */
	private void initialize()
	{
		for(int i = 0; i < WORLD_DIMENSION; i++)
		{
			for(int j = 0; j < WORLD_DIMENSION; j++)
			{
				tileCollection[j][i] = new Tile(app, j*TILE_SIZE, i*TILE_SIZE, TILE_SIZE);
			}
		}
		try 
		{
			readMapData();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to get the 2D arraylist used for the world map
	 */
	public Tile[][] getWorld()
	{
		return tileCollection;
	}
	
	/**
	 * Method to get the rendering Box
	 */
	public Box getRenderBox()
	{
		return renderBox;
	}
	
	/**
	 * Method to return the tile at the given coordinate
	 * @return indexedTile
	 */
	public Tile getTile(int someX, int someY)
	{
		return tileCollection[someX][someY];
	}
	
	/**
	 * Method to draw the world
	 */
	public void draw(Mode mode)
	{
		for(int i = 0; i < WORLD_DIMENSION; i++)
		{
			for(int j = 0; j < WORLD_DIMENSION; j++)
			{
				if(renderBox.isInside(tileCollection[j][i].getCollisionBox()))
					tileCollection[j][i].render(true);
				else
					tileCollection[j][i].render(false);
				tileCollection[j][i].draw(mode);
			}
		}
	}
	
	/**
	 * Method to draw top layer graphics on top
	 */
	public void drawTopLayer()
	{
		for(int i = 0; i < WORLD_DIMENSION; i++)
		{
			for(int j = 0; j < WORLD_DIMENSION; j++)
			{
				if(renderBox.isInside(tileCollection[j][i].getCollisionBox()))
				{
					tileCollection[j][i].render(true);
					tileCollection[j][i].drawTileWall();
				}
				else
					tileCollection[j][i].render(false);
			}
		}
	}
	
	/**
	 * Method to get the player x axis location in the world
	 * @return playerX
	 */
	public int getPlayerX()
	{
		return playerX;
	}
	
	/**
	 * Method to get the player y axis location in the world
	 * @return playerY
	 */
	public int getPlayerY()
	{
		return playerY;
	}
	
	/**
	 * Method to set the players x axis location on world map
	 */
	public void setPlayerX(int newPlayerX)
	{
		playerX = newPlayerX;
	}
	
	/**
	 * Method to set the players y axis location on world map
	 */
	public void setPlayerY(int newPlayerY)
	{
		playerY = newPlayerY;
	}
	
	/**
	 * Method to move the player up by shifting world down
	 * @param speed
	 */
	public void playerUp(float speed)
	{
		if(playerY > 0)
		{
			for(int i = 0; i < WORLD_DIMENSION; i++)
			{
					for(int j = 0; j < WORLD_DIMENSION; j++)
				    {
						tileCollection[j][i].setY(tileCollection[j][i].getY() + speed);
				    }
			   }
		   }
	}
	
	/**
	 * Method to move the player down by shifting the world up
	 * @param speed
	 */
	public void playerDown(float speed)
	{
		if(playerY < WORLD_DIMENSION - 1)
		{
			for(int i = 0; i < WORLD_DIMENSION; i++)
			{
				for(int j = 0; j < WORLD_DIMENSION; j++)
				{
					tileCollection[j][i].setY(tileCollection[j][i].getY() - speed);
				}
			}
		}
	}
	
	/**
	 * Method to move the player right by shifting the world left
	 * @param speed
	 */
	public void playerRight(float speed)
	{
		if(playerX < WORLD_DIMENSION - 1)
		{
			for(int i = 0; i < WORLD_DIMENSION; i++)
			{
				for(int j = 0; j < WORLD_DIMENSION; j++)
				{
					tileCollection[j][i].setX(tileCollection[j][i].getX() - speed);
				}
			}
		}
	}
	
	/**
	 * Method to move the player left by shifting the world right
	 * @param speed
	 */
	public void playerLeft(float speed)
	{
		if(playerX > 0)
		{
			for(int i = 0; i < WORLD_DIMENSION; i++)
			{
				for(int j = 0; j < WORLD_DIMENSION; j++)
				{
					tileCollection[j][i].setX(tileCollection[j][i].getX() + speed);
				}
			}
		}
	}
	
	/**
	 * private method to read the map data from the .txt file
	 * @throws IOException 
	 */
	public void readMapData() throws IOException
	{
		BufferedReader reader = app.createReader("mapTextures.txt");
		String line = " ";
		int counter = 0;
		while(line != null)
		{
			try {								// try to read a line
			    line = reader.readLine();
			  } catch (IOException e) {
			    e.printStackTrace();			// error if no line
			    line = null;
			  }
			  if (line == null) {  				// if the line contains null
			    reader.close();					// close the reader
			  } else {
			    String[] pieces = PApplet.split(line, ",");				// else take the line, parse into string array
			    for(int i = 0; i < pieces.length - 1; i++)						// loop through length of array
			    {
			    	tileCollection[i][counter].setTileType(pieces[i].toString());	// add the tile data to corresponding tile
			    }
			    counter++;
			  }
		}
	}
}
