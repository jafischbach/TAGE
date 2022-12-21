package items;

import game.*;

public class Corpse extends Item {

	public Corpse() {
		super("corpse");
	}
	
	@Override
	public void look() {
		if (Game.hasFlag("met bartender"))
			Game.print("Yup. It's a dead guy. It's hard to tell how long he's been dead,"
				+ " but the remains are definitely not fresh. You have no idea who he"
				+ " was or how he died or why someone stuffed him in the closet. You"
				+ " should probably be nice to the bartender from now on though.");
		else
			Game.print("Yup. It's a dead guy. It's hard to tell how long he's been dead,"
					+ " but the remains are definitely not fresh. You have no idea who he"
					+ " was or how he died or why someone stuffed him in the closet.");
	}
	
	@Override
	public void use() {
		Game.print("Please don't elaborate on how you were thinking of using the corpse. I"
				+ " can't emphasize enough how much I don't want to know what's in your"
				+ " sick head.");
	}
	
	@Override
	public void take(String command) {
		Game.print("Yeah. Sure. Carry around a dead guy. That won't look suspicious at all."
				+ " You're a dumbass. You know this, right?");
	}
	
	@Override
	public void open() {
		Game.print("Are you a medical examiner? No. No you are not.");
	}
	
	@Override
	public void move(String command) {
		if (Game.player.has("cookie"))
			if (Game.player.has("corkscrew"))
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
				Game.player.addItem(new Corkscrew());
			}
		else
			Game.print("Where are you planning to move him to? You know what, don't answer that.");
	}

	@Override
	public void uniqueCommand(String command) {
		if (command.equals("talk"))
			Game.print("You engage in a stimulating, if one-sided, conversation with"
					+ " the corpse.");
		else
			super.uniqueCommand(command);
	}
	
}
