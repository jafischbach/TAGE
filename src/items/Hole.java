package items;

import game.*;
import characters.Mouse;

public class Hole extends Item {

	private boolean keyPresent;
	
	public Hole() {
		super("hole");
		keyPresent = false;
	}
	
	public void look() {
		if (Player.has("cheese")) {
			Game.print("Looks like a mouse hole. Since there's a mouse sitting in it,"
					+ " it probably is a mouse hole.");
			Game.getCurrentRoom().addNPC(new Mouse());
			keyPresent = true;
		} else if (keyPresent) {
			if(Player.has("large key")){
				Game.print("There is nothing else of interest in the hole.");
			} else {
				Game.print("Now that the mouse is gone, you can take the time to peer carefully"
						+ " into the hole. Aha! A key! Of course there's a key. There are keys"
						+ " everywhere in this damn hotel. I'm sure there's an excellent reason "
						+ " for that that has nothing to do with lack of originality. Anyway, you"
						+ " reach into the hole and snag a large key.");
				Player.addItem(new LargeKey());
			}
		} else {
			Game.print("You peer into the hole but find nothing of interest right now except some" 
					+ " mouse droppings. You decline to take those with you.");
		}
	}
	
}
