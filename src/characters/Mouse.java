package characters;

import game.*;

public class Mouse extends NPC {

	public Mouse() {
		super("mouse");
	}
	
	public void look() {
		Game.print("A tiny gray mouse crouches just with the hole. It stares at you with"
				+ " burning malevolence.");
	}
	
	public void give(String item) {
		Game.print("The mouse likes cheese!");
	}
	
	public void talk() {
		Game.print("You want to talk to a rodent? Are you feeling okay?");
	}
	
	public void attack(String weapon) {
		Game.print("The mouse scurries out of your reach. Nice try, but you don't have the"
				+ " reflexes to hurt or catch the mouse.");
	}
	
}
