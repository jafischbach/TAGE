package items;

import game.Game;
import game.Item;
import game.Player;

public class Picture extends Item {

	private boolean keyFound;
	
	public Picture() {
		super("picture");
		keyFound = false;
	}
	
	@Override
	public void look() {
		if (Game.hasFlag("met bartender"))
			Game.print("The bartender actually has a smile in the picture. You wonder if the"
				+ " older gentleman in the picture is the bartender's father or the hotel's"
				+ " owner? Maybe they're one and the same! Does it matter? Probably not.");
		else
			Game.print("It's just an uninteresting picture of two men you don't know or"
					+ " care about.");
	}
	
	@Override
	public void look(String where) {
		if (where.equals("behind")) {
			if (keyFound)
				Game.print("Why? There's nothing else behind it.");
			else {
				Game.print("Not a bad idea. You move the picture to see if there's anything"
						+ " behind it. In fact there is something behind it! A bronze room key from"
						+ " this hotel is taped to the back. Not one to leave a key behind, you"
						+ " peel away the tape and take the bronze room key.");
				keyFound = true;
				Game.player.addItem(new RoomKey("bronze room key"));
			}

		} else
			super.look(where);
	}
	
	@Override
	public void take(String command) {
		if (Game.hasFlag("met bartender"))
			Game.print("Why would you want to carry around a picture of the bartender? Actually,"
				+ " don't answer that. I'd rather not know. Leave the picture on the wall.");
		else
			Game.print("Why would you want to carry around a picture of two random dudes?");
	}
	
	@Override
	public void move(String command) {
		if (keyFound)
			Game.print("Why? There's nothing else behind it.");
		else {
			Game.print("Not a bad idea. You move the picture to see if there's anything"
					+ " behind it. In fact there is something behind it! A bronze room key from"
					+ " this hotel is taped to the back. Not one to leave a key behind, you"
					+ " peel away the tape and take the bronze room key.");
			keyFound = true;
			Game.player.addItem(new RoomKey("bronze room key"));
		}
	}
	
}
