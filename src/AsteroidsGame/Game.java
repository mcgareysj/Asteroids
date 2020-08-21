package AsteroidsGame;

import Entities.*;
import edu.princeton.cs.introcs.StdDraw;

public class Game {

	public static void main(String[] args) {
		StdDraw.enableDoubleBuffering();
		GameScene gs = new GameScene();
		Player p1 = new Player(new Point(0.5,0.5));
		gs.addEntity(p1);
		for(int i = 0; gs.containsPlayer(p1) ; ++i) {
			if (i % 50 == 0) {
				gs.addEntity(new Asteroid(Math.random()*40 + 20, Math.random()*2.5 + 0.5,
						     Math.random() < 0.5 ? 
						     new Point(Math.random() < 0.5 ? Math.random()*50 - 450 : Math.random()*50 + 450, Math.random()*900 - 450) :
						   	 new Point(Math.random()*900 - 450, Math.random() < 0.5 ? Math.random()*50 - 450 : Math.random()*50 + 450)));
				//gs.addEntity(new Asteroid(50, 0, new Point(250,-250)));
			}
			gs.updatePositions();
			gs.checkCollisions();
			if(StdDraw.isKeyPressed(32) && p1.canAttack(i)) {
				Laser l = p1.attack(i); // Pass in i to determine if the player can fire or not
										// based on the player's fire rate
				gs.addEntity(l);

				
			}
			StdDraw.clear();
			gs.draw();
			StdDraw.show();
			StdDraw.pause(33);
		}
	}
}
