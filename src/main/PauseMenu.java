package main;

import javax.swing.JOptionPane;

import entities.Button;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.data.JSONObject;

public class PauseMenu implements ApplicationConstants
{
	private PApplet app;
	private PFont buttonFont;
	
	private float WIN_WIDTH;
	private float WIN_HEIGHT;
	private float RENDER_ORIGIN_X;
	private float RENDER_ORIGIN_Y;
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	private int roundCounter;
	
	private Button saveButton;
	private Button loadButton;
	
	private Button saveGameOneButton;
	private Button saveGameTwoButton;
	private Button saveGameThreeButton;
	
	private Button loadGameOneButton;
	private Button loadGameTwoButton;
	private Button loadGameThreeButton;
	
	private Mode mode;
	private Mode saveMode;
	private Mode loadMode;
	
	public PauseMenu(PApplet app, PFont buttonFont, float x, float y, float width, float height, int roundCounter)
	{
		this.app = app;
		this.buttonFont = buttonFont;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.roundCounter = roundCounter;
		mode = Mode.NO_MODE;
		saveMode = Mode.NO_MODE;
		loadMode = Mode.NO_MODE;
		
		saveButton = new Button(this.app, "Save", this.x*0.9f, this.y*0.8f);
		loadButton = new Button(this.app, "Load", this.x*0.9f, this.y);
		
		saveGameOneButton = new Button(this.app, "Save 1", this.x*0.9f, this.y*0.75f);
		saveGameTwoButton = new Button(this.app, "Save 2", this.x*0.9f, this.y*0.9f);
		saveGameThreeButton = new Button(this.app, "Save 3", this.x*0.9f, this.y*1.05f);
		
		loadGameOneButton = new Button(this.app, "Load 1", this.x*0.9f, this.y*0.75f);
		loadGameTwoButton = new Button(this.app, "Load 2", this.x*0.9f, this.y*0.9f);
		loadGameThreeButton = new Button(this.app, "Load 3", this.x*0.9f, this.y*1.05f);
		
		WIN_WIDTH = app.width;
		WIN_HEIGHT = app.height;
		RENDER_ORIGIN_X = (WIN_WIDTH - WIN_WIDTH/4)/2;
		RENDER_ORIGIN_Y = WIN_HEIGHT/2;
	}
	
	/**
	 * Method to draw the pause menu
	 */
	public void draw()
	{
		app.pushStyle();			// saves the current style
		app.rectMode(PConstants.CENTER);
		app.fill(WOOD_COLOR.getRGB());
		app.rect(RENDER_ORIGIN_X, RENDER_ORIGIN_Y, width, height, 20);
		app.fill(255);
		app.rect(RENDER_ORIGIN_X, RENDER_ORIGIN_Y, width*0.95f, height*0.95f, 20);
		
		if(mode == Mode.NO_MODE)
		{
			saveButton.draw(buttonFont);
			loadButton.draw(buttonFont);
			
		}
		else if(mode == Mode.SAVE)
		{
			saveGameOneButton.draw(buttonFont);
			saveGameTwoButton.draw(buttonFont);
			saveGameThreeButton.draw(buttonFont);
		}
		else if(mode == Mode.LOAD)
		{
			loadGameOneButton.draw(buttonFont);
			loadGameTwoButton.draw(buttonFont);
			loadGameThreeButton.draw(buttonFont);
		}
		
		app.popStyle();				// restores the previous style
	}
	
