package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

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
	
	private static void startGame() {
		roomDescs = new HashMap<String, String>();
		itemDescs = new HashMap<String, String>();
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
		System.out.print("What is your name? ");
		Player.name = input.nextLine();
		print(currentRoom.getDesc());
		do {
			System.out.print("What do you want to do? ");
			String command = input.nextLine();
			if (command.equalsIgnoreCase("look"))
				print(currentRoom.getDesc());
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
					if (direction != 'x' && direction != 'i') print(currentRoom.getDesc());
				} catch (InvalidDirectionException ex) {
					print("You can't go that way!");
				}
			}
		} while (play);
		System.out.println("I guess we're done here. Thanks for playing. Bye!");
	}

}
