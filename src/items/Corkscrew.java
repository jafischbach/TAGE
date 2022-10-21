package items;

import game.Game;
import game.Item;

public class Corkscrew extends Item {

	public Corkscrew() {
		super("corkscrew");
	}
	
	public void look() {
		Game.print("It's a corkscrew! You can screw it into a cork and, you know, uncork stuff.");
	}
	
	public void use() {
		Game.print("Sadly, you have nothing to uncork.");
	}
	
	public void take() {
		Game.print("You already have the corkscrew.");
	}
	
}
