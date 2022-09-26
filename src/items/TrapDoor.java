package items;

import game.*;

public class TrapDoor extends Item {

	private boolean isOpen;
	private boolean cashFound;
	
	public TrapDoor() {
		super("trap door");
		isOpen = false;
		cashFound = false;
	}
	
	public void look() {
		if(isOpen)
			if(Player.has("cash"))
				Game.print("There is nothing else hidden beneath the trap door.");
			else if (cashFound)
				Game.print("That's a lot of cash. You're not really just going to"
						+ " leave it there, are you?");
			else {
				Game.print("Well, damn! There's a whole lot of cash stashed beneath the"
						+ " trap door. The bartender's perhaps?");
				cashFound = true;
				Game.getCurrentRoom().addItem(new Cash());
			}
		else
			Game.print("There is a trap door embedded in the floor with no handle or key"
					+ "hole");
	}
	
	public void open() {
		if (isOpen)
			Game.print("Uh...sure buddy. You can open the already-opened trap door. That's"
					+ " definitely a thing you can do. Go ahead. I'll watch.");
		else if (Player.has("crowbar")) {
			Game.print("Well, isn't this just the perfect opportunity to use a crowbar! Good"
					+ " thing you're carrying one around, eh? You insert the crowbar inbetween"
					+ " the trapdoor and the floor and exert just enough force to pop that"
					+ " trapdoor open. You're very proud of yourself.");
			isOpen = true;
		} else
			Game.print("You try to pry open the trap door with your bare hands, but you can't"
					+ " quite get the leverage you need. Bummer. There's probably lots of"
					+ " cool stuff under there.");
	}
	
	public void close() {
		if(isOpen) {
			Game.print("You kick the trap door closed.");
			isOpen = false;
		} else
			Game.print("It's already closed.");
			
	}
	
}
