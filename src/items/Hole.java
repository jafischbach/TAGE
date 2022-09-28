package items;

import game.*;

public class Hole extends Item {

	public Hole() {
		super("hole");
	}
	
	public void look() {
		Game.print("Looks like a mouse hole.");
	}
	
}
