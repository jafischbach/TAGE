package items;

import game.*;

public class HotelBar extends Item {

	private boolean bottleSeen = false;
	
	public HotelBar(String lable, String name) {
		super(lable, name);
	}
	
	@Override
	public void look() {
		if (!bottleSeen) {
			Room r = Game.getCurrentRoom();
			r.addItem(new Bottle("HOTEL_BAR_BOTTLE", "bottle"));
			bottleSeen = true;
		} else if (!Game.getRoom("HOTEL_ROOM_101").isLocked())
			setDesc("HOTEL_BAR_C");
		else if (Game.hasFlag("bottleTaken")) {
			setDesc("HOTEL_BAR_B");
		}
		super.look();
	}
	
	@Override
	public void look(String where) {
		if (where.equals("behind")) {
			if (Game.hasFlag("bartender dead"))
				Game.print("You consider whether it's worth stepping in the considerable"
						+ " gore now plastering the floor behind the bar just to score"
						+ " some free liquor. Nah.");
			else if (Game.getRoom("HOTEL_BAR").hasNPC("bartender"))
				Game.print("Not while the bartender is standing right there!");
			else
				Game.print("You casually waltz behind the bar as if you own the place. You peer"
					+ " beneath the counter hoping to score some quality booze. Instead, you"
					+ " find nothing but dust covering a shelf. One area is less dusty. It"
					+ " looks like some elongated object is kept there from time to time.");
		} else
			super.look(where);
	}
	
}
