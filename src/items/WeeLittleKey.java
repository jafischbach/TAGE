package items;

import game.*;

public class WeeLittleKey extends Item {

	public WeeLittleKey() {
		super("wee little key");
	}
	
	public void look() {
		Game.print("It's a wee little key. Be careful not to drop it! It's actually smaller than the"
				+ " tiny key you found earlier.");
	}
	
	public void use() {
		if(Game.getCurrentRoom().equals("HOTEL_ROOM_202"))
			Game.print("Try opening something.");
		else
			Game.print("You don't see a keyhole nearly small enough for the wee little key"
					+ " in this room.");
	}
	
	public void take() {
		Game.print("You're already carrying the wee little key.");
	}
	
}
