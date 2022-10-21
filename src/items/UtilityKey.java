package items;

import game.Game;
import game.Item;

public class UtilityKey extends Item {

	private boolean roomUnlocked;
	
	public UtilityKey() {
		super("utility key");
		roomUnlocked = false;
	}
	
	public void look() {
		if (roomUnlocked)
			Game.print("It's the key that opens the door in the utilities room in the basement.");
		else
			Game.print("Yet another key. The bartender called it a utility key.");
	}
	
	public void use() {
		if (roomUnlocked)
			Game.print("You already unlocked the door to the utilities room.");
		else if (Game.getCurrentRoom().equals("HOTEL_BASEMENT")) {
			Game.print("Well, imagine that. Your utility key does, in fact, open"
					+ " a door maked \"Utilities.\" Amazing.");
			Game.getCurrentRoom().setDesc("HOTEL_BASEMENT_C");
			Game.getRoom("HOTEL_UTILITIES").setLocked(false);
			roomUnlocked = true;
		} else {
			Game.print("There are no utility doors to unlock here.");
		}
	}
	
}
