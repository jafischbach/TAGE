package items;

import game.*;

public class Beer extends Item {

	public Beer() {
		super("beer");
	}
	
	@Override
	public void look() {
		Game.print("It's a can of cool Dr. Hops beer!");
	}
	
	@Override
	public void use() {
		Game.print("You pop open the can of Dr. Hops beer and chug the cool, refreshing"
				+ " beverage. You smash the empty can against your forehead (because you're"
				+ " awesome that way) and, after a mighty belch, toss the aluminum disk away."
				+ " I hope you didn't need to hold onto that!");
		Game.player.removeItem("beer");
	}
	
	@Override
	public void take(String command) {
		if (Game.player.has("beer"))
			Game.print("You already have a can of beer. How many do you think you can"
					+ " stuff in your pockets?");
		else {
			Game.print("You fetch an ice cold can of Dr. Hops beer and the day seems just"
					+ " a little brighter.");
			Game.player.addItem(this);
		}
	}
	
	@Override
	public void open() {
		Game.print("Don't open the can until you're ready to use it!");
	}
	
	@Override
	public void uniqueCommand(String command) {
		if (command.equals("drink"))
			use();
		else
			super.uniqueCommand(command);
	}
	
}
