package game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JOptionPane;

import world.World;

/**
 * FlossTAGE (Text Adventure Game Engine) - A game system 
 * for the development of simple text adventure games in Java.
 * 
 * This class contains global game data structures and numerous
 * utility methods (particularly for I/O).
 * 
 * @author J Adam Fischbach
 * @version beta (2022)
 */
public class Game {
	
	/**
	 * Is the game a console application? 
	 * If false, the game will compile as a GUI application.
	 */
	public static final boolean CONSOLE = false;
	
	/**
	 * Import data from raw text files? 
	 * If false, the game will import data from obscured data files.
	 */
	public static final boolean TEXT_DATA_FILES = true;
	
	/**
	 * Launches the game!
	 */
	public static void main(String[] args) {
		startGame();
		World.buildWorld();
		if (CONSOLE)
			playText();
		else
			playGUI();
	}

	public static Player player;
	
	// Data structures containing descriptions of rooms, items, and NPCs.
	protected static HashMap<String, String> roomDescs;
	protected static HashMap<String, String> itemDescs;
	protected static HashMap<String, String> npcDescs;

	// These fields are used by GameGUI to manage user responses to NPC
	// conversations.
	protected static boolean convo = false;
	protected static int convoOptions;
	protected static NPC character;

	protected static Room currentRoom;

	protected static HashMap<String, Room> rooms; // All room objects
	protected static ArrayList<Room> visitedRooms; // Rooms visited by player
	protected static HashMap<String, Integer> flags; // Game state flags
	protected static HashMap<String, HashMap<String, Item>> roomItems;
	protected static HashMap<String, HashMap<String, NPC>> roomNPCS;

	// Scanner for user input when game is a console application.
	private static Scanner input = new Scanner(System.in);

	// Descriptions of non-interactive items.
	private static HashMap<String, String> simpleItems;

	// Shift cipher offset used to obscure data files.
	private static int OFFSET = 113;

	// Controls game loop for console applications.
	private static boolean play = true;

	/**
	 * Standard game output method. Prints the given message.
	 * 
	 * @param s message to print
	 */
	public static void print(String s) {
		if (CONSOLE)
			System.out.println(s + "\n");
		else
			GameGUI.display.append(s + "\n\n");
	}

	public static void printRoom() {
		if (!CONSOLE)
			GameGUI.displayRoom(currentRoom);
	}
	
	/**
	 * Same as print() but does not add a blank line after
	 * printing the given message.
	 * 
	 * For typical game output, use print().
	 * 
	 * @param s message to print
	 */
	public static void println(String s) {
		if (CONSOLE)
			System.out.println(s);
		else
			GameGUI.display.append(s + "\n");
	}

	/**
	 * Alerts GameGUI that the next command entered by the user
	 * is the response to a NPC dialog prompt.
	 * 
	 * @param character NPC engaged in dialog with the player
	 * @param convoOptions number of potential player responses
	 */
	public static void convoResponseGUI(NPC character, int convoOptions) {
		convo = true;
		Game.character = character;
		Game.convoOptions = convoOptions;
	}

	/**
	 * Prompts the player for a Yes/No response.
	 * 
	 * For GUI applications, this will open a dialog box.
	 * 
	 * For console applications, this method does not verify
	 * user input.
	 * 
	 * @param prompt prompt to display to player
	 * @return player's response
	 */
	public static char getYesNo(String prompt) {
		if (CONSOLE) {
			System.out.print(prompt);
			return Character.toLowerCase(input.nextLine().charAt(0));
		} else {
			int option = JOptionPane.showConfirmDialog(GameGUI.window, prompt, "Decision time!",
					JOptionPane.YES_NO_OPTION);
			return option == JOptionPane.YES_OPTION ? 'y' : 'n';
		}
	}

	/**
	 * Prompts the player to enter a number.
	 * 
	 * Will ensure player enters a number (unless cancelled).
	 * 
	 * @param prompt prompt to display to player
	 * @return number entered by player
	 * @throws CancelledInputException if player clicks Cancel (GUI only)
	 */
	public static int getInt(Object prompt) throws CancelledInputException {
		return getInt(prompt, "Input");
	}

