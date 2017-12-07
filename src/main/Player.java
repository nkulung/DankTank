package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.*;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends Tank implements ApplicationConstants
{
	private PApplet app;
	
	private float maxHealth;
	private float health;
	private int range;
	private float x;
	private float y;
	
	private float xOffset = 10;
	private float yOffset = 10;
	
	private float velocity;
	private float size;
	private float angle;
	
	private Box collisionBox;
	
	private Color tankColor;
	
	private boolean up;
	private boolean friendly;
	
	private ArrayList<Projectile> activeRounds = new ArrayList<Projectile>();
	
	private int currency;						// player currency
	private int standardRounds;
	private int fireRounds;
	private int explosiveRounds;
	private int scatterRounds;
	
	private Mode selectedRound;
	
	/**
	 * Default Constructor
	 */
	public Player(PApplet theApp, float someX, float someY)
	{
		app = theApp;
		maxHealth = 100;
		health = 100;
		currency = 500;
		x = someX;
		y = someY;
		velocity = 20;
		up = true;
		tankColor = new Color(163, 162, 63);
		size = TANK_SIZE;
		standardRounds = 10;
		fireRounds = 10;
		explosiveRounds = 10;
		scatterRounds = 10;
		angle = 0;
		activeRounds = new ArrayList<Projectile>();
		collisionBox = new Box(theApp, x + xOffset, y + yOffset, size + xOffset*2, size + yOffset*2, new Color(255, 0, 0, 102));
		setSelectedRound(Mode.STANDARD);
	}
	
	/**
	 * Constructor used when loading a saved player file
	 */
	public Player(PApplet app, float maxHealth, float health, int range, float velocity,
					int red, int green, int blue, int currency,
					int standardRounds, int fireRounds, int explosiveRounds, int scatterRounds)
	{
		this.app = app;
		this.x = PLAYER_SPAWN_X;
		this.y = PLAYER_SPAWN_Y;
		this.maxHealth = maxHealth;
		this.health = health;
		this.range = range;
		this.velocity = velocity;
		this.size = TANK_SIZE;
		this.angle = 0;
		this.tankColor = new Color(red, green, blue);
		this.up = true;
		this.currency = currency;
		this.standardRounds = standardRounds;
		this.fireRounds = fireRounds;
		this.explosiveRounds = explosiveRounds;
		this.scatterRounds = scatterRounds;
		activeRounds = new ArrayList<Projectile>();
		collisionBox = new Box(this.app, x + xOffset, y + yOffset, size + xOffset*2, size + yOffset*2, new Color(255, 0, 0, 102));
		setSelectedRound(Mode.STANDARD);
	}
	
	
	/**
	 * Method to return the players currency
	 * @return
	 */
	public int getCurrency()
	{
		return currency;
	}
	
	/**
	 * Method to return the players range
	 */
	public int getRange()
	{
		return range;
	}
	
	/**
	 * Method to get the number of standard rounds
	 * @return standardRounds
	 */
	public int getStandardProjectileRounds()
	{
		return standardRounds;
	}
	
	public void addStandardProjectileRounds(int newRounds)
	{
		standardRounds += newRounds;
	}
	
	/**
	 * Method to get the number of explosive rounds
	 * @return explosiveRounds
	 */
	public int getExplosiveProjectileRounds()
	{
		return explosiveRounds;
	}
	
	public void addExplosiveProjectileRounds(int newRounds)
	{
		explosiveRounds += newRounds;
	}
	
	/**
	 * Method to get the number of fire rounds
	 * @return fireRounds
	 */
	public int getFireProjectileRounds()
	{
		return fireRounds;
	}
	
	public void addFireProjectileRounds(int newRounds)
	{
		fireRounds += newRounds;
	}
	
	/**
	 * Method to get the number of scatter rounds
	 * @return scatterRounds
	 */
	public int getScatterProjectileRounds()
	{
		return scatterRounds;
	}
	
	public void addScatterProjectileRounds(int newRounds)
	{
		scatterRounds += newRounds;
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
	 * Method to shoot a cannonball
	 * @param someRound
	 * @param mouseX
	 * @param mouseY
	 */
	public void shoot()
	{
		if(selectedRound == Mode.STANDARD)
		{
			if(standardRounds > 0)
			{
				activeRounds.add(new StandardProjectile(app, x, y, angle, range));
				standardRounds -= 1;
			}
			else if(explosiveRounds > 0)
				selectedRound = Mode.EXPLOSIVE;
			else if(fireRounds > 0)
				selectedRound = Mode.FIRE;
			else if(scatterRounds > 0)
				selectedRound = Mode.SCATTER;
		}
		else if(selectedRound == Mode.EXPLOSIVE)
		{
			if(explosiveRounds > 0)
			{
				activeRounds.add(new ExplosiveProjectile(app, x, y, angle));
				explosiveRounds -= 1;
			}
			else if(standardRounds > 0)
				selectedRound = Mode.STANDARD;
			else if(fireRounds > 0)
				selectedRound = Mode.FIRE;
			else if(scatterRounds > 0)
				selectedRound = Mode.SCATTER;
		}
		else if(selectedRound == Mode.FIRE)
		{
			if(fireRounds > 0)
			{
				activeRounds.add(new FireProjectile(app, x, y, angle));
				fireRounds -= 1;
			}
			else if(standardRounds > 0)
				selectedRound = Mode.STANDARD;
			else if(explosiveRounds > 0)
				selectedRound = Mode.EXPLOSIVE;
			else if(scatterRounds > 0)
				selectedRound = Mode.SCATTER;
		}
		else if(selectedRound == Mode.SCATTER)
		{
			if(scatterRounds > 0)
			{
				for(int i = 0; i < 5; i++)
				{
					activeRounds.add(new StandardProjectile(app, x, y, angle + (app.random(-30, 30)), range));
				}
				scatterRounds -= 1;
			}
			else if(standardRounds > 0)
				selectedRound = Mode.STANDARD;
			else if(explosiveRounds > 0)
				selectedRound = Mode.EXPLOSIVE;
			else if(fireRounds > 0)
				selectedRound = Mode.FIRE;
		}
	}
	
	public void draw(Mode mode)
	{
		app.pushStyle();
		app.rectMode(PConstants.CENTER);
		float tempX = x - (size/2);
		float tempY = y - (size/2);
		if(!up)
		{
			drawHorizontalWheels(tempX, tempY);
			drawTankBody(x, y);
			drawHorizontalWheels(tempX, tempY + size + 30);
		}
		else
		{
		    drawVerticalWheels(tempX, tempY);
		    drawTankBody(x, y);
			drawVerticalWheels(tempX + size + 30, tempY);
		}
		if(mode == Mode.COLLISION_BOX)
		{
			  collisionBox.draw();
		}
		calculateBarrelAngle();
	  
	}
	
	private void drawTankBody(float tempX, float tempY)
	{
		app.fill(tankColor.getRGB());
		app.beginShape();
		app.vertex(tempX + size/2, tempY - size/2);
		app.vertex(tempX + size/2 + 20, tempY - size/2 + 20);
		app.vertex(tempX + size/2 + 20, tempY + size/2 + 20);
		app.vertex(tempX + size/2, tempY + size/2);
		app.endShape();
		app.beginShape();
		app.vertex(tempX - size/2, tempY + size/2);					// top left - south
		app.vertex(tempX - size/2 + 20, tempY + size/2 + 20);		// bottom left - south
		app.vertex(tempX + size/2 + 20, tempY + size/2 + 20);		// bottom right - south
		app.vertex(tempX + size/2, tempY + size/2);					// top right - south
		app.endShape();
		if(!up)
		{
			app.rect(tempX, tempY, size, size);
		}
		else
		{
			app.rect(tempX, tempY, size, size);
		}
		app.ellipse(x, y, 30, 30);
	}
	
	/**
	 * private method to draw 2.5D tank components
	 */
	private void drawVerticalWheels(float tempX, float tempY)
	{
		app.fill(TREAD_COLOR.getRGB());
		for(int i = 0; i <= 5; i++)
		{
			app.beginShape();
			app.vertex(tempX - 15, tempY + 15);		// top left - south
			app.vertex(tempX - 5, tempY + 25);		// bottom left - south
			app.vertex(tempX + 25, tempY + 25);		// bottom right - south
			app.vertex(tempX + 15, tempY + 15);		// top right - south
			app.endShape();
			app.beginShape();
			app.vertex(tempX + 15, tempY - 15);
			app.vertex(tempX + 25, tempY - 5);
			app.vertex(tempX + 25, tempY + 25);
			app.vertex(tempX + 15, tempY + 15);
			app.endShape();
			app.rect(tempX, tempY, 30, 30);			// top face
			app.beginShape(PConstants.LINES);
			app.vertex(tempX - 15, tempY);
			app.vertex(tempX + 15, tempY);
			app.endShape();
			tempY += 30;
		}
	}
	
	/**
	 * private method to draw right wheels
	 * @param tempX
	 * @param tempY
	 */
	private void drawHorizontalWheels(float tempX, float tempY)
	{
		app.fill(TREAD_COLOR.getRGB());
		for(int i = 0; i <= 5; i++)
		{
			app.beginShape();
			app.vertex(tempX - 15, tempY + 15);
			app.vertex(tempX - 5, tempY + 25);
			app.vertex(tempX + 25, tempY + 25);
			app.vertex(tempX + 15, tempY + 15);
			app.endShape();
			app.beginShape();
			app.vertex(tempX + 15, tempY - 15);
			app.vertex(tempX + 25, tempY - 5);
			app.vertex(tempX + 25, tempY + 25);
			app.vertex(tempX + 15, tempY + 15);
			app.endShape();
			app.rect(tempX, tempY, 30, 30);
			app.beginShape(PConstants.LINES);
			app.vertex(tempX, tempY - 15);
			app.vertex(tempX, tempY + 15);
			app.endShape();
			tempX += 30;
		}
	}
	
	/**
	 * Private method to calculate tank barrel angle
	 */
	private void calculateBarrelAngle()
	{
		app.line(x, y, app.mouseX, app.mouseY);
		app.pushMatrix();
		app.translate(x, y);
		float tempX;
		float tempY;
		float radians;
		if(app.mouseX > x)
		{
			tempX = app.mouseX - x;
			if(app.mouseY > y)		// Q4
			{
				tempY = app.mouseY - y;
				radians = PApplet.atan2(tempY, tempX);
				angle = 270 + (90 - PApplet.degrees(radians));
			}
			else							// Q1
			{
				tempY = y - app.mouseY;
				radians = PApplet.atan2(tempY, tempX);
				angle = PApplet.degrees(radians);
			}
		}
		else
		{
			tempX = x - app.mouseX;
			if(app.mouseY > y)		// Q3
			{
				tempY = app.mouseY - y;
				radians = PApplet.atan2(tempY, tempX);
				angle = 180 + PApplet.degrees(radians);
			}
			else							// Q2
			{
				tempY = y - app.mouseY;
				radians = PApplet.atan2(tempY, tempX);
				angle = 90 + (90 - PApplet.degrees(radians));
			}
		}
		app.rotate(-PApplet.radians(angle));
		app.fill(tankColor.getRGB());
		app.rectMode(PConstants.CENTER);
		app.rect(size/2, 0, size, 20);
		app.rect(0, 0, size/2, size/2);
		
		app.popMatrix();
	}
	
	public float getMaxHealth()
	{
		return maxHealth;
	}
	
	public float getHealth()
	{
		return health;
	}
	
	public float getVelocity()
	{
		return velocity;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getSize()
	{
		return size;
	}
	
	public Color getColor()
	{
		return tankColor;
	}
	
	public boolean isVertical()
	{
		return up;
	}
	
	public boolean isFriendly()
	{
		return friendly;
	}
	
	public void setMaxHealth(float newMaxHealth)
	{
		maxHealth = newMaxHealth;
	}
	
	public void setHealth(float newHealth)
	{
		health = newHealth;
	}
	
	public void setX(float newX)
	{
		x = newX;
		collisionBox.setX(newX + xOffset);
	}
	
	public void setY(float newY)
	{
		y = newY;
		collisionBox.setY(newY + yOffset);
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
	
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	public void setCollisionBox(Box newBox)
	{
		collisionBox = newBox;
	}

	public Mode getSelectedRound() 
	{
		return selectedRound;
	}

	public void setSelectedRound(Mode selectedRound) 
	{
		this.selectedRound = selectedRound;
	}
	
	public void setRange(int range)
	{
		this.range = range;
	}

}
