package main;

import java.awt.Color;

public interface ApplicationConstants 
{

	int WORLD_DIMENSION = 50;
	float TILE_SIZE = 100;
	
	float TANK_SIZE = 120;
	float AMMO_CAPACITY = 50;
	
	int STANDARD_AMMO_PRICE = 5;
	int EXPLOSIVE_AMMO_PRICE = 20;
	int FIRE_AMMO_PRICE = 10;
	int SCATTER_AMMO_PRICE = 20;
	int HEALTH_PACK_PRICE = 15;
	int SPEED_BOOST_PRICE = 500;
	
	int MAX_SPEED = 50;
	
	float PLAYER_SPAWN_X = 400;
	float PLAYER_SPAWN_Y = 500;

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
