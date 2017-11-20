package main;

import entities.Button;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Shop implements ApplicationConstants
{
	private PApplet app;
	
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
	
	private int standardAmmoPrice;
	private int burnAmmoPrice;
	private int explosiveAmmoPrice;
	private int scatterAmmoPrice;
	
	public Shop(PApplet theApp)
	{
		app = theApp;
		width = SCREEN_EDGE_RIGHT;
		height = SCREEN_EDGE_BOTTOM;
		
		currencyIcon = app.loadImage("coins.png");
		standardAmmoIcon = app.loadImage("standardAmmo.png");
		burnAmmoIcon = app.loadImage("burn.png");
		explosiveAmmoIcon = app.loadImage("explosiveShot.png");
		scatterAmmoIcon = app.loadImage("scatterShot.png");
		
		standardAmmoPrice = 0;
		burnAmmoPrice = 5;
		explosiveAmmoPrice = 10;
		scatterAmmoPrice = 10;
		
		
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
		app.rect(column2, row2 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		app.rect(column3, row2 - unitHeight/8, unitWidth*7/8, unitHeight*5/8, 20);
		
		float imageSize = 150;
		app.image(standardAmmoIcon, column1 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(burnAmmoIcon, column2 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(explosiveAmmoIcon, column3 - unitWidth/6, row1 - unitHeight*5/12, imageSize, imageSize);
		app.image(scatterAmmoIcon, column1 - unitWidth/6, row2 - unitHeight*5/12, imageSize, imageSize);
		
		imageSize = 50;
		app.image(currencyIcon, column1 - unitWidth*5/12, row1 + unitHeight/16, imageSize, imageSize);
		
		
		
		standardAmmoButton.draw(FLOAT_FONT);
		fireAmmoButton.draw(FLOAT_FONT);
		explosiveAmmoButton.draw(FLOAT_FONT);
		scatterAmmoButton.draw(FLOAT_FONT);
		
		
		
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

}
