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
import processing.core.PFont;
import processing.core.PImage;

public class Button implements ApplicationConstants
{
	private PApplet app;			// represents the main PApplet app
	
	private String label;			// represents the button text as a string
	private float x;					// button x coordinate (center)
	private float y;					// button y coordinate (center)
	private boolean selected;		// boolean to check button selection
	private Color buttonColor;		// color of the button
	
	private PImage buttonImage;
	
	private boolean ammoButton;
	
	/**
	 * Main Constructor given 
	 * @param someLabel
	 * @param someX
	 * @param someY
	 */
	public Button(PApplet theApp, String someLabel, float someX, float someY)
	{
		this(theApp, someLabel, someX, someY, BUTTON_COLOR);
	}
	
	// Constructor for button with unique color
	public Button(PApplet theApp, String someLabel, float someX, float someY, Color someColor)
	{
		app = theApp;
		label = someLabel;
		x = someX;
		y = someY;
		selected = false;
		buttonColor = someColor;
		buttonImage = app.loadImage("redButton.png");
		setAmmoButton(false);
	}
	
	
	public String getLabel()
	{
		return label;
	}
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
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
		return ((mouseX > x) && (mouseX < x + BUTTON_WIDTH)) && ((mouseY > y) && (mouseY < y + BUTTON_HEIGHT));
	}
	
	/**
	 * Method to draw the button to the app window
	 */
	public void draw(PFont FLOAT_FONT)
	{
		float BORDER_WIDTH = app.width/160;
		app.pushStyle();
		app.textAlign(PConstants.CENTER);
		app.rectMode(PConstants.CORNER);
		app.textFont(FLOAT_FONT);
		app.noStroke();
		
		if(ammoButton)
		{
			if(selected)
			{
				app.fill(255);
				app.rect(x - BORDER_WIDTH/2, y - BORDER_WIDTH/2, BUTTON_WIDTH + BORDER_WIDTH, BUTTON_HEIGHT + BORDER_WIDTH, 10);
			}
			app.fill(buttonColor.getRGB());
			app.rect(x, y, BUTTON_WIDTH, BUTTON_HEIGHT, 10);
			app.fill(255);
			app.text(label, x + (BUTTON_WIDTH*3/4), y + BUTTON_HEIGHT*3/4);
		}
		else
		{
			app.image(buttonImage, x - 20, y - 20);
			app.fill(255);
			app.text(label, x + (BUTTON_WIDTH/2), y + BUTTON_HEIGHT);
		}
		app.popStyle();
	}

	public boolean isAmmoButton() 
	{
		return ammoButton;
	}

	public void setAmmoButton(boolean ammoButton) 
	{
		this.ammoButton = ammoButton;
	}
	
	

}
