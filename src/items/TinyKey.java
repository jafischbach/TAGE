package items;

import game.*;

public class TinyKey extends Item {

	private boolean lockFound = false;
	
	public TinyKey(String name) {
		super(name);
	}
	
	public void look() {
		if (lockFound)
			Game.print("That's the key that opened the chest on the second floor.");
		else
			Game.print("It's a tiny key. Probably too small for a door lock.");
	}
	
	public void take() {
		Game.print("You already have the tiny key.");
	}
	
	public void use() {
		if (Game.currentRoom.equals("HOTEL_CLOSET_TOP"))
			if(lockFound)
				Game.print("The tiny key unlocks the chest.");
			else {
				Game.print("The key looks small enough to fit into the chest's lock. Try"
						+ " opening the chest!");
				lockFound = true;
			}
		else
			Game.print("The tiny key doesn't fit any lock in this room.");
	}
	
}
