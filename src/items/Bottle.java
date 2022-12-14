package items;

import game.*;

public class Bottle extends Item {

	private boolean isTaken = false;
	private boolean isOpen = false;

	public Bottle(String lable, String name) {
		super(lable, name);
	}

	@Override
	public void take(String command) {
		if (isTaken)
			Game.print("You already have the bottle.");
		else {
			Game.print("You pick up the bottle, finding it lighter than expected.");
			Game.player.addItem("bottle", this);
			Game.getCurrentRoom().removeItem("bottle");
			isTaken = true;
			Game.addFlag("bottleTaken");
		}
	}

	@Override
	public void open() {
		if (!isTaken)
			Game.print("You don't have the bottle!");
		else {
			if (isOpen)
				Game.print("You already opened the bottle. Still no genie inside." + " Stop looking.");
			else {
				Game.print("You pull out the cork and peer inside the bottle."
						+ " You find a tiny key! Tipping the bottle, you extract the tiny key.");
				Game.player.addItem("tiny key", new TinyKey("tiny key"));
				isOpen = true;
			}
		}
	}

	@Override
	public void close() {
		if (!isTaken)
			Game.print("You don't have the bottle!");
		else {
			if (isOpen)
				Game.print("You try to shove the cork back into the bottle but" + " quickly give up.");
			else
				Game.print("It's already corked shut.");
		}
	}
}
