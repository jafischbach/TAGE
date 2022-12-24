package world;

import game.*;
import items.*;
import characters.*;

/**
 * Hotel Escape - a demo text adventure developed using FlossTAGE.
 * 
 * This short game consists of 16 rooms, 32 items, and 5 NPCs.
 * 
 * The World class constructs the game world by creating all rooms,
 * most items and three NPCs. Additional items and NPCs are created
 * dynamically as the player progresses through the game.
 * 
 * @version beta (2022)
 *
 */
public class World {

	/**
	 * Game title.
	 */
	public static final String TITLE = "Hotel Escape";
	
	/**
	 * Game version number.
	 */
	public static final String VERSION = "1.0";
	
	/**
	 * Game developer name.
	 */
	public static final String DEVELOPER = "The Steve Machine";
	
	public static final String INTRO_TEXT = 
			"You find yourself in a hotel lobby. You have no idea how you got here, but you"
			+ " do know that the front door is locked. Find a way out of the hotel!";
	
	public static final String GAME_OVER_TEXT = "I guess we're done here. Thanks for playing. Bye!";
	
	public static void buildWorld() {
		Room lobby = new Room("HOTEL_LOBBY", "Lobby");
		Game.setCurrentRoom(lobby);
		
		Room bar = new Room("HOTEL_BAR", "Bar");
		Room lounge = new Room("HOTEL_LOUNGE", "Lounge");
		Room hall = new Room("HOTEL_HALL_1ST", "First Floor Hall");
		Room upperFloor = new Room("HOTEL_HALL_TOP", "Second Floor Hall");
		Room closet = new Room("HOTEL_CLOSET_TOP", "Storage Room");
		Room upperWestHallway = new Room("HOTEL_HALL_WEST", "Second Floor West Hall");
		Room rr = new Room("HOTEL_BAR_RR", "Restroom");
		Room room101 = new Room("HOTEL_ROOM_101", "Room 101");
		Room room201 = new Room("HOTEL_ROOM_201", "Room 201");
		Room room202 = new Room("HOTEL_ROOM_202", "Room 202");
		Room darkness = new Room("HOTEL_DARKNESS", "Darkness");
		Room employeesOnly = new Room("HOTEL_EMPLOYEES_ONLY", "Basement Access");
		Room basement = new Room("HOTEL_BASEMENT", "Basement");
		Room utilities = new Room("HOTEL_UTILITIES", "Utility Room");
		Room exit = new Room("EXIT", "The Great Outdoors");
		
		lobby.addExit(bar, Room.WEST);
		lobby.addExit(lounge, Room.EAST);
		lobby.addExit(hall, Room.NORTH);
		lobby.addExit(upperFloor, Room.UP);
		lobby.addExit(exit, Room.SOUTH);
		lobby.addSimpleItem("exit", "LOBBY_EXIT", "The wooden door to the outside looks sturdy. It is securely"
				+ " locked.");
		lobby.addSimpleItem("front door", "LOBBY_EXIT");
		lobby.addSimpleItem("staircase", "LOBBY_STAIRCASE", "An unremarkable staircase leads up to the second floor.");
		lobby.addSimpleItem("doorway", "DOORWAY", "You are shocked to discover that a doorway leads to another"
				+ " room! Bizarre!");
		lobby.addSimpleItem("doorways", "DOORWAY");

		exit.setLocked(true);
		exit.addExit(lobby, Room.NORTH);
		exit.addNPC(new Cop());
		exit.addSimpleItem("hotel", "HOTEL", "What awful building. Why did you ever set foot in there to begin with?");
		
		bar.addExit(rr, Room.NORTH);
		bar.addExit(lobby, Room.EAST);
		HotelBar hotelBar = new HotelBar("HOTEL_BAR", "bar");
		bar.addItem(hotelBar);
		bar.addItem(hotelBar, "long bar");
		bar.addSimpleItem("door", "BAR_DOOR", "Upon a careful examination of the door, you"
				+ " cleverly deduce that a restroom of some sort exists on the other side.");
		bar.addSimpleItem("closed door", "BAR_DOOR");
		bar.addSimpleItem("doorway", "DOORWAY");
		
		lounge.addExit(lobby, Room.WEST);
		Newspaper paper = new Newspaper("HOTEL_LOUNGE_NEWSPAPER", "newspaper");
		lounge.addItem(paper);
		lounge.addItem(paper, "old newspaper");
		lounge.addNPC(new OldMan());
		lounge.addSimpleItem("couch", "LOUNGE_COUCH", "It's a ratty old couch. This isn't a luxury hotel.");
		lounge.addSimpleItem("armchair", "LOUNGE_ARMCHAIR", "Identical armchairs sit in the room. An old man"
				+ " occupies one of them.");
		lounge.addSimpleItem("armchairs", "LOUNGE_ARMCHAIR");
		lounge.addSimpleItem("doorway", "DOORWAY");

		hall.addExit(lobby, Room.SOUTH);
		hall.addExit(room101, Room.WEST);
		hall.addExit(employeesOnly, Room.EAST);
		hall.addSimpleItem("door", "1ST_HALL_DOOR", "Both doors are unremarkable except for"
				+ " a small plaque on each. The numbers \"101\" are stamped into the western"
				+ " door's plaque, and the words \"Employees Only\" appear on the easter"
				+ " door's plaque.");
		hall.addSimpleItem("doors", "1ST_HALL_DOOR");
		
		room101.setLocked(true);
		room101.addExit(hall, Room.EAST);
		room101.addItem(new Nightstand("nightstand"));
		Drawer drawer = new Drawer("drawer", 101);
		room101.addItem(drawer);
		room101.addItem(drawer, "nightstand drawer");
		room101.addItem(new Closet("closet", 101));
		room101.addItem(new Bed());
		room101.addItem(new Picture());
		
		employeesOnly.setLocked(true);
		employeesOnly.addExit(hall, Room.WEST);
		employeesOnly.addExit(basement, Room.DOWN);
		employeesOnly.addSimpleItem("broom", "EMPLOYEE_ONLY_BROOM", "Yeah. It's a broom. Don't tell me you want to carry"
				+ " that around too? Where are you putting all the crap you've been collecting"
				+ " anyway?");
		employeesOnly.addSimpleItem("door", "EMPLOYEE_ONLY_DOOR", "It's just a door. Not even locked.");
		employeesOnly.addSimpleItem("staircase", "EMPLOYEE_ONLY_STAIRS", "A narrow staircase leads"
				+ " down. One would assume that's the way to the basement. Don't you think?");
		employeesOnly.addSimpleItem("stairs", "EMPLOYEE_ONLY_STAIRS");
		
		basement.addExit(employeesOnly, Room.UP);
		basement.addExit(utilities, Room.NORTH);
		basement.addItem(new Chainsaw());
		basement.addSimpleItem("door", "BASEMENT_DOOR", "A door to the south apparently serves as a staff entrance"
				+ " to the hotel. Why that's found in the basement is a mystery that you will never solve. The other"
				+ " door leads to a utility room.");
		basement.addSimpleItem("doors", "BASEMENT_DOOR");
		basement.addSimpleItem("metal doors", "BASEMENT_DOOR");
		basement.addSimpleItem("dust", "DUST", "Yep. That's dust. Cool.");
		
		utilities.setLocked(true);
		utilities.addExit(basement, Room.SOUTH);
		GasCan can = new GasCan();
		utilities.addItem(can);
		utilities.addItem(can, "can");
		utilities.addSimpleItem("boiler", "UTILITY_BOILER", "The thing looks ancient. You doubt it's been inspected"
				+ " within the last several decades. With your luck, it'll probably explode"
				+ " any second now. You probably don't want to linger here.");
		utilities.addSimpleItem("electric panel", "UTILITY_PANEL", "Looks complicated. Nothing is labelled."
				+ " You briefly flirt with the idea of flipping some of the switches, but, as this"
				+ " would almost certainly leave you in the dark in the basement of a creepy"
				+ " hotel, you quickly reconsider.");
		utilities.addSimpleItem("panel", "UTILITY_PANEL");
		utilities.addSimpleItem("large panel", "UTILITY_PANEL");
		utilities.addSimpleItem("large electric panel", "UTILITY_PANEL");
		utilities.addSimpleItem("laundry machines", "UTILITY_LAUNDRY", "Damn. Those look new. The bartender must"
				+ " really care about clean underwear.");
		utilities.addSimpleItem("machines", "UTILITY_LAUNDRY");
		utilities.addSimpleItem("shelf", "UTILITY_SHELF", "Just a wooden shelf.");
		
		upperFloor.addExit(lobby, Room.DOWN);
		upperFloor.addExit(closet, Room.NORTH);
		upperFloor.addExit(upperWestHallway, Room.WEST);
		Item box = new LargeBox("HOTEL_HALL_LARGE_BOX", "large box");
		upperFloor.addItem(box);
		upperFloor.addItem(box, "box");
		String furnitureDesc = "Various pieces of furniture are stacked"
				+ " hapazardly in the hallway, barring your way. There are too many peices"
				+ " of furniture for you to move yourself. You will not be able to go that way.";
		upperFloor.addSimpleItem("furniture", "2ND_HALL_FURNITURE", furnitureDesc);
		upperFloor.addSimpleItem("stacked furniture", "2ND_HALL_FURNITURE");
		upperFloor.addSimpleItem("door", "2ND_HALL_DOOR", "It's just a plain wooden door.");
		upperFloor.addSimpleItem("locked door", "2ND_HALL_DOOR");
		upperFloor.addSimpleItem("stairs", "2ND_HALL_STAIRS", "Stairs lead back down to the lobby.");
		
		closet.setLocked(true);
		closet.addExit(upperFloor, Room.SOUTH);
		Chest chest = new Chest("HOTEL_CLOSET_TOP_CHEST", "chest");
		closet.addItem(chest);
		closet.addItem(chest, "cool chest");
		closet.addSimpleItem("junk", "CLOSET_JUNK", "Just an assortment of (mostly broken) cleaning supplies"
				+ " of absolutely no use to you.");
		closet.addSimpleItem("cleaning supplies", "CLOSET_SUPPLIES", "It's all junk.");

		upperWestHallway.setLocked(true);
		upperWestHallway.addExit(upperFloor, Room.EAST);
		upperWestHallway.addExit(room201, Room.SOUTH);
		upperWestHallway.addExit(room202, Room.NORTH);
		upperWestHallway.addExit(darkness, Room.WEST);
		upperWestHallway.addSimpleItem("carpet", "WEST_HALL_CARPET", "What a boring carpet. You find yourself starting"
				+ " to fall asleep while gazing at it.");
		upperWestHallway.addSimpleItem("cobwebs", "WEST_HALL_COBWEBS", "You peer up at the ceiling and find numerous"
				+ " webs. Some just look like globs of dust while others look constructed."
				+ " You would prefer not to meet the architects.");
		upperWestHallway.addSimpleItem("bulb", "WEST_HALL_BULB", "The single light bulb barely illuminates this"
				+ " stretch of hallway. You have no desire to venture further.");
		upperWestHallway.addSimpleItem("naked bulb", "WEST_HALL_BULB");
		upperWestHallway.addSimpleItem("door", "WEST_HALL_DOOR", "Two doors lead to hotel rooms. The"
				+ " southern door is marked \"201\" and the northern door is marked \"202\".");
		upperWestHallway.addSimpleItem("doors", "WEST_HALL_DOOR");
		upperWestHallway.addSimpleItem("darkness", "WEST_HALL_DARKNESS", "Spooky. You really shouldn't"
				+ " go that way.");
		upperWestHallway.addSimpleItem("items", "WEST_HALL_ITEMS", "They're broken and torn and"
				+ " of no use to you. Did you not read the room description? Pay attention!");
		
		darkness.addExit(upperWestHallway, Room.EAST);
		
		room201.setLocked(true);
		room201.addExit(upperWestHallway, Room.NORTH);
		room201.addItem(new Closet("closet", 201));
		room201.addItem(new Nightstand("nightstand"));
		drawer = new Drawer("drawer", 201);
		room201.addItem(drawer);
		room201.addItem(drawer, "nightstand drawer");
		room201.addItem(new Bed());
		room201.addItem(new Rug());
		room201.addSimpleItem("dust", "DUST");
		room201.addSimpleItem("floor", "ROOM_201_FLOOR", "It's a dusty floor.");
		room201.addSimpleItem("hardwood floor", "ROOM_201_FLOOR");
		
		room202.setLocked(true);
		room202.addExit(upperWestHallway, Room.SOUTH);
		room202.addItem(new Refrigerator());
		Item hole = new Hole();
		room202.addItem(hole);
		room202.addItem(hole, "mouse hole");
		room202.addNPC(new Wolverine());
		room202.addSimpleItem("dust", "DUST");
		
		rr.addExit(bar, Room.SOUTH);
		rr.addItem(new Toilet("HOTEL_BAR_TOILET", "toilet"));
		rr.addSimpleItem("sink", "RR_SINK", "It's a dirty sink. You try turning on the faucet,"
				+ " but find both handles stuck. You wonder how the staff wash their"
				+ " hands.");
	}

	
}
