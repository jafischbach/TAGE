package game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import items.Item;
import world.World;

public class Game {

	public static void main(String[] args) {
		startGame();
		World.buildWorld();
		play();
	}

	public static Scanner input = new Scanner(System.in);
	public static HashMap<String, String> roomDescs;
	public static HashMap<String, String> itemDescs;
	private static ArrayList<Room> rooms;

	private static boolean play = true;
	public static Room currentRoom;

	public static void print(String s) {
		System.out.println(s + "\n");
	}

	public static void endGame() {
		play = false;
	}

	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void addRoom(Room r) {
		rooms.add(r);
	}

	private static void startGame() {
		roomDescs = new HashMap<String, String>();
		itemDescs = new HashMap<String, String>();
		rooms = new ArrayList<Room>();
		try {
			Scanner descReader = new Scanner(new File("rooms.dat"));
			while (descReader.hasNextLine()) {
				String label = descReader.nextLine();
				String desc = descReader.nextLine();
				while (descReader.hasNextLine()) {
					String line = descReader.nextLine();
					if (line.equals("#"))
						break;
					desc += line;
				}
				roomDescs.put(label, desc);
			}
			descReader = new Scanner(new File("items.dat"));
			while (descReader.hasNextLine()) {
				String label = descReader.nextLine();
				String desc = descReader.nextLine();
				while (descReader.hasNextLine()) {
					String line = descReader.nextLine();
					if (line.equals("#"))
						break;
					desc += line;
				}
				itemDescs.put(label, desc);
			}
		} catch (FileNotFoundException ex) {
			print("Error: file rooms.dat not found.");
		}
	}

	private static void play() {
//		System.out.print("What is your name? ");
//		Player.name = input.nextLine();
		print(currentRoom.getDesc());
		do {
			System.out.print("What do you want to do? ");
			String command = input.nextLine();
			if (command.equalsIgnoreCase("look"))
				print(currentRoom.getDesc());
			else if (command.equalsIgnoreCase("save"))
				saveGame();
			else if (command.equalsIgnoreCase("load"))
				loadGame();
			else if (command.length() > 1)
				currentRoom.action(command.toLowerCase());
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
						System.out.print("Are you sure you want to quit the game? (y/n) ");
						char response = Character.toLowerCase(input.nextLine().charAt(0));
						if (response == 'y') {
							play = false;
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
					print("You can't go that way!");
				}
			}
		} while (play);
		System.out.println("I guess we're done here. Thanks for playing. Bye!");
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

	public static int getOption(int min, int max) {
		try {
			System.out.print("Enter option (" + min + "-" + max + "): ");
			int option = input.nextInt();
			input.nextLine(); // Consume '\n'
			if (option < min || option > max) {
				System.out.println("Invalid option.");
				return getOption(min, max);
			}
			return option;
		} catch (InputMismatchException ex) {
			System.out.println("Please enter a number.");
			input.nextLine(); // Empty buffer
			return getOption(min, max);
		}
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
			System.out.println("0: New Save");
			int i;
			for (i = 0; i < saves.length; i++)
				System.out.println((i + 1) + ": " + saves[i]);
			System.out.println((i + 1) + ": Cancel");
			option = getOption(0, i + 1);
			if (option == 0)
				fileID = i+1;
			else if (option == i + 1)
				cancel = true;
			else
				fileID = option;
		}
		if (cancel) {
			Game.print("Save cancelled.");
		} else {
			saveGame(fileID);
		}
	}

	public static void saveGame(int fileID) {
		try {
			File saveFile = new File(System.getProperty("user.dir") + "\\saves\\save"+fileID+".sav");
			if(saveFile.exists()) {
				System.out.print("Overwrite save file (y/n)? ");
				char choice = input.nextLine().charAt(0);
				if (choice != 'y') {
					Game.print("Save cancelled.");
					return;
				}
			}
			saveFile.createNewFile();
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(saveFile));
			stream.writeObject(Player.inventory);
			stream.writeObject(rooms);
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
		try {
			File saveFile = new File("save0.sav");
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(saveFile));
			Player.inventory = (HashMap<String, Item>) stream.readObject();
			rooms = (ArrayList<Room>) stream.readObject();
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
