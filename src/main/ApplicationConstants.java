package main;

import java.awt.Color;

public interface ApplicationConstants 
{
	float WIN_WIDTH = 3200;
	float WIN_HEIGHT = 1800;
	
	float SCREEN_EDGE_TOP = WIN_HEIGHT/4;
	float SCREEN_EDGE_BOTTOM = 3 * (WIN_HEIGHT/4);
	float SCREEN_EDGE_LEFT = WIN_WIDTH/4;
	float SCREEN_EDGE_RIGHT = WIN_WIDTH/2;
	
	// User Interface variables
	float UI_WIDTH = WIN_WIDTH/4;
	float MENU_WALL = WIN_WIDTH - UI_WIDTH;
	float BORDER_WIDTH = WIN_WIDTH/160;
	
	int WORLD_DIMENSION = 50;
	float TILE_SIZE = 100;
	
		
	
	float RENDER_ORIGIN_X = (WIN_WIDTH - UI_WIDTH)/2;
	float RENDER_ORIGIN_Y = WIN_HEIGHT/2;
	float RENDER_WIDTH = WIN_WIDTH - UI_WIDTH;
	float RENDER_HEIGHT = WIN_HEIGHT;
	
	float TANK_SIZE = 120;

	float BUTTON_WIDTH = 250;						// default button width
	float BUTTON_HEIGHT = 80;						// default button height
	
	Color TREAD_COLOR = new Color(0, 51, 51);
	
	Color AMMO_BACKGROUND = new Color(66, 64, 77);
	Color TRIM_COLOR = new Color(125, 93, 6);
	Color BUTTON_COLOR = new Color(125, 93, 6);	// default button color
	Color DIRT_COLOR = new Color(87, 59, 12);
	Color GRASS_COLOR = new Color(0, 92, 9);
	Color WATER_COLOR = new Color(76, 96, 205);
	Color STONE_COLOR = new Color(125, 136, 127);
	Color WOOD_COLOR = new Color(86, 47, 14);

}
