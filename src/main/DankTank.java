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
import java.util.ArrayList;

import javax.swing.JOptionPane;

import entities.Button;
import entities.Enemy;
import entities.Projectile;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import world.World;

public class DankTank extends PApplet implements ApplicationConstants
{
	private static final int FPS = 40;				// initializing the frames per second
	
	private static final int S_KEY = 115;			// initializing the "S" key code
	private static final int D_KEY = 100;			// initializing the "D" key code
	private static final int W_KEY = 119;			// initializing the "W" key code
	private static final int A_KEY = 97;			// initializing the "A" key code
	
	float WIN_WIDTH;
	float WIN_HEIGHT;
	
	float SCREEN_EDGE_TOP;
	float SCREEN_EDGE_BOTTOM;
	float SCREEN_EDGE_LEFT;
	float SCREEN_EDGE_RIGHT;
	
	// User Interface variables
	float UI_WIDTH;
	float MENU_WALL;
	float BORDER_WIDTH;
	
	float RENDER_ORIGIN_X;
	float RENDER_ORIGIN_Y;
	float RENDER_WIDTH;
	float RENDER_HEIGHT;
	
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
	PImage explosiveAmmoIcon;
	PImage scatterAmmoIcon;
	
	private Button standardButton;
	private Button explosiveButton;
	private Button fireButton;
	private Button scatterButton;
	
	// Button instantiation
	private Button menuButton;
	private Button backButton;
	private Button exitButton;
	private Button helpButton;
	private Button shopButton;
	private Button pauseButton;
	
	private boolean animate;
	
	private int roundCounter;
	
	private Player user;									// declaring player tank object
	private ArrayList<Enemy> enemyForces;
	private World gameWorld;										// declaring world object
	private Shop shop;
	private PauseMenu pauseMenu;
	
	private Mode mode;
	private Mode displayMode;
	private Mode shopMode;
	private Mode pauseMode;
	
	/**
	 * Settings method where window size is set,
	 * Called before sketch is set up.
	 */
	public void settings()
	{
		//size((int)WIN_WIDTH, (int)WIN_HEIGHT);			// sets the window size to parameters
		fullScreen();
	}
	
	/**
	 * Setup method runs once at the start of the program
	 * to define initial environment properties.
	 */
	public void setup()
	{
		WIN_WIDTH = width;
		WIN_HEIGHT = height;
		
		SCREEN_EDGE_TOP = WIN_HEIGHT/4;
		SCREEN_EDGE_BOTTOM = 3 * (WIN_HEIGHT/4);
		SCREEN_EDGE_LEFT = WIN_WIDTH/4;
		SCREEN_EDGE_RIGHT = WIN_WIDTH/2;
		
		// User Interface variables
		UI_WIDTH = WIN_WIDTH/4;
		MENU_WALL = WIN_WIDTH - UI_WIDTH;
		BORDER_WIDTH = WIN_WIDTH/160;
		
		RENDER_ORIGIN_X = (WIN_WIDTH - UI_WIDTH)/2;
		RENDER_ORIGIN_Y = WIN_HEIGHT/2;
		RENDER_WIDTH = WIN_WIDTH - UI_WIDTH;
		RENDER_HEIGHT = WIN_HEIGHT;
		
		frameRate(FPS);								// sets the desired frames per second (default is 60)
		
		FLOAT_FONT = createFont("BOLD", 40);
		
		mode = Mode.NO_MODE;
		displayMode = Mode.MAIN_DISPLAY;
		shopMode = Mode.NO_MODE;
		pauseMode = Mode.NO_MODE;
		
		roundCounter = 1;
		
		currencyIcon = loadImage("coins.png");
		standardAmmoIcon = loadImage("standardAmmo.png");
		burnAmmoIcon = loadImage("burn.png");
		explosiveAmmoIcon = loadImage("explosiveShot.png");
		scatterAmmoIcon = loadImage("scatterShot.png");
		
		shop = new Shop(this);
		pauseMenu = new PauseMenu(this, FLOAT_FONT, RENDER_ORIGIN_X, RENDER_ORIGIN_Y, WIN_WIDTH*0.25f, WIN_HEIGHT*0.25f, roundCounter);
		user = new Player(this, PLAYER_SPAWN_X, PLAYER_SPAWN_Y);									// creates new Player 
		enemyForces = new ArrayList<Enemy>();
		for(int i = 0; i < roundCounter; i++)
		{
			enemyForces.add(new Enemy(this, 1500, 1600));
		}
		gameWorld = new World(this, (int) user.getX()/100, (int) user.getY()/100);		// creates a new World with the player location as the parameter
		animate = true;
		
		scaleFrame();								// create dimensions based on monitor width & height
		createButtons();							// instantiate all button objects
	}
	
