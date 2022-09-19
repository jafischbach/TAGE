package game;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
			else if (command.equalsIgnoreCase("list"))
				listSaves();
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

	public static void listSaves() {
		File files = new File(System.getProperty("user.dir"));
		for(String name : files.list())
			System.out.println(name);
	}
	
	public static void saveGame() {
		ObjectOutputStream stream;
		try {
			File saveFile = new File("save0.sav");
			saveFile.createNewFile();
			stream = new ObjectOutputStream(new FileOutputStream(saveFile));
			stream.writeObject(Player.inventory);
			stream.writeObject(rooms);
			stream.writeObject(currentRoom);
			stream.close();
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
