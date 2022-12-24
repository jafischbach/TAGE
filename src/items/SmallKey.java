package items;

import game.*;

public class SmallKey extends Item {

	private boolean lockFound = false;

	public SmallKey(String name) {
		super(name);
	}

	@Override
	public void look() {
		if (!lockFound) {
			Game.print("It's a small key. You wonder what it unlocks.");
		} else {
			Game.print("It's the key than unlocks the door at the top of the stairs.");
		}
	}

	@Override
	public void use() {
		Room r = Game.getCurrentRoom();
		if (r.equals("HOTEL_HALL_TOP")) {
			if (!lockFound) {
				Game.print("Score! The small key fits perfectly in the door lock."
						+ " You eagerly turn they key and are rewarded with a click!" 
						+ " The door is now unlocked.");
				lockFound = true;
				Room closet = Game.getRoom("HOTEL_CLOSET_TOP");
				closet.setLocked(false);
				r.setDesc("HOTEL_HALL_TOP_B");
				Game.printRoom();
			} else {
				Game.print("You already unlocked the door, dumbass.");
			}
		} else if (lockFound) {
			Game.print("That only unlocks the door at the top of the stairs.");
		} else {
			Game.print("The small key doesn't fit any lock in this room.");
		}
	}

}