	/**
	 * Method to reset the game
	 */
	public void resetGame()
	{
		user.setX(PLAYER_SPAWN_X);
    	user.setY(PLAYER_SPAWN_Y);
    	user.getActiveRounds().clear();
    	gameWorld = new World(this, (int) user.getX()/100, (int) user.getY()/100);
    	
		enemyForces = new ArrayList<Enemy>();
		for(int i = 0; i < roundCounter; i++)
		{
			enemyForces.add(new Enemy(this, (int) random(3500, 4500), (int) random(3500, 4500)));
		}
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
			background(WATER_COLOR.getRGB());			// paints the background grey
			
			gameWorld.draw(mode);									// draws the world first
			
			fill(0);
			for(int i = 0; i < user.getActiveRounds().size(); i++)	// for every projectile in the arraylist of active projectiles
			{
				user.getActiveRounds().get(i).draw();
				if(user.getActiveRounds().get(i).isActive())
				{
					if(mode == Mode.COLLISION_BOX)
						user.getActiveRounds().get(i).getCollisionBox().draw();
					for(Enemy enemy: enemyForces)
					{
						if(user.getActiveRounds().get(i).getCollisionBox().isInside(enemy.getCollisionBox()))
						{
							enemy.setHealth(enemy.getHealth() - user.getActiveRounds().get(i).getBaseDamage());
							user.getActiveRounds().get(i).setAnimation(true);
						}
					}
				}
				else
					user.getActiveRounds().remove(i);
			}
			user.draw(mode);								// then draws the player over the world
			
/*
 *  Enemy Tank			
 */
			for(int j = 0; j < enemyForces.size(); j++)			// for all current enemies in the arraylist
			{
				if(enemyForces.get(j).getHealth() > 0)							// if the selected enemy more than zero health
				{
					enemyForces.get(j).draw(mode, user.getX(), user.getY());				// draw the enemy
					
					for(int i = 0; i < enemyForces.get(j).getActiveRounds().size(); i++)	// for each of the selected enemies active projectiles
					{
						if(enemyForces.get(j).getActiveRounds().get(i).isActive())		// if the projectile is active
						{
							enemyForces.get(j).getActiveRounds().get(i).draw();			// draw the round
							if(mode == Mode.COLLISION_BOX)													
								enemyForces.get(j).getActiveRounds().get(i).getCollisionBox().draw();
								
							if(user.getCollisionBox().isInside(enemyForces.get(j).getActiveRounds().get(i).getCollisionBox()))	// if the projectile hit the player
							{
								user.setHealth(user.getHealth() - 5);								// reduce player health 
								enemyForces.get(j).getActiveRounds().get(i).setActive(false);		// set the projectiles active to false
							}
						}
						else													// else if the selected projectile is not active
						{
							enemyForces.get(j).getActiveRounds().remove(i);		// remove the projectile from the arraylist
						}
					}
				}
				else												// else if the selected enemy has less than zero health
				{
					user.setCurrency(user.getCurrency() + 20);		// add currency to player bank
					enemyForces.remove(j);							// remove enemy from the arraylist
				}
			}
		}
		
/*
 * Animate Projectiles & Enemies
 */
		if(animate)											// if the game is not paused
		{ 
			for(Projectile projectile: user.getActiveRounds())	// for every projectile in the arraylist of active projectiles
				projectile.animate();							// animate that projectile
			
			for(Enemy enemy: enemyForces)								// for every enemy in the arraylist of enemies
			{
				enemy.chaseAI(user.getX(), user.getY());				// move the enemies location in the direction of the player
				for(Projectile projectile: enemy.getActiveRounds())		// for each projectile in the enemies active projectiles
					projectile.animate();								// animate the projectile
			}
		}
		
