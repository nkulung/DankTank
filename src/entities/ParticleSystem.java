package entities;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ParticleSystem 
{
	PApplet app;
	ArrayList<Particle> particles;
	PVector origin;
	
	float angle;

	ParticleSystem(PApplet app, PVector position, float angle) 
	{
		this.app = app;
		origin = position.copy();
		this.angle = angle;
		particles = new ArrayList<Particle>();
	}

	public void addParticle(float dx, float dy) 
	{
		particles.add(new Particle(app, origin, dx, dy));
		particles.add(new Particle(app, origin, dx, dy));
		particles.add(new Particle(app, origin, dx, dy));
	}

	public void run() 
	{
		app.rotate(PApplet.radians(angle));
		for (int i = particles.size()-1; i >= 0; i--) 
		{
			Particle p = particles.get(i);
			p.run();
			if (p.isDead()) 
			{
				particles.remove(i);
			}
		}
	}
}
