package items;

import game.*;

public class Corpse extends Item {

	public Corpse() {
		super("corpse");
	}
	
	public void look() {
		Game.print("Yup. It's a dead guy.");
	}
	
}
