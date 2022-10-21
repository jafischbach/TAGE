package items;

import game.*;

public class WeeLittleKey extends Item {

	public boolean lockFound;
	
	public WeeLittleKey() {
		super("wee little key");
		lockFound = false;
	}
	
	public void look() {
		if (lockFound)
			Game.print("It's a key so wee you're amazed you haven't lost it yet. It unlocks"
					+ " the refrigerator in Room 202.");
		else
			Game.print("It's a wee little key. Be careful not to drop it! It's actually smaller than the"
				+ " tiny key you found earlier.");
	}
	
	public void use() {
		if(Game.getCurrentRoom().equals("HOTEL_ROOM_202")) {
			Game.print("The lock on the refrigerator is so wee, this key might actually work! Try"
					+ " opening the refrigerator.");
			lockFound = true;
		} else
			Game.print("You don't see a keyhole nearly small enough for the wee little key"
					+ " in this room.");
	}
	
	public void take() {
		Game.print("You're already carrying the wee little key.");
	}
	
}
