package items;

import game.Game;
import game.Player;

public class RoomKey extends Item {

	private boolean room201unlocked;
	
	public RoomKey(String name) {
		super(name);
		room201unlocked = false;
	}
	
	public void look() {
		if (getName().equals("bronze room key"))
			Game.print("It's a hotel room key. It has the number 201 stamped into it.");
		else
			Game.print("It's a hotel room key. It has the number 202 stamped into it.");
	}
	
	public void take() {
		if (getName().equals("bronze room key"))
			Game.print("You already have the bronze room key.");
		else if (Player.has("silver room key"))
			Game.print("You already have the silver room key.");
		else {
			Game.print("You reach into the draw and add the silver room key to your collection."
					+ " You regret not bringing a keyring.");
			Player.addItem(this);
			Game.getCurrentRoom().removeItem("silver room key");
		}
	}
	
	public void use() {
		if (getName().equals("bronze room key"))
			if (room201unlocked)
				Game.print("You've already unlocked Room 201.");
			else if (Game.getCurrentRoom().equals("HOTEL_HALL_WEST")) {
				Game.print("You insert the bronze room key into the look of Room 201. Not"
						+ " shockingly, the key fits and you unlock the door.");
				Game.getRoom("HOTEL_ROOM_201").setLocked(false);
				room201unlocked = true;
			}
		else
			Game.print("You don't want to use the silver room key just yet.");
	}
	
}
