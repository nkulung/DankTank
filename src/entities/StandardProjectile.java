package entities;

import java.awt.Color;

import processing.core.PApplet;

public class StandardProjectile extends Projectile
{
	private PApplet app;
	
	private float velocity;
	private float x;
	private float y;
	
	private float dx;
	private float dy;
	private float baseDamage;
	private boolean active;
	private float angle;
	private float diameter;
	
	private float timer;
	
	private Box collisionBox;
	
	public StandardProjectile(PApplet theApp, float userX, float userY, float someAngle)
	{
		app = theApp;
		velocity = 30;
		x = userX;
		y = userY;
		angle = -someAngle;
		dx = (float)(velocity * Math.cos(PApplet.radians(angle)));
		dy = (float)(velocity * Math.sin(PApplet.radians(angle)));
		baseDamage = 20;
		diameter = 20;
		active = true;
		collisionBox = new Box(theApp, x, y, diameter + 5, diameter + 5, new Color(255, 0, 0, 102));
	}
	
	/**
	 * Method to return the projectiles velocity
	 * @return velocity
	 */
	public float getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Method to return the projectiles X coordinate in the window.
	 * @return x
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Method to return the projectiles Y coordinate in the window.
	 * @return y
	 */
	public float getY()
	{
		return y;
	}
	
	public float getBaseDamage()
	{
		return baseDamage;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	public void setCollisionBox(Box newBox)
	{
		collisionBox = newBox;
	}
	
	public void setVelocity(float newVelocity) 
	{
		velocity = newVelocity;
	}
	
	public void setX(float newX)
	{
		x = newX;
	}
	
	public void setY(float newY)
	{
		y = newY;
	}
	
	public void setBaseDamage(int newDamage)
	{
		baseDamage = newDamage;
	}
	
	public void setActive(boolean someBool)
	{
		active = someBool;
	}
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
	}
	
	public void draw()
	{
		timer++;
		if(timer > 50)
			active = false;
		app.pushMatrix();
		app.pushStyle();
		app.translate(x, y);					// translate the reference point to the projectile coordinates
		app.rotate(-PApplet.radians(angle));
		app.fill(127, 127, 127);							// set color to white
		
		app.ellipse(0, 0, diameter, diameter);	// draw the projectile (a circle)
		
		app.popStyle();
		app.popMatrix();
	}
	
	public void animate()
	{
		x += dx;
		y += dy;
		collisionBox.setX(x);
		collisionBox.setY(y);
	}

}
