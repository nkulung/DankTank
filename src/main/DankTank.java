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

import entities.Button;
import entities.Projectile;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import world.World;

public class DankTank extends PApplet implements ApplicationConstants
{
	private static final int FPS = 400;				// initializing the frames per second
	
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
	
	private long frame = 0L;
	
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
	private boolean animate;
	
	private Player user;									// declaring player tank object
	private World gameWorld;										// declaring world object
	
	private Mode mode;
	
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
		
		mode = Mode.NO_MODE;
		
		currencyIcon = loadImage("coins.png");
		standardAmmoIcon = loadImage("standardAmmo.png");
		burnAmmoIcon = loadImage("burn.png");
		grassBlock = loadImage("grassBlock.png");
		
		speedIcon = loadImage("turboIcon.png");
		armorIcon = loadImage("shield_blue.png");
		
		
		user = new Player(this, 400, 500);						// creates new Player 
		gameWorld = new World(this, user.getLocation());		// creates a new World with the player location as the parameter
		menu = false;								// menu is closed at program start
		help = false;
		data = false;
		animate = true;
		
		scaleFrame();								// create dimensions based on monitor width & height
		createButtons();							// instantiate all button objects
	}
	
	/**
	 * Draw method that gets called for every frame,
	 * (ie. 60 fps calls the draw function 60 times in 1 second)
	 */
	public void draw()
	{
		frame++;
		if(frame % 1 == 0)
		{
			background(GRASS_COLOR.getRGB());			// paints the background grey
			
			gameWorld.draw(mode);									// draws the world first
			drawUI();									// then draws the User Interface to the window
			updateComponents();
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
			
			fill(0);
			for(int i = 0; i < user.getActiveRounds().size(); i++)	// for every projectile in the arraylist of active projectiles
			{
				if(user.getActiveRounds().get(i).isActive())
				{
					user.getActiveRounds().get(i).draw();
					if(mode == Mode.COLLISION_BOX)
						user.getActiveRounds().get(i).getCollisionBox().draw();
				}
				else
					user.getActiveRounds().remove(i);
			}
			user.draw(mode);								// then draws the player over the world
		}
		
		if(animate)											// if the game is not paused
		{ 
			for(Projectile projectile: user.getActiveRounds())	// for every projectile in the arraylist of active projectiles
			{
				projectile.animate();						// animate that projectile
			}
			
		}
		
		if(mode == Mode.COLLISION_BOX)
		{
			gameWorld.getRenderBox().draw();
			user.getCollisionBox().draw();
		}
		else if(mode == Mode.DATA_MODE)
			drawPerformance();
		
		check();
	}		
	
	/**
	 * Method to check the mouses location
	 */
	private void check()
	{
		if(mouseX < MENU_WALL)
		{
			cursor(CROSS);
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
		pushStyle();
		rectMode(CORNER);
		fill(112, 109, 99);						// fill with grey and draw UI background
	    rect(MENU_WALL, 0, UI_WIDTH, WIN_HEIGHT);
	  
	    fill(125, 93, 6);						// fill with brown and draw borders
	    noStroke();
	    rect(MENU_WALL, 0, BORDER_WIDTH, WIN_HEIGHT);			// left menu border
	    rect(0, 0, BORDER_WIDTH, WIN_HEIGHT);
	    rect(MENU_WALL, 0, UI_WIDTH, BORDER_WIDTH);		// top menu border
	    rect(0, WIN_HEIGHT - BORDER_WIDTH, WIN_WIDTH, BORDER_WIDTH);		// entire bottom window border
	    rect(0, 0, MENU_WALL, BORDER_WIDTH);
	    rect(WIN_WIDTH - BORDER_WIDTH, 0, BORDER_WIDTH, WIN_HEIGHT);		// right menu border
	    
	    int mapWidth = (int) ((WIN_WIDTH - MENU_WALL) - 120);
	    rect(MENU_WALL + 70, 50, mapWidth, mapWidth);
	  
	    stroke(0);
	    int mapSize = (int) ((mapWidth - BORDER_WIDTH) / 50);
	    for(int i = 0; i < WORLD_DIMENSION; i++)			// for loops to run through and draw the minimap with player location
	    {
		    for(int j = 0; j < WORLD_DIMENSION; j++)
		    {
			    if(gameWorld.getTile(j, i).isInside(user.getX(), user.getY()))
			    {
			    	gameWorld.setPlayerX(j);
			    	gameWorld.setPlayerY(i);
			    	fill(0);
			    }
			    else
			    	fill(gameWorld.getTile(j, i).getTileColor().getRGB());
			    rect((MENU_WALL + 80) + (j*mapSize), 60 + (i*mapSize), mapSize, mapSize);
		    }
	    }
	    fill(0);
	    textSize(20);
	    text("fps: " + frameRate, MENU_WALL + 30, WIN_HEIGHT/2 - 30);
	    popStyle();
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
		text(" " + user.getStandardProjectileRounds(), MENU_WALL + (UI_WIDTH/4), UI_row2);
		
		
		menuButton.draw();
		exitButton.draw();
		
		
		popStyle();						// restores the previous style
	}
	
	/**
	 * Method to draw the menu
	 */
	void drawMenu()
	{
		pushStyle();			// saves the current style
		
		blackButton.draw();
		redButton.draw();
		greenButton.draw();
		blueButton.draw();
		backButton.draw();
		helpButton.draw();
		
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
		
		backButton.draw();
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
		text("Map X: " + gameWorld.getPlayerX(), col1, 200);
		text("Map Y: " + gameWorld.getPlayerY(), col2, 200);
		text("Barrel Angle: " + user.getAngle(), col1, 250);
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
		if(mouseX > 0 && mouseX < MENU_WALL)
		{
			user.shoot();
		}
		else if(!help && mouseX > MENU_WALL)
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
		 if(mode == Mode.NO_MODE)
			 mode = Mode.DATA_MODE;
		 else if(mode == Mode.COLLISION_BOX)
			 mode = Mode.DATA_MODE;
		 else if(mode == Mode.DATA_MODE)
			 mode = Mode.NO_MODE;
	 }
	 if(key == "c".charAt(0))
	 {
		 if(mode == Mode.NO_MODE)
			 mode = Mode.COLLISION_BOX;
		 else if(mode == Mode.DATA_MODE)
			 mode = Mode.COLLISION_BOX;
		 else if(mode == Mode.COLLISION_BOX)
			 mode = Mode.NO_MODE;
			 
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
		   gameWorld.playerDown(user.getVelocity());
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
		   gameWorld.playerRight(user.getVelocity());
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
		   gameWorld.playerUp(user.getVelocity());
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
		   gameWorld.playerLeft(user.getVelocity());
	   }
	 }
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
	
	private void updateComponents()
	{

	}
	
	
	
