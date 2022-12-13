package items;

import game.*;

public class Nightstand extends Item {

	public Nightstand(String name) {
		super(name);
	}

	public void look() {
		Game.print("It's just a simple nightstand with a single drawer.");
	}

	public void open() {
		Game.print("Try opening the drawer.");
	}

}
