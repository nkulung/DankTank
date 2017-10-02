package main;
/**
 * Main class for Dank Tank game
 * CSC 305: Software Engineering Fall '17
 * @author Nate Larson   (fullscreen 3200 x 1800)
 * @author Gabe Le
 * @author Nick Kulungian
 * @author Ronil Soto
 * @author Kevin Andrade
 */
import java.awt.Color;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class ProcessingMain extends PApplet
{
	private static final int FPS = 60;				// initializing the frames per second
	
	private static final int S_KEY = 115;			// initializing the "S" key code
	private static final int D_KEY = 100;			// initializing the "D" key code
	private static final int W_KEY = 119;			// initializing the "W" key code
	private static final int A_KEY = 97;			// initializing the "A" key code
	
	// Player window variables
	private int SCREEN_EDGE_TOP;		// top edge coordinate
	private int SCREEN_EDGE_BOTTOM;		// bottom edge coordinate
	private int SCREEN_EDGE_LEFT;		// left edge coordinate
	private int SCREEN_EDGE_RIGHT;		// right edge coordinate
	
	private PFont FLOAT_FONT;
	
	// User Interface variables
	private int BORDER_WIDTH;						// variable to represent width of borders
	private int UI_WIDTH;
	private int MENU_WALL;							// menu border between player window and menu
	
	// UI column and row coordinates
	private int UI_column0,
				UI_column1,
				UI_column2;
	private int UI_row0,
				UI_row1,
				UI_row2,
				UI_row3,
				UI_row4,
				UI_row5,
				UI_row6,
				UI_row7,
				UI_row8;
	
	private float angle;			// angle of the tank barrel, based on mouse location
	
	PImage currencyIcon;
	PImage standardAmmoIcon;
	PImage burnAmmoIcon;
	PImage grassBlock;
	PImage heart;
	PImage speedIcon;
	PImage armorIcon;
	PImage damageIcon;
	
	
	// Button instantiation
	private Button menuButton;
	private Button backButton;
	private Button exitButton;
	private Button helpButton;
	private Button blackButton;
	private Button redButton;
	private Button greenButton;
	private Button blueButton;
	
	
	
	private boolean menu;							// declaring the "main menu open" value
	private boolean help;							// declaring the "help menu open" value
	private boolean data;							// declaring the "data visible" value
	
	private Player user;									// declaring player tank object
	private World map;										// declaring world object

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
		//size(1200, 800);			// sets the window size to parameters
		fullScreen();
	}
	
	/**
	 * Setup method runs once at the start of the program
	 * to define initial environment properties.
	 */
	public void setup()
	{
		frameRate(FPS);								// sets the desired frames per second (default is 60)
		
		FLOAT_FONT = createFont("BOLD", 40);
		
		currencyIcon = loadImage("coins.png");
		standardAmmoIcon = loadImage("standardAmmo.png");
		burnAmmoIcon = loadImage("burn.png");
		grassBlock = loadImage("grassBlock.png");
		
		speedIcon = loadImage("turboIcon.png");
		armorIcon = loadImage("shield_blue.png");
		
		
		user = new Player();						// creates new Player 
		map = new World(user.getLocation());		// creates a new World with the player location as the parameter
		menu = false;								// menu is closed at program start
		help = false;
		data = false;
		
		scaleFrame();								// create dimensions based on monitor width & height
		createButtons();							// instantiate all button objects
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
		if(!help)
		{
			if(!menu)
			{
				drawData();
			}
			else if(menu)
			{
				drawMenu();
			}
		}
		else if(help)
		{
			drawHelpMenu();
		}
		
		if(data)
		{
			drawPerformance();
		}
		fill(0);
	}
	
	/**
	 * Method to draw the players tank when called
	 */
	void drawPlayer()
	{
	  pushStyle();
	  fill(user.getColor().getRed(), user.getColor().getGreen(), user.getColor().getBlue());
	  rectMode(CENTER);
	  rect(user.getX(), user.getY(), user.getSize(), user.getSize());
	  fill(160, 160, 160);
	  if(user.isVertical() == false)
	  {
	    rect(user.getX(), user.getY() - (user.getSize()/2), user.getSize() + 20, 20);
	    rect(user.getX(), user.getY() + (user.getSize()/2), user.getSize() + 20, 20);
	    int x = user.getX() - (user.getSize()/2) - 10;
	    int y = user.getY() - (user.getSize()/2) - 10;
	    for(int i = 0; i <= (user.getSize() + 20)/20; i++)
	    {
	    	line(x, y, x, y + 20);
	    	line(x, y + user.getSize(), x, y + user.getSize() + 20);
	    	x += 20;
	    }
	  }
	  else
	  { 
	    rect(user.getX() - (user.getSize()/2), user.getY(), 20, user.getSize() + 20);
	    rect(user.getX() + (user.getSize()/2), user.getY(), 20, user.getSize() + 20);
	    int x = user.getX() - (user.getSize()/2) - 10;
	    int y = user.getY() - (user.getSize()/2) - 10;
	    for(int i = 0; i <= (user.getSize() + 20)/20; i++)
	    {
	    	line(x, y, x + 20, y);
	    	line(x + user.getSize(), y, x + user.getSize() + 20, y);
	    	y += 20;
	    }
	  }
	  ellipse(user.getX(), user.getY(), user.getSize()/2, user.getSize()/2);
	  calculateBarrelAngle();
	  //ellipse(user.getX(), user.getY(), radius, radius);
	  popStyle();
	  
	}
	
	/**
	 * Method to check the mouses location
	 */
	void checkMouse()
	{
		if(mouseX < MENU_WALL)
		{
			cursor(CROSS);
			line(user.getX(), user.getY(), mouseX, mouseY);
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
		fill(112, 109, 99);						// fill with grey and draw UI background
	    rect(MENU_WALL, 0, UI_WIDTH, height);
	  
	    fill(125, 93, 6);						// fill with brown and draw borders
	    noStroke();
	    rect(MENU_WALL, 0, BORDER_WIDTH, height);			// left menu border
	    rect(MENU_WALL, 0, UI_WIDTH, BORDER_WIDTH);		// top menu border
	    rect(0, height - BORDER_WIDTH, width, BORDER_WIDTH);		// entire bottom window border
	    rect(width - BORDER_WIDTH, 0, BORDER_WIDTH, height);		// right menu border
	    
	    int mapWidth = (width - MENU_WALL) - 120;
	    rect(MENU_WALL + 70, 50, mapWidth, mapWidth);
	  
	    stroke(0);
	    int mapSize = (mapWidth - BORDER_WIDTH) / 50;
	    for(int i = 0; i < map.getDimension(); i++)			// for loops to run through and draw the minimap with player location
	    {
		    for(int j = 0; j < map.getDimension(); j++)
		    {
			    fill(255, 255, 255);
			    if(i == map.getPlayerLocation()[0] && j == map.getPlayerLocation()[1])
			    {
				    fill(0, 255, 0);
			    }
			    rect((MENU_WALL + 80) + (i*mapSize), 60 + (j*mapSize), mapSize, mapSize);
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
		
		rectMode(CENTER);
		fill(255, 0, 0);				// draws the red health bar 
		rect(MENU_WALL + (UI_WIDTH/2), UI_row0, 500, 50, 20);
		
		fill(0, 255, 0);				// draws the green health bar over the red
		rect(MENU_WALL + (UI_WIDTH/2), UI_row0, 500, 50, 20); 
		rectMode(CORNER);
		
		fill(66, 64, 77);
		rect(UI_column1 - 10, UI_row1 - 10, 300, 70, 10);
		
		image(currencyIcon, UI_column1, UI_row1, 50, 50);
		rect(UI_column1 - 10, UI_row2 - 10, 300, 70, 10);
		image(standardAmmoIcon, UI_column1, UI_row2, 50, 50);
		
		fill(255);
		textSize((float)(width/100));
		textAlign(CENTER, TOP);
		text(" " + user.getCurrency(), MENU_WALL + (UI_WIDTH/4), UI_row1);
		
		
		drawButton(menuButton);
		drawButton(exitButton);
		
		
		popStyle();						// restores the previous style
	}
	
	/**
	 * Method to draw the menu
	 */
	void drawMenu()
	{
		pushStyle();			// saves the current style
		
		drawButton(blackButton);
		drawButton(redButton);
		drawButton(greenButton);
		drawButton(blueButton);
		drawButton(backButton);
		drawButton(helpButton);
		
		popStyle();				// restores the previous style
	}
	
	/**
	 * Method to draw the help menu
	 */
	void drawHelpMenu()
	{
		pushStyle();
		text("Use 'WASD' to move your tank", UI_column1, UI_row1);
		text("Press 'P' for performance", UI_column1, UI_row2);
		
		drawButton(backButton);
		popStyle();
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
				
				if((unitX > user.getX() - 75 && unitX < user.getX() + 45)		
						&& (unitY > user.getY() - 75 && unitY < user.getY() + 45))
				{
					fill(0, 255, 0);
					int[] newLocation = new int[2];
					newLocation[0] = i;
					newLocation[1] = j;
					map.setPlayerLocation(newLocation);
					user.setLocation(newLocation);
				}
				rect(unitX, unitY, 100, 100);
			}
		}
		popStyle();
	}
	
	/**
	 * Method to draw the performance data to the screen
	 */
	void drawPerformance()
	{
		int col1 = 30;
		int col2 = 330;
		
		pushStyle();
		fill(0);
		//rect(10, 10, 100, 100);
		textFont(FLOAT_FONT);
		text(frameRate, col1, 100);
		text("User X: " + user.getX(), col1, 150);
		text("User Y: " + user.getY(), col2, 150);
		text("Map X: " + map.getPlayerLocation()[0], col1, 200);
		text("Map Y: " + map.getPlayerLocation()[1], col2, 200);
		text("Barrel Angle: " + angle, col1, 250);
		text("Mouse X: " + mouseX, col1, 300);
		text("Mouse Y: " + mouseY, col2, 300);
		text("Screen Width: " + width, col1, 350);
		text("Screen Height: " + height, col1, 400);
		popStyle();
	}
	
	
	/**
	 * Method called when mouse is clicked
	 */
	public void mouseClicked()
	{
		if(!help)
		{
			if(!menu)
			{
				if(menuButton.isInside(mouseX, mouseY))
				{
					menu = true;
				}
				if(exitButton.isInside(mouseX, mouseY))
				{
					exit();
				}
			}
			else if(menu)
			{
				if(backButton.isInside(mouseX, mouseY))
				{
					menu = false;
				}
				if(helpButton.isInside(mouseX, mouseY))
				{
					help = true;
				}
				if(blackButton.isInside(mouseX, mouseY))
				{
					deSelect();
					blackButton.select(true);
					user.setColor(blackButton.getColor());
				}
				if(redButton.isInside(mouseX, mouseY))
				{
					deSelect();
					redButton.select(true);
					user.setColor(redButton.getColor());
				}
				if(greenButton.isInside(mouseX, mouseY))
				{
					deSelect();
					greenButton.select(true);
					user.setColor(greenButton.getColor());
				}
				if(blueButton.isInside(mouseX, mouseY))
				{
					deSelect();
					blueButton.select(true);
					user.setColor(blueButton.getColor());
				}
				
			}
		}
		else if(help)
		{
			if(backButton.isInside(mouseX, mouseY))
			{
				help = false;
				menu = true;
			}
		}
	}
	
	
	
	
	
	/**
	 * Method called when a key is pressed
	 */
	public void keyPressed()
	{
	 println("pressed " + key + " " + keyCode); 
	 
	 if (key == "p".charAt(0))
	 {
		 data = !data;
	 }
	 // move down if "S" key is pressed
	 if (key == S_KEY)
	 {
	   user.isVertical(true);		// set orientation to "true" because the tank will be oriented parallel to the y-axis
	   
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
	   user.isVertical(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
	   
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
	   user.isVertical(true);		// set orientation to "true" because the tank will be oriented parallel to the y-axis
	   
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
	   user.isVertical(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
	   
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
	 * Private method to draw standard button rather than 
	 * retyping the same lines.
	 */
	private void drawButton(Button someButton)
	{
		pushStyle();
		textAlign(CENTER);
		noStroke();
		
		if(someButton.selected())
		{
			fill(255);
			rect(someButton.getX() - (BORDER_WIDTH/2), someButton.getY() - (BORDER_WIDTH/2), 
					someButton.getWidth() + BORDER_WIDTH, someButton.getHeight() + BORDER_WIDTH, 10);
		}
		
		fill(someButton.getColor().getRed(), someButton.getColor().getGreen(), someButton.getColor().getBlue());
		rect(someButton.getX(), someButton.getY(), someButton.getWidth(), someButton.getHeight(), 10);
		
		fill(255);
		text(someButton.getLabel(), someButton.getX() + (someButton.getWidth()/2), someButton.getY() + (someButton.getHeight()/2));
		popStyle();
	}
	
	/**
	 * Private method to deselect all other tank colors
	 */
	private void deSelect()
	{
		blackButton.select(false);
		redButton.select(false);
		greenButton.select(false);
		blueButton.select(false);
	}
	
	/**
	 * Private method to calculate tank barrel angle
	 */
	private void calculateBarrelAngle()
	{
		pushMatrix();
		translate(user.getX(), user.getY());
		int x;
		int y;
		float radians;
		if(mouseX > user.getX())
		{
			x = mouseX - user.getX();
			if(mouseY > user.getY())		// Q4
			{
				y = mouseY - user.getY();
				radians = atan2(y, x);
				angle = 270 + (90 - degrees(radians));
			}
			else							// Q1
			{
				y = user.getY() - mouseY;
				radians = atan2(y, x);
				angle = degrees(radians);
			}
		}
		else
		{
			x = user.getX() - mouseX;
			if(mouseY > user.getY())		// Q3
			{
				y = mouseY - user.getY();
				radians = atan2(y, x);
				angle = 180 + degrees(radians);
			}
			else							// Q2
			{
				y = user.getY() - mouseY;
				radians = atan2(y, x);
				angle = 90 + (90 - degrees(radians));
			}
		}
		rotate(-radians(angle));
		fill(0);
		rectMode(CORNERS);
		rect(-10, -10, 100, 10);
		fill(160, 160, 160);
		rect(90, -15, 120, 15);
		rect(-20, -20, 20, 20);
		
		
		popMatrix();
	}
	
	
//===========================================================
//				   Initialization Methods
//===========================================================
	private void scaleFrame()
	{
		UI_WIDTH = width/4;
		BORDER_WIDTH = width/160;
		MENU_WALL = width - UI_WIDTH;
		
		SCREEN_EDGE_TOP = height/4;
		SCREEN_EDGE_BOTTOM = 3 * (height/4);
		SCREEN_EDGE_LEFT = width/4;
		SCREEN_EDGE_RIGHT = width/2;
		
		UI_column0 = MENU_WALL + (UI_WIDTH/4);
		UI_column1 = MENU_WALL + (BORDER_WIDTH * 2);
		UI_column2 = UI_column1 + (UI_WIDTH/2);
		
		UI_row0 = (height*4)/9;
		UI_row1 = (height*1)/2;
		UI_row2 = (height*5)/9;
		UI_row3 = (height*11)/18;
		UI_row4 = (height*2)/3;
		UI_row5 = (height*13)/18;
		UI_row6 = (height*7/9);
		UI_row7 = (height*5)/6;
		UI_row8 = (height*8)/9;
	}
	
	private void createButtons()
	{
		menuButton = new Button("Menu", UI_column1, UI_row8);
		exitButton = new Button("End Game", UI_column2, UI_row8);
		backButton = new Button("Back", UI_column1, UI_row8);
		helpButton = new Button("Help", UI_column2, UI_row8);
		blackButton = new Button(" ", UI_column1, UI_row1, new Color(0));
		redButton = new Button(" ", UI_column1, UI_row2, new Color(255, 0, 0));
		greenButton = new Button(" ", UI_column1, UI_row3, new Color(0, 255, 0));
		blueButton = new Button(" ", UI_column1, UI_row4, new Color(0, 0, 255));
	}
	
//=======================================================================
//						Projectile Animations
//=======================================================================
	private void explosiveAnimation()
	{
		
	}

}
