package items;

import game.Game;

public class Nightstand extends Item {

	public Nightstand(String name) {
		super(name);
	}
	
	public void look() {
		Game.print("It's just a simple nightstand with a single drawer.");
		Game.getCurrentRoom().addItem(new Drawer("drawer", 101));
	}
	
	public void open() {
		Game.print("Try opening the drawer.");
	}
	
}
