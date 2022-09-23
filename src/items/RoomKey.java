package items;

import game.Game;

public class RoomKey extends Item {

	public RoomKey() {
		super("room key");
	}
	
	public void look() {
		Game.print("It's a hotel room key. It has the number 201 stamped into it.");
	}
	
	public void take() {
		Game.print("You already have the room key.");
	}
	
}
