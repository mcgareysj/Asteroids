package AsteroidsGame;

import java.awt.Color;
import java.util.LinkedList;

import Entities.*;
import edu.princeton.cs.introcs.StdDraw;

public class GameScene {
	
	private LinkedList<Entity> entities = new LinkedList<Entity>();
	
	public GameScene()
	{
		StdDraw.setXscale(-500,500);
		StdDraw.setYscale(-500,500);

	}
	
	public void draw() {
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		StdDraw.filledSquare(0, 0, 500);
		for (Entity e: entities) {
			e.draw();
		}
		StdDraw.setPenColor(Color.DARK_GRAY);
		StdDraw.filledRectangle(0, 490, 500, 10);
		StdDraw.filledRectangle(0, -490, 500, 10);
		StdDraw.filledRectangle(490, 0, 10, 500);
		StdDraw.filledRectangle(-490, 0, 10, 500);

	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void updatePositions() {
		LinkedList<Entity> toRemove = new LinkedList<Entity>();
		for (Entity e : entities) {
			e.updatePosition();
			if(e.outOfBounds()) {
				toRemove.add(e);
			}
		}
		entities.removeAll(toRemove);
		toRemove.clear();
		
		
	}
	
			// This big mess below is collision detection using the Separating Axis Theorem.
			// the big long OR statement in the if conditional is testing to see if the two objects are
			// in the vicinity of each other- myHash produces an int from 0-99 based on where in the
			// frame the Entity is by splitting the frame into 100 grid boxes. If two entities are
			// in the same or adjacent grid boxes, then we will test if they are colliding with SAT
	public void checkCollisions() {
		LinkedList<Entity> toRemove = new LinkedList<Entity>();
		for(Entity e : entities) {
			for (Entity other : entities) {
				if (!e.equals(other) && (e.getClass() != other.getClass()) && ( (other.myHash() == e.myHash() - 11) ||
										   									  (other.myHash() == e.myHash() - 10) ||
										   									  (other.myHash() == e.myHash() - 9)  ||
										   									  (other.myHash() == e.myHash() - 1)  ||
										   									  (other.myHash() == e.myHash())      ||
										   									  (other.myHash() == e.myHash() + 1)  ||
										   									  (other.myHash() == e.myHash() + 9)  ||
										   									  (other.myHash() == e.myHash() + 10) ||
										   									  (other.myHash() == e.myHash() + 11)) )
				{
					if(e.isCollidingBySAT(other) ) {
						if(!toRemove.contains(e)) {
							toRemove.add(e);
						}
						if(!toRemove.contains(other)) {
							toRemove.add(other);
						}
						
					}
				}
			}
		}
		entities.removeAll(toRemove);
		toRemove.clear();
	}
	
	public boolean containsPlayer(Player p) {
		return entities.contains(p);
	}
	
}
