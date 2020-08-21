package Entities;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;

import AsteroidsGame.Point;
import edu.princeton.cs.introcs.StdDraw;

public abstract class Entity {

	
	protected Point[] vertices;
	protected Color team;
	protected Point center;
	
	public Entity(Point[] vertices, Color team, Point center) {
		this.vertices = vertices;
		this.team = team;
		this.center = center;
	}

	public Point[] getVertices() {
		return vertices;
	}

	public void setVertices(Point[] vertices) {
		this.vertices = vertices;
	}
	
	public void draw() {
		double[] x = new double[vertices.length];
		double[] y = new double[vertices.length];
		for (int i = 0; i < vertices.length; ++i) {
			x[i] = vertices[i].x;
			y[i] = vertices[i].y;
		}
		StdDraw.setPenColor(team);
		StdDraw.polygon(x, y);
	}

	public void updatePosition() {
		
	}
	
	public int myHash() {
		return center.myHash();
	}
	
	/**
	 * Method to determine whether an entity is out of bounds and can be safely deleted.
	 * The method in subclasses will assume a -500 to 500 bounds by default.
	 * The default of the method is to return false and assume that the entity is always
	 * in bounds.
	 * @return boolean indicating whether the entity is out of bounds
	 */
	public boolean outOfBounds() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		result = prime * result + Arrays.hashCode(vertices);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		if (!Arrays.equals(vertices, other.vertices))
			return false;
		return true;
	}
	
	public boolean isCollidingBySAT(Entity other) {
		LinkedList<Point> axisWithOrigin = new LinkedList<Point>(); // the points in this list will
																	// form perpendicular lines
																	// with the origin (0,0) that the
																	// two entities will be projected
																	// onto
		// add the axes formed from this Entity to the list
		for(int i = 0; i < vertices.length; i++) {
			if(i == vertices.length-1) {
				axisWithOrigin.add(new Point(1, -(vertices[i].x - vertices[0].x)/
						 						 (vertices[i].y - vertices[0].y)));
			} else {
				axisWithOrigin.add(new Point(1, -(vertices[i].x - vertices[(i+1)].x)/
						 						 (vertices[i].y - vertices[(i+1)].y)));
												// the above code is the formula for perpendicular slope
			}
			
		}
		
		// add the axes formed from the other Entity to the list
		for(int i = 0; i < other.vertices.length; i++) {
			if(i == other.vertices.length-1) {
				axisWithOrigin.add(new Point(1, -(other.vertices[i].x - other.vertices[0].x)/
						 						 (other.vertices[i].y - other.vertices[0].y)));
			} else {
				axisWithOrigin.add(new Point(1, -(other.vertices[i].x - other.vertices[(i+1)].x)/
						 						 (other.vertices[i].y - other.vertices[(i+1)].y)));
												// the above code is the formula for perpendicular slope
			}
		}
		for(Point p : axisWithOrigin) {
			p.makeIntoUnitVector();
		}
		
		// for each axis we need to check, call the appropriate methods
		for(Point lineDef : axisWithOrigin) {
			double thisMax = Double.NEGATIVE_INFINITY;
			double thisMin = Double.POSITIVE_INFINITY;
			for(Point vertex : this.vertices) {
				double dist = vertex.dotProduct(lineDef);
				if(dist > thisMax) {
					thisMax = dist;
				}
				if(dist < thisMin) {
					thisMin = dist;
				}

			}
			double otherMax = Double.NEGATIVE_INFINITY;
			double otherMin = Double.POSITIVE_INFINITY;
			for(Point vertex : other.vertices) {
				double dist = vertex.dotProduct(lineDef);
				if(dist > otherMax) {
					otherMax = dist;
				}
				if(dist < otherMin) {
					otherMin = dist;
				}

			}
			// if either of the extremes for this are NOT within line segment defined by others' 
			// extremes, then the lines are not colliding and the method should return false
			if (thisMax < otherMin ||thisMin > otherMax)
			{
				return false;
			}
		}
		// if NONE of the axes had a separating axis then the objects are colliding
		// and the method should return true
		return true;
	}
}
