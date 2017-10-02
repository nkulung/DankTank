package main;
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
    int velocity;
	int x;
	int y;
	int baseDamage;
	int rounds;
	
	public abstract int getVelocity();
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract int getBaseDamage();
	
	public abstract int getRoundsLeft();
	
	public abstract void setVelocity(int newVelocity);
	
	public abstract void setX(int newX);
	
	public abstract void setY(int newY);
	
	public abstract void setBaseDamage(int newDamage);
	
	public abstract void setRoundsLeft(int newRoundsLeft);

}
