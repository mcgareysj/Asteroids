package Entities;

import java.awt.Color;
import java.util.Random;

import AsteroidsGame.Point;

public class Asteroid extends Entity {
	
	private static Random rng = new Random(System.currentTimeMillis());
		
	private double speed;
	double dx;
	double dy;

	public Asteroid(double radius, double speed, Point center) {
		super(new Point[] { new Point(radius, rng.nextDouble()*2*Math.PI/5, 0),
							new Point(radius, rng.nextDouble()*2*Math.PI/5, Math.PI/5*2),
							new Point(radius, rng.nextDouble()*2*Math.PI/5, Math.PI/5*4),
							new Point(radius, rng.nextDouble()*2*Math.PI/5, Math.PI/5*6),
							new Point(radius, rng.nextDouble()*2*Math.PI/5, Math.PI/5*8),
						  }, new Color(204, 85, 0), center);
		this.speed = speed;
		this.center = center;
		for(Point p : vertices) {
			p.x += center.x;
			p.y += center.y;
		}
		Point velocityVec = new Point(100, rng.nextDouble()*2*Math.PI, 0);
		this.dx = velocityVec.x - center.x;
		this.dy = velocityVec.y - center.y;
	}
	
	@Override
	public boolean outOfBounds() {
		if (center.x >= 500 || center.x < -500) {
			return true;
		} else if (center.y >= 500 || center.y < -500) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updatePosition() {
		for (Point p : vertices) {
			p.x += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dx;
			p.y += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dy;
		}
		center.x += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dx;
		center.y += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dy;
	}
	

}
