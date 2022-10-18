package game;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map.Entry;
import java.util.Scanner;
import characters.NPC;

import javax.swing.JOptionPane;

import items.Item;
import world.World;

public class Game {

	public static final String TITLE = "Hotel Escape";
	public static final String VERSION = "ALPHA";
	public static final String DEVELOPER = "The Steve Machine";
	public static final boolean CONSOLE = false;

	public static void main(String[] args) {
		startGame();
		World.buildWorld();
		if (CONSOLE)
			playText();
		else
			playGUI();
	}

	public static Scanner input = new Scanner(System.in);
	public static HashMap<String, String> roomDescs;
	public static HashMap<String, String> itemDescs;
	public static HashMap<String, String> npcDescs;
	private static HashMap<String, Room> rooms;
	private static HashMap<String, Integer> flags;
	
	private static int OFFSET = 113;

	private static boolean play = true;
	public static boolean convo = false;
	public static int convoOptions;
	public static NPC character;
	public static Room currentRoom;

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
				String s = JOptionPane.showInputDialog(
						title.equals("Dialog")?GameGUI.command:GameGUI.window, prompt, title,
						JOptionPane.QUESTION_MESSAGE);
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
		println("look <item> - displays description of item");
		println("use <item> - use the item");
		println("take <item> - takes the item and adds it to player's inventory");
		println("move <item> - move the item");
		println("open <item> - open the item");
		println("close <item> - close the item\n");
	}

	public static void npcHelp() {
		println("look <npc> - displays a description of the NPC");
		println("talk <npc> - talk to the NPC");
		println("give <item> to <npc> - give the item to the NPC");
		println("attack <npc> with <item> - attack the NPC with the item\n");
	}

	private static void populateDescs(String fileName, HashMap<String, String> map) throws FileNotFoundException {
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
	}

	private static void loadDataFile(String fileName, HashMap<String, String> map) {
		try {
			File dataFile = new File(fileName);
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(dataFile));
			HashMap<String, String> dataMap = (HashMap<String, String>) stream.readObject();
			unobscure(dataMap, map);
			stream.close();
		} catch (FileNotFoundException ex) {
			// Nothing to do here (data files may not be present)
		} catch (IOException ex) {
			Game.print("Error reading data file "+fileName+ ".");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	private static void unobscure(HashMap<String, String> dataMap, HashMap<String, String> map) {
		for(Entry<String, String> entry : dataMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			String newKey = "";
			String newValue = "";
			for(char c : key.toCharArray())
				newKey += (char) (c-OFFSET);
			for(char c : value.toCharArray())
				newValue += (char) (c-OFFSET);
			map.put(newKey, newValue);
		}
	}
	
	public static void startGame() {
		roomDescs = new HashMap<String, String>();
		itemDescs = new HashMap<String, String>();
		npcDescs = new HashMap<String, String>();
		rooms = new HashMap<String, Room>();
		
			//populateDescs("rooms.txt", roomDescs);
			loadDataFile("rooms.dat", roomDescs);
		
			//populateDescs("items.txt", itemDescs);
			loadDataFile("items.dat", itemDescs);
		
			//populateDescs("npcs.txt", npcDescs);
			loadDataFile("npcs.dat", npcDescs);
			
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
		if (command.equalsIgnoreCase("look"))
			print(currentRoom.getDesc());
		else if (command.equalsIgnoreCase("save"))
			saveGame();
		else if (command.equalsIgnoreCase("load"))
			loadGame();
		else if (command.equalsIgnoreCase("help"))
			help();
		else if (command.equalsIgnoreCase("help item"))
			itemHelp();
		else if (command.equalsIgnoreCase("help npc"))
			npcHelp();
		else if (command.length() > 1)
			try {
				currentRoom.action(command.toLowerCase());
			} catch (InvalidActionException ex) {
				Game.print(ex.getMessage());
			}
		else {
			try {
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
			} catch (InvalidDirectionException ex) {
				print(ex.getMessage());
			} catch (IndexOutOfBoundsException ex) {
				// Nothing to do here.
			}
		}
	}

	public static String[] getSaves() {
		File files = new File(System.getProperty("user.dir") + "\\saves");
		if (files.exists()) {
			String[] list = files.list(new FilenameFilter() {
				public boolean accept(File f, String name) {
					int len = name.length();
					return len < 4 ? false : name.substring(len - 3, len).equals("sav");
				}
			});
			return list;
		} else {
			return null;
		}
	}

	public static int getOption(String s, int min, int max, String title) throws CancelledInputException {
		int option = getInt(s + "\nEnter option (" + min + "-" + max + "): ", title);
		if (option < min || option > max) {
			if (CONSOLE)
				print("Invalid option.");
			else
				JOptionPane.showMessageDialog(GameGUI.window, "Invalid option.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			return getOption(s, min, max, title);
		}
		return option;
	}

	public static void saveGame() {
		int option;
		int fileID = 1;
		boolean cancel = false;
		String[] saves = getSaves();
		if (saves == null) {
			File files = new File(System.getProperty("user.dir") + "\\saves");
			files.mkdir();
			option = 0;
		} else {
			String s = "0: New Save\n";
			int i;
			for (i = 0; i < saves.length; i++)
				s += (i + 1) + ": " + saves[i] + "\n";
			s += (i + 1) + ": Cancel\n";
			try {
				option = getOption(s, 0, i + 1, "Save Game");
				if (option == 0)
					fileID = i + 1;
				else if (option == i + 1)
					cancel = true;
				else
					fileID = option;
			} catch (CancelledInputException ex) {
				cancel = true;
			}
		}
		if (cancel) {
			Game.print("Save cancelled.");
		} else {
			saveGame(fileID);
		}
	}

	public static void saveGame(int fileID) {
		try {
			File saveFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			if (saveFile.exists()) {
				char choice = getYesNo("Overwrite save file (y/n)? ");
				if (choice != 'y') {
					Game.print("Save cancelled.");
					return;
				}
			}
			saveFile.createNewFile();
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(saveFile));
			stream.writeObject(Player.inventory);
			stream.writeObject(rooms);
			stream.writeObject(flags);
			stream.writeObject(currentRoom);
			stream.close();
			Game.print("Game saved.");
		} catch (FileNotFoundException ex) {
			Game.print("Error accessing save file.");
		} catch (IOException ex) {
			Game.print("Error creating save file.");
			ex.printStackTrace();
		}
	}

	public static void loadGame() {
		String[] saves = getSaves();
		if (saves == null)
			Game.print("No saved games.");
		else {
			int i;
			String s = "";
			for (i = 0; i < saves.length; i++)
				s += (i + 1) + ": " + saves[i] + "\n";
			s += (i + 1) + ": Cancel\n";
			try {
				int option = getOption(s, 1, i + 1, "Load Game");
				if (option == i + 1) {
					Game.print("Load cancelled.");
				} else {
					loadGame(option);
				}
			} catch (CancelledInputException ex) {
				print("Load cancelled.");
			}
		}
	}

	public static void loadGame(int fileID) {
		try {
			File loadFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(loadFile));
			Player.inventory = (HashMap<String, Item>) stream.readObject();
			rooms = (HashMap<String, Room>) stream.readObject();
			flags = (HashMap<String, Integer>) stream.readObject();
			currentRoom = (Room) stream.readObject();
			stream.close();
		} catch (FileNotFoundException ex) {
			Game.print("Save file not found.");
		} catch (IOException ex) {
			Game.print("Error loading save file.");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

}
