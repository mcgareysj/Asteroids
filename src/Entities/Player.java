package Entities;

import java.awt.Color;

import AsteroidsGame.Point;
import edu.princeton.cs.introcs.StdDraw;

public class Player extends Entity{
	
	private double speed = 0;
	private int degreesPerRotation = 5;
	private int fireRate = 30; // fireRate is used to determine if the player can fire.
							   // it does this by comparing what frame the player last fired on
							   // with the current frame they are trying to fire on. If the difference
							   // in frames is greater than or equal to the fireRate, the player can
	private int lastFrameFired = -30; // default to -30 so the player can fire instantly

	public Player(Point center) {
		super(new Point[] {new Point(center.x, center.y+25),
						   new Point(center.x-20, center.y-15),
						   new Point(center.x+20, center.y-15)}, 
			  Color.BLACK, center);
	}
	
	public void updatePosition() {
			// check if any key has been pressed and update the figure accordingly
			if (StdDraw.isKeyPressed(87)) {
				if (speed < 5) {
					speed += 0.333;
				}

			}
			if (StdDraw.isKeyPressed(83)) {
				if(speed > 0) {
					speed -= 0.333;
					if (speed < 0) {
						speed = 0;
					}
				}
				
			}
			if(StdDraw.isKeyPressed(65)) {
				for(Point p : vertices) {
					p.RotateAroundCenter(degreesPerRotation, center);
				}
			} 
			if(StdDraw.isKeyPressed(68)) {
				for(Point p : vertices) {
					p.RotateAroundCenter(-degreesPerRotation, center);
				}
			}
			// Update the location of all the points based on direction and speed.
			// direction is based off of the 0th and 2nd points in the vertices array,
			// as those points bisect the figure.
			double dx = vertices[0].x - center.x;
			double dy = vertices[0].y - center.y;
			
			
			for (Point p : vertices) {
				p.x += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dx;
				p.y += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dy;
			}
			center.x += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dx;
			center.y += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dy;
			
			// Wrap the entire object to the other side if the center is OOB
			if(center.x >= 500) {
				for (Point p : vertices) {
					p.x = ((p.x + 25) % 500) - 500;
				}
				center.x = ((center.x + 25) % 500) - 500;
			} else if (center.x < -500) {
				for (Point p : vertices) {
					p.x = ((p.x - 25) % 500) + 500;
				}
				center.x = ((center.x - 25) % 500) + 500;
			}
			if(center.y >= 500) {
				for (Point p : vertices) {
					p.y = ((p.y + 25) % 500) - 500;
				}
				center.y = ((center.y + 25) % 500) - 500;
			} else if (center.y < -500) {
				for (Point p : vertices) {
					p.y = ((p.y - 25) % 500) + 500;
				}
				center.y = ((center.y - 25) % 500) + 500;
			}
			

		}
	
	public boolean canAttack(int currentFrame) {
		if(currentFrame - this.lastFrameFired >= this.fireRate) {
			return true;
		} else {
			return false;
		}
	}

	public Laser attack(int currentFrame) {
			double dx = vertices[0].x - center.x;
			double dy = vertices[0].y - center.y;
			this.lastFrameFired = currentFrame;
			return(new Laser(new Point[] {new Point(vertices[0].x + dx/10, vertices[0].y + dy/10),
										  new Point(vertices[0].x + 3*dx/4, vertices[0].y + 3*dy/4)}));
			
		
	}
	
	@Override
	public void draw() {
		double[] x = new double[vertices.length+1];
		double[] y = new double[vertices.length+1];
		for (int i = 0; i < vertices.length; ++i) {
			if (i == 2) { // this if statement is the only difference between this draw method
						  // and the superclass's draw method. The purpose of this difference is
						  // to treat the player like a convex polygon (a triangle specifically)
						  // for calculating collisions, but to draw it as a convex figure.
				x[i] = center.x;
				y[i] = center.y;
				x[i+1] = vertices[i].x;
				y[i+1] = vertices[i].y;
			} else {
				x[i] = vertices[i].x;
				y[i] = vertices[i].y;
			}
		}
		StdDraw.setPenColor(team);
		StdDraw.polygon(x, y);
	}
}

