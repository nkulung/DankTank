package entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import entities.Box;
import entities.Projectile;
import entities.StandardProjectile;
import entities.Tank;
import main.ApplicationConstants;
import main.Mode;
import processing.core.PApplet;
import processing.core.PConstants;

public class Enemy extends Tank implements ApplicationConstants
{
	private static final float CANNON_RANGE = 1400;

	private PApplet app;
	
	private float maxHealth;
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
	
	private Color tankColor;
	
	private boolean up;
	private boolean friendly;
	private float shootCounter;
	
	private ArrayList<Projectile> activeRounds = new ArrayList<Projectile>();
	
	private int standardRounds;
	
	
	public Enemy (PApplet theApp, float someX, float someY)
	{
		
		app = theApp;
		maxHealth = 100;
		health = 100;
		shootCounter = 0;
		
		x = someX;
		y = someY;
		velocity = app.random(5, 15);
		up = true;
		location[0] = (x/100);
		location[1] = (y/100);
		tankColor = Color.GRAY;
		size = TANK_SIZE;
		standardRounds = 1000;
		angle = 0;
		activeRounds = new ArrayList<Projectile>();
		collisionBox = new Box(theApp, x + xOffset, y + yOffset, size + xOffset*2, size + yOffset*2, new Color(255, 0, 0, 102));
	
	}
	
	
	public void shoot()
	{
		if(shootCounter%20 == 0)
		{
			activeRounds.add(new StandardProjectile(app, x, y, angle, 50));
			standardRounds -= 1;
		}
	}


	
	public void draw(Mode mode, float playerX, float playerY)
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
		calculateBarrelAngle(playerX, playerY);						// calculate the angle of the tanks barrel given player coordinates
		
		app.fill(255, 0, 0);													// set color to red
		app.rect(x + xOffset, y - size, maxHealth*1.6f, app.width/160, 10);		// draw the background red health bar (based on max health)
		app.fill(0, 255, 0);													// set color to green
		app.rect(x + xOffset, y - size, health*2f - 40, app.width/160, 10);			// draw the foreground green health bar (based on current health)
	  
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
	private void calculateBarrelAngle(float playerX, float playerY)
	{
		
		app.pushMatrix();
		app.translate(x, y);
		float tempX;
		float tempY;
		float radians;
		if(app.mouseX > x)
		{
			tempX =playerX - x;
			if(playerY > y)		// Q4
			{
				tempY = playerY - y;
				radians = PApplet.atan2(tempY, tempX);
				angle = 270 + (90 - PApplet.degrees(radians));
			}
			else							// Q1
			{
				tempY = y - playerY;
				radians = PApplet.atan2(tempY, tempX);
				angle = PApplet.degrees(radians);
			}
		}
		else
		{
			tempX = x -playerX;
			if(playerY > y)		// Q3
			{
				tempY = playerY - y;
				radians = PApplet.atan2(tempY, tempX);
				angle = 180 + PApplet.degrees(radians);
			}
			else							// Q2
			{
				tempY = y - playerY;
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

	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}

	public float getVelocity(){
		return velocity;
	}
	
	public void moveUp(){
		up = true;
		this.setY(this.getY()-this.getVelocity());
		
	}

	public float getHealth(){
	return health;
	}
	
	public void setHealth(float newHealth){
	health = newHealth;
	
	}

	public void moveDown(){
		up = true;
		this.setY(this.getY()+this.getVelocity());
		
	}
	public void moveLeft(){
	up = false;
	this.setX(this.getX()-this.getVelocity());
	
	}

	public void moveRight(){
	up = false;
	this.setX(this.getX()+this.getVelocity());
	
	}

	public void chaseAI(float playerX, float playerY)
	{
		if (this.getY() > playerY + 200)
		{
			this.moveUp();
		}
		
		else if(this.getY() < playerY - 200)
		{
			this.moveDown();
		}
		
		else if(this.getX() > playerX + 200)
		{
			this.moveLeft();
		}
		else if(this.getX() < playerX - 200) 
		{
			this.moveRight();
		}
		
		if(this.inRange(playerX, playerY))
			this.shoot();
		
		shootCounter++;
	 }   
	
	public boolean inRange(float targetX, float targetY)
	{
		return ((targetX > x - CANNON_RANGE && targetX < x + CANNON_RANGE) 
				&& (targetY > y - CANNON_RANGE && targetY < y + CANNON_RANGE));
	}

	public List<Projectile> getActiveRounds()
	{
		return activeRounds;
	}
	
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	public int getAmmo()
	{
		
		return standardRounds;
	}

}
