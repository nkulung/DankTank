package main;

import processing.core.PApplet;

public class ProcessingMain extends PApplet
{
	private static final int WINDOW_WIDTH = 2000;	// initializing the window width
	private static final int WINDOW_HEIGHT = 1200;	// initializing the window height
	private static final int FPS = 60;				// initializing the frames per second
	
	private static final int S_KEY = 115;			// initializing the "S" key code
	private static final int D_KEY = 100;			// initializing the "D" key code
	private static final int W_KEY = 119;			// initializing the "W" key code
	private static final int A_KEY = 97;			// initializing the "A" key code
	
	private static final int SCREEN_EDGE_TOP = 400;		// top edge coordinate
	private static final int SCREEN_EDGE_BOTTOM = 800;	// bottom edge coordinate
	private static final int SCREEN_EDGE_LEFT = 400;	// left edge coordinate
	private static final int SCREEN_EDGE_RIGHT = 1000;	// right edge coordinate
	
	private boolean menu;							// declaring the "menu open" value
	
	Player user;									// declaring player tank object
	World map;										// declaring world object

	public static void main(String[] args) 
	{
		PApplet.main("main.ProcessingMain");
	}
	
	/**
	 * Settings method where window size is set,
	 * Called before sketch is set up.
	 */
	public void settings()
	{
		size(WINDOW_WIDTH, WINDOW_HEIGHT);			// sets the window size to parameters
	}
	
	/**
	 * Setup method runs once at the start of the program
	 * to define initial environment properties.
	 */
	public void setup()
	{
		frameRate(FPS);								// sets the desired frames per second (default is 60)
		user = new Player();						// creates new Player 
		map = new World(user.getLocation());		// creates a new World with the player location as the parameter
		menu = false;								// menu is closed at program start
	}
	
	/**
	 * Draw method that gets called for every frame,
	 * (ie. 60 fps calls the draw function 60 times in 1 second)
	 */
	public void draw()
	{
		background(119, 119, 119);					// paints the background grey
		drawMap();									// draws the world first
		drawPlayer();								// then draws the player over the world
		checkMouse();								// checks mouse location and acts accordingly
		drawUI();									// then draws the User Interface to the window
		if(menu == false)
		{
			drawData();						
		}
		else if(menu == true)
		{
			drawMenu();
		}
		fill(0);
	}
	
	/**
	 * Method to draw the players tank when called
	 */
	void drawPlayer()
	{
	  fill(0);
	  
	  if(user.getOrientation() == false)
	  {
	    rect(user.getX(), user.getY(), 120, 80);
	    fill(160, 160, 160);
	    rect(user.getX() - 5, user.getY() - 5, 130, 20);
	    rect(user.getX() - 5, user.getY() + 80, 130, 20);
	    ellipse(user.getX() + 60, user.getY() + 45, 40, 40);
	  }
	  else
	  {
	    rect(user.getX(), user.getY(), 80, 120); 
	    fill(160, 160, 160);
	    rect(user.getX() - 5, user.getY() - 5, 20, 130);
	    rect(user.getX() + 80, user.getY() -5, 20, 130);
	    ellipse(user.getX() + 45, user.getY() + 60, 40, 40);
	  }
	  
	}
	
	/**
	 * Method to check the mouses location
	 */
	void checkMouse()
	{
		if(mouseX < 1400)
		{
			cursor(CROSS);
			if(user.getOrientation() == false)
			{
				line(user.getX() + 60, user.getY() + 40, mouseX, mouseY);
			}
			else
			{
				line(user.getX() + 40, user.getY()+ 60, mouseX, mouseY);
			}
		}
		else
		{
			cursor(HAND);
		}
	}
	
	/**
	 * Method to draw the user interface
	 */
	void drawUI()
	{
		final int LEFT_WALL = 1400;
	  fill(112, 109, 99);
	  rect(LEFT_WALL, 0, 600, WINDOW_HEIGHT);
	  
	  fill(125, 93, 6);
	  noStroke();
	  //rect(0, 0, 1400, 20);
	  //rect(0, 0, 20, 1200);
	  //rect(0, 1180, 1400, 20);
	  rect(LEFT_WALL, 0, 20, WINDOW_HEIGHT);
	  rect(LEFT_WALL, 0, 600, 20);
	  rect(LEFT_WALL, 1180, 600, 20);
	  rect(LEFT_WALL + 580, 0, 20, 1200);
	  rect(LEFT_WALL + 40, 50, 520, 520);
	  
	  stroke(0);
	  for(int i = 0; i < map.getDimension(); i++)			// for loops to run through and draw the minimap with player location
	  {
		  for(int j = 0; j < map.getDimension(); j++)
		  {
			  fill(255, 255, 255);
			  if(i == map.getPlayerLocation()[0] && j == map.getPlayerLocation()[1])
			  {
				  fill(0, 255, 0);
			  }
			  rect(1450+(i*10), 60+(j*10), 10, 10);
		  }
	  }

	}
	
	/**
	 * Method to draw the user data
	 */
	void drawData()
	{
		pushStyle();					// saves the current style
		stroke(0);
		
		fill(255, 0, 0);				// draws the red health bar 
		rect(1500, 600, 400, 50, 20);
		
		fill(0, 255, 0);				// draws the green health bar over the red
		rect(1500, 600, 400, 50, 20); 
		
		fill(255);
		textSize(32);					// draws the framerate to interface
		text(frameRate, 1450, 700);
		text("User X: " + user.getX(), 1450, 750);
		text("User Y: " + user.getY(), 1700, 750);
		text("Map X: " + map.getPlayerLocation()[0], 1450, 850);
		text("Map Y: " + map.getPlayerLocation()[1], 1700, 850);
		
		drawButton("Menu", 1600, 1100);
		
		popStyle();						// restores the previous style
	}
	
