package world;

import game.*;
import items.*;

public class World {

	public static void buildWorld() {
		Room lobby = new Room("HOTEL_LOBBY");
		Game.currentRoom = lobby;

		Room bar = new Room("HOTEL_BAR");
		Room lounge = new Room("HOTEL_LOUNGE_A");
		Room hall = new Room("HOTEL_HALL_1ST");
		Room upperFloor = new Room("HOTEL_HALL_TOP_A");
		Room rr = new Room("HOTEL_BAR_RR");

		lobby.addExit(bar, Room.WEST);
		lobby.addExit(lounge, Room.EAST);
		lobby.addExit(hall, Room.NORTH);
		lobby.addExit(upperFloor, Room.UP);

		bar.addExit(rr, Room.NORTH);
		bar.addExit(lobby, Room.EAST);
		bar.addItem(new HotelBar("HOTEL_BAR", "bar"));

		lounge.addExit(lobby, Room.WEST);
		lounge.addItem(new Newspaper("HOTEL_LOUNGE_NEWSPAPER", "newspaper"));

		hall.addExit(lobby, Room.SOUTH);

		upperFloor.addExit(lobby, Room.DOWN);
		upperFloor.addItem(new LargeBox("HOTEL_HALL_LARGE_BOX", "large box"));

		rr.addExit(bar, Room.SOUTH);
		rr.addItem(new Toilet("HOTEL_BAR_TOILET", "toilet"));
	}
	
}
