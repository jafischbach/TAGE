package items;

import game.Game;

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
			Game.print("Wow! Look at all this stuff! You have no use for any of it! The drawer"
					+ " just contains a few of the bartender's personal items and nothing of use"
					+ " to you.");
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
				Game.print("You close the drawer. Wouldn't want the bartender to know you've"
						+ " been sooping, eh?");
			isOpen = false;
		} else
			Game.print("You push and push on the drawer but nothing happens. Probably because"
					+ " it's already closed.");
	}
	
}
