package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * This class contains methods to save and load games.
 * 
 * Games are saved in a "saves" folder within the main game folder.
 * This folder will be created if it does not already exist.
 * 
 * Save files are named "saveX.sav" where X is replaced with the number
 * of the saved game.
 */
public class SaveLoad {

	/**
	 * Returns an array containing the names of all save files.
	 * Assumes save files have a ".sav" extension and are stored
	 * in the "saves" folder.
	 * @return names of save files
	 */
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

	/**
	 * Prompts the player to enter an integer option and returns that integer.
	 * Method verifies that integer entered is between min and max values, inclusive.
	 * Assumes min <= max.
	 * @param s prompt to display
	 * @param min minimum available option
	 * @param max maximum available option
	 * @param title title of dialog box (for GUI applications)
	 * @return option entered by player
	 * @throws CancelledInputException if player selects "Cancel"
	 */
	public static int getOption(String s, int min, int max, String title) throws CancelledInputException {
		int option = Game.getInt(s + "\nEnter option (" + min + "-" + max + "): ", title);
		if (option < min || option > max) {
			if (Game.CONSOLE)
				Game.print("Invalid option.");
			else
				JOptionPane.showMessageDialog(GameGUI.window, "Invalid option.", "Error",
						JOptionPane.INFORMATION_MESSAGE);
			return getOption(s, min, max, title);
		}
		return option;
	}

	/**
	 * Saves the current game by creating or updating a save file.
	 */
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

	/**
	 * Saves the current game in a file named "saveX.sav" where
	 * X is the given fileID.
	 * @param fileID number of save file
	 */
	public static void saveGame(int fileID) {
		try {
			File saveFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			if (saveFile.exists()) {
				char choice = Game.getYesNo("Overwrite save file (y/n)? ");
				if (choice != 'y') {
					Game.print("Save cancelled.");
					return;
				}
			}
			saveFile.createNewFile();
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(saveFile));
			stream.writeObject(Game.player);
			stream.writeObject(Game.visitedRooms);
			stream.writeObject(Game.flags);
			stream.writeObject(Game.currentRoom);
			stream.close();
			Game.print("Game saved.");
		} catch (FileNotFoundException ex) {
			Game.print("Error accessing save file.");
		} catch (IOException ex) {
			Game.print("Error creating save file.");
			ex.printStackTrace();
		}
	}

	/**
	 * Loads a game from a save file.
	 */
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
				Game.print("Load cancelled.");
			}
		}
	}

	/**
	 * Loads the save filed named "saveX.sav" where X is the
	 * given fileID.
	 * @param fileID number of save file
	 */
	@SuppressWarnings("unchecked")
	public static void loadGame(int fileID) {
		try {
			File loadFile = new File(System.getProperty("user.dir") + "\\saves\\save" + fileID + ".sav");
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(loadFile));
			Game.player = (Player) stream.readObject();
			Game.visitedRooms = (ArrayList<Room>) stream.readObject();
			Game.flags = (HashMap<String, Integer>) stream.readObject();
			Game.currentRoom = (Room) stream.readObject();
			updateRooms();
			Game.printRoom();
			if (!Game.CONSOLE) 
				GameGUI.display.setText("");
			stream.close();
		} catch (FileNotFoundException ex) {
			Game.print("Save file not found.");
		} catch (IOException ex) {
			Game.print("Error loading save file.");
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}
	
	// Updates the visited rooms in Game's global list of rooms.
	// This method is called when loading a save game.
	private static void updateRooms() {
		for(Room r : Game.visitedRooms)
			Game.rooms.put(r.getRoomLabel(), r);
	}
	
}
