package items;

import game.*;

public class Refrigerator extends Item {

	public Refrigerator() {
		super("refrigerator");
	}
	
	public void look() {
		Game.print("It's a refrigerator!");
	}
	
}
