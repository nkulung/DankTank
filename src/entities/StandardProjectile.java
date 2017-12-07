package entities;

import java.awt.Color;

import main.Mode;
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
	private boolean animation;
	private float angle;
	private float diameter;
	
	private int timer;
	private float range;
	
	private Box collisionBox;
	
	private Mode projectileMode;
	
	public StandardProjectile(PApplet app, float x, float y, float angle, float range)
	{
		this.app = app;
		velocity = 30;
		this.x = x;
		this.y = y;
		this.angle = -angle;
		dx = (float)(velocity * Math.cos(PApplet.radians(this.angle)));
		dy = (float)(velocity * Math.sin(PApplet.radians(this.angle)));
		baseDamage = 20;
		diameter = 20;
		active = true;
		collisionBox = new Box(this.app, this.x, this.y, diameter + 5, diameter + 5, new Color(255, 0, 0, 102));
		setProjectileMode(Mode.STANDARD);
		this.range = range;
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
		collisionBox.setX(x);
	}
	
	public void setY(float newY)
	{
		y = newY;
		collisionBox.setY(y);
	}
	
	public void setBaseDamage(int newDamage)
	{
		baseDamage = newDamage;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
	}
	
	public void draw()
	{
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
		timer++;
		x += dx;
		y += dy;
		collisionBox.setX(x);
		collisionBox.setY(y);
	}

	/**
	 * @return the projectileMode
	 */
	public Mode getProjectileMode() {
		return projectileMode;
	}

	/**
	 * @param projectileMode the projectileMode to set
	 */
	public void setProjectileMode(Mode projectileMode) {
		this.projectileMode = projectileMode;
	}

	@Override
	public int getTimer() 
	{
		return timer;
	}

	@Override
	public void setTimer(int timer) 
	{
		this.timer = timer;
	}

	@Override
	public boolean isAnimation() 
	{
		return animation;
	}

	@Override
	public void setAnimation(boolean animation) 
	{
		if(animation == true)
			active = false;
	}

}
