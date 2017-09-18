package main;

import processing.core.PApplet;

public class ProcessingMain extends PApplet
{
	Player user;	// player tank object
	World map;		// world map

	public static void main(String[] args) 
	{
		PApplet.main("main.ProcessingMain");
	}
	
	public void settings()
	{
		size(2000, 1200);
	}
	
	public void setup()
	{
		user = new Player();
		map = new World(user.getLocation());
	}
	
	public void draw()
	{
		background(0);
		drawMap();
		drawPlayer();
		drawUI();
		drawData();
	}
	
	/**
	 * Method to draw the player tank
	 */
	void drawPlayer()
	{
	  fill(0);
	  
	  if(user.getOrientation() == false)
	  {
	    rect(user.getX(), user.getY(), 120, 80);
	    fill(160, 160, 160);
	    rect(user.getX() - 5, user.getY() - 5, 130, 20);
	    rect(user.getX() - 5, user.getY() + 80, 130, 20);
	    stroke(0);
	    line(user.getX() + 60, user.getY() + 40, mouseX, mouseY);
	    ellipse(user.getX() + 60, user.getY() + 45, 40, 40);
	  }
	  else
	  {
	    rect(user.getX(), user.getY(), 80, 120); 
	    fill(160, 160, 160);
	    rect(user.getX() - 5, user.getY() - 5, 20, 130);
	    rect(user.getX() + 80, user.getY() -5, 20, 130);
	    stroke(0);
	    line(user.getX() + 40, user.getY()+ 60, mouseX, mouseY);
	    ellipse(user.getX() + 45, user.getY() + 60, 40, 40);
	  }
	  
	}
	
	/**
	 * Method to draw the user interface
	 */
	void drawUI()
	{
	  fill(112, 109, 99);
	  rect(1400, 0, 600, 1200);
	  
	  fill(125, 93, 6);
	  noStroke();
	  //rect(0, 0, 1400, 20);
	  //rect(0, 0, 20, 1200);
	  //rect(0, 1180, 1400, 20);
	  rect(1400, 0, 20, 1200);
	  rect(1400, 0, 600, 20);
	  rect(1400, 1180, 600, 20);
	  rect(1980, 0, 20, 1200);
	  rect(1440, 50, 520, 520);
	  
	  stroke(0);
	  for(int i = 0; i < map.getDimension(); i++)
	  {
		  for(int j = 0; j < map.getDimension(); j++)
		  {
			  fill(255, 255, 255);
			  if(i == map.getPlayerLocation()[0] && j == map.getPlayerLocation()[1])
			  {
				  fill(0, 255, 0);
			  }
			  rect(1450+(i*10), 60+(j*10), 10, 10);
		  }
	  }

	}
	
	/**
	 * Method to draw the user data
	 */
	void drawData()
	{
		fill(255, 0, 0);
		stroke(0);
		rect(1500, 600, 400, 50, 20);
		fill(0, 255, 0);
		rect(1500, 600, 400, 50, 20); 
	}
	
	/**
	 * Method to draw the map 
	 */
	void drawMap()
	{
		stroke(0);
		for(int i = 0; i < 50; i++)
		{
			for(int j = 0; j < 50; j++)
			{
				int unitX = map.getMap()[i][j].getX();
				int unitY = map.getMap()[i][j].getY();
				fill(255, 255, 255);
				if((unitX > user.getLocation()[0] - 55 && unitX < user.getLocation()[0] + 55)
						&& (unitY > user.getLocation()[1] - 55 && unitY < user.getLocation()[1] + 55))
				{
					fill(0, 255, 0);
					int[] newLocation = new int[2];
					newLocation[0] = i;
					newLocation[1] = j;
					map.setPlayerLocation(newLocation);
				}
				rect(unitX, unitY, 100, 100);
			}
		}
	}
	
	public void keyPressed()
	{
	 // println("pressed " + key + " " + keyCode); 
	 
	 // move down if "S" key is pressed
	 if (key == 115)
	 {
	   user.setOrientation(true);
	   if(user.getY() < 800)
	   {
		   user.setY(user.getY() + user.getVelocity());
	   }
	   else
	   {
		   for(int i = 0; i < map.getDimension(); i++)
		   {
			   for(int j = 0; j < map.getDimension(); j++)
			   {
				   map.getMap()[i][j].setY(map.getMap()[i][j].getY() - user.getVelocity());
			   }
		   }
	   }
	 }
	 
	 // move right if "D" key is pressed
	 if (key == 100)
	 {
	   user.setOrientation(false);
	   if(user.getX() < 1000)
	   {
		   user.setX(user.getX() + user.getVelocity()); 
	   }
	   else
	   {
		   for(int i = 0; i < map.getDimension(); i++)
		   {
			   for(int j = 0; j < map.getDimension(); j++)
			   {
				   map.getMap()[i][j].setX(map.getMap()[i][j].getX() - user.getVelocity());
			   }
		   }
	   }
	 }
	 
	 // move up if "W" key is pressed
	 if (key == 119)
	 {
	   user.setOrientation(true);
	   if(user.getY() > 400)
	   {
		   user.setY(user.getY() - user.getVelocity());
	   }
	   else
	   {
		   for(int i = 0; i < map.getDimension(); i++)
		   {
			   for(int j = 0; j < map.getDimension(); j++)
			   {
				   map.getMap()[i][j].setY(map.getMap()[i][j].getY() + user.getVelocity());
			   }
		   }
	   }
	 }
	 
	 // move left if "A" key is pressed
	 if (key == 97)
	 {
	   user.setOrientation(false);
	   if(user.getX() > 400)
	   {
		   user.setX(user.getX() - user.getVelocity());
	   }
	   else
	   {
		   for(int i = 0; i < map.getDimension(); i++)
		   {
			   for(int j = 0; j < map.getDimension(); j++)
			   {
				   map.getMap()[i][j].setX(map.getMap()[i][j].getX() + user.getVelocity());
			   }
		   }
	   }
	 }
	}

	public void keyTyped()
	{
	  // println("typed " + key + " " + keyCode);
	}

	public void keyReleased()
	{
	  // println("released " + key + " " + keyCode);
	}

}
