package world;

import game.*;
import items.*;
import characters.*;

public class World {

	public static void buildWorld() {
		Room lobby = new Room("HOTEL_LOBBY");
		Game.currentRoom = lobby;

		Room bar = new Room("HOTEL_BAR");
		Room lounge = new Room("HOTEL_LOUNGE");
		Room hall = new Room("HOTEL_HALL_1ST");
		Room upperFloor = new Room("HOTEL_HALL_TOP");
		Room closet = new Room("HOTEL_CLOSET_TOP");
		Room upperWestHallway = new Room("HOTEL_HALL_WEST");
		Room rr = new Room("HOTEL_BAR_RR");
		Room room101 = new Room("HOTEL_ROOM_101");
		Room room201 = new Room("HOTEL_ROOM_201");
		Room room202 = new Room("HOTEL_ROOM_202");
		Room employeesOnly = new Room("HOTEL_EMPLOYEES_ONLY");
		Room basement = new Room("HOTEL_BASEMENT");
		Room utilities = new Room("HOTEL_UTILITIES");
		Room exit = new Room("EXIT");
		
		lobby.addExit(bar, Room.WEST);
		lobby.addExit(lounge, Room.EAST);
		lobby.addExit(hall, Room.NORTH);
		lobby.addExit(upperFloor, Room.UP);
		lobby.addExit(exit, Room.SOUTH);
		lobby.addNPC(new Bartender("bartender"));

		exit.setLocked(true);
		exit.addExit(lobby, Room.NORTH);
		exit.addNPC(new Cop());
		
		bar.addExit(rr, Room.NORTH);
		bar.addExit(lobby, Room.EAST);
		bar.addItem(new HotelBar("HOTEL_BAR", "bar"));

		lounge.addExit(lobby, Room.WEST);
		lounge.addItem(new Newspaper("HOTEL_LOUNGE_NEWSPAPER", "newspaper"));
		lounge.addNPC(new OldMan());
		lounge.addSimpleItem("couch", "It's a ratty old couch. This isn't a luxury hotel.");
		lounge.addSimpleItem("armchair", "Identical armchairs sit in the room. An old man"
				+ " occupies one of them.");

		hall.addExit(lobby, Room.SOUTH);
		hall.addExit(room101, Room.WEST);
		hall.addExit(employeesOnly, Room.EAST);
		
		room101.setLocked(true);
		room101.addExit(hall, Room.EAST);
		room101.addItem(new Nightstand("nightstand"));
		room101.addItem(new Closet("closet", 101));
		room101.addItem(new Bed());
		room101.addItem(new Picture());
		
		employeesOnly.setLocked(true);
		employeesOnly.addExit(hall, Room.WEST);
		employeesOnly.addExit(basement, Room.DOWN);
		
		basement.addExit(employeesOnly, Room.UP);
		basement.addExit(utilities, Room.NORTH);
		basement.addItem(new Chainsaw());
		
		utilities.setLocked(true);
		utilities.addExit(basement, Room.SOUTH);
		utilities.addItem(new GasCan());
		
		upperFloor.addExit(lobby, Room.DOWN);
		upperFloor.addExit(closet, Room.NORTH);
		upperFloor.addExit(upperWestHallway, Room.WEST);
		upperFloor.addItem(new LargeBox("HOTEL_HALL_LARGE_BOX", "large box"));
		upperFloor.addSimpleItem("furniture", "Various pieces of furniture are stacked"
				+ " hapazardly in the hallway, barring your way. There are too many peices"
				+ " of furniture for you to move yourself. You will not be able to go that way.");
		
		closet.setLocked(true);
		closet.addExit(upperFloor, Room.SOUTH);
		closet.addItem(new Chest("HOTEL_CLOSET_TOP_CHEST", "chest"));

		upperWestHallway.setLocked(true);
		upperWestHallway.addExit(upperFloor, Room.EAST);
		upperWestHallway.addExit(room201, Room.SOUTH);
		upperWestHallway.addExit(room202, Room.NORTH);
		
		room201.setLocked(true);
		room201.addExit(upperWestHallway, Room.NORTH);
		room201.addItem(new Closet("closet", 201));
		room201.addItem(new Nightstand("nightstand"));
		room201.addItem(new Bed());
		room201.addItem(new Rug());
		
		room202.setLocked(true);
		room202.addExit(upperWestHallway, Room.SOUTH);
		room202.addItem(new Refrigerator());
		room202.addItem(new Hole());
		
		rr.addExit(bar, Room.SOUTH);
		rr.addItem(new Toilet("HOTEL_BAR_TOILET", "toilet"));
	}

	
}
