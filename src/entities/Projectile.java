package entities;

/**
 * The abstract Projectile class acts as a base class for different projectile specializations.
 * All comments for methods in child classes are located in this class, with the exception of 
 * methods that operate differently or private methods.
 * CSC 305: Software Engineering Fall '17
 * @author Nate Larson
 * @author Gabe Le
 * @author Nick Kulungian
 * @author Ronil Soto
 * @author Kevin Andrade
 */

public abstract class Projectile 
{
	float velocity;
	float x;
	float y;
	float baseDamage;
	boolean active;
	float angle;
	Box collisionBox;
	
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
	
	public abstract void draw();
	
	public abstract void animate();
	
	public void setAngle(float newAngle)
	{
		angle = newAngle;
	}

}
