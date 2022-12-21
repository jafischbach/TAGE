package items;

import game.*;

public class Chest extends Item {

	private boolean isOpen = false;
	
	public Chest(String label, String name) {
		super(label, name);
	}
	
	@Override
	public void look() {
		if (!isOpen)
			super.look();
		else if (Game.player.has("sledge hammer"))
			Game.print("An empty chest sits on the floor. It is of no further interest to you.");
		else
			Game.print("The chest sits open on the floor. There is a sledge hammer inside.");
	}
	
	@Override
	public void open() {
		if (!isOpen) {
			if(Game.player.has("tiny key")) {
				Game.print("The tiny key fits snugly into the lock and you soon have"
						+ " the chest open. You lift the lid and see a slegde hammer inside.");
				isOpen = true;
				Game.getCurrentRoom().addItem(new SledgeHammer("sledge hammer"));
			} else {
				Game.print("The chest is locked.");
			}
		} else {
			Game.print("You've already lifted the lid!");
		}
	}
	
	@Override
	public void close() {
		if (isOpen)
			Game.print("You could close the chest but decide you have no reason to.");
		else
			Game.print("The lid is already closed.");
	}
	
	@Override
	public void take(String command) {
		Game.print("Why the hell would you want to carry around a large chest? Besides,"
				+ " it's way too heavy. Don't give yourself a hernia.");
	}
	
	@Override
	public void uniqueCommand(String command) {
		if (command.equals("unlock"))
			open();
		else
			super.uniqueCommand(command);
	}
	
}
