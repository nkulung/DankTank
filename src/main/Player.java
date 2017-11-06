package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.Box;
import entities.Projectile;
import entities.StandardProjectile;
import entities.Tank;
import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends Tank implements ApplicationConstants
{
	private PApplet app;
	
	private float health;
	private float x;
	private float y;
	
	private float xOffset = 10;
	private float yOffset = 10;
	
	private float velocity;
	private float size;
	private float angle;
	private float[] location = new float[2];
	
	private Box collisionBox;
	private Box leftCollisionBox;
	private Box rightCollisionBox;
	
	private Color tankColor;
	
	private boolean up;
	private boolean friendly;
	
	private ArrayList<Projectile> activeRounds = new ArrayList<Projectile>();
	
	private int currency;						// player currency
	private int standardRounds;
	
	
	/**
	 * Default Constructor
	 */
	public Player(PApplet theApp, float someX, float someY)
	{
		app = theApp;
		health = 100;
		currency = 50;
		x = someX;
		y = someY;
		velocity = 25;
		up = true;
		location[0] = (x/100);
		location[1] = (y/100);
		tankColor = new Color(163, 162, 63);
		size = TANK_SIZE;
		standardRounds = 50;
		angle = 0;
		activeRounds = new ArrayList<Projectile>();
		leftCollisionBox = new Box(theApp, x + 70, y - 55, 250, 50, new Color(255, 0, 0, 102));
		rightCollisionBox = new Box(theApp, x, y + 100, 220, 40, new Color(255, 0, 0, 102));
		collisionBox = new Box(theApp, x + xOffset, y + yOffset, size + xOffset*2, size + yOffset*2, new Color(255, 0, 0, 102));
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
	 * 
	 * @return
	 */
	public int getStandardProjectileRounds()
	{
		return standardRounds;
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
		activeRounds.add(new StandardProjectile(app, x, y, angle));
		standardRounds -= 1;
	}
	
	public void draw(Mode mode)
	{
		app.pushStyle();
		app.rectMode(PConstants.CENTER);
		float tempX = x - (size/2);
		float tempY = y - (size/2);
		if(!up)
		{
			drawHorizontalWheels(tempX + 20, tempY);
			drawTankBody(x, y);
			drawHorizontalWheels(tempX + 20, tempY + size + 30);
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
			  leftCollisionBox.draw();
			  rightCollisionBox.draw();
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
		for(int i = 0; i <= 7; i++)
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
		for(int i = 0; i <= 7; i++)
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
	
	public float[] getLocation()
	{
		return location;
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
	
	public void setHealth(float newHealth)
	{
		health = newHealth;
	}
	
	public void setX(float newX)
	{
		x = newX;
		collisionBox.setX(newX + xOffset);
		leftCollisionBox.setX(newX + 70);
		rightCollisionBox.setX(newX + 70);
	}
	
	public void setY(float newY)
	{
		y = newY;
		collisionBox.setY(newY + yOffset);
		leftCollisionBox.setY(newY - 55);
		rightCollisionBox.setY(newY - 55);
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
	
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	public Box getLeftCollisionBox()
	{
		return leftCollisionBox;
	}
	
	public Box getRightCollisionBox()
	{
		return rightCollisionBox;
	}
	
	public void setCollisionBox(Box newBox)
	{
		collisionBox = newBox;
	}

}
