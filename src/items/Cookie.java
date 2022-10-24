package items;

import game.*;

public class Cookie extends Item {

	public Cookie() {
		super("cookie");
	}
	
	public void look() {
		Game.print("Mmmm. Cookie. It's a chocolate chip cookie!");
	}
	
	public void use() {
		if (Player.has("corkscrew") || Player.has("large key")) {
			Game.print("Yes! It is time for cookie! You stuff the cookie in"
					+ " your mouth. It's fresh and moist. You wish you had milk.");
			Player.removeItem("cookie");
		} else {
			Game.print("No! It is not time for cookie!");
		}
	}
	
	public void uniqueCommand(String command) {
		if (command.equals("eat"))
			use();
		else
			super.uniqueCommand(command);
	}
	
}
