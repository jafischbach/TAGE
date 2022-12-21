package items;

import game.Game;
import game.Item;

public class Corkscrew extends Item {

	public Corkscrew() {
		super("corkscrew");
	}
	
	@Override
	public void look() {
		Game.print("It's a corkscrew! You can screw it into a cork and, you know, uncork stuff.");
	}
	
	@Override
	public void use() {
		Game.print("Sadly, you have nothing to uncork.");
	}
	
	@Override
	public void take(String command) {
		Game.print("You already have the corkscrew.");
	}
	
}
