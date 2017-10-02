package main;

public class StandardProjectile extends Projectile
{
	private int velocity;
	private int x;
	private int y;
	private int baseDamage;
	private int rounds;

	@Override
	public int getVelocity() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getX() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBaseDamage() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVelocity(int newVelocity) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setX(int newX) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(int newY) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBaseDamage(int newDamage) 
	{
		// TODO Auto-generated method stub
		
	}

	public int getRoundsLeft() 
	{
		return rounds;
	}

	@Override
	public void setRoundsLeft(int newRoundsLeft) 
	{
		rounds = newRoundsLeft;
	}

}
