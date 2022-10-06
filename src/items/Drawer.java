package items;

import game.Game;
import game.Player;

public class Drawer extends Item {

	private int roomNum;
	private boolean isOpen;
	
	public Drawer(String name, int roomNum) {
		super(name);
		this.roomNum = roomNum;
		isOpen = false;
	}
	
	public void look() {
		if (!isOpen)
			Game.print("What a nice drawer. It fits nicely in the nightstand. The wood even matches!");
		else if (roomNum == 101)
			if (Game.hasFlag("met bartender"))
				Game.print("Wow! Look at all this stuff! You have no use for any of it! The drawer"
					+ " just contains a few of the bartender's personal items and nothing of use"
					+ " to you.");
			else
				Game.print("Wow! Look at all this stuff! You have no use for any of it! The drawer"
						+ " just contains a few personal items and nothing of use"
						+ " to you.");
		else if (roomNum == 201)
			if (Player.has("silver room key"))
				Game.print("There is nothing else in the drawer.");
			else {
				Game.print("The only thing in the draw is a silver room key.");
				Game.getCurrentRoom().addItem(new RoomKey("silver room key"));
			}
	}
	
	public void open() {
		if (isOpen)
			Game.print("It's already open. You feeling okay?");
		else {
			Game.print("Will you look at that! Not even locked! The drawer slides open easily.");
			isOpen = true;
		}
	}
	
	public void close() {
		if (isOpen) {
			if (roomNum == 101)
				if (Game.hasFlag("met bartender"))
					Game.print("You close the drawer. Wouldn't want the bartender to know you've"
						+ " been sooping, eh?");
				else
					Game.print("You close the drawer.");
			else
				Game.print("You close the drawer.");
			isOpen = false;
		} else
			Game.print("You push and push on the drawer but nothing happens. Probably because"
					+ " it's already closed.");
	}
	
}
