package items;

import game.*;

public class Nightstand extends Item {

	public Nightstand(String name) {
		super(name);
	}

	@Override
	public void look() {
		Game.print("It's just a simple nightstand with a single drawer.");
	}

	@Override
	public void open() {
		Game.print("Try opening the drawer.");
	}

}
