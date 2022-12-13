package items;

import game.*;

public class Refrigerator extends Item {

	private boolean isLocked;
	private boolean isOpen;
	
	public Refrigerator() {
		super("refrigerator");
		isLocked = true;
		isOpen = false;
	}
	
	public void look() {
		Game.print("It's a working refrigerator and it's not caked in dust. Creepy.");
	}
	
	public void open() {
		if (Game.hasFlag("wolverine sleeping")) {
		if (isOpen)
			Game.print("The refrigerator is already open. That's why it's getting"
					+ " uncomfortably cold in here. Close the damn thing already!");
		else if (isLocked) {
			Game.print("The damn thing is locked! The keyhole is so small you didn't notice"
					+ " it at first. Now who the hell locks a refrigerator?");
			if (Game.player.has("wee little key")) {
				Game.print("The wee little key fits perfectly in the tiny lock. The key is"
						+ " so wee, it's hard to turn the damn thing! You eventually manage"
						+ " though and now the refrigerator is unlocked!");
				Game.print("You open the refrigerator and find it stocked with beer! Lots and"
						+ " lots of ice cold Dr. Hops beer! You wonder who it's all for. Doesn't"
						+ " seem like the bartender's style.");
				Game.getCurrentRoom().addItem(new Beer());
				isLocked = false;
				isOpen = true;
			}
		} else {
			Game.print("You open the refrigerator and find it stocked with beer! Lots and"
					+ " lots of ice cold Dr. Hops beer! You wonder who it's all for. Doesn't"
					+ " seem like the bartender's style.");
			isOpen = true;
		}
		} else {
			Game.print("Is this really the time to go for a snack? THERE'S A VIOLENT ANIMAL"
					+ " EYEBALLING YOU!");
		}
	}
	
	public void close() {
		if (isOpen) {
			Game.print("You close the refrigerator. Gotta keep all that beer nice and cold!");
			isOpen = false;
		} else
			Game.print("The refrigerator is already closed.");
	}
	
}
