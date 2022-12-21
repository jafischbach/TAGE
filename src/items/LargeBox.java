package items;

import game.*;

public class LargeBox extends Item {
	
	public LargeBox(String label, String name) {
		super(label, name);
	}
	
	@Override
	public void take(String command) {
		Game.print("The box is much too large for you to carry around.");
	}
	
	@Override
	public void move(String command) {
		if (command.equals("push") || command.equals("pull"))
			Game.print("You "+command+" with all your might, but you're just"
					+ " not strong enough to maneuver the box out of your way.");
		else
			Game.print("Even if you could manage to move the box, there's nowhere"
					+ " to move it to!");
	}
	
	@Override
	public void open() {
		Game.print("If you had a crowbar, maybe. Otherwise, there's no a chance you're"
				+ " opening this box.");
	}
	
}