		if(mode == Mode.COLLISION_BOX)
		{
			gameWorld.getRenderBox().draw();
			user.getCollisionBox().draw();
			for(Enemy enemy: enemyForces)
				enemy.getCollisionBox().draw();
		}
		else if(mode == Mode.DATA_MODE)
			drawPerformance();
		
		gameWorld.drawTopLayer();
		
		drawUI();									// then draws the User Interface to the window
		if(displayMode == Mode.MAIN_DISPLAY)
		{
			drawData();
		}
		else if(displayMode == Mode.MENU_DISPLAY)
		{
			drawMenu();
		}
		else if(displayMode == Mode.HELP_DISPLAY)
		{
			drawHelpMenu();
		}
		check();
		
		if(pauseMode == Mode.PAUSE_DISPLAY)
		{
			pauseMenu.draw();
		}
		else if(shopMode == Mode.SHOP_DISPLAY)
		{
			shop.draw(FLOAT_FONT);
		}
	}		
	
	/**
	 * Method to check the mouses location
	 */
	private void check()
	{
		if(user.getHealth() <= 0)
		{
			int reply = JOptionPane.showConfirmDialog(null, "You Died! Continue?", "Death", JOptionPane.YES_NO_OPTION);
	        if (reply == JOptionPane.YES_OPTION)
	        {
	        	roundCounter = 1;
	        	user.setHealth(user.getMaxHealth());
	        	this.resetGame();
	        }
	        else 
	           System.exit(0);
		}
		if(enemyForces.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "Round " + roundCounter + " Complete. \n onto Round " + (++roundCounter));
			resetGame();
		}
		if(mouseX < MENU_WALL)
		{
			if(shopMode == Mode.SHOP_DISPLAY && shop.isInside(mouseX, mouseY))
				cursor(HAND);
			else
				cursor(CROSS);
		}
		else
		{
			cursor(HAND);
		}
		standardButton.setLabel(Integer.toString(user.getStandardProjectileRounds()));
		explosiveButton.setLabel(Integer.toString(user.getExplosiveProjectileRounds()));
		fireButton.setLabel(Integer.toString(user.getFireProjectileRounds()));
		scatterButton.setLabel(Integer.toString(user.getScatterProjectileRounds()));
		
		deselectAmmoButtons();
		if(user.getSelectedRound() != Mode.STANDARD)
		{
			if(user.getSelectedRound() != Mode.EXPLOSIVE)
			{
				if(user.getSelectedRound() != Mode.FIRE)
				{
					if(user.getSelectedRound() != Mode.SCATTER)
						standardButton.select(true);
					else
						scatterButton.select(true);
				}
				else
					fireButton.select(true);
			}
			else
				explosiveButton.select(true);
		}
		else
			standardButton.select(true);
	}
	
	/**
	 * Method to draw the user interface
	 */
	void drawUI()
	{
		pushStyle();
		rectMode(CORNER);
		noStroke();
		fill(WOOD_COLOR.getRGB());
		rect(MENU_WALL, 0, UI_WIDTH, WIN_HEIGHT);
		rect(0, 0, MENU_WALL, BORDER_WIDTH);
		rect(0, WIN_HEIGHT - BORDER_WIDTH, MENU_WALL, BORDER_WIDTH);
		rect(0, 0, BORDER_WIDTH, WIN_HEIGHT);
		fill(112, 109, 99);						// fill with grey and draw UI background
	    rect(MENU_WALL + BORDER_WIDTH, BORDER_WIDTH, UI_WIDTH - BORDER_WIDTH*2, WIN_HEIGHT - BORDER_WIDTH*2, 20);
	  
	    
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
			    
			    for(Enemy enemy: enemyForces)
			    {
			    	if(gameWorld.getTile(j, i).isInside(enemy.getX(), enemy.getY()))
			    		fill(255, 0, 0);
			    }
			    
			    rect((MENU_WALL + 80) + (j*mapSize), 60 + (i*mapSize), mapSize, mapSize);
		    }
	    }
	    rectMode(CENTER);
		fill(255, 0, 0);				// draws the red health bar 
		rect(MENU_WALL + (UI_WIDTH/2), UI_row0, UI_WIDTH*0.75f, 50, 20);
		fill(0, 255, 0);				// draws the green health bar over the red
		rect(MENU_WALL + (UI_WIDTH/2), UI_row0, (user.getHealth()/user.getMaxHealth())*UI_WIDTH*0.75f, 50, 20); 
		
	    fill(0);
	    textSize(20);
	    text("fps: " + frameRate, MENU_WALL*1.02f, WIN_HEIGHT/2*0.96f);
	    
	    textSize(32);
	    text("Round: " + roundCounter, MENU_WALL*1.02f, WIN_HEIGHT/2*0.84f);
	    
	    rectMode(CORNER);
	    fill(66, 64, 77);
		rect(UI_column1 - 10, UI_row1 - 10, 300, 70, 10);	// currency rectangle
		image(currencyIcon, UI_column1, UI_row1, 50, 50);	// currency icon
		fill(255);
		textSize((float)(width/100));
		textAlign(CENTER, TOP);
		text(" " + user.getCurrency(), MENU_WALL + (UI_WIDTH/4), UI_row1);
		textSize((float)(width/70));
		text("Speed:", UI_column1 + UI_WIDTH*0.15f, UI_row6*1.01f);
	    popStyle();
	}
	
	/**
	 * Method to draw the user data
	 */
	void drawData()
	{	
		pushStyle();					// saves the current style
		stroke(0);
		
		rectMode(CORNER);
		
		
		standardButton.draw(FLOAT_FONT);
		image(standardAmmoIcon, UI_column1 + 20, UI_row2 + 20, 50, 50);		// standard ammo icon
		
		explosiveButton.draw(FLOAT_FONT);
		image(explosiveAmmoIcon, UI_column1 + 20, UI_row3 + 20, 50, 50);	// explosive ammo icon
		
		fireButton.draw(FLOAT_FONT);
		image(burnAmmoIcon, UI_column1 + 20, UI_row4 + 20, 50, 50);
		
		scatterButton.draw(FLOAT_FONT);
		image(scatterAmmoIcon, UI_column1 + 20, UI_row5 + 20, 50, 50);
		
		float x1 = UI_column1 + UI_WIDTH*0.3f;
		float x2 = UI_WIDTH*0.5f;
		fill(66, 64, 77);
		rect(x1, UI_row6, x2, 100, 10);
		fill(0, 255, 0);
		rect(x1, UI_row6, (user.getVelocity()/MAX_SPEED)*x2, 100, 10);
		
		shopButton.draw(FLOAT_FONT);
		menuButton.draw(FLOAT_FONT);
		exitButton.draw(FLOAT_FONT);
		pauseButton.draw(FLOAT_FONT);
		
		
		popStyle();						// restores the previous style
	}
	
	/**
	 * Method to draw the menu
	 */
	private void drawMenu()
	{
		pushStyle();			// saves the current style
		
		backButton.draw(FLOAT_FONT);
		helpButton.draw(FLOAT_FONT);
		
		popStyle();				// restores the previous style
	}
	
	/**
	 * Method to draw the help menu
	 */
	void drawHelpMenu()
	{
		textFont(FLOAT_FONT);
		text("Use 'WASD' to move your tank", UI_column1, UI_row1);
		text("Press 'P' for performance", UI_column1, UI_row2);
		text("Press 'C' to view collision detection", UI_column1, UI_row3);
		
		backButton.draw(FLOAT_FONT);
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
		fill(255);
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
	public void mouseReleased()
	{
		if(gameWorld.getRenderBox().pointIsInside(mouseX, mouseY))				// if the mouse location in inside the world display area
		{
			if(pauseMode == Mode.PAUSE_DISPLAY)
			{
				if(pauseMenu.getMode() == Mode.NO_MODE)
				{
					if(pauseMenu.getSaveButton().isInside(mouseX, mouseY))
						pauseMenu.setMode(Mode.SAVE);
					else if(pauseMenu.getLoadButton().isInside(mouseX, mouseY))
						pauseMenu.setMode(Mode.LOAD);
				}
				else if(pauseMenu.getMode() == Mode.SAVE)
				{
					if(pauseMenu.getSaveGameOneButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setSaveMode(Mode.SAVE_GAME_ONE);
						pauseMenu.saveGame(user, roundCounter);
					}
					else if(pauseMenu.getSaveGameTwoButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setSaveMode(Mode.SAVE_GAME_TWO);
						pauseMenu.saveGame(user, roundCounter);
					}
					else if(pauseMenu.getSaveGameThreeButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setSaveMode(Mode.SAVE_GAME_THREE);
						pauseMenu.saveGame(user, roundCounter);
					}
				}
				else if(pauseMenu.getMode() == Mode.LOAD)
				{
					if(pauseMenu.getLoadGameOneButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setLoadMode(Mode.SAVE_GAME_ONE);
						user = pauseMenu.loadGame();
						
					}
					else if(pauseMenu.getLoadGameTwoButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setLoadMode(Mode.SAVE_GAME_TWO);
						user = pauseMenu.loadGame();
					}
					else if(pauseMenu.getLoadGameThreeButton().isInside(mouseX, mouseY))
					{
						pauseMenu.setLoadMode(Mode.SAVE_GAME_THREE);
						user = pauseMenu.loadGame();
					}
				}
			}
			else if(shopMode == Mode.NO_MODE && animate)			// if the shop window is not open and animate is true
				user.shoot();								// the player will shoot in the mouses direction
			else if(shopMode == Mode.SHOP_DISPLAY)			// else if the shop window is open
			{
				if(shop.getStandardAmmoButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))	// Standard Ammo Button
				{
					if(user.getStandardProjectileRounds() < AMMO_CAPACITY && user.getCurrency() >= STANDARD_AMMO_PRICE)		// Check capacity & currency
					{
						user.addStandardProjectileRounds(5);								// add standard rounds
						user.setCurrency(user.getCurrency() - STANDARD_AMMO_PRICE);			// remove currency
					}
				}
				else if(shop.getFireAmmoButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))	// else if fire rounds button clicked
				{
					if(user.getFireProjectileRounds() < AMMO_CAPACITY && user.getCurrency() >= FIRE_AMMO_PRICE)		
					{																					
						user.addFireProjectileRounds(5);									// add fire rounds
						user.setCurrency(user.getCurrency() - FIRE_AMMO_PRICE);				// remove currency
					}
				}
				else if(shop.getExplosiveAmmoButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))	// Explosive Ammo Button
				{
					if(user.getExplosiveProjectileRounds() < AMMO_CAPACITY && user.getCurrency() >= EXPLOSIVE_AMMO_PRICE)	 // Check capacity & currency
					{																					 
						user.addExplosiveProjectileRounds(5);								// add explosive rounds
						user.setCurrency(user.getCurrency() - EXPLOSIVE_AMMO_PRICE);		// remove currency
					}
				}
				else if(shop.getScatterAmmoButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))	// Scatter Ammo Button
				{
					if(user.getScatterProjectileRounds() < AMMO_CAPACITY && user.getCurrency() >= SCATTER_AMMO_PRICE)	// check capacity & currency
					{
						user.addScatterProjectileRounds(5);									// add scatter rounds
						user.setCurrency(user.getCurrency() - SCATTER_AMMO_PRICE);			// remove currency
					}
				}
				else if(shop.getHealthPackButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))
				{
					if(user.getHealth() < user.getMaxHealth() && user.getCurrency() >= HEALTH_PACK_PRICE)
					{
						user.setHealth(user.getHealth() + 10);
						user.setCurrency(user.getCurrency() - HEALTH_PACK_PRICE);
					}
				}
				else if(shop.getSpeedBoostButton().isInside(mouseX - RENDER_ORIGIN_X, mouseY - RENDER_ORIGIN_Y))
				{
					if(user.getVelocity() < MAX_SPEED && user.getCurrency() >= SPEED_BOOST_PRICE)
					{
						user.setVelocity(user.getVelocity() + 10);
						user.setCurrency(user.getCurrency() - SPEED_BOOST_PRICE);
					}
				}
			}
		}											// else if the shop window is not open, and the mouse was not clicked in the world window
		else if(displayMode == Mode.MAIN_DISPLAY)				// else if main UI window is open
		{
			if(menuButton.isInside(mouseX, mouseY))				// Menu Button
				displayMode = Mode.MENU_DISPLAY;				
			else if(shopButton.isInside(mouseX, mouseY))		// Shop Button
			{
				if(shopMode == Mode.NO_MODE)
					shopMode = Mode.SHOP_DISPLAY;
				else
					shopMode = Mode.NO_MODE;
			}
			else if(pauseButton.isInside(mouseX, mouseY))		// Pause Button
			{
				animate = !animate;
				if(pauseMode == Mode.NO_MODE)
					pauseMode = Mode.PAUSE_DISPLAY;
				else
					pauseMode = Mode.NO_MODE;
			}
			else if(exitButton.isInside(mouseX, mouseY))		// Exit Button
				exit();
			else if(standardButton.isInside(mouseX, mouseY))	// Standard Rounds Button
			{
				deselectAmmoButtons();
				standardButton.select(true);
				user.setSelectedRound(Mode.STANDARD);
			}
			else if(explosiveButton.isInside(mouseX, mouseY))	// Explosive Rounds Button
			{
				deselectAmmoButtons();
				explosiveButton.select(true);
				user.setSelectedRound(Mode.EXPLOSIVE);
			}
			else if(fireButton.isInside(mouseX, mouseY))		// Fire Rounds Button
			{
				deselectAmmoButtons();
				fireButton.select(true);
				user.setSelectedRound(Mode.FIRE);
			}
			else if(scatterButton.isInside(mouseX, mouseY))		// Scatter Rounds Button
			{
				deselectAmmoButtons();
				scatterButton.select(true);
				user.setSelectedRound(Mode.SCATTER);
			}
		}
		
		else if(displayMode == Mode.MENU_DISPLAY)	// else if the menu window is open
		{
			if(backButton.isInside(mouseX, mouseY))				// Back Button
					displayMode = Mode.MAIN_DISPLAY;
			else if(helpButton.isInside(mouseX, mouseY))		// Help Button
				displayMode = Mode.HELP_DISPLAY;
				
		}
		else if(displayMode == Mode.HELP_DISPLAY)	// else if the help window is open
		{
			if(backButton.isInside(mouseX, mouseY))				// Back Button
				displayMode = Mode.MENU_DISPLAY;
		}
	}
	
	/**
	 * Method called when a key is pressed
	 */
	public void keyPressed()
	{
		 if (key == "p".charAt(0))					// "P"  key press
		 {
			 if(mode == Mode.NO_MODE)
				 mode = Mode.DATA_MODE;					// Toggle data display mode
			 else if(mode == Mode.COLLISION_BOX)
				 mode = Mode.DATA_MODE;
			 else if(mode == Mode.DATA_MODE)
				 mode = Mode.NO_MODE;
		 }
		 else if(key == "c".charAt(0))				// "C"  key press
		 {
			 if(mode == Mode.NO_MODE)
				 mode = Mode.COLLISION_BOX;				// Toggle collision detection display mode
			 else if(mode == Mode.DATA_MODE)
				 mode = Mode.COLLISION_BOX;
			 else if(mode == Mode.COLLISION_BOX)
				 mode = Mode.NO_MODE;
				 
		 }
		 
		 if(animate)
		 {
			 if (key == S_KEY)						// "S" key press
			 {
			   user.isVertical(true);						// set orientation to "true" because the tank will be oriented parallel to the y-axis
			   
			   if(user.getY() < SCREEN_EDGE_BOTTOM)				// if user is above the bottom "soft" barrier
			   {
				   user.setY(user.getY() + user.getVelocity());	// the tank will move on the map without moving the screen
			   }
			   else												// otherwise	
			   {
				   gameWorld.playerDown(user.getVelocity());
				   for(Projectile userProjectile: user.getActiveRounds())
					   userProjectile.setY(userProjectile.getY() - user.getVelocity());
				   for(Enemy enemy: enemyForces)
				   {
					   enemy.setY(enemy.getY() - user.getVelocity());
					   for(Projectile enemyProjectile: enemy.getActiveRounds())
						   enemyProjectile.setY(enemyProjectile.getY() - user.getVelocity());
				   }
			   }
			 }
			 
			 // move right if "D" key is pressed
			 else if (key == D_KEY)
			 {
			   user.isVertical(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
			   
			   if(user.getX() < SCREEN_EDGE_RIGHT)
			   {
				   user.setX(user.getX() + user.getVelocity()); 
			   }
			   else
			   {
				   gameWorld.playerRight(user.getVelocity());
				   for(Projectile userProjectile: user.getActiveRounds())
					   userProjectile.setX(userProjectile.getX() - user.getVelocity());
				   for(Enemy enemy: enemyForces)
				   {
					   enemy.setX(enemy.getX() - user.getVelocity());
					   for(Projectile enemyProjectile: enemy.getActiveRounds())
					   {
						   enemyProjectile.setX(enemyProjectile.getX() - user.getVelocity());
					   }
				   }
			   }
			 }
			 
			 // move up if "W" key is pressed
			 else if (key == W_KEY)
			 {
			   user.isVertical(true);		// set orientation to "true" because the tank will be oriented parallel to the y-axis
			   
			   if(user.getY() > SCREEN_EDGE_TOP)
			   {
				   user.setY(user.getY() - user.getVelocity());
			   }
			   else
			   {
				   gameWorld.playerUp(user.getVelocity());
				   for(Projectile userProjectile: user.getActiveRounds())
					   userProjectile.setY(userProjectile.getY() + user.getVelocity());
				   for(Enemy enemy: enemyForces)
				   {
					   enemy.setY(enemy.getY() + user.getVelocity());
					   for(Projectile enemyProjectile: enemy.getActiveRounds())
					   {
						   enemyProjectile.setY(enemyProjectile.getY() + user.getVelocity());
					   }
				   }
			   }
			 }
			 
			 // move left if "A" key is pressed
			 else if (key == A_KEY)
			 {
			   user.isVertical(false);		// set orientation to "false" because the tank will be oriented perpendicular to the y-axis
			   
			   if(user.getX() > SCREEN_EDGE_LEFT)
			   {
				   user.setX(user.getX() - user.getVelocity());
			   }
			   else
			   {
				   gameWorld.playerLeft(user.getVelocity());
				   for(Projectile userProjectile: user.getActiveRounds())
					   userProjectile.setX(userProjectile.getX() + user.getVelocity());
				   for(Enemy enemy: enemyForces)
				   {
					   enemy.setX(enemy.getX() + user.getVelocity());
					   for(Projectile enemyProjectile: enemy.getActiveRounds())
					   {
						   enemyProjectile.setX(enemyProjectile.getX() + user.getVelocity());
					   }
				   }
			   }
			 }
		 }
	}
	
	
	
//===========================================================
//				   Initialization Methods
//===========================================================
	private void scaleFrame()
	{
		
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
		shopButton = new Button(this, "Shop", UI_column1*1.02f, UI_row7);
		menuButton = new Button(this, "Menu", UI_column1*1.02f, UI_row8);
		exitButton = new Button(this, "End Game", UI_column2, UI_row8);
		backButton = new Button(this, "Back", UI_column1*1.02f, UI_row8);
		helpButton = new Button(this, "Help", UI_column2, UI_row8);
		pauseButton = new Button(this, "Pause", UI_column2, UI_row7);
		
		standardButton = new Button(this, Integer.toString(user.getStandardProjectileRounds()), UI_column1, UI_row2, AMMO_BACKGROUND);
		standardButton.setAmmoButton(true);
		standardButton.select(true);
		explosiveButton = new Button(this, Integer.toString(user.getExplosiveProjectileRounds()), UI_column1, UI_row3, AMMO_BACKGROUND);
		explosiveButton.setAmmoButton(true);
		fireButton = new Button(this, Integer.toString(user.getFireProjectileRounds()), UI_column1, UI_row4, AMMO_BACKGROUND);
		fireButton.setAmmoButton(true);
		scatterButton = new Button(this, Integer.toString(user.getScatterProjectileRounds()), UI_column1, UI_row5, AMMO_BACKGROUND);
		scatterButton.setAmmoButton(true);
		
	}
	
	private void deselectAmmoButtons()
	{
		standardButton.select(false);
		explosiveButton.select(false);
		fireButton.select(false);
		scatterButton.select(false);
	}
	
//=======================================================================
//									Main
//=======================================================================
	public static void main(String[] args) 
	{
		PApplet.main("main.DankTank");
	}

}
