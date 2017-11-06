package entities;

import java.awt.Color;

import processing.core.PApplet;
import processing.core.PConstants;

public class Box
{
	private PApplet app;
	private float x;
	private float y;
	
	private float width;
	private float height;
	
	private Color boxColor;
	
	public Box(PApplet theApp, float objectX, float objectY, float objectW, float objectH, Color objectColor)
	{
		app = theApp;
		x = objectX;
		y = objectY;
		width = objectW;
		height = objectH;
		boxColor = objectColor;
	}

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		this.y = y;
	}

	public float getWidth() 
	{
		return width;
	}

	public void setWidth(float width) 
	{
		this.width = width;
	}

	public float getHeight() 
	{
		return height;
	}

	public void setHeight(float height) 
	{
		this.height = height;
	}
	
	public float getLeftEdge()
	{
		return (x - width/2);
	}
	
	public float getRightEdge()
	{
		return (x + width/2);
	}
	
	public float getTopEdge()
	{
		return (y - height/2);
	}
	
	public float getBottomEdge()
	{
		return (y + height/2);
	}
	
	public boolean isInside(Box otherBox)
	{
		if(otherBox.getX() >= x)				// if the other box is to the right
		{
			if(otherBox.getY() >= y)			// if the other box is below
			{
				if(otherBox.getLeftEdge() < getRightEdge() && otherBox.getTopEdge() < getBottomEdge())
				{
					return true;
				}
				else
					return false;
			}
			else if(otherBox.getY() <= y)		// if the other box is above
			{
				if(otherBox.getLeftEdge() < getRightEdge() && otherBox.getBottomEdge() > getTopEdge())
				{
					return true;
				}
				else
					return false;
			}
			else 
				return false;
		}
		else if(otherBox.getX() <= x)			// else if the other box is to the left
		{
			if(otherBox.getY() >= y)			// if the other box is below
			{
				if(otherBox.getRightEdge() > getLeftEdge() && otherBox.getTopEdge() < getBottomEdge())
				{
					return true;
				}
				else
					return false;
			}
			else if(otherBox.getY() <= y)		// if the other box is above
			{
				if(otherBox.getRightEdge() > getLeftEdge() && otherBox.getBottomEdge() > getTopEdge())
				{
					return true;
				}
				else
					return false;
			}
			else 
				return false;
		}
		else
			return false;
	}
	
	public void draw()
	{
		app.pushStyle();
		app.fill(boxColor.getRGB());
		app.rectMode(PConstants.CENTER);
		app.rect(x, y, width, height);
		app.popStyle();
	}

}
