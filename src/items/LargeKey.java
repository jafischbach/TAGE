package items;

import game.Game;
import game.Item;

public class LargeKey extends Item {

	private boolean roomFound;
	
	public LargeKey() {
		super("large key");
		roomFound = false;
	}
	
	public void look() {
		if (roomFound)
			Game.print("It's a large, metal key that opens the door marked \"Employees Only\".");
		else
			Game.print("It's a large, metal key. It doesn't look like one of the hotel room"
					+ " keys, but it probably opens a door somewhere.");
	}
	
	public void use() {
		if (Game.getCurrentRoom().equals("HOTEL_HALL_1ST")) {
			if(roomFound)
				Game.print("You already unlocked the door to the basement.");
			else {
				Game.print("The key fits nicely in the door marked \"Employees Only\""
						 + " and you easily unlock the door.");
				roomFound = true;
				Game.getRoom("HOTEL_EMPLOYEES_ONLY").setLocked(false);
			}
		} else {
			Game.print("The large key doesn't fit any lock in this room.");
		}
	}
	
}
