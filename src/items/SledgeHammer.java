package items;

import game.*;
import characters.*;

public class SledgeHammer extends Item {

	private boolean isTaken = false;
	private boolean boxSmashed = false;
	
	public SledgeHammer(String name) {
		super(name);
	}
	
	public void look() {
		Game.print("It's a sledge hammer.");
	}
	
	public void use() {
		Room r = Game.getCurrentRoom();
		if(!boxSmashed && r.equals("HOTEL_HALL_TOP")) {
			Game.print("With a mighty swing, you smite the large box. Ten minutes later,"
					+ " you manage to reduce the large box to a pile of splinters.");
			r.setDesc("HOTEL_HALL_TOP_C");
			Game.printRoom();
			boxSmashed = true;
			r.removeItem("large box");
			r.addSimpleItem("debris", "You admire the evidence of your prowess with the mighty"
					+ " sledge hammer. All large boxes beware! Now careful where you step. You"
					+ " don't want to get a splinter.");
			Room hallway = Game.getRoom("HOTEL_HALL_WEST");
			hallway.setLocked(false);
			Room room101 = Game.getRoom("HOTEL_ROOM_101");
			room101.setLocked(false);
			Room bar = Game.getRoom("HOTEL_BAR");
			bar.addNPC(new Bartender("bartender"));
			bar.setDesc("HOTEL_BAR_B");
		} else if(r.equals("HOTEL_LOBBY"))
			Game.print("You swing the sledge hammer at the door leading outside, hoping"
					+ " to at last secure your freedom from this creepy hotel. Sadly, you"
					+ " lack the strength to smash down this door.");
		else if (r.equals("HOTEL_BASEMENT"))
			Game.print("You swing the mighty sledge hammer against the door marked \"Exit\""
					+ " and the sledge hammer bounces off the metal surface of the door so"
					+ " hard that you nearly lose your grip. You didn't really expect that"
					+ " to actually work, did you?");
		else
			Game.print("Stop swinging that thing around. You can't smash everything!");
	}
	
	public void take() {
		if (isTaken)
			Game.print("You already have the sledge hammer.");
		else {
			Game.print("You lift the sledge hammer out of the chest. The damn thing"
					+ " is heavy, but you decide to carry it around anyway and shove"
					+ " it into your pocket.");
			isTaken = true;
			Player.addItem("sledge hammer", this);
			Game.getCurrentRoom().removeItem("sledge hammer");
		}
			
	}
	
}
