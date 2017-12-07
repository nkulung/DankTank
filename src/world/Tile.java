package world;

import java.awt.Color;

import entities.Box;
import main.ApplicationConstants;
import main.Mode;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Tile implements ApplicationConstants
{
	private PApplet app;
	
	private float x;
	private float y;
	private float worldX;
	private float worldY;
	private float length;
	private Box collisionBox;
	
	private Color tileColor;
	
	private TileType type;
	
	private boolean renderTile;
	
	public Tile(PApplet theApp, float tileX, float tileY, float tileLength)
	{
		app = theApp;
		x = tileX;
		y = tileY;
		worldX = x;
		worldY = y;
		length = tileLength;
		tileColor = new Color(255, 255, 255);
		collisionBox = new Box(theApp, x, y, length, length, new Color(0, 0, 255, 102));
		renderTile = false;
		type = TileType.NULL_TILE;
	}
	
	public float getWorldX()
	{
		return worldX;
	}
	
	public float getWorldY()
	{
		return worldY;
	}

	public float getX() 
	{
		return x;
	}

	public void setX(float x) 
	{
		collisionBox.setX(x);
		this.x = x;
	}

	public float getY() 
	{
		return y;
	}

	public void setY(float y) 
	{
		collisionBox.setY(y);
		this.y = y;
	}

	public Color getTileColor() 
	{
		return tileColor;
	}

	public void setTileColor(Color tileColor) 
	{
		this.tileColor = tileColor;
	}
	
	public TileType getTileType()
	{
		return type;
	}
	
	public void setTileType(String id)
	{
		switch(id)
		{
		case "0":	
			type = TileType.DIRT_TILE;
			tileColor = DIRT_COLOR;
			break;

		case "1": 
			type = TileType.GRASS_TILE;
			tileColor = GRASS_COLOR;
			break;
			
		case "2": 
			type = TileType.WATER_TILE;
			tileColor = WATER_COLOR;
			break;
			
		case "3": 
			type = TileType.HORIZONTAL_WALL_TILE;
			tileColor = GRASS_COLOR;
			break;
			
		case "4": 
			type = TileType.VERTICAL_WALL_TILE;
			tileColor = GRASS_COLOR;
			break;
					
		case "5": 
			type = TileType.BOTTOM_LEFT_WALL;
			tileColor = GRASS_COLOR;
			break;
			
		case "6":
			type = TileType.BOTTOM_RIGHT_WALL;
			tileColor = GRASS_COLOR;
			break;
			
		case "7":
			type = TileType.TOP_RIGHT_WALL;
			tileColor = GRASS_COLOR;
			break;
			
		case "8":
			type = TileType.TOP_LEFT_WALL;
			tileColor = GRASS_COLOR;
			break;
		
		case "9":
			type = TileType.WOOD_TILE;
			tileColor = WOOD_COLOR;
			break;
			
		case "A":
			type = TileType.RIGHT_HILL_FACE;
			tileColor = GRASS_COLOR;
			break;
			
		case "B":
			type = TileType.BOTTOM_HILL_FACE;
			tileColor = GRASS_COLOR;
			break;
			
		case "C":
			type = TileType.HILL_CORNER;
			tileColor = GRASS_COLOR;
			break;
			
		default:
			type = TileType.NULL_TILE;
			tileColor = Color.WHITE;
			break;
		}	
	}
	
	public boolean render()
	{
		return renderTile;
	}
	
	public void render(boolean newRender)
	{
		renderTile = newRender;
	}

	public float getLength() 
	{
		return length;
	}

	public void setLength(float length) 
	{
		this.length = length;
	}
	
	public boolean isInside(float someX, float someY)
	{
		return ((someX <= x + length/2) && (someX > x - length/2) && (someY <= y + length/2) && (someY > y - length/2));
	}
	
	public Box getCollisionBox()
	{
		return collisionBox;
	}
	
	public void setCollisionBox(Box newBox)
	{
		collisionBox = newBox;
	}
	
	public void draw(Mode mode)
	{
		if(renderTile)
		{
			app.pushStyle();
			app.noStroke();
			app.rectMode(PConstants.CENTER);
			app.fill(tileColor.getRGB());
			app.rect(x, y, length, length);
			
			if(mode == Mode.DATA_MODE)
			{
				app.textAlign(PConstants.CENTER);
				app.textSize(15);
				app.fill(0);
				app.text("X: " + x + "\n Y: " + y, x, y);
			}
			app.popStyle();
		}
	}
	
	private void drawHills()
	{
		if(type == TileType.RIGHT_HILL_FACE)
		{
			app.stroke(0);
			float shiftX = 20,
				  shiftY = 50;
			float x1 = x - length/2,
				  y1 = y - length,
				  y2 = y - length/2,
				  y3 = y + length/2;
			app.quad(x1, y1, x, y2, x, y3, x1, y);
		}
		else if(type == TileType.BOTTOM_HILL_FACE)
		{
			app.stroke(0);
			float x1 = x - length/2,
				  x2 = x - length/4,
				  x3 = x + length/2,
				  x4 = x + length/2,
				  y1 = y - length/2 - length/4,
				  y2 = y + length/4;
			app.quad(x1, y1, x2, y2, x3, y2, x4, y1);
		}
	}
	
	public void drawTileWall()
	{
		float wallsize = length/2;
		float shiftX = 20,
			  shiftY = 70;
		
		app.stroke(1);
		if(type == TileType.VERTICAL_WALL_TILE)
		{
			float x1 = x - shiftX - wallsize/2,
				  x2 = x - wallsize/2,
				  x3 = x - shiftX + wallsize/2,
				  x4 = x + wallsize/2;
		    float y1 = y - shiftY - wallsize,
		    	  y2 = y - wallsize,
				  y3 = y - shiftY + wallsize,
				  y4 = y + wallsize;
			
			app.fill(STONE_COLOR.getRGB());
			app.beginShape();
			app.vertex(x1, y3);
			app.vertex(x2, y4);
			app.vertex(x4, y4);
			app.vertex(x3, y3);
			app.endShape();
			app.beginShape();
			app.vertex(x3, y1);
			app.vertex(x4, y2);
			app.vertex(x4, y4);
			app.vertex(x3, y3);
			app.endShape();
			app.beginShape();
			app.vertex(x1, y3);
			app.vertex(x1, y1);
			app.vertex(x3, y1);
			app.vertex(x3, y3);
			app.vertex(x1, y3);
			app.endShape();
		}
		else if(type == TileType.HORIZONTAL_WALL_TILE)
		{
			float x1 = x - shiftX - wallsize,
				  x2 = x - wallsize,
				  x3 = x - shiftX + wallsize,
				  x4 = x + wallsize;
			float y1 = y - shiftY - wallsize/2,
			      y2 = y - shiftY + wallsize/2,
			      y3 = y - wallsize/2,
				  y4 = y + wallsize/2;
			
			app.fill(STONE_COLOR.getRGB());
			app.beginShape();
			app.vertex(x3, y2);
			app.vertex(x4, y4);
			app.vertex(x4, y3);
			app.vertex(x3, y1);
			app.endShape();
			app.beginShape();
			app.vertex(x1, y2);
			app.vertex(x2, y4);
			app.vertex(x4, y4);
			app.vertex(x3, y2);
			app.endShape();
			app.beginShape();
			app.vertex(x1, y1);
			app.vertex(x1, y2);
			app.vertex(x3, y2);
			app.vertex(x3, y1);
			app.vertex(x1, y1);
			app.endShape();
			
		}
		else if(type == TileType.BOTTOM_LEFT_WALL)
		{
			float x1 = x - shiftX - wallsize/2,
				  x2 = x - wallsize/2,
				  x3 = x - shiftX + wallsize/2,
				  x4 = x - shiftX + wallsize,
				  x5 = x + wallsize/2,
				  x6 = x + wallsize;
			float y1 = y - shiftY - wallsize,
				  y2 = y - shiftY - wallsize/2,
				  y3 = y - wallsize,
				  y4 = y - shiftY + wallsize/2,
				  y5 = y - wallsize/2,
				  y6 = y + wallsize/2;
			
			app.fill(STONE_COLOR.getRGB());
			app.beginShape();
			app.vertex(x1, y4);
			app.vertex(x2, y6);
			app.vertex(x6, y6);
			app.vertex(x4, y4);
			app.endShape();
			app.beginShape();
			app.vertex(x4, y4);
			app.vertex(x6, y6);
			app.vertex(x6, y5);
			app.vertex(x4, y2);
			app.endShape();
			app.beginShape();
			app.vertex(x3, y2);
			app.vertex(x5, y5);
			app.vertex(x5, y3);
			app.vertex(x3, y1);
			app.endShape();
			app.beginShape();
			app.vertex(x1, y1);
			app.vertex(x1, y4);
			app.vertex(x4, y4);
			app.vertex(x4, y2);
			app.vertex(x3, y2);
			app.vertex(x3, y1);
			app.vertex(x1, y1);
			app.endShape();
		}
		else if(type == TileType.BOTTOM_RIGHT_WALL)
		{
			float x1 = x - shiftX - wallsize,
				  x2 = x - shiftX - wallsize/2,
				  x3 = x - wallsize,
				  x4 = x - shiftX + wallsize/2,
				  x5 = x + wallsize/2;
			float y1 = y - shiftY - wallsize,
				  y2 = y - shiftY - wallsize/2,
				  y3 = y - shiftY + wallsize/2,
				  y4 = y - wallsize,
				  y5 = y + wallsize/2;
			
			app.fill(STONE_COLOR.getRGB());
			app.beginShape();
			app.vertex(x1, y3);
			app.vertex(x3, y5);
			app.vertex(x5, y5);
			app.vertex(x4, y3);
			app.endShape();
			app.beginShape();
			app.vertex(x4, y3);
			app.vertex(x5, y5);
			app.vertex(x5, y4);
			app.vertex(x4, y1);
			app.endShape();
			app.beginShape();
			app.vertex(x4, y3);
			app.vertex(x1, y3);
			app.vertex(x1, y2);
			app.vertex(x2, y2);
			app.vertex(x2, y1);
			app.vertex(x4, y1);
			app.vertex(x4, y3);
			app.endShape();
			
		}
		else if(type == TileType.TOP_RIGHT_WALL)
		{
			float x1 = x - shiftX - wallsize,
				  x2 = x - shiftX - wallsize/2,
				  x3 = x - wallsize,
				  x4 = x - wallsize/2,
				  x5 = x - shiftX + wallsize/2,
				  x6 = x + wallsize/2;
			float y1 = y - shiftY - wallsize/2,
				  y2 = y - shiftY + wallsize/2,
				  y3 = y - wallsize/2,
				  y4 = y - shiftY + wallsize,
				  y5 = y + wallsize/2,
				  y6 = y + wallsize;
			
			app.fill(STONE_COLOR.getRGB());
			app.beginShape();
			app.vertex(x1, y2);
			app.vertex(x3, y5);
			app.vertex(x4, y5);
			app.vertex(x2, y2);
			app.endShape();
			app.beginShape();
			app.vertex(x5, y4);
			app.vertex(x6, y6);
			app.vertex(x6, y3);
			app.vertex(x5, y1);
			app.endShape();
			app.beginShape();
			app.vertex(x2, y4);
			app.vertex(x5, y4);
			app.vertex(x5, y1);
			app.vertex(x1, y1);
			app.vertex(x1, y2);
			app.vertex(x2, y2);
			app.vertex(x2, y4);
			app.endShape();
		}
	}

}
