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
	
	@Override
	public void look() {
		if(isOpen)
			if(Game.player.has("cash"))
				if (Game.player.has("coupon") && !Game.player.has("wee little key")) {
					Game.print("You peer into the now cash-less hole beneath the trap door and see a tiny"
							+ " glint of metal. Reaching into the hole, you find a wee little key, which you"
							+ " take.");
					Game.player.addItem(new WeeLittleKey());
				} else 
					Game.print("You don't see anything else hidden beneath the trap door.");
			else if (cashFound)
				Game.print("That's a lot of cash. You're not really just going to"
						+ " leave it there, are you?");
			else {
				if (Game.hasFlag("met bartender"))
					Game.print("Well, damn! There's a whole lot of cash stashed beneath the"
						+ " trap door. The bartender's perhaps?");
				else 
					Game.print("Well, damn! There's a whole lot of cash stashed beneath the"
							+ " trap door.");
				cashFound = true;
				Game.getCurrentRoom().addItem(new Cash());
			}
		else
			Game.print("There is a trap door embedded in the floor with no handle or key"
					+ "hole.");
	}
	
	@Override
	public void look(String where) {
		look();
	}
	
	@Override
	public void open() {
		if (isOpen)
			Game.print("Uh...sure buddy. You can open the already-opened trap door. That's"
					+ " definitely a thing you can do. Go ahead. I'll watch.");
		else if (Game.player.has("crowbar")) {
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
	
	@Override
	public void close() {
		if(isOpen) {
			Game.print("You kick the trap door closed.");
			isOpen = false;
		} else
			Game.print("It's already closed.");
			
	}
	
}
