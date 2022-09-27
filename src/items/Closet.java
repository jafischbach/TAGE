package items;

import game.Game;
import game.Player;

public class Closet extends Item {

	private int roomNum;
	private boolean isOpen;
	private boolean corpseFound;
	
	public Closet(String name, int roomNum) {
		super(name);
		this.roomNum = roomNum;
		isOpen = false;
		corpseFound = false;
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
			} else if (corpseFound)
				Game.print("The closet is still inhabited by a dead guy.");
			else {
				Game.print("Well, this is a fun find. A corpse is stuffed into the closet. It doesn't"
						+ " look fresh. The bartender's work perhaps? Is this why he hid the key to"
						+ " this room? You could go ask him, but...probably safest not to unless you"
						+ " want to join the corpse.");
				Game.getCurrentRoom().addItem(new Corpse());
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
		} else {
			Game.print("The closet door doesn't want to budge at first. But you yank and heave and"
					+ " eventually pull it open. You are rewarded with a delightful smell.");
			isOpen = true;
		}
	}
	
	public void close() {
		if(isOpen) {
			if (roomNum == 101)
				Game.print("You close the closet. How considerate!");
			else
				Game.print("Good plan. Best leave it closed.");
			isOpen = false;
		} else 
			Game.print("It's already closed. You really should pay more attention.");
	}
	
}
