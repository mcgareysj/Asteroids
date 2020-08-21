package Entities;

import java.awt.Color;

import AsteroidsGame.Point;
import edu.princeton.cs.introcs.StdDraw;

public class Laser extends Entity {
	
	private double speed = 7;

	/**
	 * Constructor for Laser class
	 * @param vertices - assumed to be two points only
	 */
	public Laser(Point[] vertices) {
		super(vertices, Color.RED, vertices[1]);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updatePosition() {
		double dx = vertices[1].x - vertices[0].x;
		double dy = vertices[1].y - vertices[0].y;
		
		
		for (Point p : vertices) {
			p.x += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dx;
			p.y += (speed/Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2)))*dy;
		}
	}
	
	@Override
	public boolean outOfBounds() {
		if (vertices[1].x > 500 || vertices[1].x < -500) {
			return true;
		} else if (vertices[1].y > 500 || vertices[1].y < -500) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void draw() {
		StdDraw.setPenColor(team);
		StdDraw.line(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y);
	}

}
