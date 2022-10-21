package world;

import game.*;
import items.*;
import characters.*;

/**
 * Hotel Escape - a demo text adventure developed using FlossTAGE.
 * 
 * This short game consists of 15 rooms, 32 items, and 4 NPCs.
 * 
 * The World class constructs the game world by creating all rooms,
 * most items and one NPC. Additional items and NPCs are created
 * dynamically as the player progresses through the game.
 * 
 * @version beta (2022)
 *
 */
public class World {

	public static void buildWorld() {
		Room lobby = new Room("HOTEL_LOBBY");
		Game.setCurrentRoom(lobby);
		
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
		employeesOnly.addSimpleItem("broom", "Yeah. It's a broom. Don't tell me you want to carry"
				+ " that around too? Where are you putting all the crap you've been collecting"
				+ " anyway?");
		
		basement.addExit(employeesOnly, Room.UP);
		basement.addExit(utilities, Room.NORTH);
		basement.addItem(new Chainsaw());
		
		utilities.setLocked(true);
		utilities.addExit(basement, Room.SOUTH);
		utilities.addItem(new GasCan());
		utilities.addSimpleItem("boiler", "The thing looks ancient. You doubt it's been inspected"
				+ " within the last several decades. With your luck, it'll probably explode"
				+ " any second now. You probably don't want to linger here.");
		utilities.addSimpleItem("electric panel", "Looks complicated. Nothing is labelled."
				+ " You briefly flirt with the idea of flipping some of the switches, but, as this"
				+ " would almost certainly leave you in the dark in the basement of a creepy"
				+ " hotel, you quickly reconsider.");
		utilities.addSimpleItem("laundry machines", "Damn. Those look new. The bartender must"
				+ " really care about clean underwear.");
		utilities.addSimpleItem("shelf", "Just a wooden shelf.");
		
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
		closet.addSimpleItem("junk", "Just an assortment of (mostly broken) cleaning supplies"
				+ " of absolutely no use to you.");

		upperWestHallway.setLocked(true);
		upperWestHallway.addExit(upperFloor, Room.EAST);
		upperWestHallway.addExit(room201, Room.SOUTH);
		upperWestHallway.addExit(room202, Room.NORTH);
		upperWestHallway.addSimpleItem("carpet", "What a boring carpet. You find yourself starting"
				+ " to fall asleep while gazing at it.");
		upperWestHallway.addSimpleItem("cobwebs", "You peer up at the ceiling and find numerous"
				+ " webs. Some just look like globs of dust while others look constructed."
				+ " You would prefer not to meet the architects.");
		upperWestHallway.addSimpleItem("bulb", "The single light bulb barely illuminates this"
				+ " stretch of hallway. You have no desire to venture further.");
		
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
		rr.addSimpleItem("sink", "It's a dirty sink. You try turning on the faucet,"
				+ " but find both handles stuck. You wonder how the staff wash their"
				+ " hands.");
	}

	
}
