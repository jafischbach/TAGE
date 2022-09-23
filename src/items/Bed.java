package items;

import game.Game;

public class Bed extends Item {

	public Bed() {
		super("bed");
	}
	
	public void look() {
		Game.print("It's just a bed.");
	}
	
	public void use() {
		Game.print("You really want to sleep in this hotel? No. No, you do not.");
	}
	
}
