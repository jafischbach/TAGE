package items;

import game.*;

public class GasCan extends Item {

	private boolean isEmpty;
	
	public GasCan() {
		super("gas can");
		isEmpty = false;
	}
	
	public void look() {
		if (Player.has("gas can"))
			if (isEmpty) {
				Game.print("You're carrying around an empty gas can because you just"
					+ " can't let go of things.");
			} else
				Game.print("It's a metal cannister labelled \"Super Flammable Stuff\" and you"
					+ " hear a liquid sloshing around inside.");
		else
			Game.print("It's a metal cannister labelled \"Super Flammable Stuff.\"");
	}
	
	public void take() {
		if (Player.has("gas can"))
			Game.print("You already have the gas can.");
		else {
			Game.print("You retrieve the gas can from the shelf.");
			Player.addItem(this);
			Game.getCurrentRoom().removeItem("gas can");
		}
	}
	
	public void open() {
		if (Player.has("gas can"))
			if (isEmpty)
				Game.print("There's nothing else in there.");
			else
				Game.print("You remove the cap from the cannister. Smells like gas. You take a"
						+ " glorious wiff and replace the cap.");
		else
			Game.print("You don't have the gas can.");
	}
	
	public void use() {
		if (Player.has("gas can"))
			if (isEmpty)
				Game.print("The can is empty. Where you planning to use it as a...no, never mind.");
			else {
				Game.print("You remove the cap and empty the contents of the can into the "
					+ "chainsaw's tank. Now you have a working chainsaw! You briefly fantasize"
					+ " about the bartended and your favorite scene from Scarface, but then"
					+ " you realize that the chainsaw only has so much gas and you should"
					+ " probably use it strategically...you know, in a way that won't land"
					+ " you in prison for eternity.");
				isEmpty = true;
				Game.addFlag("chainsaw gassed");
			}
		else
			Game.print("You don't have the gas can.");
	}
	
}