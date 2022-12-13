package items;

import game.Game;
import game.Item;
import game.Player;

public class RoomKey extends Item {

	private boolean room101unlocked;
	private boolean room201unlocked;
	private boolean room202unlocked;
	
	public RoomKey(String name) {
		super(name);
		room101unlocked = false;
		room201unlocked = false;
		room202unlocked = false;
	}
	
	public void look() {
		if (getName().equals("bronze room key"))
			Game.print("It's a hotel room key. It has the number 201 stamped into it.");
		else if (getName().equals("silver room key"))
			Game.print("It's a hotel room key. It has the number 202 stamped into it.");
		else
			Game.print("It's a hotel room key. It has the number 101 stamped into it.");
	}
	
	public void take() {
		String key = getName();
		if (key.equals("bronze room key"))
			Game.print("You already have the bronze room key.");
		else if (key.equals("silver room key")) {
			if (Game.player.has("silver room key"))
				Game.print("You already have the silver room key.");
			else {
				Game.print("You reach into the drawer and add the silver room key to your collection."
					+ " You regret not bringing a keyring.");
				Game.player.addItem(this);
				Game.getCurrentRoom().removeItem("silver room key");
			}
		} else if (Game.player.has("gold room key")) 
			Game.print("You already have the gold room key.");
		else {
			Game.print("Making sure the bartender isn't looking, you quickly bend down and"
					+ " pocket the gold room key.");
			Game.getCurrentRoom().removeItem("gold room key");
			Game.player.addItem(this);
		}
	}
	
	public void use() {
		if (getName().equals("bronze room key"))
			if (room201unlocked)
				Game.print("You've already unlocked Room 201.");
			else if (Game.getCurrentRoom().equals("HOTEL_HALL_WEST")) {
				Game.print("You insert the bronze room key into the lock of Room 201. Not"
						+ " shockingly, the key fits and you unlock the door.");
				Game.getRoom("HOTEL_ROOM_201").setLocked(false);
				room201unlocked = true;
			} else
				Game.print("The bronze room key doesn't unlock anything in this room.");
		else if (getName().equals("silver room key")) {
			if (room202unlocked)
				Game.print("You're already unlocked Room 202.");
			else if (Game.getCurrentRoom().equals("HOTEL_HALL_WEST")) {
				Game.print("You unlock Room 202 with the silver room key. You're getting"
						+ " good at this whole unlock door thing.");
				Game.getRoom("HOTEL_ROOM_202").setLocked(false);
				room202unlocked = true;
			} else
				Game.print("The silver room key doesn't unlock anything in this room.");
		} else {
			if (room101unlocked)
				Game.print("You're already unlocked Room 101.");
			else if (Game.getCurrentRoom().equals("HOTEL_HALL_1ST")) {
				Game.print("You unlock Room 101 with the gold room key. Brace yourself; "
						+ "you're about to enter the bartender's abode.");
				Game.getRoom("HOTEL_ROOM_101").setLocked(false);
				room101unlocked = true;
			} else
				Game.print("The gold room key doesn't unlock anything in this room.");
		}
	}
	
}
