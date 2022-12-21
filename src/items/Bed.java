package items;

import game.Game;
import game.Item;

public class Bed extends Item {

	public Bed() {
		super("bed");
	}
	
	@Override
	public void look() {
		Game.print("It's just a bed.");
	}
	
	@Override
	public void use() {
		Game.print("You really want to sleep in this hotel? No. No, you do not.");
	}
	
}