	/**
	 * Method to draw the menu
	 */
	void drawMenu()
	{
		pushStyle();			// saves the current style
		drawButton("Back", 1450, 1100);
		drawButton("exit", 1700, 1100);
		
		popStyle();				// restores the previous style
	}
	
	/**
	 * Method to draw the map 
	 */
	void drawMap()
	{
		pushStyle();				// saves the current style
		stroke(0);
		
		for(int i = 0; i < map.getDimension(); i++)
		{
			for(int j = 0; j < map.getDimension(); j++)
			{
				int unitX = map.getMap()[i][j].getX();		// gets the x coordinate of the current unit on map
				int unitY = map.getMap()[i][j].getY();		// gets the y coordinate of the current unit on map
				
				fill(255, 255, 255);						// default fill the rectangle unit with white
				
				if((unitX > user.getLocation()[0] - 55 && unitX < user.getLocation()[0] + 55)		
						&& (unitY > user.getLocation()[1] - 55 && unitY < user.getLocation()[1] + 55))
				{
					fill(0, 255, 0);
					int[] newLocation = new int[2];
					newLocation[0] = i;
					newLocation[1] = j;
					map.setPlayerLocation(newLocation);
				}
				rect(unitX, unitY, 100, 100);
			}
		}
		popStyle();
	}
	
	
	/**
	 * Method called when mouse is clicked
	 */
	public void mouseClicked()
	{
		if(menu == false)
		{
			if((mouseX > 1600 && mouseX < 1800) && (mouseY > 1100 && mouseY < 1150))
			{
				menu = true;
			}
		}
		else
		{
			if((mouseX > 1450 && mouseX < 1650) && (mouseY > 1100 && mouseY < 1150))
			{
				menu = false;
			}
			if((mouseX > 1700 && mouseX < 1900) && (mouseY > 1100 && mouseY < 1150))
			{
				exit();
			}
		}
	}
	
	
	
	
	
	/**
	 * Method called when a key is pressed
	 */
	public void keyPressed()
	{
	 // println("pressed " + key + " " + keyCode); 
	 
	 // move down if "S" key is pressed
	 if (key == S_KEY)
	 {
	   user.setOrientation(true);		// set orientation to "true" because the tank will be oriented parallel to the y-axis
	   
	   if(user.getY() < SCREEN_EDGE_BOTTOM)				// if user is above the bottom "soft" barrier
	   {
		   user.setY(user.getY() + user.getVelocity());	// the tank will move on the map without moving the screen
	   }
	   else												// otherwise	
	   {
		   if(map.getPlayerLocation()[1] < 49)			// if the player is not at the edge of the map
		   {
			   for(int i = 0; i < map.getDimension(); i++)			// shift the whole map under the player n
			   {
				   for(int j = 0; j < map.getDimension(); j++)
				   {
					   map.getMap()[i][j].setY(map.getMap()[i][j].getY() - user.getVelocity());
				   }
			   }
		   }
	   }
	 }
	 
	 // move right if "D" key is pressed
	 if (key == D_KEY)
	 {
	   user.setOrientation(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
	   
	   if(user.getX() < SCREEN_EDGE_RIGHT)
	   {
		   user.setX(user.getX() + user.getVelocity()); 
	   }
	   else
	   {
		   if(map.getPlayerLocation()[0] < 49)
		   {
			   for(int i = 0; i < map.getDimension(); i++)
			   {
				   for(int j = 0; j < map.getDimension(); j++)
				   {
					   map.getMap()[i][j].setX(map.getMap()[i][j].getX() - user.getVelocity());
				   }
			   }
		   }
	   }
	 }
	 
	 // move up if "W" key is pressed
	 if (key == W_KEY)
	 {
	   user.setOrientation(true);		// set orientation to "true" because the tank will be oriented parallel to the y-axis
	   
	   if(user.getY() > SCREEN_EDGE_TOP)
	   {
		   user.setY(user.getY() - user.getVelocity());
	   }
	   else
	   {
		   if(map.getPlayerLocation()[1] > 0)
		   {
			   for(int i = 0; i < map.getDimension(); i++)
			   {
				   for(int j = 0; j < map.getDimension(); j++)
				   {
					   map.getMap()[i][j].setY(map.getMap()[i][j].getY() + user.getVelocity());
				   }
			   }
		   }
	   }
	 }
	 
	 // move left if "A" key is pressed
	 if (key == A_KEY)
	 {
	   user.setOrientation(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
	   
	   if(user.getX() > SCREEN_EDGE_LEFT)
	   {
		   user.setX(user.getX() - user.getVelocity());
	   }
	   else
	   {
		   if(map.getPlayerLocation()[0] > 0)
		   {
			   for(int i = 0; i < map.getDimension(); i++)
			   {
				   for(int j = 0; j < map.getDimension(); j++)
				   {
					   map.getMap()[i][j].setX(map.getMap()[i][j].getX() + user.getVelocity());
				   }
			   }
		   }
	   }
	 }
	}

	/**
	 * Method called when a keyboard key is typed 
	 * (pressed and released).
	 */
	public void keyTyped()
	{
	  // println("typed " + key + " " + keyCode);
	}

	/**
	 * Method called when a keyboard key in released
	 */
	public void keyReleased()
	{
	  // println("released " + key + " " + keyCode);
	}
	
	/**
	 * Private method to draw standard button rather than 
	 * retyping the same lines.
	 */
	private void drawButton(String label, int x, int y)
	{
		pushStyle();
		fill(125, 93, 6);
		noStroke();
		rect(x, y, 200, 50, 10);
		fill(255);
		text(label, x + 50, y + 35);
		popStyle();
	}

}
