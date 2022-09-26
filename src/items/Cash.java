package items;

import game.*;

public class Cash extends Item {
	
	private boolean isTaken;
	
	public Cash() {
		super("cash");
		isTaken = false;
	}
	
	public void look() {
		Game.print("That's a whole lot of sweet legal tender!");
	}
	
	public void use() {
		if (isTaken)
			Game.print("You fan yourself with the cash and feel much cooler.");
		else
			Game.print("You don't have the cash!");
	}
	
	public void take() {
		if (isTaken)
			Game.print("You already have the cash.");
		else {
			Game.print("Hot damn! You stuff your pockets with all of the cash"
					+ " you find hidden beneath the trap door. You'll count it"
					+ " later, but you're pretty sure this is the seed for your"
					+ " beach house fund. Assuming you ever get out of this damn"
					+ " hotel that is.");
			isTaken = true;
			Player.addItem(this);
			Game.getCurrentRoom().removeItem("cash");
		}
	}
	
}