	/**
	 * Prompts the player to enter a number.
	 * 
	 * Will ensure player enters a number (unless cancelled).
	 * 
	 * @param prompt prompt to display to player
	 * @param title title for dialog box (GUI only)
	 * @return number entered by player
	 * @throws CancelledInputException if player clicks Cancel (GUI only)
	 */
	public static int getInt(Object prompt, String title) throws CancelledInputException {
		if (CONSOLE) {
			try {
				System.out.print(prompt);
				int x = input.nextInt();
				input.nextLine();
				return x;
			} catch (InputMismatchException ex) {
				Game.print("You must enter a number.");
				return getInt(prompt, title);
			}
		} else {
			try {
				String s = JOptionPane.showInputDialog(title.equals("Dialog") ? 
						   GameGUI.command : 
						   GameGUI.window, prompt, title, JOptionPane.QUESTION_MESSAGE);
				if (s == null)
					throw new CancelledInputException();
				return Integer.parseInt(s);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(GameGUI.window, "You must enter a number.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
				return getInt(prompt, title);
			}
		}
	}

	/**
	 * Ends the game.
	 */
	public static void endGame() {
		if (CONSOLE)
			play = false;
		else
			gameOverMessage();
	}

	// Displays the game over message when game ends.
	private static void gameOverMessage() {
		print(World.GAME_OVER_TEXT); 
		if (CONSOLE) {
			print("<Press ENTER to exit.>");
			input.nextLine();
		} else {
			print("Select New or Load game from File menu or exit.");
			GameGUI.command.setEditable(false);
			GameGUI.saveMenuItem.setEnabled(false);
		}
	}

	/**
	 * Returns the room the player is currently in.
	 * 
	 * @return current room
	 */
	public static Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Sets the current room to the given room.
	 * 
	 * This essentially "moves" the player to the
	 * given room.
	 * 
	 * @param r room player will move to
	 */
	public static void setCurrentRoom(Room r) {
		currentRoom = r;
	}

	/**
	 * Sets the current room to the room with the
	 * given label.
	 * 
	 * This essentially "moves" the player to the
	 * given room.
	 * 
	 * @param label label of room player will move to
	 */
	public static void setCurrentRoom(String label) {
		Room r = rooms.get(label);
		if (r != null)
			currentRoom = r;
		else
			throw new InvalidLabelException(label);
	}

	/**
	 * Adds the given status flag to the list of global game flags.
	 * 
	 * This can be used to indicate that some event has
	 * occurred in the game.
	 * 
	 * @param flag name of flag
	 */
	public static void addFlag(String flag) {
		addFlag(flag, 0);
	}

	/**
	 * Adds or updates the given status flag with the given value 
	 * to the list of global game flags.
	 * 
	 * If flag has already been added, this will update the flag's
	 * value.
	 * 
	 * This can be used to add or update an attribute for the
	 * player, for example.
	 * 
	 * @param flag name of flag/attribute
	 * @param value value of attribute
	 */
	public static void addFlag(String flag, int value) {
		if (flags == null)
			flags = new HashMap<String, Integer>();
		flags.put(flag, value);
	}

	/**
	 * Returns the specified flag's (or attribute's) value.
	 * 
	 * @param flag name of flag/attribute
	 * @return flag's value
	 */
	public static int getFlag(String flag) {
		if (flags == null)
			throw new InvalidLabelException("no such flag: " + flag);
		Integer i = flags.get(flag);
		if (i == null)
			throw new InvalidLabelException("no such flag: " + flag);
		return i;
	}

	/**
	 * Returns true if the specified flag exists.
	 * 
	 * @param flag name of flag
	 * @return true if flag exists
	 */
	public static boolean hasFlag(String flag) {
		if (flags == null)
			return false;
		return flags.containsKey(flag);
	}

	/**
	 * Adds the given simple item and item description to the
	 * global list of simple items.
	 * 
	 * A "simple item" is an item that the player can look at but
	 * not otherwise interact with.
	 * 
	 * @param name name of simple item
	 * @param desc item description
	 */
	public static void addSimpleItem(String name, String desc) {
		simpleItems.put(name, desc);
	}

	/**
	 * Returns the description of the specified simple item.
	 * 
	 * @param name name of simple item
	 * @return simple item's description
	 */
	protected static String getSimpleItem(String name) {
		return simpleItems.get(name);
	}

	/**
	 * Add the given room with the given label to the global
	 * list of rooms.
	 * 
	 * @param label
	 * @param r
	 */
	protected static void addRoom(String label, Room r) {
		rooms.put(label, r);
	}

	/**
	 * Returns the room with the specified label.
	 * 
	 * @param label label of room
	 * @return room object
	 */
	public static Room getRoom(String label) {
		return rooms.get(label);
	}

	/**
	 * Displays the list of general player commands.
	 */
	public static void help() {
		println("look - display desription of current room");
		println("n - move north");
		println("s - move south");
		println("e - move east");
		println("w - move west");
		println("u - move up");
		println("d - move down");
		println("i - display player's inventory");
		println("x - exit game");
		println("save - save current game");
		println("load - load previously saved game");
		println("help item - displays items help");
		println("help npc - displays NPC help\n");
	}

	/**
	 * Displays the list of commands player can use with items.
	 */
	public static void itemHelp() {
		println("look <item> - display description of item");
		println("use <item> - use the item");
		println("take <item> - take the item and add it to player's inventory");
		println("move <item> - move the item");
		println("open <item> - open the item");
		println("close <item> - close the item");
		println("equip <item> - equips the item as a weapon\n");
	}

	/**
	 * Displays the list of commands player can use with NPCs.
	 */
	public static void npcHelp() {
		println("look <npc> - display a description of the NPC");
		println("talk <npc> - talk to the NPC");
		println("give <item> to <npc> - give the item to the NPC");
		println("attack <npc> with <item> - attack the NPC with the item\n");
	}

	// Populates the specified game data structure with data from the specified 
	// game text file. Used if application reads data as raw text.
	private static void populateDescs(String fileName, HashMap<String, String> map) {
		try {
			Scanner descReader = new Scanner(new File(fileName));
			while (descReader.hasNextLine()) {
				String label = descReader.nextLine();
				String desc = descReader.nextLine();
				while (descReader.hasNextLine()) {
					String line = descReader.nextLine();
					if (line.equals("#"))
						break;
					if (line.length() == 0)
						desc += "\n\n";
					else
						desc += line;
				}
				map.put(label, desc);
			}
			descReader.close();
		} catch (FileNotFoundException ex) {
			// Nothing to do here (text file may not be present)
		}
	}

	// Populates the specified game data structure with data from the specified
	// game data file. Used if application reads data from obscured data file.
	@SuppressWarnings("unchecked")
	private static void loadDataFile(String fileName, HashMap<String, String> map) {
		try {
			File dataFile = new File(fileName);
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(dataFile));
			HashMap<String, String> dataMap = (HashMap<String, String>) stream.readObject();
			unobscure(dataMap, map);
			stream.close();
		} catch (FileNotFoundException ex) {
			// Nothing to do here (data file may not be present)
		} catch (IOException ex) {
			Game.print("Error reading data file " + fileName + ".");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	// Used by loadDataFile() to decrypt the data read from a data file.
	private static void unobscure(HashMap<String, String> dataMap, HashMap<String, String> map) {
		for (Entry<String, String> entry : dataMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String newKey = "";
			String newValue = "";
			for (char c : key.toCharArray())
				newKey += (char) (c - OFFSET);
			for (char c : value.toCharArray())
				newValue += (char) (c - OFFSET);
			map.put(newKey, newValue);
		}
	}

	/**
	 * Starts the game by initializing and populating core game
	 * data structures.
	 */
	protected static void startGame() {
		roomDescs = new HashMap<String, String>();
		itemDescs = new HashMap<String, String>();
		npcDescs = new HashMap<String, String>();
		rooms = new HashMap<String, Room>();
		visitedRooms = new ArrayList<Room>();
		roomItems = new HashMap<String, HashMap<String, Item>>();
		roomNPCS = new HashMap<String, HashMap<String, NPC>>();
		simpleItems = new HashMap<String, String>();

		if (TEXT_DATA_FILES) {
			populateDescs("rooms.txt", roomDescs);
			populateDescs("items.txt", itemDescs);
			populateDescs("npcs.txt", npcDescs);
		} else {
			loadDataFile("rooms.dat", roomDescs);
			loadDataFile("items.dat", itemDescs);
			loadDataFile("npcs.dat", npcDescs);
		}
	}

	// Main game loop for console applications. 
	private static void playText() {
		player = new Player();
		print(World.INTRO_TEXT);
		print(currentRoom.getDesc());
		do {
			System.out.print("What do you want to do? ");
			String command = input.nextLine();
			processCommand(command);
		} while (play);
		gameOverMessage();
	}

	// Initializes the GUI for GUI applications.
	private static void playGUI() {
		player = new Player();
		GameGUI.buildWindow();
		printRoom();
		print(World.INTRO_TEXT);
	}

	/**
	 * Processes the given command entered by the player.
	 * 
	 * This method handles directional commands as well as
	 * i (inventory) and x (exit game). Other commands are
	 * set to Parser for processing.
	 * 
	 * @param command command entered by player
	 */
	protected static void processCommand(String command) {
		try {
			command = command.trim();
			if (command.length() > 1)
				Parser.processCommand(currentRoom, command.toLowerCase());
			else {
				char direction = Character.toLowerCase(command.charAt(0));
				switch (direction) {
				case 'e':
					currentRoom = currentRoom.goEast();
					break;
				case 'w':
					currentRoom = currentRoom.goWest();
					break;
				case 'n':
					currentRoom = currentRoom.goNorth();
					break;
				case 's':
					currentRoom = currentRoom.goSouth();
					break;
				case 'u':
					currentRoom = currentRoom.goUp();
					break;
				case 'd':
					currentRoom = currentRoom.goDown();
					break;
				case 'i':
					player.printInventory();
					break;
				case 'x':
					char response = getYesNo("Are you sure you want to quit the game? (y/n) ");
					if (response == 'y') {
						endGame();
					} else {
						print("Whew. You scared me for a moment there.");
					}
					break;
				default:
					print("Invalid direction.");
					return;
				}
				if (direction != 'x' && direction != 'i')
					if (CONSOLE)
						print(currentRoom.getDesc());
					else
						printRoom();
			}
		} catch (InvalidDirectionException ex) {
			print(ex.getMessage());
		} catch (InvalidActionException ex) {
			Game.print(ex.getMessage());
		} catch (IndexOutOfBoundsException ex) {
			Game.print("Invalid command.");
		}
	}

}
