package items;

import game.*;

public class Nightstand extends Item {

	private boolean drawerFound;

	public Nightstand(String name) {
		super(name);
		drawerFound = false;
	}

	public void look() {
		Game.print("It's just a simple nightstand with a single drawer.");
		if (!drawerFound) {
			Room r = Game.getCurrentRoom();
			if (r.equals("HOTEL_ROOM_101"))
				r.addItem(new Drawer("drawer", 101));
			else
				r.addItem(new Drawer("drawer", 201));
			drawerFound = true;
		}
	}

	public void open() {
		Game.print("Try opening the drawer.");
	}

}
