package entities;

import java.awt.Color;

import main.Mode;
import processing.core.PApplet;
import processing.core.PVector;

public class FireProjectile extends Projectile
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
	
	private Box collisionBox;
	private ParticleSystem ps;
	
	private Mode particleMode;
	
	public FireProjectile(PApplet theApp, float userX, float userY, float someAngle)
	{
		timer = 0;
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
		animation = false;
		collisionBox = new Box(theApp, x, y, diameter + 5, diameter + 5, new Color(255, 0, 0, 102));
		ps = new ParticleSystem(app, new PVector(0,0), angle);
		projectileMode = Mode.FIRE;
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
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
	}
	
	@Override
	public void draw() 
	{
		app.println("start fire draw");
		float temp = app.random(255);
		timer++;
		app.pushMatrix();
		app.pushStyle();
		app.translate(x, y);					// translate the reference point to the projectile coordinates
		app.rotate(-PApplet.radians(angle));
		app.fill(255, temp, 0);							// set color to yellow
		
		app.ellipse(0, 0, diameter, diameter);	// draw the projectile (a circle)
		
		ps.addParticle(dx, dy);
		ps.run();
		
		app.popStyle();
		app.popMatrix();
		app.println("End fire draw");
	}

	@Override
	public void animate() 
	{
		if(timer > 50)
			active = false;
		x += dx;
		y += dy;
		collisionBox.setX(x);
		collisionBox.setY(y);
	}

	/**
	 * @return the particleMode
	 */
	public Mode getParticleMode() {
		return particleMode;
	}

	/**
	 * @param particleMode the particleMode to set
	 */
	public void setParticleMode(Mode particleMode) {
		this.particleMode = particleMode;
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
