package items;

import game.Game;
import game.Player;

public class Closet extends Item {

	private int roomNum;
	private boolean isOpen;
	
	public Closet(String name, int roomNum) {
		super(name);
		this.roomNum = roomNum;
		isOpen = false;
	}
	
	public void look() {
		if (isOpen) {
			if (roomNum == 101) {
				if (Player.has("crowbar"))
					Game.print("The closet is full of the bartender's clothes, neatly arranged"
							+ " on hangars.");
				else {
					Game.print("The closet is full of the bartender's clothes, neatly arranged on"
						+ " hangars. At the bottom of the closet, you find a crowbar. Weird"
						+ " place for that, but whatever.");
					Game.getCurrentRoom().addItem(new Crowbar("crowbar"));
				}
			}
		} else 
			Game.print("It's a standalone closet positioned in the corner of the room. No, it does"
				+ " not lead to Narnia.");
	}
	
	public void open() {
		if(isOpen)
			Game.print("The closet is already open. You're not that bright, are you?");
		else if (roomNum == 101) {
			Game.print("You open the closet. The well-oiled hinges make no sound.");
			isOpen = true;
		}
	}
	
	public void close() {
		if(isOpen) {
			Game.print("You close the closet. How considerate!");
			isOpen = false;
		} else 
			Game.print("It's already closed. You really should pay more attention.");
	}
	
}
