package items;

import game.Game;
import game.Item;
import game.Player;
import game.Room;

public class Crowbar extends Item {

	private boolean isTaken;
	
	public Crowbar(String name) {
		super(name);
		isTaken = false;
	}
	
	@Override
	public void look() {
		Game.print("What a cool crowbar!");
	}
	
	@Override
	public void use() {
		Room r = Game.getCurrentRoom();
		if (r.equals("HOTEL_ROOM_201") && r.hasItem("trap door"))
			Game.print("Not a bad idea. Try opening the trap door!");
		else if (r.equals("HOTEL_LOBBY"))
			Game.print("You try to pry open the door to the outside. No dice. The door is"
					+ " too flush with the wall to get any leverage with the crowbar.");
		else if (r.equals("HOTEL_BASEMENT"))
			Game.print("Nice try. I told you that you're not getting through that exit door!");
		else
			Game.print("You don't see anything that needs prying.");
	}
	
	@Override
	public void take(String command) {
		if(isTaken)
			Game.print("You already have the crowbar.");
		else {
			Game.print("Cool! You can always use a crowbar. Imagine all the things you"
					+ " can pry open now!");
			Game.player.addItem(this);
			Game.getCurrentRoom().removeItem("crowbar");
		}
	}
	
}