//===========================================================
//				   Initialization Methods
//===========================================================
	private void scaleFrame()
	{
		
		SCREEN_EDGE_TOP = height/4;
		SCREEN_EDGE_BOTTOM = 3 * (height/4);
		SCREEN_EDGE_LEFT = width/4;
		SCREEN_EDGE_RIGHT = width/2;
		
		UI_column0 = (int) (MENU_WALL + (UI_WIDTH/4));
		UI_column1 = (int) (MENU_WALL + (BORDER_WIDTH * 2));
		UI_column2 = (int) (UI_column1 + (UI_WIDTH/2));
		
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
		menuButton = new Button(this, "Menu", UI_column1, UI_row8);
		exitButton = new Button(this, "End Game", UI_column2, UI_row8);
		backButton = new Button(this, "Back", UI_column1, UI_row8);
		helpButton = new Button(this, "Help", UI_column2, UI_row8);
		blackButton = new Button(this, " ", UI_column1, UI_row1, new Color(0));
		redButton = new Button(this, " ", UI_column1, UI_row2, new Color(255, 0, 0));
		greenButton = new Button(this, " ", UI_column1, UI_row3, new Color(0, 255, 0));
		blueButton = new Button(this, " ", UI_column1, UI_row4, new Color(0, 0, 255));
	}
	
//=======================================================================
//									Main
//=======================================================================
	public static void main(String[] args) 
	{
		PApplet.main("main.DankTank");
	}

}
