package items;

import game.*;

public class Newspaper extends Item {

	private boolean taken = false;
	private boolean keyFound = false;
	
	public Newspaper(String lable, String name) {
		super(lable, name);
	}

	public void open() {
		if (!taken) {
			Game.print("You don't have the newspaper.");
		} else {
			if (!keyFound) {
				Game.print("You open the newspaper and a small key falls to the floor."
						+ " Curious, you bend down and pick up the small key. You then close"
						+ " the newspaper and put it away.");
				keyFound = true;
				Player.addItem("small key", new SmallKey("small key"));
			} else {
				Game.print("Nah. You don't feel like reading the newspaper.");
			}
		}
	}
	
	public void close() {
		Game.print("The newspaper is already closed.");
	}
	
	public void take() {
		if (!taken) {
			Game.print("You gently lift the newspaper from the old man's lap, being " + 
					"careful not to disturb him.");
			Player.addItem(getName(), this);
			Room r = Game.getCurrentRoom();
			r.removeItem("newspaper");
			r.setDesc("HOTEL_LOUNGE_B");
			Game.printRoom();
			taken = true;
		} else {
			Game.print("You already took the paper.");
		}
	}

	public void uniqueCommand(String command) {
		if (command.equals("read"))
			open();
		else
			super.uniqueCommand(command);
	}
}
