package items;

import game.Game;
import game.Player;

public class Crowbar extends Item {

	private boolean isTaken;
	
	public Crowbar(String name) {
		super(name);
		isTaken = false;
	}
	
	public void look() {
		Game.print("What a cool crowbar!");
	}
	
	public void take() {
		if(isTaken)
			Game.print("You already have the crowbar.");
		else {
			Game.print("Cool! You can always use a crowbar. Imagine all the things you"
					+ " can pry open now!");
			Player.addItem(this);
			Game.getCurrentRoom().removeItem("crowbar");
		}
	}
	
}
