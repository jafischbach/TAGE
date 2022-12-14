package items;

import game.Game;
import game.Item;

public class Cheese extends Item {

	public Cheese() {
		super("cheese");
	}
	
	@Override
	public void look() {
		Game.print("It's a hunk of cheese. Nothing special about it. Enjoy carrying it around.");
	}
	
	@Override
	public void use() {
		Game.print("You bring the hunk of cheese to your mouth, ready to take a bite. But then"
				+ " you remember your explosive intolerance to dairy products and decide to"
				+ " keep carrying the cheese around instead.");
	}
	
	@Override
	public void move(String command) {
		Game.print("You amuse yourself by moving the cheese around in the air, pretending it's"
				+ " a slimy, yellow space ship cruising the cosmos. You quickly grow bored.");
	}
	
	@Override
	public void uniqueCommand(String command) {
		if (command.equals("eat"))
			use();
		else
			super.uniqueCommand(command);
	}
	
}
