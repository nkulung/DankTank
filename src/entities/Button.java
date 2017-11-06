package entities;

/**
 * This class represents buttons that can be created given a number
 * of input parameters and interacted with.
 * @author natel
 *
 */
import java.awt.Color;

import main.ApplicationConstants;
import processing.core.PApplet;
import processing.core.PConstants;

public class Button implements ApplicationConstants
{
	private PApplet app;			// represents the main PApplet app
	
	private String label;			// represents the button text as a string
	private int x;					// button x coordinate (center)
	private int y;					// button y coordinate (center)
	private boolean selected;		// boolean to check button selection
	private Color buttonColor;		// color of the button
	
	/**
	 * Main Constructor given 
	 * @param someLabel
	 * @param someX
	 * @param someY
	 */
	public Button(PApplet theApp, String someLabel, int someX, int someY)
	{
		this(theApp, someLabel, someX, someY, BUTTON_COLOR);
	}
	
	// Constructor for button with unique color
	public Button(PApplet theApp, String someLabel, int someX, int someY, Color someColor)
	{
		app = theApp;
		label = someLabel;
		x = someX;
		y = someY;
		selected = false;
		buttonColor = someColor;
	}
	
	
	public String getLabel()
	{
		return label;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Color getColor()
	{
		return buttonColor;
	}
	
	public boolean selected()
	{
		return selected;
	}
	
	public void setLabel(String newLabel)
	{
		label = newLabel;
	}
	
	public void setX(int newX)
	{
		x = newX;
	}
	
	public void setY(int newY)
	{
		y = newY;
	}
	
	public void setColor(Color newColor)
	{
		buttonColor = newColor;
	}
	
	public void select(boolean choose)
	{
		selected = choose;
	}
	
	/**
	 * Method to check whether the input parameters (mouseclick coordinates)
	 * is inside the button or not
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public boolean isInside(float mouseX, float mouseY)
	{
		if(((mouseX > x) && (mouseX < x + BUTTON_WIDTH)) && ((mouseY > y) && (mouseY < y + BUTTON_HEIGHT)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method to draw the button to the app window
	 */
	public void draw()
	{
		app.pushStyle();
		app.textAlign(PConstants.CENTER);
		app.noStroke();
		
		if(selected)
		{
			app.fill(255);
			app.rect(x - (BORDER_WIDTH/2), y - (BORDER_WIDTH/2), 
					BUTTON_WIDTH + BORDER_WIDTH, BUTTON_HEIGHT + BORDER_WIDTH, 10);
		}
		
		app.fill(buttonColor.getRGB());
		app.rect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, 10);
		
		app.fill(255);
		app.text(label, x + (BUTTON_WIDTH/2), y + (BUTTON_HEIGHT/2));
		app.popStyle();
	}
	
	

}
