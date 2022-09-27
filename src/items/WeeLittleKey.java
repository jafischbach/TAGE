package items;

import game.*;

public class WeeLittleKey extends Item {

	public WeeLittleKey() {
		super("wee little key");
	}
	
	public void look() {
		Game.print("It's a wee little key. Be careful not to drop it! It's actuall smaller than the tiny key you"
				+ " found earlier.");
	}
	
	public void take() {
		Game.print("You're already carrying the wee little key.");
	}
	
}
