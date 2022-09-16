package items;

import game.*;

public class HotelBar extends Item {

	public HotelBar(String lable, String name) {
		super(lable, name);
	}
	
	public void look() {
		super.look();
		Room r = Game.getCurrentRoom();
		r.addItem(new Bottle("HOTEL_BAR_BOTTLE", "bottle"));
	}
	
}
