package entities;

import main.ApplicationConstants;
import processing.core.PApplet;
import processing.core.PVector;

public class Particle implements ApplicationConstants
{
	
	// 	Here we store a reference to the app. in a static (aka "class") variable.
	private static PApplet app; 	
	private static int appSetCounter_ = 0;

	PVector position;
	PVector velocity;
	PVector acceleration;
	float lifespan;

	Particle(PApplet app, PVector l, float dx, float dy) 
	{
		this.app = app;
		float vmax = 100;
		acceleration = new PVector(dx + (app.random(-15, 15)), dy + app.random(-15, 15));
		velocity = new PVector(app.random(-vmax, vmax)/app.frameRate, app.random(-vmax, vmax)/app.frameRate);
		//velocity = new PVector(random(-0.5, 0.5) * PIXEL_TO_WORLD, random(-0.5, 0.5) * PIXEL_TO_WORLD);
		position = l.copy();
		lifespan = 1.0f;
	}

	public void run() 
	{
		update();
		display();
	}

	// Method to update position
	public void update() 
	{
		velocity.add(acceleration);
		position.add(velocity);
		lifespan -= 1.0f;
	}

	// Method to display
	public void display() 
	{
		app.pushMatrix();
		app.strokeWeight(1);
		app.scale(-(float)0.5, -(float)0.5);
		app.stroke(255, lifespan);
		app.fill(255, app.random(255), 0);
		app.ellipse(position.x, position.y, 10, 10);
		app.popMatrix();
	}

	// Is the particle still useful?
	public boolean isDead() 
	{
		if (lifespan < 0.0f) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
}
