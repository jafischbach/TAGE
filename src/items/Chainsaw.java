package items;

import game.*;

public class Chainsaw extends Item {

	public Chainsaw() {
		super("chainsaw");
	}
	
	public void look() {
		Game.print("It's an Easy Slaughter(tm) Mid-Size Chainsaw! You've been asking"
				+ " Santa for one of these for years.");
	}
	
	public void take() {
		if(Player.has("chainsaw"))
			Game.print("You're already carrying the chainsaw. How do you not know this?");
		else {
			Game.print("You heft the Easy Slaughter(tm) Mid-Size Chainsaw, strike a pose"
					+ " and feel awesome, and then stuff the chainsaw in your pocket with"
					+ " all the other stuff you're carrying around.");
			Player.addItem(this);
			Room r = Game.getCurrentRoom();
			r.removeItem("chainsaw");
			r.setDesc("HOTEL_BASEMENT_B");
		}
	}
	
	public void open() {
		Game.print("Don't do that! You'll void the warranty!");
	}
	
	public void use() {
		if (Game.hasFlag("chainsaw gassed")) {
			Room r = Game.getCurrentRoom();
			if (r.equals("HOTEL_BASEMENT"))
				Game.print("You rev up the might chainsaw and proceed to attack the door"
						+ " marked \"Exit\" hoping that, at long last, you've found your"
						+ " freedom! Alas, the door is impervious even to the violent"
						+ " efforts of the buzzing chainsaw. You cease your efforts before"
						+ " you use up all the gas.");
			else if (r.equals("HOTEL_LOBBY")) {
				Game.print("You stand there and relish wielding the raw power of the mighty"
						+ " chainsaw. You stride toward the door to the outside and proceed"
						+ " to tear that wooden bastard to shreds. Sun shines through the"
						+ " opening and you glimpse freedom beyond.");
				Game.getRoom("EXIT").setLocked(false);
			} else {
				Game.print("There's only so much gas in the chainsaw. Don't waste it trying"
						+ " to destroy everything you see.");
			}
		} else if (Player.has("chainsaw")){
			Game.print("You eagerly heft the chainsaw to wreak righteous devastation on"
					+ " this vile hotel...only to discover that the chainsaw is out of"
					+ " gas. Major bummer.");
		} else {
			Game.print("Try taking it first.");
		}
	}
	
}
