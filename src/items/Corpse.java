package items;

import game.*;

public class Corpse extends Item {

	public Corpse() {
		super("corpse");
	}
	
	public void look() {
		if (Game.hasFlag("met bartender"))
			Game.print("Yup. It's a dead guy. It's hard to tell how long he's been dead,"
				+ " but the remains are definitely not fresh. You have no idea who he"
				+ " is or how he died or why someone stuffed him in the closet. You"
				+ " should probably be nice to the bartender from now on though.");
		else
			Game.print("Yup. It's a dead guy. It's hard to tell how long he's been dead,"
					+ " but the remains are definitely not fresh. You have no idea who he"
					+ " is or how he died or why someone stuffed him in the closet.");
	}
	
	public void use() {
		Game.print("Please don't elaborate on how you were thinking of using the corpse. I"
				+ " can't emphasize enough how much I don't want to know what's in your"
				+ " sick head.");
	}
	
	public void take() {
		Game.print("Yeah. Sure. Carry around a dead guy. That won't look suspicious at all."
				+ " You're a dumbass. You know this, right?");
	}
	
	public void open() {
		Game.print("Are you a medical examiner? No. No you are not.");
	}
	
	public void move() {
		if (Player.has("cookie"))
			if (Player.has("corkscrew"))
				Game.print("Stop playing with the dead guy.");
			else {
				Game.print("You really want to put your hands on a dead dude and start moving"
						+ " him around? Sure. Fine. Whatever you want to do. Sigh. Okay, here"
						+ " we go: Remembering how much you enjoy handling dead flesh, you"
						+ " eagerly grab the corpse and start moving it to and fro, you sick,"
						+ " sick bastard. Oh. Wait. Look at that! Something fell out of his"
						+ " pocket. Reluctantly releasing the dead guy's remains, you bend down"
						+ " to inspect the floor. A corkscrew! You pick up and pocket the"
						+ " corkscrew.");
				Player.addItem(new Corkscrew());
			}
		else
			Game.print("Where are you planning to move him to? You know what, don't answer that.");
	}
	
}
