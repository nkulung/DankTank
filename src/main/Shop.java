package main;

import entities.Button;
import processing.core.PApplet;

public class Shop implements ApplicationConstants
{
	private PApplet app;
	
	private float width;
	private float height;
	
	private Button speedButton;
	
	public Shop(PApplet theApp)
	{
		app = theApp;
		width = UI_WIDTH - BORDER_WIDTH*4;
		height = WIN_HEIGHT/4;
	}
	
	public void draw()
	{
		app.pushMatrix();
		app.translate(MENU_WALL + UI_WIDTH/2, WIN_HEIGHT*5/8);
		app.pushStyle();
		app.fill(255);
		app.rect(0, 0, width, height, 10);
		
		app.popStyle();
		app.popMatrix();
	}

}
