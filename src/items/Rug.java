package items;

import game.*;

public class Rug extends Item {

	private boolean isMoved;
	
	public Rug() {
		super("rug");
		isMoved = false;
	}
	
	public void look() {
		Game.print("A small, oval run lays on the floor at the foot of the bed. It's caked in"
				+ " dust, but you think it was once a nice shade of burgundy.");
	}
	
	public void take() {
		Game.print("Were you planning to take the rug outside and beat all the dust away. Nice"
				+ " thought and I'm sure the hotel's housekeeping staff (should they exist)"
				+ " would appreciate the gesture. But, you might recall that you can't get"
				+ " outside. So, leave the rug on the floor.");
	}
	
	public void move() {
		if(isMoved) {
			Game.print("You don't work here! Just leave the rug where it is.");
		} else {
			Game.print("Good idea. You never know when you'll find a trap door underneath "
					+ " a rug in a creepy, dusty room, am I right? You shove the rug aside "
					+ " with your foot. Huh. Look at that. A trap door.");
			Game.getCurrentRoom().addItem(new TrapDoor());
			isMoved = true;
		}
	}
	
}
