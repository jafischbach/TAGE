package characters;

import game.*;

public class Mouse extends NPC {

	public Mouse() {
		super("mouse");
	}
	
	@Override
	public void look() {
		Game.print("A tiny gray mouse crouches just within the hole. It stares at you with"
				+ " burning malevolence.");
	}
	
	@Override
	public void give(String item) {
		if (item.equals("cheese")) {
			Game.print("You place the cheese in front of the hole. The mouse regards you suspiciously."
					+ " Only when you retreat to the other end of the room does the mouse dare to"
					+ " venture out of its hole to examine the cheese. Its nose twitches. Satisfied"
					+ " that you have not laid a trap, the mouse snatches the cheese and scurries"
					+ " away, leaving its hole undefended.");
			Game.getCurrentRoom().removeNPC("mouse");
			Game.player.removeItem("cheese");
		} else {
			super.give(item);
		}
	}
	
	@Override
	public void talk() {
		Game.print("You want to talk to a rodent? Are you feeling okay?");
	}
	
	@Override
	public void attack(String weapon) {
		Game.print("The mouse scurries out of your reach. Nice try, but you don't have the"
				+ " reflexes to hurt or catch the mouse.");
	}
	
}