	public void saveGame(Player player, int round)
	{
		JSONObject playerSave = playerToJSON(player, round);
		if(saveMode == Mode.SAVE_GAME_ONE)
		{
			try
			{
				app.saveJSONObject(playerSave, "SaveGameOne.json");
				JOptionPane.showMessageDialog(null, "Game file 1 Overwritten!");
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to save to game file 1.");
			}
		}
		else if(saveMode == Mode.SAVE_GAME_TWO)
		{
			try
			{
				app.saveJSONObject(playerSave, "SaveGameTwo.json");
				JOptionPane.showMessageDialog(null, "Game file 2 Overwritten!");
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to save to game file 2.");
			}
		}
		else if(saveMode == Mode.SAVE_GAME_THREE)
		{
			try
			{
				app.saveJSONObject(playerSave, "SaveGameThree.json");
				JOptionPane.showMessageDialog(null, "Game file 3 Overwritten!");
			} catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to save to game file 3.");
			}
		}
	}
	
	/**
	 * Method to load player data from a json file 
	 */
	public Player loadGame()
	{
		Player loadPlayer = null;
		if(loadMode == Mode.LOAD_GAME_ONE)
		{
			try
			{
				JSONObject data = app.loadJSONObject("SaveGameOne.json");
				loadPlayer = JSONtoPlayer(data);
				if(loadPlayer != null)
					JOptionPane.showMessageDialog(null, "loaded game file 1!");
				else
					JOptionPane.showMessageDialog(null, "game file 1 returned null.");
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to load game file 1.");
			}
		}
		if(loadMode == Mode.LOAD_GAME_TWO)
		{
			try
			{
				JSONObject data = app.loadJSONObject("SaveGameTwo.json");
				loadPlayer = JSONtoPlayer(data);
				JOptionPane.showMessageDialog(null, "loaded game file 2!");
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to load game file 2.");
			}
		}
		if(loadMode == Mode.LOAD_GAME_THREE)
		{
			try
			{
				JSONObject data = app.loadJSONObject("SaveGameThree.json");
				loadPlayer = JSONtoPlayer(data);
				JOptionPane.showMessageDialog(null, "loaded game file 3!");
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Failed to load game file 3.");
			}
		}
		
		return loadPlayer;
	}

	/**
	 * @return the saveButton
	 */
	public Button getSaveButton() {
		return saveButton;
	}

	/**
	 * @param saveButton the saveButton to set
	 */
	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}

	/**
	 * @return the loadButton
	 */
	public Button getLoadButton() {
		return loadButton;
	}

	/**
	 * @param loadButton the loadButton to set
	 */
	public void setLoadButton(Button loadButton) {
		this.loadButton = loadButton;
	}

	/**
	 * @return the saveGameOneButton
	 */
	public Button getSaveGameOneButton() {
		return saveGameOneButton;
	}

	/**
	 * @param saveGameOneButton the saveGameOneButton to set
	 */
	public void setSaveGameOneButton(Button saveGameOneButton) {
		this.saveGameOneButton = saveGameOneButton;
	}

	/**
	 * @return the saveGameTwoButton
	 */
	public Button getSaveGameTwoButton() {
		return saveGameTwoButton;
	}

	/**
	 * @param saveGameTwoButton the saveGameTwoButton to set
	 */
	public void setSaveGameTwoButton(Button saveGameTwoButton) {
		this.saveGameTwoButton = saveGameTwoButton;
	}

	/**
	 * @return the saveGameThreeButton
	 */
	public Button getSaveGameThreeButton() {
		return saveGameThreeButton;
	}

	/**
	 * @param saveGameThreeButton the saveGameThreeButton to set
	 */
	public void setSaveGameThreeButton(Button saveGameThreeButton) {
		this.saveGameThreeButton = saveGameThreeButton;
	}

	/**
	 * @return the loadGameOneButton
	 */
	public Button getLoadGameOneButton() {
		return loadGameOneButton;
	}

	/**
	 * @param loadGameOneButton the loadGameOneButton to set
	 */
	public void setLoadGameOneButton(Button loadGameOneButton) {
		this.loadGameOneButton = loadGameOneButton;
	}

	/**
	 * @return the loadGameTwoButton
	 */
	public Button getLoadGameTwoButton() {
		return loadGameTwoButton;
	}

	/**
	 * @param loadGameTwoButton the loadGameTwoButton to set
	 */
	public void setLoadGameTwoButton(Button loadGameTwoButton) {
		this.loadGameTwoButton = loadGameTwoButton;
	}

	/**
	 * @return the loadGameThreeButton
	 */
	public Button getLoadGameThreeButton() {
		return loadGameThreeButton;
	}

	/**
	 * @param loadGameThreeButton the loadGameThreeButton to set
	 */
	public void setLoadGameThreeButton(Button loadGameThreeButton) {
		this.loadGameThreeButton = loadGameThreeButton;
	}
	
	/**
	 * @return the mode
	 */
	public Mode getMode()
	{
		return mode;
	}
	
	/**
	 * @param mode the mode to set
	 */
	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	/**
	 * @return the saveMode
	 */
	public Mode getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(Mode saveMode) {
		this.saveMode = saveMode;
	}

	/**
	 * @return the loadMode
	 */
	public Mode getLoadMode() {
		return loadMode;
	}

	/**
	 * @param loadMode the loadMode to set
	 */
	public void setLoadMode(Mode loadMode) {
		this.loadMode = loadMode;
	}
	
	private JSONObject playerToJSON(Player player, int round)
	{
		JSONObject tankObject = new JSONObject();
		tankObject.setInt("Round", round);
		tankObject.setFloat("Max Health", player.getMaxHealth());
		tankObject.setFloat("Health", player.getHealth());
		tankObject.setFloat("Range", player.getRange());
		tankObject.setFloat("Velocity", player.getVelocity());
		tankObject.setInt("Red", player.getColor().getRed());
		tankObject.setInt("Blue", player.getColor().getBlue());
		tankObject.setInt("Green", player.getColor().getGreen());
		tankObject.setInt("Currency", player.getCurrency());
		tankObject.setInt("Standard Ammo", player.getStandardProjectileRounds());
		tankObject.setInt("Fire Ammo", player.getFireProjectileRounds());
		tankObject.setInt("Explosive Ammo", player.getExplosiveProjectileRounds());
		tankObject.setInt("Scatter Ammo", player.getScatterProjectileRounds());
		return tankObject;
	}
	
	private Player JSONtoPlayer(JSONObject tankObject)
	{
		Player loadPlayer = new Player(app, tankObject.getFloat("Max Health"), 
											tankObject.getFloat("Health"),
											tankObject.getInt("Range"), 
											tankObject.getFloat("Velocity"),
											tankObject.getInt("Red"), 
											tankObject.getInt("Green"), 
											tankObject.getInt("Blue"),
											tankObject.getInt("Currency"), 
											tankObject.getInt("Standard Ammo"), 
											tankObject.getInt("Fire Ammo"),
											tankObject.getInt("Explosive Ammo"), 
											tankObject.getInt("Scatter Ammo"));
		roundCounter = tankObject.getInt("Round");
		
		return loadPlayer;
	}

}
