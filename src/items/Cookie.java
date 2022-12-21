package items;

import game.*;

public class Cookie extends Item {

	public Cookie() {
		super("cookie");
	}
	
	@Override
	public void look() {
		Game.print("Mmmm. Cookie. It's a chocolate chip cookie!");
	}
	
	@Override
	public void use() {
		if (Game.player.has("corkscrew") || Game.player.has("large key")) {
			Game.print("Yes! It is time for cookie! You stuff the cookie in"
					+ " your mouth. It's fresh and moist. You wish you had milk.");
			Game.player.removeItem("cookie");
		} else {
			Game.print("No! It is not time for cookie!");
		}
	}
	
	@Override
	public void uniqueCommand(String command) {
		if (command.equals("eat"))
			use();
		else
			super.uniqueCommand(command);
	}
	
}
