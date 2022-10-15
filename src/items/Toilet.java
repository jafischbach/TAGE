package items;

import game.*;

public class Toilet extends Item {
	
	public Toilet(String label, String name) {
		super(label, name);
	}
	
	@Override
	public void use() {
		Game.print("Really? Trust me. You can hold it.");
	}
	
	@Override
	public void open() {
		char response = Game.getYesNo("Dude. Seriously? Are you sure? (y/n) ");
		if (response == 'y') {
			String s = "You lift the toilet seat. You cannot unsee the horror " +
					   "that you find in that bowl. Fortunately, you don't have " +
					   "much time to worry about that as you quickly succumb to " +
					   "the deadly stench.";
			Game.print(s);
			Game.endGame();
		} else {
			Game.print("Probably a good idea.");
		}
	}
	
	@Override
	public void close() {
		Game.print("Fortunately for you, the toilet seat is already down.");
	}
	
}
