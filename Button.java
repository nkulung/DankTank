package main;

/**
 * This class represents buttons that can be created given a number
 * of input parameters and interacted with.
 * @author natel
 *
 */
import java.awt.Color;

public class Button 
{
	private static final int BUTTON_WIDTH = 250;						// default button width
	private static final int BUTTON_HEIGHT = 80;						// default button height
	private static final Color BUTTON_COLOR = new Color(125, 93, 6);	// default button color
	
	private String label;			// represents the button text as a string
	private int x;
	private int y;
	private boolean selected;
	private Color buttonColor;
	
	/**
	 * Main Constructor given 
	 * @param someLabel
	 * @param someX
	 * @param someY
	 */
	public Button(String someLabel, int someX, int someY)
	{
		this(someLabel, someX, someY, BUTTON_COLOR);
	}
	
	// Constructor for button with unique color
	public Button(String someLabel, int someX, int someY, Color someColor)
	{
		label = someLabel;
		x = someX;
		y = someY;
		selected = false;
		buttonColor = someColor;
	}
	
	
	String getLabel()
	{
		return label;
	}
	
	int getX()
	{
		return x;
	}
	
	int getY()
	{
		return y;
	}
	
	int getWidth()
	{
		return BUTTON_WIDTH;
	}
	
	int getHeight()
	{
		return BUTTON_HEIGHT;
	}
	
	Color getColor()
	{
		return buttonColor;
	}
	
	boolean selected()
	{
		return selected;
	}
	
	void setLabel(String newLabel)
	{
		label = newLabel;
	}
	
	void setX(int newX)
	{
		x = newX;
	}
	
	void setY(int newY)
	{
		y = newY;
	}
	
	void setColor(Color newColor)
	{
		buttonColor = newColor;
	}
	
	void select(boolean choose)
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
	boolean isInside(float mouseX, float mouseY)
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
	
	

}
