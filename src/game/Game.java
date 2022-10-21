package game;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.swing.JOptionPane;

import world.World;

public class Game {

	public static final String TITLE = "Hotel Escape";
	public static final String VERSION = "ALPHA";
	public static final String DEVELOPER = "The Steve Machine";
	public static final boolean CONSOLE = false;
	public static final boolean TEXT_DATA_FILES = true;

	public static void main(String[] args) {
		startGame();
		World.buildWorld();
		if (CONSOLE)
			playText();
		else
			playGUI();
	}

	protected static HashMap<String, String> roomDescs;
	protected static HashMap<String, String> itemDescs;
	protected static HashMap<String, String> npcDescs;

	protected static boolean convo = false;
	protected static int convoOptions;
	protected static NPC character;

	protected static Room currentRoom;

	protected static HashMap<String, Room> rooms;
	protected static HashMap<String, Integer> flags;

	private static Scanner input = new Scanner(System.in);

	private static HashMap<String, String> simpleItems;

	private static int OFFSET = 113;

	private static boolean play = true;

	public static void print(String s) {
		if (CONSOLE)
			System.out.println(s + "\n");
		else
			GameGUI.display.append(s + "\n\n");
	}

	public static void println(String s) {
		if (CONSOLE)
			System.out.println(s);
		else
			GameGUI.display.append(s + "\n");
	}

	public static void convoResponseGUI(NPC character, int convoOptions) {
		convo = true;
		Game.character = character;
		Game.convoOptions = convoOptions;
	}

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

	public static int getInt(Object prompt) throws CancelledInputException {
		return getInt(prompt, "Input");
	}

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
				String s = JOptionPane.showInputDialog(title.equals("Dialog") ? GameGUI.command : GameGUI.window,
						prompt, title, JOptionPane.QUESTION_MESSAGE);
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

	public static void endGame() {
		if (CONSOLE)
			play = false;
		else
			gameOverMessage();
	}

	public static void gameOverMessage() {
		print("I guess we're done here. Thanks for playing. Bye!");
		if (CONSOLE) {
			print("<Press ENTER to exit.>");
			input.nextLine();
		} else {
			print("Select New or Load game from File menu or exit.");
			GameGUI.command.setEditable(false);
			GameGUI.saveMenuItem.setEnabled(false);
		}
	}

	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void setCurrentRoom(Room r) {
		currentRoom = r;
	}

	public static void setCurrentRoom(String label) {
		Room r = rooms.get(label);
		if (r != null)
			currentRoom = r;
		else
			throw new InvalidLabelException(label);
	}

	public static void addFlag(String flag) {
		addFlag(flag, 0);
	}

	public static void addFlag(String flag, int value) {
		if (flags == null)
			flags = new HashMap<String, Integer>();
		flags.put(flag, value);
	}

	public static int getFlag(String flag) {
		if (flags == null)
			throw new InvalidLabelException("no such flag: " + flag);
		Integer i = flags.get(flag);
		if (i == null)
			throw new InvalidLabelException("no such flag: " + flag);
		return i;
	}

	public static boolean hasFlag(String flag) {
		if (flags == null)
			return false;
		return flags.containsKey(flag);
	}

	public static void addSimpleItem(String name, String desc) {
		simpleItems.put(name, desc);
	}

	public static String getSimpleItem(String name) {
		return simpleItems.get(name);
	}

	public static void addRoom(String label, Room r) {
		rooms.put(label, r);
	}

	public static Room getRoom(String label) {
		return rooms.get(label);
	}

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

	public static void itemHelp() {
		println("look <item> - display description of item");
		println("use <item> - use the item");
		println("take <item> - take the item and add it to player's inventory");
		println("move <item> - move the item");
		println("open <item> - open the item");
		println("close <item> - close the item\n");
	}

	public static void npcHelp() {
		println("look <npc> - display a description of the NPC");
		println("talk <npc> - talk to the NPC");
		println("give <item> to <npc> - give the item to the NPC");
		println("attack <npc> with <item> - attack the NPC with the item\n");
	}

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
					desc += line;
				}
				map.put(label, desc);
			}
		} catch (FileNotFoundException ex) {
			// Nothing to do here (text file may not be present)
		}
	}

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

	public static void startGame() {
		roomDescs = new HashMap<String, String>();
		itemDescs = new HashMap<String, String>();
		npcDescs = new HashMap<String, String>();
		rooms = new HashMap<String, Room>();
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

	private static void playText() {
		print(currentRoom.getDesc());
		do {
			System.out.print("What do you want to do? ");
			String command = input.nextLine();
			processCommand(command);
		} while (play);
		gameOverMessage();
	}

	private static void playGUI() {
		GameGUI.buildWindow();
		print(currentRoom.getDesc());
	}

	public static void processCommand(String command) {
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
					Player.printInventory();
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
				}
				if (direction != 'x' && direction != 'i')
					print(currentRoom.getDesc());
			}
		} catch (InvalidDirectionException ex) {
			print(ex.getMessage());
		} catch (InvalidActionException ex) {
			Game.print(ex.getMessage());
		} catch (IndexOutOfBoundsException ex) {
			// Nothing to do here.
		}
	}

}
