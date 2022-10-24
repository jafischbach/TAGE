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
	
	@Override
	public void uniqueCommand(String command) {
		if (command.equalsIgnoreCase("flush")) {
			Game.print("Demonstrating a shocking lack of common sense, you press"
					+ " the handle on the toilet. You hear a strained gurgling sound"
					+ " from within the bowl. Pipes begin to clang and the entire"
					+ " toilet starts to vibrate quite violently. The sink faucet"
					+ " suddely bursts and a powerful stream of some vile, murky"
					+ " slime shoots upwards and splatters across the ceiling and"
					+ " then everywhere else. You are drenched in a toxic slime."
					+ " Weeks later, the doctors tell you that there's nothing else"
					+ " they can do and you spend your final days raving at the"
					+ " shrill voices in your head.");
			Game.endGame();
		} else
			super.uniqueCommand(command);
	}
	
}
