package items;

import game.*;
import characters.Mouse;

public class Hole extends Item {

	public Hole() {
		super("hole");
	}
	
	public void look() {
		Game.print("Looks like a mouse hole. Since there's a mouse sitting in it,"
				+ " it probably is a mouse hole.");
		Game.getCurrentRoom().addNPC(new Mouse());
	}
	
}
