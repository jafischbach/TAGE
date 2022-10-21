package items;

import game.*;

public class HotelBar extends Item {

	private boolean bottleSeen = false;
	
	public HotelBar(String lable, String name) {
		super(lable, name);
	}
	
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
	
}
