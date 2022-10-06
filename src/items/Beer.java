package items;

import game.*;

public class Beer extends Item {

	public Beer() {
		super("beer");
	}
	
	public void look() {
		Game.print("It's a can of cool Dr. Hops beer!");
	}
	
	public void use() {
		Game.print("You pop open the can of Dr. Hops beer and chug the cool, refreshing"
				+ " beverage. You smash the empty can against your forehead (because you're"
				+ " awesome that way) and, after a mighty belch, toss the aluminum disk away."
				+ " I hope you didn't need to hold onto that!");
		Player.removeItem("beer");
	}
	
	public void take() {
		if (Player.has("beer"))
			Game.print("You already have a can of beer. How many do you think you can"
					+ " stuff in your pockets?");
		else {
			Game.print("You fetch an ice cold can of Dr. Hops beer and the day seems just"
					+ " a little brighter.");
			Player.addItem(this);
		}
	}
	
	public void open() {
		Game.print("Don't open the can until you're ready to use it!");
	}
	
}