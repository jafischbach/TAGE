package items;

import game.Game;
import game.Player;

public class Picture extends Item {

	private boolean keyFound;
	
	public Picture() {
		super("picture");
		keyFound = false;
	}
	
	public void look() {
		Game.print("The bartender actually has a smile in the picture. You wonder if the "
				+ " older gentleman in the picture is the bartender's father or the hotel's"
				+ " owner? Maybe they're one and the same! Does it matter? Probably not.");
	}
	
	public void take() {
		Game.print("Why would you want to carry around a picture of the bartender? Actually,"
				+ " don't answer that. I'd rather not know. Leave the picture on the wall.");
	}
	
	public void move() {
		if (keyFound)
			Game.print("Why? There's nothing else behind it.");
		else {
			Game.print("Not a bad idea. You move the picture to see if there's anything"
					+ " behind it. In fact there is something behind it! A room key from"
					+ " this hotel is taped to the back. Not one to leave a key behind, you"
					+ " peel away the tape and take the room key.");
			keyFound = true;
			Player.addItem(new RoomKey());
		}
	}
	
}
