package main;

import entities.Button;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Shop implements ApplicationConstants
{
	private PApplet app;
	
	float WIN_WIDTH;
	float WIN_HEIGHT;
	
	float SCREEN_EDGE_TOP;
	float SCREEN_EDGE_BOTTOM;
	float SCREEN_EDGE_LEFT;
	float SCREEN_EDGE_RIGHT;
	
	// User Interface variables
	float UI_WIDTH;
	float MENU_WALL;
	float BORDER_WIDTH;
	
	float RENDER_ORIGIN_X;
	float RENDER_ORIGIN_Y;
	float RENDER_WIDTH;
	float RENDER_HEIGHT;
	
	private float width;
	private float height;
	
	private float unitWidth;
	private float unitHeight;
	
	private float column1;
	private float column2;
	private float column3;
	
	private float row1;
	private float row2;
	
	private PImage currencyIcon;
	private PImage standardAmmoIcon;
	private PImage burnAmmoIcon;
	private PImage explosiveAmmoIcon;
	private PImage scatterAmmoIcon;
	
	private Button standardAmmoButton;
	private Button fireAmmoButton;
	private Button explosiveAmmoButton;
	private Button scatterAmmoButton;
	private Button healthPackButton;
	private Button speedBoostButton;
	
	public Shop(PApplet theApp)
	{
		app = theApp;
		WIN_WIDTH = app.width;
		WIN_HEIGHT = app.height;
		
		SCREEN_EDGE_TOP = WIN_HEIGHT/4;
		SCREEN_EDGE_BOTTOM = 3 * (WIN_HEIGHT/4);
		SCREEN_EDGE_LEFT = WIN_WIDTH/4;
		SCREEN_EDGE_RIGHT = WIN_WIDTH/2;
		
		// User Interface variables
		UI_WIDTH = WIN_WIDTH/4;
		MENU_WALL = WIN_WIDTH - UI_WIDTH;
		BORDER_WIDTH = WIN_WIDTH/160;
		
		RENDER_ORIGIN_X = (WIN_WIDTH - UI_WIDTH)/2;
		RENDER_ORIGIN_Y = WIN_HEIGHT/2;
		RENDER_WIDTH = WIN_WIDTH - UI_WIDTH;
		RENDER_HEIGHT = WIN_HEIGHT;
		width = SCREEN_EDGE_RIGHT;
		height = SCREEN_EDGE_BOTTOM;
		
		currencyIcon = app.loadImage("coins.png");
		standardAmmoIcon = app.loadImage("standardAmmo.png");
		burnAmmoIcon = app.loadImage("burn.png");
		explosiveAmmoIcon = app.loadImage("explosiveShot.png");
		scatterAmmoIcon = app.loadImage("scatterShot.png");
		
		
		scaleFrame();
	}
	
	public void draw(PFont FLOAT_FONT)
	{
		app.pushMatrix();
		app.pushStyle();
		app.translate(RENDER_ORIGIN_X, RENDER_ORIGIN_Y);
		app.fill(WOOD_COLOR.getRGB(), 180);
		app.rect(0, 0, width + BORDER_WIDTH*2, height + BORDER_WIDTH*2, 20);
		app.fill(TRIM_COLOR.getRGB(), 150);
		app.rect(0, 0, width, height, 20);
		
		app.fill(WOOD_COLOR.getRGB());
		app.rect(column1, row1, unitWidth, unitHeight, 20);
		app.rect(column2, row1, unitWidth, unitHeight, 20);
		app.rect(column3, row1, unitWidth, unitHeight, 20);
		app.rect(column1, row2, unitWidth, unitHeight, 20);
		app.rect(column2, row2, unitWidth, unitHeight, 20);
		app.rect(column3, row2, unitWidth, unitHeight, 20);
		
		app.fill(AMMO_BACKGROUND.getRGB());
		app.rect(column1, row1 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rect(column2, row1 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rect(column3, row1 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rect(column1, row2 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rect(column3, row2 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		
		float imageSize = 150;
		app.image(standardAmmoIcon, column1 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(burnAmmoIcon, column2 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(explosiveAmmoIcon, column3 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(scatterAmmoIcon, column1 - unitWidth/6, row2 - unitHeight*5/12, imageSize, imageSize);
		
		imageSize = 50;
		app.image(currencyIcon, column1 - unitWidth*5/12, row1 + unitHeight/16, imageSize, imageSize);
		
		app.fill(255);
		app.rect(column2, row2 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rectMode(app.CENTER);
		app.fill(255, 0, 0);
		app.noStroke();
		app.rect(column2, row2 - unitHeight/7, unitWidth/1.6f, unitHeight/6);
		app.rect(column2, row2 - unitHeight/7, unitHeight/6, unitWidth/1.6f);
		
		
		
		standardAmmoButton.draw(FLOAT_FONT);
		fireAmmoButton.draw(FLOAT_FONT);
		explosiveAmmoButton.draw(FLOAT_FONT);
		scatterAmmoButton.draw(FLOAT_FONT);
		healthPackButton.draw(FLOAT_FONT);
		speedBoostButton.draw(FLOAT_FONT);
		
		
		
		app.popStyle();
		app.popMatrix();
	}
	
	private void drawOption(PImage icon, int price, int amount)
	{
		
	}
	
	public void scaleFrame()
	{
		unitWidth = width/4;
		unitHeight = height/3;
		
		column1 = -width/3;
		column2 = 0;
		column3 = width/3;
		
		row1 = -height/4;
		row2 = height/4;
		
		standardAmmoButton = new Button(app, "Free", column1 - unitWidth/3, row1 + unitHeight/5);
		fireAmmoButton = new Button(app, "Buy", column2 - unitWidth/3, row1 + unitHeight/5);
		explosiveAmmoButton = new Button(app, "Buy", column3 - unitWidth/3, row1 + unitHeight/5);
		scatterAmmoButton = new Button(app, "Buy", column1 - unitWidth/3, row2 + unitHeight/5);
		healthPackButton = new Button(app, "Buy", column2 - unitWidth/3, row2 + unitHeight/5);
		speedBoostButton = new Button(app, "Buy", column3 - unitWidth/3, row2 + unitHeight/5);
	}
	
	public boolean isInside(float pointX, float pointY)
	{
		return (pointX > SCREEN_EDGE_LEFT/2 && pointX < SCREEN_EDGE_RIGHT*5/4 && pointY > SCREEN_EDGE_TOP/2 && pointY < SCREEN_EDGE_BOTTOM*9/8);
	}
	
	public Button getStandardAmmoButton()
	{
		return standardAmmoButton;
	}
	
	public Button getFireAmmoButton()
	{
		return fireAmmoButton;
	}
	
	public Button getExplosiveAmmoButton()
	{
		return explosiveAmmoButton;
	}
	
	public Button getScatterAmmoButton()
	{
		return scatterAmmoButton;
	}
	
	public Button getHealthPackButton()
	{
		return healthPackButton;
	}
	
	public Button getSpeedBoostButton()
	{
		return speedBoostButton;
	}

}
